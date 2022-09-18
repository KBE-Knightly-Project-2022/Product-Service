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

import java.util.List;

@Slf4j
public class RabbitServer {

    @Autowired
    DTOService dtoService;

    @Autowired
    NameOracle nameOracle;

    private static final Logger logger = LoggerFactory.getLogger(RabbitServer.class);

    @RabbitListener(queues = "${product.queue.name}")
    public String handleRequest(ProductRequest productRequest) {
        RequestType requestType;
        Currency currency;
        try {
            requestType = productRequest.getRequestType();
            currency = productRequest.getCurrency();
        } catch (IllegalStateException | NullPointerException e) {
            logger.error("Error Reading ProductRequest in:" + this.getClass());
            return "Error Reading ProductRequest";
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
                    return "Error While creating Produkt Request";
                }
            }
            case getAge -> {
                return nameOracle.getAge(productRequest.getOracleName());
            }
        }
        return "Error While Handling Request";
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
}
