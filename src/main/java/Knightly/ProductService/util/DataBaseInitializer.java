package Knightly.ProductService.util;

import Knightly.ProductService.repository.jpa.Component;
import Knightly.ProductService.repository.jpa.Product;
import Knightly.ProductService.repository.ComponentRepository;
import Knightly.ProductService.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.IOException;
import java.util.List;

@Configuration
public class DataBaseInitializer {

    private final String WAREHOUSE_PRODUCTS_URL = "http://WarehouseApp:8080/products";
    private final String WAREHOUSE_COMPONENTS_URL = "http://WarehouseApp:8080/components";
    private final WarehouseComponentsGetter warehouseComponentsGetter = new WarehouseComponentsGetter();
    private final WarehouseProductsGetter warehouseProductsGetter = new WarehouseProductsGetter();
    private static final Logger logger = LoggerFactory.getLogger(DataBaseInitializer.class);

    @Bean
    CommandLineRunner initializeDatabase(ComponentRepository componentRepository, ProductRepository productRepository) {
        return args -> {
            try {
                List<Component> components = warehouseComponentsGetter.getComponentsFromWarehouse(WAREHOUSE_COMPONENTS_URL);
                componentRepository.saveAll(components);

                List<Product> products = warehouseProductsGetter.getProductsFromWarehouse(WAREHOUSE_PRODUCTS_URL);
                productRepository.saveAll(products);
            } catch (DataIntegrityViolationException e) {
               logger.error("Data has already been fetched");
            }
        };
    }
}
