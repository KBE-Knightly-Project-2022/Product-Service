package Knightly.ProductService.server.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    @Id
    @Column
    private long id;

    @Column
    @ManyToMany
    @JoinTable
    private List<ProductDTO> products;

    @Column
    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
