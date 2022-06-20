package Knightly.ProductService.payLoadModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPayload {

    @Id
    private long id;

    private List<ProductPayload> products;

    public void setProducts(List<ProductPayload> products) {
        this.products = products;
    }
}
