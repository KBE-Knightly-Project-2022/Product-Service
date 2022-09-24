package Knightly.ProductService.service;

import Knightly.ProductService.repository.ComponentRepository;
import Knightly.ProductService.repository.ProductRepository;
import Knightly.ProductService.repository.UserRepository;
import Knightly.ProductService.repository.jpa.Product;
import Knightly.ProductService.server.dto.ProductRequest;
import Knightly.ProductService.service.impl.DataServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class DataServiceTest {

    @InjectMocks
    DataServiceImpl dataService;

    @Mock
    private ComponentRepository componentRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Product product;

    @Test
    public void getComponents() {
        dataService.getComponents();

        Mockito.verify(componentRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getProducts() {
        dataService.getProducts();

        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getUser() {
        dataService.getUser(1L);

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void createProduct() {
        dataService.createProduct(product);

        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }

    @Test
    public void getComponentById() {
        dataService.getComponentByID(1L);

        Mockito.verify(componentRepository, Mockito.times(1)).getById(1L);
    }

}
