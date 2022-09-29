package Knightly.ProductService.service;

import Knightly.ProductService.microservices.CurrencyConversionGetter;
import Knightly.ProductService.microservices.PriceGetter;
import Knightly.ProductService.repository.jpa.Component;
import Knightly.ProductService.repository.jpa.Product;
import Knightly.ProductService.service.impl.DTOServiceImpl;
import Knightly.ProductService.service.impl.DataServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DTOServiceTest {

    @InjectMocks
    private DTOServiceImpl dtoService;
    @Mock
    private DataServiceImpl dataService;
    @Mock
    private PriceGetter priceGetter;
    @Mock
    private CurrencyConversionGetter currencyConversionGetter;

    @Test
    public void getAllComponentDTOs() {
        dtoService.getAllComponentDTOs();

        Mockito.verify(dataService, Mockito.times(1)).getComponents();
    }

    @Test
    public void getAllProductDTOs() {
        dtoService.getAllProductDTOs();

        Mockito.verify(dataService, Mockito.times(1)).getProducts();
    }


    @Test
    public void createProduct() {
        Product product = getProduct();

        dtoService.createProduct(product);

        Mockito.verify(dataService, Mockito.times(1)).createProduct(product);

    }

    @Test
    public void createProductFromIDs() {
        dtoService.createProductFromIDs(List.of(2L,4L,10L), "Obama");

        Mockito.verify(dataService, Mockito.times(1)).createProduct(any());
    }

    private Product getProduct() {
        return new Product(20L, "bleh",
                List.of(new Component(
                        8L
                        , "boy"
                        , 1
                        , "next"
                        , 3
                        , 4
                        , "door"
                        , 3
                        , 3
                        , 4
                ), new Component(
                15L
                , "some"
                , 1
                , "body"
                , 3
                , 4
                , "once"
                , 3
                , 3
                , 4
        )));

    }

    private List<Component> getComponentList() {
        return List.of(new Component(
                10L
                , "floomp"
                , 1
                , "nein"
                , 3
                , 4
                , "ead"
                , 3
                , 3
                , 4
        ));
    }
}
