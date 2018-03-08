package teamlab.rest.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import teamlab.rest.entity.Product;
import teamlab.rest.repository.ProductRepository;

@RestController
@RequestMapping("/api/v1")
public class ProductApiController {
	
	@Autowired
	ProductRepository productRepository;

	@GetMapping(path="/products")
    @ResponseBody
    public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	@GetMapping(path="/products/{id}")
    @ResponseBody
    public String findDetail(@PathVariable("id") Integer id){
		return "hello world "+id;
	}
	
	@GetMapping(path="/products/search")
    @ResponseBody
    public String searchByKeyword(@RequestParam("title") String title){
		return "hello world "+title;
	}
	
	
}
