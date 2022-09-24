package Knightly.ProductService.server.dto;

import Knightly.ProductService.enums.Currency;
import Knightly.ProductService.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductRequest {

    public RequestType requestType;
    public Currency currency;
    public List<Long> componentIDs;
    public Long userID;
    public String productName;
    public String oracleName;

    public ProductRequest(RequestType requestType) {
        this.requestType = requestType;
        this.currency = Currency.bronze;
    }

    public ProductRequest(RequestType requestType, Currency currency) {
        this(requestType);
        this.currency = currency;
    }

    public ProductRequest(RequestType requestType, List<Long> componentIDs, String productName) {
        this(requestType);
        this.componentIDs = componentIDs;
        this.productName = productName;
    }

    public ProductRequest(RequestType requestType, Long userID, Currency currency) {
        this(requestType);
        this.currency = currency;
        this.userID = userID;
    }

    public ProductRequest(RequestType requestType, Long userID) {
        this(requestType);
        this.userID = userID;
    }

    public ProductRequest(RequestType requestType, String oracleName) {
        this(requestType);
        this.oracleName = oracleName;
    }
}
