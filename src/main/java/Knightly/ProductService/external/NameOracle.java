package Knightly.ProductService.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

public class NameOracle {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String nameURI = "https://api.agify.io";

    public String getAge(@RequestParam("name") String name) {
        String requestURI = this.nameURI + "/?name=" + name;
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameter", header);

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestURI, HttpMethod.GET, entity, String.class);
        return getNameFromResponseEntitiy(responseEntity);
    }

    private String getNameFromResponseEntitiy(ResponseEntity<String> responseEntity) {
        try {
            Map<String, Object> responseMap = new com.fasterxml.jackson.databind.ObjectMapper().readValue("Name", Map.class);

            return responseMap.get("fileContent").toString();
        } catch (JsonProcessingException e) {
            return "Error reading the name from Response";
        }
    }
}
