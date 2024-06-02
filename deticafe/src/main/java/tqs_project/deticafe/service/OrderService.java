package tqs_project.deticafe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tqs_project.deticafe.model.OrderDetails;
import tqs_project.deticafe.model.Order;

@Service
public interface OrderService {

    public Long createOrder(List<OrderDetails> orderDetailsList);
    public Order getOrder(Long id);
    public List<Order> getAllOrders();
    public void deleteAllOrders();
    public Order save(Order order);
}

