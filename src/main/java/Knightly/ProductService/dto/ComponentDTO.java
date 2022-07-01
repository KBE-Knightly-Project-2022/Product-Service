package Knightly.ProductService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDTO implements Serializable {

    @Id
    private long id;

    private String name;

    private BigDecimal price;

    private String description;

    private int attack;

    private int defence;

    private String position;

    private int weight;

    private int minrange;

    private int maxrange;
}
