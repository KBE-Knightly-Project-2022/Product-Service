package Knightly.ProductService.repository;


import Knightly.ProductService.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {
}
