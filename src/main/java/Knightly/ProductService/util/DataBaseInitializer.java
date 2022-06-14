package Knightly.ProductService.util;

import Knightly.ProductService.model.Component;
import Knightly.ProductService.model.Product;
import Knightly.ProductService.repository.ComponentRepository;
import Knightly.ProductService.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class DataBaseInitializer {

    private final String WAREHOUSE_PRODUCTS_URL = "http://localhost:6868/products";
    private final String WAREHOUSE_COMPONENTS_URL = "http://localhost:6868/components";
    private final WarehouseComponentsGetter warehouseComponentsGetter = new WarehouseComponentsGetter();
    private final WarehouseProductsGetter warehouseProductsGetter = new WarehouseProductsGetter();

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
