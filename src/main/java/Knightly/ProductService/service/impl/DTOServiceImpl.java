package Knightly.ProductService.service.impl;

import Knightly.ProductService.enums.Currency;
import Knightly.ProductService.repository.jpa.Component;
import Knightly.ProductService.repository.jpa.Product;
import Knightly.ProductService.repository.jpa.User;
import Knightly.ProductService.server.dto.ComponentDTO;
import Knightly.ProductService.server.dto.ProductDTO;
import Knightly.ProductService.server.dto.UserDTO;
import Knightly.ProductService.microservices.CurrencyConversionGetter;
import Knightly.ProductService.microservices.PriceGetter;
import Knightly.ProductService.service.DTOService;
import Knightly.ProductService.service.impl.DataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@EnableCaching
@Service
public class DTOServiceImpl implements DTOService {

    @Autowired
    DataServiceImpl dataService;
    @Autowired
    PriceGetter priceGetter;
    @Autowired
    CurrencyConversionGetter currencyConversionGetter;

    @Cacheable("componentDTOs")
    public List<ComponentDTO> getAllComponentDTOs(Currency currency) {
        return createComponentDTOList(
                this.dataService.getComponents(),
                currency
        );
    }

    @Cacheable("componentDTOsStandard")
    public List<ComponentDTO> getAllComponentDTOs() {
        return getAllComponentDTOs(Currency.bronze);
    }


    public List<ProductDTO> getAllProductDTOs(Currency currency) {
        return createProductDTOList(
                this.dataService.getProducts(),
                currency
        );
    }

    public List<ProductDTO> getAllProductDTOs() {
        return getAllProductDTOs(Currency.bronze);
    }

    @Cacheable("userDTOs")
    public UserDTO getUserDTO(Long userID, Currency currency) {
        return createUserDTO(
                this.dataService.getUser(userID),
                currency);
    }

    public void createProduct(Product product) {
        this.dataService.createProduct(product);
    }

    public void createProductFromIDs(List<Long> ids, String name){
        List<Component> components = getComponentsFromIDList(ids);
        long productID = UUID.randomUUID().getMostSignificantBits();
        this.dataService.createProduct(new Product(productID, name, components));
    }

    public void emptyShoppingCart(Long userID) {
        this.dataService.emptyShoppingCart(userID);
    }

    public void updateShoppingCart(Long userID, List<Product> newShoppingCart) {
        this.dataService.updateShoppingCart(userID, newShoppingCart);
    }

    private UserDTO createUserDTO(User user, Currency currency) {
        List<ProductDTO> productDTOS = createProductDTOList(
                user.getProducts(),
                currency
        );

        return new UserDTO(
                user.getId(),
                productDTOS
        );
    }

    private ProductDTO createProductDTO(Product product, Currency currency) {
        BigDecimal productPrice = this.priceGetter
                .getPriceFromMicroService(product);

        BigDecimal convertedPrice = this.currencyConversionGetter
                .getConversionFromMicroService(productPrice.intValue(),currency);

        List<ComponentDTO> componentDTOS = createComponentDTOList(
                product.getComponents(), currency);

        return new ProductDTO(
                product.getId(),
                product.getName(),
                convertedPrice,
                componentDTOS
                );
    }

    private List<ProductDTO> createProductDTOList(List<Product> products, Currency currency) {
        return products
                .stream()
                .map(product -> createProductDTO(product, currency))
                .collect(Collectors.toList());
    }

    private List<ComponentDTO> createComponentDTOList(List<Component> components , Currency currency) {
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

    private List<Component> getComponentsFromIDList(List<Long> ids) {
        return ids.stream()
                .map(id -> dataService.getComponentByID(id))
                .collect(Collectors.toList());
    }

}
