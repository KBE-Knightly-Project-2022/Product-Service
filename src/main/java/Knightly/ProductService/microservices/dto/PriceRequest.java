package Knightly.ProductService.microservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PriceRequest {
    public List<Integer> prices;
}
