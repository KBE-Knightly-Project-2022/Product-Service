package Knightly.ProductService.repository;

import Knightly.ProductService.repository.jpa.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
