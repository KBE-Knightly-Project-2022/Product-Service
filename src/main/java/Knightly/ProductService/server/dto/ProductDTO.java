package Knightly.ProductService.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
public class ProductDTO implements Serializable {

    private long id;

    private String name;

    private BigDecimal price;

    private List<ComponentDTO> components;
}
