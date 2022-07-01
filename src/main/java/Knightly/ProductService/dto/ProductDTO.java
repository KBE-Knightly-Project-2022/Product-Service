package Knightly.ProductService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class ProductDTO {

    @Id
    private long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @ManyToMany
    @JoinTable
    @Column
    private List<ComponentDTO> components;
}
