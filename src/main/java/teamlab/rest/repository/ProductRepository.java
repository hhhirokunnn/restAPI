package teamlab.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import teamlab.rest.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	public Optional<Product> findByTitleContaining(String title);

}
