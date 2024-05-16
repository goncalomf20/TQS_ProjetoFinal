package tqs_project.DETICafe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.aspectj.weaver.ast.Or;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tqs_project.DETICafe.DTO.OrderDetailsDTO;
import tqs_project.DETICafe.model.Order;
import tqs_project.DETICafe.model.OrderDetails;
import tqs_project.DETICafe.model.Product;
import tqs_project.DETICafe.repository.ProductRepo;
import tqs_project.DETICafe.service.OrderService;
import tqs_project.DETICafe.service.ProductService;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class CheckoutController {

    private final OrderService orderService;
    private final ProductService productService;


    @Autowired
    public CheckoutController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody List<OrderDetailsDTO> orderDetailsList) {
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsList) {
            System.out.println(orderDetailsDTO.getName() + " name");
            System.out.println(orderDetailsDTO.getQuantity() + " quantity");
            System.out.println(orderDetailsDTO.getProdcutId() + " foodId");
            System.out.println(orderDetailsDTO.getOrderDetails() + " orderDetails");
        }

        

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/getOrder")
    public ResponseEntity<Order> getOrder(@RequestParam Long id) {
        Order order = orderService.getOrder(id);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
    
}
