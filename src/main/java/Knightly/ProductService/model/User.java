package Knightly.ProductService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

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
