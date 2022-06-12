package Knightly.ProductService;

import Knightly.ProductService.util.WarehouseGetter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {

	private WarehouseGetter warehouseGetter = new WarehouseGetter();

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
