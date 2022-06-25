package Knightly.ProductService.microservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
public class ConversionGetter {

    @Autowired
    MicroServiceClient microServiceClient;

    public BigDecimal getConversionFromMicroService(int enteredAmount,Currency requestedCurrency) {
        return microServiceClient.sendToCurrencyService(enteredAmount, requestedCurrency.toString());
    }
}
