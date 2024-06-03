package tqs_project.deticafe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tqs_project.deticafe.dto.OrderDetailsDTO;
import tqs_project.deticafe.model.Order;
import tqs_project.deticafe.model.OrderDetails;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.model.Status;
import tqs_project.deticafe.repository.OrderRepo;
import tqs_project.deticafe.service.OrderService;
import tqs_project.deticafe.service.ProductService;

@RestController
@RequestMapping("/api/order")
public class CheckoutController {

    private final OrderService orderService;
    private final ProductService productService;
    private final OrderRepo orderRepo;
    private final SimpMessagingTemplate template;

    public CheckoutController(OrderService orderService, ProductService productService, OrderRepo orderRepo, SimpMessagingTemplate template) {
        this.orderService = orderService;
        this.productService = productService;
        this.orderRepo = orderRepo;
        this.template = template;
    }


    @PostMapping("/createOrder")
    public ResponseEntity<Long> createOrder(@RequestBody List<OrderDetailsDTO> orderDetailsList) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        if(orderDetailsList == null || orderDetailsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsList) {
            List<String> customizations = new ArrayList<>();
                        
            Product product = productService.getProductById(orderDetailsDTO.getFoodId());
    
            if (product == null) {
                continue;
            }
    
            for (Entry<String, Boolean> entry : orderDetailsDTO.getOrderDetails().entrySet()) {
                if (Boolean.TRUE.equals(entry.getValue())) {
                    customizations.add(entry.getKey());
                }
            }
    
            OrderDetails orderDetail = new OrderDetails(customizations, product);
                        
            orderDetails.add(orderDetail);
        }
    
        Order order = new Order(orderDetails);
        order.setStatus(Status.PREPARING);
        orderRepo.save(order);
            
        template.convertAndSend("/topic/orders", order);
    
        return new ResponseEntity<>(order.getOrderId(), HttpStatus.OK);
    }
    

    @GetMapping("/getOrder")
    public ResponseEntity<Order> getOrder(@RequestParam Long id) {
        Order order = orderService.getOrder(id);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}