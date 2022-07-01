package Knightly.ProductService.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Id
    private long id;

    private List<ProductDTO> products;

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
