package Knightly.ProductService.util;

import Knightly.ProductService.repository.jpa.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WarehouseProductsGetter {


    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(WarehouseProductsGetter.class);

    public List<Product> getProductsFromWarehouse(String wareHouseProductsURL){
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameter", header);

        ResponseEntity<String> response = restTemplate.exchange(wareHouseProductsURL, HttpMethod.GET, entity, String.class);
        return getComponentListFromResponse(response.getBody());
    }

    private List<Product> getComponentListFromResponse(String response) {
        try {
            return mapper.readValue(response, new TypeReference<List<Product>>(){});
        } catch (JsonProcessingException e) {
            logger.error("Error while processing Json in: " + this.getClass());
        }
        return Collections.emptyList();
    }
}
