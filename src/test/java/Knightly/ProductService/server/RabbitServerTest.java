package Knightly.ProductService.server;

import Knightly.ProductService.enums.Currency;
import Knightly.ProductService.enums.RequestType;
import Knightly.ProductService.repository.jpa.Component;
import Knightly.ProductService.server.dto.ComponentDTO;
import Knightly.ProductService.server.dto.ProductRequest;
import Knightly.ProductService.service.DTOService;
import Knightly.ProductService.service.impl.DTOServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RabbitServerTest {

    @InjectMocks
    RabbitServer rabbitServer;
    @Mock
    DTOServiceImpl dtoService;

    @Test
    public void handleGetComponentsRequestBronze() {
        ProductRequest productRequest = new ProductRequest(RequestType.getComponents, Currency.bronze);
        String productRequestJson = convertProductRequestTJson(productRequest);

        rabbitServer.handleProductRequest(productRequestJson);

        Mockito.verify(dtoService, Mockito.times(1)).getAllComponentDTOs(Currency.bronze);
    }

    @Test
    public void handleGetComponentsRequestGold() {
        ProductRequest productRequest = new ProductRequest(RequestType.getComponents, Currency.gold);
        String productRequestJson = convertProductRequestTJson(productRequest);

        rabbitServer.handleProductRequest(productRequestJson);

        Mockito.verify(dtoService, Mockito.times(1)).getAllComponentDTOs(Currency.gold);
    }

    @Test
    public void handleGetProductsRequestSilver() {
        ProductRequest productRequest = new ProductRequest(RequestType.getProducts, Currency.silver);
        String productRequestJson = convertProductRequestTJson(productRequest);

        rabbitServer.handleProductRequest(productRequestJson);

        Mockito.verify(dtoService, Mockito.times(1)).getAllProductDTOs(Currency.silver);
    }

    @Test
    public void handleGetProductRequestCow() {
        ProductRequest productRequest = new ProductRequest(RequestType.getProducts, Currency.cow);
        String productRequestJson = convertProductRequestTJson(productRequest);

        rabbitServer.handleProductRequest(productRequestJson);

        Mockito.verify(dtoService, Mockito.times(1)).getAllProductDTOs(Currency.cow);
    }


    @Test
    public void handleCreateProductRequest() {
        List<Long> componentIDs = List.of(1L, 2L);
        ProductRequest productRequest = new ProductRequest(RequestType.createProduct, componentIDs, "floomp");

        Mockito.when(dtoService.getAllComponentDTOs()).thenReturn(getTestComponents());
        String productRequestJson = convertProductRequestTJson(productRequest);


        rabbitServer.handleProductRequest(productRequestJson);

        Mockito.verify(dtoService, Mockito.times(1)).createProductFromIDs(componentIDs, "floomp");
    }

    @Test
    public void handleCreateProductWrongID() {
        List<Long> componentIDs = List.of(1L, 4L);
        ProductRequest productRequest = new ProductRequest(RequestType.createProduct, componentIDs, "floomp");

        Mockito.when(dtoService.getAllComponentDTOs()).thenReturn(getTestComponents());
        String productRequestJson = convertProductRequestTJson(productRequest);


        rabbitServer.handleProductRequest(productRequestJson);

        Mockito.verify(dtoService, Mockito.times(0)).createProductFromIDs(componentIDs, "floomp");
    }


    @Test
    public void handleFaultyProductRequest() {
        String faultyRequest = "this isn't the beach, this is a bathtub!";

        rabbitServer.handleProductRequest(faultyRequest);

        Mockito.verifyNoInteractions(dtoService);
    }

    private String convertProductRequestTJson(ProductRequest productRequest) {
        return new Gson().toJson(productRequest, ProductRequest.class);
    }

    @Test
    private List<ComponentDTO> getTestComponents() {
        return List.of(
                new ComponentDTO(
                        1
                        , "yes"
                        , new BigDecimal(1)
                        , "yes"
                        , 1
                        , 1
                        , "yes"
                        , 1
                        , 1
                        , 1
                ),
                new ComponentDTO(
                        2
                        , "no"
                        , new BigDecimal(2)
                        , "no"
                        , 2
                        , 2
                        , "no"
                        , 2
                        , 2
                        , 2
                )
        );
    }

}
