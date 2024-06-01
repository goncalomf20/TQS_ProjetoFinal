package tqs_project.deticafe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


import tqs_project.deticafe.DTO.OrderDetailsDTO;
import tqs_project.deticafe.model.Order;
import tqs_project.deticafe.model.OrderDetails;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.model.Status;
import tqs_project.deticafe.repository.OrderRepo;
import tqs_project.deticafe.service.OrderService;
import tqs_project.deticafe.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

@RestController
@RequestMapping("/api/order")
public class CheckoutController {

    private final OrderService orderService;
    private final ProductService productService;
    private final OrderRepo orderRepo;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    public CheckoutController(OrderService orderService, ProductService productService, OrderRepo orderRepo) {
        this.orderService = orderService;
        this.productService = productService;
        this.orderRepo = orderRepo;
    }


    @PostMapping("/createOrder")
    public ResponseEntity<Long> createOrder(@RequestBody List<OrderDetailsDTO> orderDetailsList) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsList) {
            List<String> customizations = new ArrayList<>();
            Product product = productService.getProductById(orderDetailsDTO.getFoodId());


            for (Entry<String, Boolean> entry : orderDetailsDTO.getOrderDetails().entrySet()) {
                if (entry.getValue()) {
                    customizations.add(entry.getKey());
                }
            }

            OrderDetails orderDetail = new OrderDetails(customizations, product);
            System.out.println("OrderDetail created: " + orderDetail.toString());
            orderDetails.add(orderDetail);
        }

        Order order = new Order(orderDetails);
        order.setStatus(Status.PREPARING);
        orderRepo.save(order);
        
        orderRepo.save(order);
        System.out.println("Order created: " + order.getOrderId() + "with details: " + orderDetailsList.toString());


        template.convertAndSend("/topic/orders", order);

        return new ResponseEntity<>(order.getOrderId(), HttpStatus.OK);
    }

    @GetMapping("/getOrder")
    public ResponseEntity<Order> getOrder(@RequestParam Long id) {
        Order order = orderService.getOrder(id);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }
}