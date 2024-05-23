package tqs_project.DETICafe.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order {
    public Order(List<OrderDetails> orderDetailsList) {
        this.orderDetails = orderDetailsList;
    }

    // New constructor to match the test case expectation
    public Order(Long orderId, List<OrderDetails> orderDetailsList) {
        this.orderId = orderId;
        this.orderDetails = orderDetailsList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;

    @OneToMany
    @JoinColumn(name = "order_details_id", nullable = false)
    private List<OrderDetails> orderDetails;

    @Column(name="status_order")
    private Status status;
}
