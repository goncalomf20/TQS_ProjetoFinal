package tqs_project.DETICafe.model;


import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long order_details_id;

    @Column
    private List<String> customizations;
    
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Long product_id;
    

}
