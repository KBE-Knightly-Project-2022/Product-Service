package Knightly.ProductService.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.transaction.Transactional;

@Transactional
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonPropertyOrder({"id","name","price","description","attack","defence","position","weight","minrange","maxrange"})
public class Component {

    @Id
    @Column(unique = true)
    private long id;
    @Column(unique = true)
    private String name;
    @Column
    private int price;
    @Column
    private String description;
    @Column
    private int attack;
    @Column
    private int defence;
    @Column
    private String position;
    @Column
    private int weight;
    @Column
    private int minrange;
    @Column
    private int maxrange;

}
