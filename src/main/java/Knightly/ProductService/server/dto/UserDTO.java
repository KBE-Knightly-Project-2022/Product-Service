package Knightly.ProductService.server.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private long id;

    private List<ProductDTO> products;

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
