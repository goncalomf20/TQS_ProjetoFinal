package tqs_project.DETICafe.model;

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
    private Long product_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ingredients", nullable = false)
    private List<String> ingredients;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToMany
    @JoinColumn(name = "category_id", nullable = false)
    private Long category_id;

    
}