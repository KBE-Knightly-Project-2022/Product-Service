package Knightly.ProductService.microservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PriceReply {
    BigDecimal calculatedPrice;
}
