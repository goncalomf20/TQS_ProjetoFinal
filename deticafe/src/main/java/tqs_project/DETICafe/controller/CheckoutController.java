package tqs_project.DETICafe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import tqs_project.DETICafe.repository.OrderRepo;
import tqs_project.DETICafe.service.OrderService;
import tqs_project.DETICafe.service.ProductService;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<String> createOrder(@RequestBody List<OrderDetailsDTO> orderDetailsList) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsList) {
            List<String> costumizations = new ArrayList<>();
            System.out.println(orderDetailsDTO.getName() + " name");
            System.out.println(orderDetailsDTO.getQuantity() + " quantity");
            System.out.println(orderDetailsDTO.getFoodId() + " foodId");
            System.out.println(orderDetailsDTO.getOrderDetails() + " orderDetails");

            Product product = productService.getProductById(orderDetailsDTO.getFoodId());
            System.out.println(product.getName() + " product name");

            for (Entry<String, Boolean> entry : orderDetailsDTO.getOrderDetails().entrySet()) {
                System.out.println(entry.getKey() + " key");
                System.out.println(entry.getValue() + " value");
                if (entry.getValue()) {
                    costumizations.add(entry.getKey());
                }
            }

            OrderDetails orderDetail = new OrderDetails(1l ,costumizations, product);
            orderDetails.add(orderDetail);
        }

        Order order = new Order(orderDetails);
        orderRepo.save(order);

        System.out.println(order.getOrderDetails());
        System.out.println(order.getOrderId());

        if (order!= null && order.getOrderId()!= null) {
            template.convertAndSend("/topic/orders", order.getOrderId());
        } else {
            // Log ou trate o caso em que a ordem ou o ID da ordem é nulo
            System.err.println("Erro: A ordem ou o ID da ordem é nulo.");
        }
        

        
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/getOrder")
    public ResponseEntity<Order> getOrder(@RequestParam Long id) {
        Order order = orderService.getOrder(id);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
    
}
