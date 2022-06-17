package Knightly.ProductService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(unique = true)
    private long id;

    @Column
    @ManyToMany
    @JoinTable
    private List<Product> products;

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
