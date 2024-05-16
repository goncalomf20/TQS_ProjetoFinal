package tqs_project.DETICafe.repository;

import org.springframework.stereotype.Repository;

import tqs_project.DETICafe.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{

    public Order findByOrderId(long orderId);
    
}
