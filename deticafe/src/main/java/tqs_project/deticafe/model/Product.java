package tqs_project.deticafe.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection // To handle list of basic types
    @Column(name = "ingredients", nullable = false)
    private List<String> ingredients;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(String name, List<String> ingredients, double price, Category category) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.category = category;
    }
}
