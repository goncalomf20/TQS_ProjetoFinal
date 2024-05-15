package tqs_project.DETICafe.UnitTests.ControllersTest;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import tqs_project.DETICafe.model.Order;
import tqs_project.DETICafe.model.OrderDetails;
import tqs_project.DETICafe.service.OrderService;

@SpringBootTest
@AutoConfigureMockMvc
public class CheckOutControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private OrderService orderService;

    @Test
    public void createOrderTest() throws Exception {
        OrderDetails orderDetails1 = new OrderDetails(); 
        OrderDetails orderDetails2 = new OrderDetails(); 

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetails1);
        orderDetailsList.add(orderDetails2);

        String jsonOrderDetailsList = new ObjectMapper().writeValueAsString(orderDetailsList);
        
        
        when(orderService.createOrder(anyList())).thenReturn(456L);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/createOrder")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonOrderDetailsList)) 
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("456"));
    }

    @Test
    public void getOrderTest() throws Exception {
        Order order = new Order();
        Long orderId = 123L;

        when(orderService.getOrder(orderId)).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/getOrder")
            .param("id", orderId.toString())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
