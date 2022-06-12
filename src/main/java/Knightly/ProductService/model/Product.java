package Knightly.ProductService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class Product {

    @Id
    @Column(nullable = false, unique = true)
    private long id;

    @Column
    private String name;

    @Column
    @ManyToMany
    @JoinTable
    private List<Component> components;
}
