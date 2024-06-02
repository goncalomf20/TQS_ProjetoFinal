package tqs_project.deticafe.ControllerWithMock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import tqs_project.deticafe.DTO.OrderDetailsDTO;
import tqs_project.deticafe.controller.CheckoutController;
import tqs_project.deticafe.model.Order;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.OrderRepo;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.serviceImpl.ProductServiceImpl;

@WebMvcTest(CheckoutController.class)
public class CheckoutControllerTest_WithMockService {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @MockBean
    private OrderRepo orderRepo;

    // Additional mock beans if needed
    @MockBean
    private CategoryRepo categoryRepo;

    @MockBean
    private ProductRepo productRepo;

    private Product coffee;
    private Product pizza;

    @BeforeEach
    void setUp() {
        // Creating mock Products
        coffee = new Product();
        coffee.setProductId(1L);
        coffee.setName("Coffee");
        coffee.setPrice(2.50);

        pizza = new Product();
        pizza.setProductId(3L);
        pizza.setName("Pizza");
        pizza.setPrice(5.99);
    }

    @Test
    public void testCreateOrder() throws Exception {
        // Prepare test data
        Map<String, Boolean> orderDetailsMap1 = new HashMap<>();
        orderDetailsMap1.put("cheese", true);
        orderDetailsMap1.put("tomato sauce", true);
        orderDetailsMap1.put("flour", false);

        OrderDetailsDTO orderDetailsDTO1 = new OrderDetailsDTO();
        orderDetailsDTO1.setFoodId(3);
        orderDetailsDTO1.setOrderDetails(orderDetailsMap1);
        orderDetailsDTO1.setName("Pizza");
        orderDetailsDTO1.setQuantity(1);

        Map<String, Boolean> orderDetailsMap2 = new HashMap<>();
        orderDetailsMap2.put("sugar", true);

        OrderDetailsDTO orderDetailsDTO2 = new OrderDetailsDTO();
        orderDetailsDTO2.setFoodId(1);
        orderDetailsDTO2.setOrderDetails(orderDetailsMap2);
        orderDetailsDTO2.setName("Coffee");
        orderDetailsDTO2.setQuantity(2);

        List<OrderDetailsDTO> orderDetailsList = Arrays.asList(orderDetailsDTO1, orderDetailsDTO2);

        // Mock the behavior of ProductService and OrderRepo
        Mockito.when(productService.getProductById(3)).thenReturn(pizza);
        Mockito.when(productService.getProductById(1)).thenReturn(coffee);

        Order order = new Order();
        order.setOrderId(1L);

        Mockito.when(orderRepo.save(Mockito.any(Order.class))).thenReturn(order);

        // Perform the POST request
        mockMvc.perform(post("/api/order/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderDetailsList)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
