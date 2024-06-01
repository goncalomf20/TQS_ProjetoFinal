package tqs_project.deticafe.model;


import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "oder_details_id")
    private Long orderDetailsId;

    @Column(name = "customizations")
    private List<String> customizations;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderDetails(List<String> customizations, Product product) {
        this.customizations = customizations;
        this.product = product;
    }
    
    
    

}
