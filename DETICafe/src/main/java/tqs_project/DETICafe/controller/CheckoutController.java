package tqs_project.DETICafe.controller;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tqs_project.DETICafe.model.Order;
import tqs_project.DETICafe.model.OrderDetails;
import tqs_project.DETICafe.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class CheckoutController {

    private final OrderService orderService;

    @Autowired
    public CheckoutController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Long> createOrder(@RequestBody List<OrderDetails> orderDetailsList) {
        Long orderId = orderService.createOrder(orderDetailsList);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }


    @GetMapping("/getOrder")
    public ResponseEntity<Order> getOrder(@RequestParam Long id) {
        Order order = orderService.getOrder(id);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
    
}
