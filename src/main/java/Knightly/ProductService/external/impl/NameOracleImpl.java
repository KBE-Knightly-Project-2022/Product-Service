package Knightly.ProductService.external.impl;

import Knightly.ProductService.external.NameOracle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class NameOracleImpl implements NameOracle {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String nameURI = "https://api.agify.io";
    private static final Logger logger = LoggerFactory.getLogger(NameOracleImpl.class);

    public String getAge(String name) {
        String requestURI = this.nameURI + "/?name=" + name;
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameter", header);

        try {
            return restTemplate.exchange(requestURI, HttpMethod.GET, entity, String.class).getBody();
        } catch (RestClientException e) {
            logger.error("Error connecting to external API in NameOracle.class");
            return "Could not reach api so im guessing you are 25";
        }
    }

}
