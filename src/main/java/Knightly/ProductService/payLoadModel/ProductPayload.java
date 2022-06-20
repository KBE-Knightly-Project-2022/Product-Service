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
public class ProductPayload {

    @Id
    private long id;

    private String name;

    private BigDecimal price;

    private List<ComponentPayload> components;
}
