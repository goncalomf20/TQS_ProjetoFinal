package tqs_project.deticafe.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tqs_project.deticafe.model.Order;
import tqs_project.deticafe.model.OrderDetails;
import tqs_project.deticafe.repository.OrderRepo;
import tqs_project.deticafe.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;

    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Long createOrder(List<OrderDetails> orderDetailsList) {
        if (orderDetailsList == null || orderDetailsList.isEmpty()) {
            throw new IllegalArgumentException("Order details list cannot be null or empty");
        }
        Order order = new Order(orderDetailsList);
        orderRepo.save(order);
        return order.getOrderId();
    }

    public Order getOrder(Long id) {
        return orderRepo.findByOrderId(id);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }


    public void deleteAllOrders() {
        orderRepo.deleteAll();
    }

    public Order save(Order order) {
        return orderRepo.save(order);
    }
    
}
