package Knightly.ProductService.service.impl;

import Knightly.ProductService.repository.jpa.Component;
import Knightly.ProductService.repository.jpa.Product;
import Knightly.ProductService.repository.jpa.User;
import Knightly.ProductService.repository.ComponentRepository;
import Knightly.ProductService.repository.ProductRepository;
import Knightly.ProductService.repository.UserRepository;
import Knightly.ProductService.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Component> getComponents() {
        return this.componentRepository.findAll();
    }

    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    public User getUser(long userID) {
        return this.userRepository.findById(userID);
    }

    public Component getComponentByID(Long id) {
        return this.componentRepository.getById(id);
    }

    public void createProduct(Product product) {
        this.productRepository.save(product);
    }

    public void emptyShoppingCart(Long userID) {
        User emptyCartUser = userRepository.findById(userID.longValue());
        emptyCartUser.setProducts(Collections.emptyList());
        this.userRepository.save(emptyCartUser);
    }

    public void  updateShoppingCart(long userID, List<Product> newShoppingCart) {
        User updatedCartUser = userRepository.findById(userID);
        updatedCartUser.setProducts(newShoppingCart);
        this.userRepository.save(updatedCartUser);
    }
}
