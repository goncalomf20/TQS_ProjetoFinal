package tqs_project.DETICafe.model;


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
    private Long orderDetailsId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column
    private List<String> customizations;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    
    

}
