package Knightly.ProductService.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

public class NameOracle {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String nameURI = "https://api.agify.io";

    public ResponseEntity<String> getAge(String name) {
        String requestURI = this.nameURI + "/?name=" + name;
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameter", header);

        return restTemplate.exchange(requestURI, HttpMethod.GET, entity, String.class);

    }

}
