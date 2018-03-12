package teamlab.rest.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamlab.rest.entity.Product;
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
	
}
