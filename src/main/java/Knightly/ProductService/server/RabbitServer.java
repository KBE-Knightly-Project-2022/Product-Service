package Knightly.ProductService.server;

import Knightly.ProductService.enums.Currency;
import Knightly.ProductService.enums.RequestType;
import Knightly.ProductService.external.impl.NameOracleImpl;
import Knightly.ProductService.server.dto.ComponentDTO;
import Knightly.ProductService.server.dto.ProductDTO;
import Knightly.ProductService.server.dto.ProductRequest;
import Knightly.ProductService.server.dto.UserDTO;
import Knightly.ProductService.service.impl.DTOServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RabbitServer {

    @Autowired
    DTOServiceImpl dtoService;

    @Autowired
    NameOracleImpl nameOracle;

    private static final Logger logger = LoggerFactory.getLogger(RabbitServer.class);

    @RabbitListener(queues = "${product.queue.name}")
    public String handleProductRequest(String productRequestJson) {
        ProductRequest productRequest;
        RequestType requestType;
        Currency currency;
        try {
            productRequest = convertJsonToProductRequest(productRequestJson);
            requestType = productRequest.getRequestType();
            currency = productRequest.getCurrency();
        } catch (IllegalStateException | NullPointerException | JsonSyntaxException e) {
            return logError("[Error]: Reading ProductRequest in:" + this.getClass());
        }

        switch (requestType) {
            case getComponents -> {
                return convertComponentsDTOtoJson(this.dtoService
                                .getAllComponentDTOs(currency));
            }
            case getProducts -> {
                return convertProductsDTOToJson(this.dtoService
                .getAllProductDTOs(currency));
            }
            case createProduct -> {
                try {
                    List<Long> componentIDs = productRequest.getComponentIDs();
                    String productName = productRequest.getProductName();
                    if(verifyComponentsIDs(componentIDs)) {
                        this.dtoService
                                .createProductFromIDs(componentIDs, productName);
                        return "Product created Sucessfully";
                    } else {
                        return logError("[Error] ProductID not found");
                    }
                } catch (NullPointerException e){
                    return logError("[Error] While creating Produkt Request");
                }
            }
            case getAge -> {
                return nameOracle.getAge(productRequest.getOracleName());
            }
        }
        return logError("[Error] While Handling Request");
    }

    private String convertUserDTOtoJson(UserDTO userDTO) {
        return new Gson().toJson(userDTO);
    }

    private String convertProductsDTOToJson(List<ProductDTO> allProductDTOs) {
        return new Gson().toJson(allProductDTOs);
    }

    private String convertComponentsDTOtoJson(List<ComponentDTO> allComponentDTOs) {
        return new Gson().toJson(allComponentDTOs);
    }

    private String logError(String errorMessage){
        logger.error(errorMessage);
        return errorMessage;
    }

    private ProductRequest convertJsonToProductRequest(String productRequestJson){
        return new Gson().fromJson(productRequestJson, ProductRequest.class);
    }

    private boolean verifyComponentsIDs(List<Long> componentIds){
        if(componentIds == null) {
            return false;
        }

        List<Long> availableIDs = dtoService.getAllComponentDTOs()
                .stream()
                .map(ComponentDTO::getId)
                .collect(Collectors.toList());

        return (availableIDs.containsAll(componentIds));
    }
}
