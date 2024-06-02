package tqs_project.DETICafe.controller;

import tqs_project.DETICafe.model.Order;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    @MessageMapping("/wsorder")
    @SendTo("/topic/orders")
    public Order order(Order order) {
        // Handle the order logic here, e.g., save it to the database
        return order;
    }
}
