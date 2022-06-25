package Knightly.ProductService.microservices;

import Knightly.ProductService.model.Component;
import Knightly.ProductService.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceGetter {

    @Autowired
    MicroServiceClient microServiceClient;

    public BigDecimal getPriceFromMicroService (Product product) {
        return microServiceClient.sendToPriceServince(getPricesFromProduct(product));
    }

    private List<Integer> getPricesFromProduct(Product product) {
        return product
                .getComponents()
                .stream()
                .map(Component::getPrice)
                .collect(Collectors.toList());
    }
}
