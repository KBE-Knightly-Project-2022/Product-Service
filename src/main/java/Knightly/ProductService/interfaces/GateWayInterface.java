package Knightly.ProductService.interfaces;

import Knightly.ProductService.model.Product;

public interface GateWayInterface {
    void getComponents();

    void getComponent(long componentID);

    void getProducts();

    void getProduct(long productID);

    void getUser(long userID);

    void emptyShoppingCart(long userID);

    void createProduct(Product product);
}
