package tqs_project.deticafe.ControllerWithMock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import tqs_project.deticafe.DTO.OrderDetailsDTO;
import tqs_project.deticafe.controller.CheckoutController;
import tqs_project.deticafe.model.Order;
import tqs_project.deticafe.model.OrderDetails;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.model.Status;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.OrderRepo;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.OrderService;
import tqs_project.deticafe.service.ProductService;

@WebMvcTest(CheckoutController.class)
public class CheckoutControllerTest_WithMockService {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderRepo orderRepo;

    @MockBean
    private CategoryRepo categoryRepo;

    @MockBean
    private ProductRepo productRepo;    

    @MockBean
    private SimpMessagingTemplate template;

    @InjectMocks
    private CheckoutController checkoutController;

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
    void testGetOrder() throws Exception {
        Order order = new Order(new ArrayList<>());
        order.setOrderId(1L);
        order.setStatus(Status.PREPARING);

        when(orderService.getOrder(1L)).thenReturn(order);

        mockMvc.perform(get("/api/order/getOrder")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1));
    }
}
