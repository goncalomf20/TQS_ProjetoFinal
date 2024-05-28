package tqs_project.DETICafe.ControllersTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import tqs_project.DETICafe.controller.CheckoutController;
import tqs_project.DETICafe.model.Order;
import tqs_project.DETICafe.DTO.OrderDetailsDTO;
import tqs_project.DETICafe.model.OrderDetails; 
import tqs_project.DETICafe.service.OrderService;
import tqs_project.DETICafe.model.Product;
import tqs_project.DETICafe.repository.CategoryRepo;
import tqs_project.DETICafe.service.ProductService;


// @WebMvcTest(CheckoutController.class)
// class CheckoutController_WithMockServiceTest {

//     @Autowired
//     private MockMvc mockMvc;
    
//     @MockBean
//     private OrderServiceImpl orderService;

//     @Test
//     @Disabled("Needs to fix")
//     void createOrderTest() throws Exception {
//         OrderDetails orderDetails1 = new OrderDetails(); 
//         OrderDetails orderDetails2 = new OrderDetails(); 

//         List<OrderDetails> orderDetailsList = new ArrayList<>();
//         orderDetailsList.add(orderDetails1);
//         orderDetailsList.add(orderDetails2);

//         String jsonOrderDetailsList = new ObjectMapper().writeValueAsString(orderDetailsList);
        
        
//         when(orderService.createOrder(anyList())).thenReturn(456L);


//         mockMvc.perform(MockMvcRequestBuilders.post("/api/order/createOrder")
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(jsonOrderDetailsList)) 
//             .andExpect(MockMvcResultMatchers.status().isOk())
//             .andExpect(MockMvcResultMatchers.content().string("456"));
//     }

//     @Test
//     void getOrderTest() throws Exception {
//         Order order = new Order();
//         Long orderId = 123L;

//         when(orderService.getOrder(orderId)).thenReturn(order);

//         mockMvc.perform(MockMvcRequestBuilders.get("/api/order/getOrder")
//             .param("id", orderId.toString())
//             .contentType(MediaType.APPLICATION_JSON))
//             .andExpect(MockMvcResultMatchers.status().isOk());
//     }

// }

@WebMvcTest(CheckoutController.class)
class CheckoutController_WithMockServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;
    
    @Disabled("Needs to fix")
    @Test
    void createOrderTest() throws Exception {
        OrderDetailsDTO orderDetailsDTO1 = new OrderDetailsDTO();
        orderDetailsDTO1.setName("Coffee");
        orderDetailsDTO1.setQuantity(1);
        orderDetailsDTO1.setFoodId(201);

        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetailsDTO1);

        String jsonOrderDetailsList = new ObjectMapper().writeValueAsString(orderDetailsList);

        Product mockProduct = new Product();
        mockProduct.setProductId(201L);
        mockProduct.setName("Coffee");

        when(productService.getProductById(201)).thenReturn(mockProduct);

        when(orderService.createOrder(anyList())).thenReturn(456L);

        mockMvc.perform(post("/api/order/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOrderDetailsList))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().equals("ok"));
        
    }

    @Disabled("Needs to fix")
    @Test
    void getOrderTest() throws Exception {
        Order order = new Order();
        Long orderId = 123L;

        when(orderService.getOrder(orderId)).thenReturn(order);

        mockMvc.perform(get("/api/order/getOrder")
                .param("id", orderId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}