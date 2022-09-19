package Knightly.ProductService.microservices.dto;

import Knightly.ProductService.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyRequest {

    public int enteredAmound;
    public Currency requestedCurrency;
}
