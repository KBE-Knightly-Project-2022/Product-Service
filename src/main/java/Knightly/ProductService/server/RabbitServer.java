package Knightly.ProductService.server;

import Knightly.ProductService.enums.Currency;
import Knightly.ProductService.enums.RequestType;
import Knightly.ProductService.external.NameOracle;
import Knightly.ProductService.repository.jpa.Product;
import Knightly.ProductService.server.dto.ComponentDTO;
import Knightly.ProductService.server.dto.ProductDTO;
import Knightly.ProductService.server.dto.ProductRequest;
import Knightly.ProductService.server.dto.UserDTO;
import Knightly.ProductService.service.DTOService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RabbitServer {

    @Autowired
    DTOService dtoService;

    @Autowired
    NameOracle nameOracle;

    private static final Logger logger = LoggerFactory.getLogger(RabbitServer.class);

    @RabbitListener(queues = "${product.queue.name}")
    public String handleRequest(String productRequestJson) {
        ProductRequest productRequest = convertJsonToProductRequest(productRequestJson);
        RequestType requestType;
        Currency currency;
        try {
            requestType = productRequest.getRequestType();
            currency = productRequest.getCurrency();
        } catch (IllegalStateException | NullPointerException e) {
            return logError("Error Reading ProductRequest in:" + this.getClass());
        }

        switch (requestType) {
            case getComponents -> {
                return componentsToDTOJson(this.dtoService
                                .getAllComponentDTOs(currency));
            }
            case getProducts -> {
                return productDTOtoJson(this.dtoService
                .getAllProductDTOs(currency));
            }
            case getUser -> {
                return userToDTOJsnon(this.dtoService
                        .getUserDTO(productRequest.getUserID()
                        , currency));
            }
            case createProduct -> {
                try {
                    this.dtoService
                            .createProductFromIDs(productRequest.getComponentIDs(),
                                    productRequest.getProductName());
                    return "Product created Sucessfully";
                } catch (Exception e){
                    return logError("Error While creating Produkt Request");
                }
            }
            case emptyShoppingCart -> {
                this.dtoService
                        .emptyShoppingCart(productRequest.getUserID());
                return "Shopping care emptied successfully";
            }
            case getAge -> {
                return nameOracle.getAge(productRequest.getOracleName());
            }
        }
        return logError("Error While Handling Request");
    }

    private String userToDTOJsnon(UserDTO userDTO) {
        return new Gson().toJson(userDTO);
    }

    private String productDTOtoJson(List<ProductDTO> allProductDTOs) {
        return new Gson().toJson(allProductDTOs);
    }

    private String componentsToDTOJson(List<ComponentDTO> allComponentDTOs) {
        return new Gson().toJson(allComponentDTOs);
    }

    private String logError(String errorMessage){
        logger.error(errorMessage);
        return errorMessage;
    }

    private ProductRequest convertJsonToProductRequest(String productRequestJson){
        return new Gson().fromJson(productRequestJson, ProductRequest.class);
    }
}
