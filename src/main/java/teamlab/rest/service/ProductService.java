package teamlab.rest.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import teamlab.rest.dto.ProductDto;
import teamlab.rest.entity.Product;
import teamlab.rest.form.ProductForm;
import teamlab.rest.repository.ProductRepository;
import teamlab.rest.util.ApplicationUtil;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }
    
    public Optional<Product> findById(int id){
        return productRepository.findById(id);
    }
    
    public Optional<Product> findByTitleContaining(String title){
        return productRepository.findByTitleContaining(title);
    }
    
    public Product save(ProductDto dto){
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        return productRepository.save(product);
    }
    
    /**
     * バリデーション
     * タイトルは100文字以内
     * 説明文は500文字以内
     * @param form
     */
    public boolean formValidation(ProductForm form){
        String title = form.getTitle();
        if(!StringUtils.isEmpty(title) && title.codePointCount(0, title.length()) > 100){
            return true;
        }
        String description = form.getDescription();
        if(!StringUtils.isEmpty(description) && description.codePointCount(0, description.length()) > 500){
            return true;
        }
        return false;
    }
    
    /**
     * 登録用formをdtoに変換する
     * @param form
     * @return
     */
    public ProductDto convertFormToDto(ProductForm form){
        MultipartFile uploadFile = form.getUploadFile();
        String uploadPath = ApplicationUtil.uploadFile(uploadFile);
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(form, dto);
        dto.setPicPath(uploadPath.equals("") ? null : uploadPath);
        //特殊文字エスケープ
        dto.setTitle(ApplicationUtil.translateEscapeSequence(form.getTitle()));
        dto.setDescription((ApplicationUtil.translateEscapeSequence(form.getDescription())));
        return dto;
    }
    
    /**
     * 更新用formをdtoに変換する
     * @param form
     * @param optProduct
     * @return
     */
    public ProductDto convertFormToDto(ProductForm form, Optional<Product> optProduct){
        Product product = optProduct.get();
        ProductDto dto = new ProductDto();
        String title = StringUtils.isEmpty(form.getTitle()) ? product.getTitle() : form.getTitle();
        dto.setTitle(ApplicationUtil.translateEscapeSequence(title));
        String description = StringUtils.isEmpty(form.getDescription()) ? product.getDescription() : form.getDescription();
        dto.setDescription(ApplicationUtil.translateEscapeSequence(description));
        dto.setPrice(form.getPrice() == null ? product.getPrice() : form.getPrice());
        MultipartFile uploadPic = form.getUploadFile();
        if(uploadPic != null){
            String existingPic = product.getPicPath();
            if(!StringUtils.isEmpty(existingPic))
                ApplicationUtil.deleteFile(existingPic);
            dto.setPicPath(ApplicationUtil.uploadFile(uploadPic));
        }
        return dto;
    }
}
