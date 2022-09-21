package Knightly.ProductService.microservices;

import Knightly.ProductService.enums.Currency;
import Knightly.ProductService.microservices.dto.CurrencyReply;
import Knightly.ProductService.microservices.dto.CurrencyRequest;
import Knightly.ProductService.microservices.dto.PriceRequest;
import Knightly.ProductService.util.WarehouseComponentsGetter;
import com.google.gson.Gson;
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

        String reply;
        try {
            reply = rabbitTemplate.convertSendAndReceive(
                    directExchange.getName()
                    ,routingKeyCurrencyService
                    ,convertCurrencyRequestToJson(currencyRequest))
                    .toString();
            return new BigDecimal(reply);
//            CurrencyReply currencyReply = convertJsonToCurrencyReply(reply);
//            return currencyReply.g;
        } catch (AmqpException e) {
            logger.error("Error while making request to microservice in class: " + this.getClass().toString());
            return new BigDecimal("0.00");
        }

    }

    public BigDecimal sendToPriceService(List<Integer> prices){
        PriceRequest priceRequest = new PriceRequest(prices);

        String reply;
        try {
            reply = rabbitTemplate.convertSendAndReceive(
                    directExchange.getName()
                    ,routingKeyPriceService
                    ,convertPriceRequestToJson(priceRequest))
                    .toString();
            return new BigDecimal(reply);
        } catch (AmqpException e) {
            logger.error("Error while making request to microservice in class: " + this.getClass().toString());
            return new BigDecimal("0.00");
        }
    }

    public CurrencyReply convertJsonToCurrencyReply(String json) {
        return new Gson().fromJson(json, CurrencyReply.class);
    }

    public String convertCurrencyRequestToJson(CurrencyRequest currencyRequest){
        return new Gson().toJson(currencyRequest);
    }

    public String convertPriceRequestToJson(PriceRequest priceRequest){
        return new Gson().toJson(priceRequest);
    }


}
