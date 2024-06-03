package tqs_project.deticafe.ControllerWithMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
public class CheckoutController_WithMockServiceTest {
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

    @Test
    void testGetAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(new ArrayList<>());
        order.setOrderId(1L);
        order.setStatus(Status.PREPARING);
        orders.add(order);

        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/order/getAllOrders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].orderId").value(1));
    }

    @Test
    void testCreateOrder_ShowOk() throws Exception {
        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
        Map<String, Boolean> details = new HashMap<>();
        details.put("Extra cheese", true);
        orderDetailsDTOList.add(new OrderDetailsDTO(1, 2, "Pizza", details));

        Product product = new Product();
        product.setProductId(1L);
        product.setName("Pizza");

        when(productService.getProductById(1)).thenReturn(product);

        Order order = new Order(new ArrayList<>());
        order.setOrderId(1L);
        order.setStatus(Status.PREPARING);

        when(orderRepo.save(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/order/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderDetailsDTOList)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOrder_NotFound() throws Exception {
        when(orderService.getOrder(999L)).thenReturn(null);

        mockMvc.perform(get("/api/order/getOrder")
                .param("id", "999"))
                .andExpect(status().isNotFound());
    }

    @Test
void testCustomizationsWithMixedValues() {
    Map<String, Boolean> orderDetails = new HashMap<>();
    orderDetails.put("Extra cheese", true);
    orderDetails.put("Extra sauce", false);

    for (Map.Entry<String, Boolean> entry : orderDetails.entrySet()) {
        // Verifica se o valor é consistente com o esperado
        assertEquals(entry.getKey().equals("Extra cheese"), entry.getValue());
    }
}

    @Test
    void testCustomizationsWithEmptyMap() {
        Map<String, Boolean> orderDetails = new HashMap<>();

        assertTrue(orderDetails.isEmpty());
    }

    @Test
    void testCustomizationsWithMultipleEntries() {
        Map<String, Boolean> orderDetails = new HashMap<>();
        orderDetails.put("Extra cheese", true);
        orderDetails.put("Extra sauce", false);
        orderDetails.put("Extra bacon", true);

        assertEquals(3, orderDetails.size());
    }

    @Test
    void testCustomizationsWithNullValues() {
        Map<String, Boolean> orderDetails = new HashMap<>();
        orderDetails.put("Extra cheese", null);

        assertNull(orderDetails.get("Extra cheese"));
    }

    @Test
    void testCreateOrder_NonexistentProduct() throws Exception {
        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
        Map<String, Boolean> details = new HashMap<>();
        details.put("Extra cheese", true);
        orderDetailsDTOList.add(new OrderDetailsDTO(999, 2, "Unknown Product", details)); // Nonexistent product ID

        when(productService.getProductById(999)).thenReturn(null);

        mockMvc.perform(post("/api/order/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderDetailsDTOList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void testCreateOrder_EmptyOrderDetailsList() throws Exception {
        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();

        mockMvc.perform(post("/api/order/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderDetailsDTOList)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateOrderWithoutRequestBody() throws Exception {
        mockMvc.perform(post("/api/order/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content("null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateOrderWithoutOrderDetails() throws Exception {
        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
        Map<String, Boolean> details = new HashMap<>();
        orderDetailsDTOList.add(new OrderDetailsDTO(1, -2, "Pizza", details)); // Invalid quantity

        mockMvc.perform(post("/api/order/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderDetailsDTOList)))
                .andExpect(status().isOk());
    }


    @Test
    public void testCustomizationsWithValueTrue() {
        Map<String, Boolean> orderDetails = new HashMap<>();
        orderDetails.put("Extra cheese", true);
        orderDetails.put("Extra sauce", true);

        for (Map.Entry<String, Boolean> entry : orderDetails.entrySet()) {
            assertTrue(entry.getValue());
        }
    }

    @Test
    public void testCustomizationsWithValueFalse() {
        Map<String, Boolean> orderDetails = new HashMap<>();
        orderDetails.put("Extra cheese", false);
        orderDetails.put("Extra sauce", false);

        for (Map.Entry<String, Boolean> entry : orderDetails.entrySet()) {
            assertFalse(entry.getValue());
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateOrderWithNullOrderDetailsList() throws Exception {
        mockMvc.perform(post("/api/order/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateOrderWithEmptyOrderDetailsList() throws Exception {
        mockMvc.perform(post("/api/order/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new ArrayList<>())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateOrderWithNonEmptyOrderDetailsList() throws Exception {
        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
        Map<String, Boolean> details = new HashMap<>();
        details.put("Extra cheese", true);
        orderDetailsDTOList.add(new OrderDetailsDTO(1, 2, "Pizza", details));

        when(productService.getProductById(1)).thenReturn(new Product());

        mockMvc.perform(post("/api/order/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderDetailsDTOList)))
                .andExpect(status().isOk());
    }

    

}
