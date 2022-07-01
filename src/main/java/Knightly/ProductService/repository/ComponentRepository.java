package Knightly.ProductService.repository;


import Knightly.ProductService.repository.jpa.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {
}
