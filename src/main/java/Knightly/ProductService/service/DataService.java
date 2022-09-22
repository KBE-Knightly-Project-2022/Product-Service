package Knightly.ProductService.service;

import Knightly.ProductService.repository.jpa.Component;
import Knightly.ProductService.repository.jpa.Product;
import Knightly.ProductService.repository.jpa.User;

import java.util.List;

public interface DataService {

    List<Component> getComponents();
    List<Product> getProducts();
    User getUser(long userID);
    Component getComponentByID(Long id);
    void createProduct(Product product);
    void emptyShoppingCart(Long userId);
    void updateShoppingCart(long userID, List<Product> newShoppingCart);
}
