package Knightly.ProductService.microservices;

import org.json.simple.JSONObject;
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
    @Value("${routing.key.currency.service}")
    private String routingKeyCurrencyService;

    @Value("${routing.key.price.service}")
    private String routingKeyPriceService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    public BigDecimal sendToCurrencyService(int enteredAmount , String requestedCurrency) {
        JSONObject payload = buildPayload(enteredAmount,requestedCurrency);

        String response;
        try {
            response = rabbitTemplate.convertSendAndReceive(directExchange.getName(), routingKeyCurrencyService, payload).toString();
            return new BigDecimal(response);
        } catch (AmqpException e) {
            e.printStackTrace();
            return new BigDecimal("0.00");
        }

    }

    public BigDecimal sendToPriceServince(List<Integer> prices){

        String response;
        try {
            response = rabbitTemplate.convertSendAndReceive(directExchange.getName(), routingKeyPriceService, prices).toString();
            return new BigDecimal(response);
        } catch (AmqpException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return payload;
    }
}
