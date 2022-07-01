package Knightly.ProductService.service;

import Knightly.ProductService.enums.Currency;
import Knightly.ProductService.repository.jpa.Component;
import Knightly.ProductService.repository.jpa.Product;
import Knightly.ProductService.repository.jpa.User;
import Knightly.ProductService.api.dto.ComponentDTO;
import Knightly.ProductService.api.dto.ProductDTO;
import Knightly.ProductService.api.dto.UserDTO;
import Knightly.ProductService.microservices.CurrencyConversionGetter;
import Knightly.ProductService.microservices.PriceGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

//@EnableCaching
@Service
public class DTOService {

    @Autowired
    DataService dataService;
    @Autowired
    PriceGetter priceGetter;
    @Autowired
    CurrencyConversionGetter currencyConversionGetter;

    @Cacheable("componentDTOs")
    public List<ComponentDTO> getComponentDTOs(Currency currency) {
        return createComponentDTOs(
                this.dataService.getComponents(),
                currency
        );
    }

    @Cacheable("productDTOs")
    public List<ProductDTO> getProductDTOs(Currency currency) {
        return createProductDTOs(
                this.dataService.getProducts(),
                currency
        );
    }

    @Cacheable("userDTOs")
    public UserDTO getUserDTOs(long userID, Currency currency) {
        return createUserDTO(
                this.dataService.getUser(userID),
                currency);
    }

    public void createProduct(Product product) {
        this.dataService.createProduct(product);
    }

    public void emptyShoppingCart(long userID) {
        this.dataService.emptyShoppingCart(userID);
    }

    public void updateShoppingCart(long userID, List<Product> newShoppingCart) {
        this.dataService.updateShoppingCart(userID, newShoppingCart);
    }

    private UserDTO createUserDTO(User user, Currency currency) {
        List<ProductDTO> productDTOS = createProductDTOs(
                user.getProducts(),
                currency
        );

        return new UserDTO(
                user.getId(),
                productDTOS
        );
    }

    private ProductDTO createProductDTO(Product product, Currency currency) {
        BigDecimal priceSum = this.priceGetter
                .getPriceFromMicroService(product);

        BigDecimal convertedSum = this.currencyConversionGetter
                .getConversionFromMicroService(priceSum.intValue(),currency);

        List<ComponentDTO> componentDTOS = createComponentDTOs(
                product.getComponents(), currency);

        return new ProductDTO(
                product.getId(),
                product.getName(),
                convertedSum,
                componentDTOS
                );
    }

    private List<ProductDTO> createProductDTOs(List<Product> products, Currency currency) {
        return products
                .stream()
                .map(product -> createProductDTO(product, currency))
                .collect(Collectors.toList());
    }

    private List<ComponentDTO> createComponentDTOs(List<Component> components , Currency currency) {
        return components
                .stream()
                .map(component -> createComponentDTO(component, currency))
                .collect(Collectors.toList());
    }

    private ComponentDTO createComponentDTO(Component component, Currency currency) {
        BigDecimal convertedPrice = this.currencyConversionGetter
                .getConversionFromMicroService(component.getPrice(), currency);

        return new ComponentDTO(
                component.getId(),
                component.getName(),
                convertedPrice,
                component.getDescription(),
                component.getAttack(),
                component.getDefence(),
                component.getPosition(),
                component.getMaxrange(),
                component.getMinrange(),
                component.getWeight()
        );
    }

}
