package teamlab.rest.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ProductApiController {

	@GetMapping(path="/products")
    @ResponseBody
    public String findAll(){
		return "hello world !";
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
