package tqs_project.DETICafe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tqs_project.DETICafe.model.OrderDetails;
import tqs_project.DETICafe.model.Order;

@Service
public interface OrderService {

    public Long createOrder(List<OrderDetails> orderDetailsList);
    public Order getOrder(Long id);
    public List<Order> getAllOrders();
}