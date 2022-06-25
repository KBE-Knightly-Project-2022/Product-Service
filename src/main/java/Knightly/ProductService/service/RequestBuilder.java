package Knightly.ProductService.service;

import Knightly.ProductService.model.Component;
import Knightly.ProductService.model.Product;
import Knightly.ProductService.model.User;
import Knightly.ProductService.payLoadModel.ComponentPayload;
import Knightly.ProductService.payLoadModel.ProductPayload;
import Knightly.ProductService.payLoadModel.UserPayload;
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
    public List<ComponentPayload> getComponentsAsPayload(Currency currency) {
        return createPayloadsFromComponents(
                this.dataService.getComponents(),
                currency
        );
    }

    @Cacheable("producsPayload")
    public List<ProductPayload> getProductsAsPayload(Currency currency) {
        return createPayloadsFromProducts(
                this.dataService.getProducts(),
                currency
        );
    }

    @Cacheable("userPayload")
    public UserPayload getUserAsPayload(long userID, Currency currency) {
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

    private UserPayload createPayloadFromUser(User user, Currency currency) {
        List<ProductPayload> productPayloads = createPayloadsFromProducts(
                user.getProducts(),
                currency
        );

        return new UserPayload(
                user.getId(),
                productPayloads
        );
    }

    private ProductPayload createProductPayload(Product product, Currency currency) {
        BigDecimal priceSum = this.priceGetter
                .getPriceFromMicroService(product);

        BigDecimal convertedSum = this.conversionGetter
                .getConversionFromMicroService(priceSum.intValue(),currency);

        List<ComponentPayload> componentPayloads = createPayloadsFromComponents(
                product.getComponents(), currency);

        return new ProductPayload(
                product.getId(),
                product.getName(),
                convertedSum,
                componentPayloads
                );
    }

    private List<ProductPayload> createPayloadsFromProducts(List<Product> products, Currency currency) {
        return products
                .stream()
                .map(product -> createProductPayload(product, currency))
                .collect(Collectors.toList());
    }

    private List<ComponentPayload> createPayloadsFromComponents(List<Component> components , Currency currency) {
        return components
                .stream()
                .map(component -> createComponentPayload(component, currency))
                .collect(Collectors.toList());
    }

    private ComponentPayload createComponentPayload(Component component, Currency currency) {
        BigDecimal convertedPrice = this.conversionGetter
                .getConversionFromMicroService(component.getPrice(), currency);

        return new ComponentPayload(
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
