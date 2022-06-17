package Knightly.ProductService.interfaces;

public interface GateWayInterface {
    void getComponents();

    void getComponent(long componentID);

    void getProducts();

    void getProduct(long productID);

    void getUser(long userID);

    void emptyShoppingCart(long userID);
}
