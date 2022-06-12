package Knightly.ProductService.repository;

import Knightly.ProductService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
