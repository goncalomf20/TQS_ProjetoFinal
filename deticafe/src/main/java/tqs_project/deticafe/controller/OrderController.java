package tqs_project.deticafe.controller;

import tqs_project.deticafe.model.Order;
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
