package Knightly.ProductService.repository;

import Knightly.ProductService.repository.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
}
