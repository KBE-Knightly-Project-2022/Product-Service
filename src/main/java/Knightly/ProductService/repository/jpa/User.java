package Knightly.ProductService.repository.jpa;

import Knightly.ProductService.repository.jpa.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
