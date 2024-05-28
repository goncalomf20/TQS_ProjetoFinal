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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;

    // @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @OneToMany
    @JoinColumn(name = "order_details_id", nullable = false)
    private List<OrderDetails> orderDetails;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
        result = prime * result + ((orderDetails == null) ? 0 : orderDetails.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (orderId == null) {
            if (other.orderId != null)
                return false;
        } else if (!orderId.equals(other.orderId))
            return false;
        if (orderDetails == null) {
            if (other.orderDetails != null)
                return false;
        } else if (!orderDetails.equals(other.orderDetails))
            return false;
        return true;
    }

    
    
}
