package Knightly.ProductService.util;

import Knightly.ProductService.model.Component;
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

public class WarehouseComponentsGetter {


    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(WarehouseComponentsGetter.class);

    public List<Component> getComponentsFromWarehouse(String warehouseComponentURL){
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameter", header);

        ResponseEntity<String> response = restTemplate.exchange(warehouseComponentURL, HttpMethod.GET, entity, String.class);
        return getComponentListFromResponse(response.getBody());
    }


    private List<Component> getComponentListFromResponse(String response) {
        try {
            return mapper.readValue(response, new TypeReference<List<Component>>(){});
        } catch (JsonProcessingException e) {
           logger.error("Error while processing Json in: " + this.getClass());
        }
        return Collections.emptyList();
    }

}
