package tqs_project.DETICafe.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import tqs_project.DETICafe.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{

    public Order findByOrderId(long orderId);
    
}
