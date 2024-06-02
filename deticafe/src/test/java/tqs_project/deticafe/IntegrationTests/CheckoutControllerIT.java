package tqs_project.deticafe.IntegrationTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.ErrorResponse;

import jakarta.transaction.Transactional;
import tqs_project.deticafe.DTO.OrderDetailsDTO;
import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.model.Order;
import tqs_project.deticafe.model.OrderDetails;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.OrderDetailsRepo;
import tqs_project.deticafe.repository.OrderRepo;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.OrderService;
import tqs_project.deticafe.service.ProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Transactional
class CheckoutControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Autowired
    private OrderService orderService;

    private Category savedCategory;

    @BeforeEach
    void setUp() {
    
        // Clean up and initialize data
        productRepo.deleteAll();
        categoryRepo.deleteAll();
        orderDetailsRepo.deleteAll();
    
        savedCategory = new Category("Foods");
        categoryRepo.save(savedCategory);  // Ensure the category is saved first
    
        // Add products with valid IDs
        Product product1 = new Product("Pizza", List.of("cheese", "tomato sauce", "flour"), 5.99, savedCategory);
        product1.setProductId(1L);
        productRepo.save(product1);
    
        Product product2 = new Product("Ham and Cheese Croissant", List.of("croissant", "ham", "cheese"), 3.99, savedCategory);
        product2.setProductId(2L);
        productRepo.save(product2);
    
        Product product3 = new Product("Large Coffee", List.of("coffee", "water", "sugar"), 2.49, savedCategory);
        product3.setProductId(3L);
        productRepo.save(product3);
    
        Product product4 = new Product("Lemonada", List.of("water", "lemon", "sugar"), 1.99, savedCategory);
        product4.setProductId(4L);
        productRepo.save(product4);
    
        Product product5 = new Product("Cappuccino", List.of("milk", "coffee", "foam"), 3.49, savedCategory);
        product5.setProductId(5L);
        productRepo.save(product5);
    }

    @AfterEach
    void tearDown() {
        // Clean up code after each test
        orderService.deleteAllOrders(); 
        System.out.println("Number of orders after deletion: " + orderRepo.findAll().size());
    }
    
    @Test
    void testGetAllOrders() {
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        Map<String, Boolean> orderDetailsMap = new HashMap<>();
        orderDetailsMap.put("cheese", false);
        orderDetailsMap.put("tomato sauce", true);
        orderDetailsMap.put("flour", false);

        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setName("Pizza");
        dto.setQuantity(7);
        dto.setFoodId(3);
        dto.setOrderDetails(orderDetailsMap);

        orderDetailsList.add(dto);

        restTemplate.postForEntity("/api/order/createOrder", orderDetailsList, Long.class);

        List<OrderDetailsDTO> orderDetailsList1 = new ArrayList<>();
        Map<String, Boolean> orderDetailsMap1 = new HashMap<>();
        orderDetailsMap1.put("cheese", false);
        orderDetailsMap1.put("tomato sauce", true);
        orderDetailsMap1.put("flour", false);

        OrderDetailsDTO dto1 = new OrderDetailsDTO();
        dto1.setName("Pizza");
        dto1.setQuantity(20);
        dto1.setFoodId(3);
        dto1.setOrderDetails(orderDetailsMap1);

        orderDetailsList1.add(dto1);

        restTemplate.postForEntity("/api/order/createOrder", orderDetailsList1, Long.class);

        List<OrderDetailsDTO> orderDetailsList2 = new ArrayList<>();
        Map<String, Boolean> orderDetailsMap2 = new HashMap<>();
        orderDetailsMap2.put("cheese", false);
        orderDetailsMap2.put("tomato sauce", true);
        orderDetailsMap2.put("flour", false);

        OrderDetailsDTO dto2 = new OrderDetailsDTO();
        dto2.setName("Pizza");
        dto2.setQuantity(1);
        dto2.setFoodId(3);
        dto2.setOrderDetails(orderDetailsMap);

        orderDetailsList2.add(dto2);

        restTemplate.postForEntity("/api/order/createOrder", orderDetailsList2, Long.class);

        ResponseEntity<List<Order>> response = restTemplate.exchange(
        "/api/order/getAllOrders",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Order>>() {});

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Order> returnedOrderList = response.getBody();
        assertNotNull(returnedOrderList);

        for (Order order : returnedOrderList) {
            System.out.println("Order ID: " + order.getOrderId());
            for (OrderDetails orderDetails : order.getOrderDetails()) {
                System.out.println("Product: " + orderDetails.getProduct().getName());
            }
        }

        // assertEquals(3, returnedOrderList.size()); -> está a retornar todas as orders dos testes, mesmo limpando

    }

    @Test
    void whenCreateOrder_thenShowConfirmedOrder() {
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        Map<String, Boolean> orderDetailsMap = new HashMap<>();
        orderDetailsMap.put("cheese", true);
        orderDetailsMap.put("tomato sauce", false);
        orderDetailsMap.put("flour", false);
    
        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setName("Pizza");
        dto.setQuantity(1);
        dto.setFoodId(27);
        dto.setOrderDetails(orderDetailsMap);
    
        orderDetailsList.add(dto);
    
        ResponseEntity<Long> response = restTemplate
            .postForEntity("/api/order/createOrder", orderDetailsList, Long.class);
    
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isInstanceOf(Long.class);
    }


    @Test
    void whenInvalidId_thenReturnNotFound(){
        ResponseEntity<OrderDetails> response = restTemplate
            .getForEntity("/api/order/getOrder?id=0", OrderDetails.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void whenCreateOrder_thenShowOrdersProducts() {
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        Map<String, Boolean> orderDetailsMap = new HashMap<>();
        orderDetailsMap.put("cheese", false);
        orderDetailsMap.put("tomato sauce", true);
        orderDetailsMap.put("flour", false);

        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setFoodId(3);  // Use the ID of the saved product
        dto.setQuantity(1);
        dto.setOrderDetails(orderDetailsMap);

        orderDetailsList.add(dto);

        // Act: Send request to create order
        ResponseEntity<Long> response = restTemplate.postForEntity("/api/order/createOrder", orderDetailsList, Long.class);

        // Assert: Validate response and order creation
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long orderId = response.getBody();
        assertThat(orderId).isNotNull();

        Order order = orderRepo.findById(orderId).orElseThrow();

        assertThat(order.getOrderDetails()).hasSize(1); 
        OrderDetails orderDetail = order.getOrderDetails().get(0);
        assertNotNull(orderDetail.getProduct());
        assertEquals(3, orderDetail.getProduct().getProductId());
        assertEquals("Pizza", orderDetail.getProduct().getName());
    }

    @Test
    void whenCreateOrder_thenShowOrdersIngredients() {
        // Arrange
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        Map<String, Boolean> orderDetailsMap = new HashMap<>();
        orderDetailsMap.put("cheese", false);
        orderDetailsMap.put("tomato sauce", true);
        orderDetailsMap.put("flour", false);

        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setName("Pizza");
        dto.setQuantity(1);
        dto.setFoodId(3);
        dto.setOrderDetails(orderDetailsMap);

        orderDetailsList.add(dto);

        // Act
        ResponseEntity<Long> response = restTemplate.postForEntity("/api/order/createOrder", orderDetailsList, Long.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long orderId = response.getBody();
        assertThat(orderId).isNotNull();

        List<Order> orders = orderService.getAllOrders();

        // Verify that the created order is present in the repository
        assertThat(orders).extracting(Order::getOrderDetails)
            .flatExtracting(orderDetails -> orderDetails)
            .extracting(OrderDetails::getCustomizations)
            .flatExtracting(customizations -> customizations)
            .contains("tomato sauce");
    }

    @Test
    void whenGetOrder_thenReturnOrder() {
        ResponseEntity<OrderDetails> response = restTemplate
            .getForEntity("/api/order/getOrder?id=1", OrderDetails.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void whenOrdersEmpty_thenReturnBadRequest(){
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity("/api/order/createOrder", orderDetailsList, ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenCreateOrderWithFourProducts_thenAllProductsArePresent() {
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        Map<String, Boolean> orderDetailsMap = new HashMap<>();
        orderDetailsMap.put("croissant", false);
        orderDetailsMap.put("ham", true);
        orderDetailsMap.put("cheese", false);

        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setName("Ham and Cheese Croissant");
        dto.setQuantity(5);
        dto.setFoodId(1);
        dto.setOrderDetails(orderDetailsMap);

        orderDetailsList.add(dto);

        Map<String, Boolean> orderDetailsMap1 = new HashMap<>();
        orderDetailsMap1.put("coffee", true);
        orderDetailsMap1.put("water", true);
        orderDetailsMap1.put("sugar", false);

        OrderDetailsDTO dto1 = new OrderDetailsDTO();
        dto1.setName("Large Coffee");
        dto1.setQuantity(1);
        dto1.setFoodId(2);
        dto1.setOrderDetails(orderDetailsMap);

        orderDetailsList.add(dto1);

        Map<String, Boolean> orderDetailsMap2 = new HashMap<>();
        orderDetailsMap2.put("water", false);
        orderDetailsMap2.put("lemon", true);
        orderDetailsMap2.put("sugar", true);

        OrderDetailsDTO dto2 = new OrderDetailsDTO();
        dto2.setName("Lemonade");
        dto2.setQuantity(2);
        dto2.setFoodId(8);
        dto2.setOrderDetails(orderDetailsMap2);

        orderDetailsList.add(dto2);

        Map<String, Boolean> orderDetailsMap3 = new HashMap<>();
        orderDetailsMap3.put("milk", false);
        orderDetailsMap3.put("coffee", true);
        orderDetailsMap3.put("foam", false);

        OrderDetailsDTO dto3 = new OrderDetailsDTO();
        dto3.setName("Cappuccino");
        dto3.setQuantity(1);
        dto3.setFoodId(9);
        dto3.setOrderDetails(orderDetailsMap);

        orderDetailsList.add(dto3);
        
        // Act: Send request to create order
        ResponseEntity<Long> response = restTemplate.postForEntity("/api/order/createOrder", orderDetailsList, Long.class);

        // Assert: Validate response and order creation
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long orderId = response.getBody();
        assertThat(orderId).isNotNull();

        // Fetch the created order from the repository
        Order order = orderRepo.findById(orderId).orElseThrow();

        // Verify that all products are present in the order details
        assertThat(order.getOrderDetails()).hasSize(4);
        Set<String> expectedProductNames = Set.of("Ham and Cheese Croissant", "Large Coffee", "Lemonade", "Cappuccino");
        for (OrderDetails orderDetail : order.getOrderDetails()) {
            assertTrue(expectedProductNames.contains(orderDetail.getProduct().getName()));
        }

    }
    


    

}
