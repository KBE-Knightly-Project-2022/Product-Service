package Knightly.ProductService.service;

import Knightly.ProductService.enums.Currency;
import Knightly.ProductService.repository.jpa.Product;
import Knightly.ProductService.server.dto.ComponentDTO;
import Knightly.ProductService.server.dto.ProductDTO;
import Knightly.ProductService.server.dto.UserDTO;

import java.util.List;

public interface DTOService {
    public List<ComponentDTO> getAllComponentDTOs(Currency currency);
    public List<ComponentDTO> getAllComponentDTOs();
    public List<ProductDTO> getAllProductDTOs(Currency currency);
    public List<ProductDTO> getAllProductDTOs();
    public UserDTO getUserDTO(Long userID, Currency currency);
    public void createProduct(Product product);
    public void createProductFromIDs(List<Long> ids, String name);
    public void emptyShoppingCart(Long userID);
    public void updateShoppingCart(Long userID, List<Product> newShoppingCart);
}
