package Knightly.ProductService.microservices;

import Knightly.ProductService.enums.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class CurrencyConversionGetter {

    @Autowired
    MicroServiceClient microServiceClient;

    public BigDecimal getConversionFromMicroService(int enteredAmount, Currency requestedCurrency) {
        return microServiceClient.sendToCurrencyService(enteredAmount, requestedCurrency);
    }
}
