package tqs_project.deticafe.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tqs_project.deticafe.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{

    public Order findByOrderId(long orderId);
    public List<Order> findAll();
    
}
