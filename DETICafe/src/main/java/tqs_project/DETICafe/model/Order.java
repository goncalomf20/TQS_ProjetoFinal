package tqs_project.DETICafe.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long order_id;

    @OneToMany
    @JoinColumn(name = "order_details_id")
    private Long order_details_id;

    
}
