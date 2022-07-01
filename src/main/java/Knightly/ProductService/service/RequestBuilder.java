package Knightly.ProductService.service;

import Knightly.ProductService.model.Component;
import Knightly.ProductService.model.Product;
import Knightly.ProductService.model.User;
import Knightly.ProductService.payLoadModel.ComponentDTO;
import Knightly.ProductService.payLoadModel.ProductDTO;
import Knightly.ProductService.payLoadModel.UserDTO;
import Knightly.ProductService.microservices.ConversionGetter;
import Knightly.ProductService.microservices.PriceGetter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@EnableCaching
@Service
public class RequestBuilder {

    private final DataService dataService = new DataService();
    private final ConversionGetter conversionGetter = new ConversionGetter();
    private final PriceGetter priceGetter = new PriceGetter();

    @Cacheable("componentsPayload")
    public List<ComponentDTO> getComponentsAsPayload(Currency currency) {
        return createPayloadsFromComponents(
                this.dataService.getComponents(),
                currency
        );
    }

    @Cacheable("producsPayload")
    public List<ProductDTO> getProductsAsPayload(Currency currency) {
        return createPayloadsFromProducts(
                this.dataService.getProducts(),
                currency
        );
    }

    @Cacheable("userPayload")
    public UserDTO getUserAsPayload(long userID, Currency currency) {
        return createPayloadFromUser(
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

    private UserDTO createPayloadFromUser(User user, Currency currency) {
        List<ProductDTO> productDTOS = createPayloadsFromProducts(
                user.getProducts(),
                currency
        );

        return new UserDTO(
                user.getId(),
                productDTOS
        );
    }

    private ProductDTO createProductPayload(Product product, Currency currency) {
        BigDecimal priceSum = this.priceGetter
                .getPriceFromMicroService(product);

        BigDecimal convertedSum = this.conversionGetter
                .getConversionFromMicroService(priceSum.intValue(),currency);

        List<ComponentDTO> componentDTOS = createPayloadsFromComponents(
                product.getComponents(), currency);

        return new ProductDTO(
                product.getId(),
                product.getName(),
                convertedSum,
                componentDTOS
                );
    }

    private List<ProductDTO> createPayloadsFromProducts(List<Product> products, Currency currency) {
        return products
                .stream()
                .map(product -> createProductPayload(product, currency))
                .collect(Collectors.toList());
    }

    private List<ComponentDTO> createPayloadsFromComponents(List<Component> components , Currency currency) {
        return components
                .stream()
                .map(component -> createComponentPayload(component, currency))
                .collect(Collectors.toList());
    }

    private ComponentDTO createComponentPayload(Component component, Currency currency) {
        BigDecimal convertedPrice = this.conversionGetter
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
