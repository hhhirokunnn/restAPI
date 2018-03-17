package teamlab.rest.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import teamlab.rest.dto.ProductDto;
import teamlab.rest.entity.Product;
import teamlab.rest.form.ProductForm;
import teamlab.rest.repository.ProductRepository;
import teamlab.rest.service.ProductService;
import teamlab.rest.util.ApplicationUtil;

/**
 * 商品のAPIcontroller
 * @author mukaihiroto
 *
 */
@RestController
@RequestMapping(value="/api/v1", produces=MediaType.APPLICATION_JSON_VALUE)
public class ProductApiController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductService productService;
    
    @ModelAttribute
    private ProductForm setUpForm() {
        return new ProductForm();
    }

    /**
     * 全商品を表示する
     * @return
     */
    @ResponseBody
    @GetMapping(path="/products")
    public ResponseEntity<?> findAll(){
        List<Product> products = productService.findAll();
        if(products.isEmpty())
            return new ResponseEntity<>(products,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    
    /**
     * 指定されたidの商品を表示する
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(path="/products/{id}")
    public ResponseEntity<?> findDetail(@PathVariable("id") int id){
        Optional<Product> product = productService.findById(id);
        if(product.isPresent())
            return new ResponseEntity<>(product,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } 
    
    /**
     * タイトルで曖昧検索した商品を表示する
     * @param title
     * @return
     */
    @ResponseBody
    @GetMapping(path="/products/search")
    public ResponseEntity<?> findByTitle(@RequestParam("title") String title){
        Optional<Product> product = productService.findByTitleContaining(title);
        if(product.isPresent())
            return new ResponseEntity<>(product,HttpStatus.OK);
        return new ResponseEntity<>(product,HttpStatus.NOT_FOUND);
    }
    
    /**
     * 商品を登録する
     * @param form
     * @param result
     * @return
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    @ResponseBody
    @PostMapping(path="/products")
    public ResponseEntity < ? > create(@Validated ProductForm form, BindingResult result) throws IllegalArgumentException, IllegalAccessException{
        if (result.hasErrors() || StringUtils.isEmpty(form.getTitle()) || productService.formValidation(form))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Product product = productService.save(productService.convertFormToDto(form));
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }
    
    /**
     * 商品を削除する
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping(path="/products/{id}")
    public ResponseEntity < ? > delete(@PathVariable("id") int id){
        Optional<Product> existProduct = productRepository.findById(id);
        if(!existProduct.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ApplicationUtil.deleteFile(existProduct.get().getPicPath());
        productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 商品を更新する
     * @param form
     * @param result
     * @param id
     * @return
     */
    @ResponseBody
    @PutMapping(path="/products/{id}")
    public ResponseEntity < ? > update(@Validated ProductForm form, BindingResult result, @PathVariable("id") int id) {
        if (result.hasErrors() || productService.formValidation(form))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Optional<Product> existProduct = productService.findById(id);
        if(!existProduct.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ProductDto dto = productService.convertFormToDto(form,existProduct);
        dto.setId(id);
        productService.save(dto);
        Optional<Product> updatedProduct = productService.findById(id);
        return new ResponseEntity<>(updatedProduct,HttpStatus.CREATED);
    }
}
