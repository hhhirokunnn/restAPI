package teamlab.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import teamlab.rest.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
