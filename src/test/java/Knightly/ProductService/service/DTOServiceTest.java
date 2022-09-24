package Knightly.ProductService.service;

import Knightly.ProductService.enums.Currency;
import Knightly.ProductService.microservices.CurrencyConversionGetter;
import Knightly.ProductService.microservices.PriceGetter;
import Knightly.ProductService.repository.jpa.Component;
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
    public void getComponents() {
        Mockito.when(dataService.getComponents()).thenReturn(getComponentList());
        dtoService.getAllComponentDTOs();

        Mockito.verify(dataService, Mockito.times(1)).getComponents();
    }

    @Test
    public void getProducts() {
        dtoService.getAllProductDTOs();

        Mockito.verify(dataService, Mockito.times(1)).getProducts();
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
