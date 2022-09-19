package Knightly.ProductService.microservices;

import Knightly.ProductService.enums.Currency;
import Knightly.ProductService.microservices.dto.CurrencyRequest;
import Knightly.ProductService.microservices.dto.PriceRequest;
import Knightly.ProductService.util.WarehouseComponentsGetter;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;

public class MicroServiceClient {

    private final String ENTERED_AMOUNT = "enteredAmount";
    private final String REQUESTED_CURRENCY = "requestedCurrency";
    private static final Logger logger = LoggerFactory.getLogger(WarehouseComponentsGetter.class);
    @Value("${routing.key.currency.service}")
    private String routingKeyCurrencyService;

    @Value("${routing.key.price.service}")
    private String routingKeyPriceService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    public BigDecimal sendToCurrencyService(int enteredAmount , Currency requestedCurrency) {
        CurrencyRequest currencyRequest = new CurrencyRequest(enteredAmount, requestedCurrency);

        String response;
        try {
            response = rabbitTemplate.convertSendAndReceive(directExchange.getName(), routingKeyCurrencyService, currencyRequest).toString();
            return new BigDecimal(response);
        } catch (AmqpException e) {
            logger.error("Error while making request to microservice in class: " + this.getClass().toString());
            return new BigDecimal("0.00");
        }

    }

    public BigDecimal sendToPriceService(List<Integer> prices){
        PriceRequest priceRequest = new PriceRequest(prices);

        String response;
        try {
            response = rabbitTemplate.convertSendAndReceive(directExchange.getName(), routingKeyPriceService, priceRequest).toString();
            return new BigDecimal(response);
        } catch (AmqpException e) {
            logger.error("Error while making request to microservice in class: " + this.getClass().toString());
            return new BigDecimal("0.00");
        }
    }

    private JSONObject buildPayload(int enteredAmount, String requestedCurrency) {
        JSONObject payload = null;
        try {
            payload = new JSONObject();
            payload.put(ENTERED_AMOUNT, enteredAmount);
            payload.put(REQUESTED_CURRENCY, requestedCurrency);
        } catch (Exception e) {
            logger.error("Error creating Json to send to Microservice in class: " + this.getClass().toString());
        }
        return payload;
    }
}
