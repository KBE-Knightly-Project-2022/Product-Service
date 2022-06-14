package Knightly.ProductService;

import Knightly.ProductService.util.WarehouseComponentsGetter;
import Knightly.ProductService.util.WarehouseProductsGetter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProductServiceApplication  {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
