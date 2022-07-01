package Knightly.ProductService.payLoadModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class ProductDTO {

    @Id
    private long id;

    private String name;

    private BigDecimal price;

    private List<ComponentDTO> components;
}
