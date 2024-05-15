package tqs_project.DETICafe.repository;

import org.springframework.stereotype.Repository;

import tqs_project.DETICafe.model.Order;

@Repository
public interface OrderRepo {

    public void saveOrder(Order order);
    public Order getOrderById(Long id);
    
}
