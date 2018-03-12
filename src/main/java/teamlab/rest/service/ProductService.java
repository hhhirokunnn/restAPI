package teamlab.rest.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import teamlab.rest.entity.Product;
import teamlab.rest.form.ProductForm;
import teamlab.rest.repository.ProductRepository;

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
	
	public void save(Product product){
		productRepository.save(product);
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
			return false;
		}
		String description = form.getDescription();
		if(!StringUtils.isEmpty(description) && description.codePointCount(0, description.length()) > 500){
			return false;
		}
		return true;
	}
}
