package Knightly.ProductService.util;

import Knightly.ProductService.model.Component;
import Knightly.ProductService.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PriceGetter {

    public BigDecimal getPriceFromMicroService (Product product) {
        //placeholder
        return new BigDecimal("0");
    }

    private List<Integer> getPricesFromProduct(Product product) {
        return product
                .getComponents()
                .stream()
                .map(Component::getPrice)
                .collect(Collectors.toList());
    }
}
