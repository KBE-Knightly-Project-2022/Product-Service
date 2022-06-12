package Knightly.ProductService.util;

import Knightly.ProductService.model.Component;
import org.springframework.web.client.RestTemplate;

public class WarehouseGetter {

    private final String WarehouseComponentsURL = "localhost:6868/components";
    private final String WarehouseProductsURL = "localhost:6868/products";
    private final RestTemplate restTemplate = new RestTemplate();

    public Component getProductsFromWarehouse(){
        Component component = restTemplate.getForObject(WarehouseComponentsURL, Component.class);
        System.out.println(component);

        return component;
    }

}
