package Knightly.ProductService.interfaces;

public interface GateWayInterface {
    void getComponents();

    void getComponent(long componentID);

    void getProducts();

    void getProduct();

    void getUser(long userID);
}
