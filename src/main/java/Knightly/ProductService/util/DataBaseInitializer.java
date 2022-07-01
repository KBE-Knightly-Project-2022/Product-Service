package Knightly.ProductService.util;

import Knightly.ProductService.dto.ComponentDTO;
import Knightly.ProductService.dto.ProductDTO;
import Knightly.ProductService.enums.Currency;
import Knightly.ProductService.model.Component;
import Knightly.ProductService.model.Product;
import Knightly.ProductService.repository.ComponentRepository;
import Knightly.ProductService.repository.ProductRepository;
import Knightly.ProductService.service.DTOService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataBaseInitializer {

    private final String WAREHOUSE_PRODUCTS_URL = "http://localhost:6868/products";
    private final String WAREHOUSE_COMPONENTS_URL = "http://localhost:6868/components";
    private final WarehouseComponentsGetter warehouseComponentsGetter = new WarehouseComponentsGetter();
    private final WarehouseProductsGetter warehouseProductsGetter = new WarehouseProductsGetter();
    @Autowired
    DTOService dtoService;

    @Bean
    CommandLineRunner initializeDatabase(ComponentRepository componentRepository, ProductRepository productRepository) {
        return args -> {
            List<Component> components = warehouseComponentsGetter.getComponentsFromWarehouse(WAREHOUSE_COMPONENTS_URL);
            componentRepository.saveAll(components);

            List<Product> products = warehouseProductsGetter.getProductsFromWarehouse(WAREHOUSE_PRODUCTS_URL);
            productRepository.saveAll(products);
        };
    }
}
