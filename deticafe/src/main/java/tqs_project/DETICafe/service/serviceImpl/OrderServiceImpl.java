package tqs_project.DETICafe.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import tqs_project.DETICafe.model.OrderDetails;
import tqs_project.DETICafe.service.OrderService;
import tqs_project.DETICafe.model.Order;
import tqs_project.DETICafe.repository.OrderRepo;


@Service
public class OrderServiceImpl implements OrderService {

    OrderRepo orderRepo;

    public Long createOrder(List<OrderDetails> orderDetailsList) {
        if (orderDetailsList == null || orderDetailsList.isEmpty()) {
            return null;
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
    
}
