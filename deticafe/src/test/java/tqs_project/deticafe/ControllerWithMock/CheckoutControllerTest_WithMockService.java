package tqs_project.deticafe.ControllerWithMock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import tqs_project.deticafe.DTO.OrderDetailsDTO;
import tqs_project.deticafe.controller.CheckoutController;
import tqs_project.deticafe.model.Order;
import tqs_project.deticafe.model.OrderDetails;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.OrderRepo;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.OrderService;
import tqs_project.deticafe.service.serviceImpl.CategoryServiceImpl;
import tqs_project.deticafe.service.serviceImpl.OrderServiceImpl;

@WebMvcTest(CheckoutController.class)
public class CheckoutControllerTest_WithMockService {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CheckoutController checkoutController;

    @MockBean
    private OrderServiceImpl orderService;

    @MockBean
    private CategoryServiceImpl service;

    @MockBean
    private CategoryRepo categoryRepo;

    @MockBean
    private ProductRepo productRepo;


    @MockBean
    private OrderRepo orderRepo;

    private Product coffee;
    private Product tea;
    private Product pizza;
    private Order order1;
    private Order order2;
    private Order order3;

    @BeforeEach
    void setUp() {
        // Creating mock Products
        coffee = new Product();
        coffee.setProductId(1L);
        coffee.setName("Coffee");
        coffee.setPrice(2.50);

        tea = new Product();
        tea.setProductId(2L);
        tea.setName("Tea");
        tea.setPrice(1.50);

        pizza = new Product();
        pizza.setProductId(3L);
        pizza.setName("Pizza");
        pizza.setPrice(5.99);

        // Creating OrderDetails instances for each product
        OrderDetails orderDetails1 = new OrderDetails(Arrays.asList("Extra Sugar"), coffee);
        OrderDetails orderDetails2 = new OrderDetails(Arrays.asList("No Sugar"), tea);
        OrderDetails orderDetails3 = new OrderDetails(Arrays.asList("Extra Cheese"), pizza);

        // Creating Orders with the list of OrderDetails
        order1 = new Order(Arrays.asList(orderDetails1));
        order2 = new Order(Arrays.asList(orderDetails2));
        order3 = new Order(Arrays.asList(orderDetails3));
    }

    @Test
    void whenPostOrder_thenShowOrder() {
        // Mocking the behavior of the service method
        when(orderService.save(order1)).thenReturn(order1); // Mocking any Order object

        // Setting up the mockMvc
        mvc = MockMvcBuilders.standaloneSetup(checkoutController).build();

        // Creating a request payload using the initialized mock products and order details
        Map<String, Boolean> orderDetailsMap1 = new HashMap<>();
        orderDetailsMap1.put("cheese", true);
        orderDetailsMap1.put("tomato sauce", true);
        orderDetailsMap1.put("flour", false);
        OrderDetailsDTO orderDetailsDTO1 = new OrderDetailsDTO(3, 1, "Pizza", orderDetailsMap1);

        Map<String, Boolean> orderDetailsMap2 = new HashMap<>();
        orderDetailsMap2.put("sugar", true);
        OrderDetailsDTO orderDetailsDTO2 = new OrderDetailsDTO(1, 2, "Coffee", orderDetailsMap2);

        List<OrderDetailsDTO> orderDetailsList = Arrays.asList(orderDetailsDTO1, orderDetailsDTO2);

        // Performing the request
        try {
            mvc.perform(MockMvcRequestBuilders
                    .post("/api/order/createOrder")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(orderDetailsList)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Verifying that the service method was called with any Order object
        verify(orderService).createOrder(anyList());
    }
}
