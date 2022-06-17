package Knightly.ProductService.service;

import Knightly.ProductService.model.Component;
import Knightly.ProductService.model.Product;
import Knightly.ProductService.model.User;
import Knightly.ProductService.repository.ComponentRepository;
import Knightly.ProductService.repository.ProductRepository;
import Knightly.ProductService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@EnableCaching
@Service
public class DataService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "Component")
    public List<Component> getComponents() {
        return this.componentRepository.findAll();
    }

    @Cacheable(value = "Product")
    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    @Cacheable(value = "User", key = "#id")
    public User getUser(long userID) {
        return this.userRepository.findById(userID);
    }

    public void emptyShoppingCart(long userID) {
        User emptyCartUser = userRepository.findById(userID);
        emptyCartUser.setProducts(Collections.emptyList());
        this.userRepository.save(emptyCartUser);
    }

    public void  updateShoppingCart(long userID, List<Product> newShoppingCart) {
        User updatedCartUser = userRepository.findById(userID);
        updatedCartUser.setProducts(newShoppingCart);
        this.userRepository.save(updatedCartUser);
    }
}
