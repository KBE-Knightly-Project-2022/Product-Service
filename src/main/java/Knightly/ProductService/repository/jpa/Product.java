package Knightly.ProductService.repository.jpa;

import Knightly.ProductService.repository.jpa.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Entity
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class Product implements Serializable {

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
