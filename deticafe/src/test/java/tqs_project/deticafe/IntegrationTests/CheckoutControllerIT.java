package tqs_project.deticafe.IntegrationTests;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import tqs_project.deticafe.DTO.OrderDetailsDTO;
import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.model.Order;
import tqs_project.deticafe.model.OrderDetails;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.OrderDetailsRepo;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.OrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
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
    private OrderDetailsRepo orderDetailsRepo;


    @Autowired
    private OrderService orderService;

    private Category savedCategory;

    @BeforeEach
    void setUp() {

        // Clean up and initialize data
        productRepo.deleteAll();
        categoryRepo.deleteAll();

        savedCategory = new Category("Foods");
        categoryRepo.save(savedCategory);  // Ensure the category is saved first

        Product product = new Product("Pizza", List.of("cheese", "tomato sauce", "flour"), 5.99, savedCategory);
        product.setProductId(201L);
        productRepo.save(product);
    }

    @Test
    void whenCreateOrder_thenShowConfirmedOrder() {
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        Map<String, Boolean> orderDetailsMap = new HashMap<>();
        orderDetailsMap.put("Extra cheese", true);
        orderDetailsMap.put("Spicy", false);
    
        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setName("Pizza");
        dto.setQuantity(1);
        dto.setFoodId(201);
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
            .getForEntity("/api/order/getOrder?id=90", OrderDetails.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void whenCreateOrder_thenShowOrdersProducts() {
        // Arrange: Set up test data
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        Map<String, Boolean> orderDetailsMap = new HashMap<>();
        orderDetailsMap.put("Extra cheese", true);
        orderDetailsMap.put("Spicy", false);
    
        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setName("Pizza");
        dto.setQuantity(1);
        dto.setFoodId(201);
        dto.setOrderDetails(orderDetailsMap);
    
        orderDetailsList.add(dto);
    
        // Act: Send request to create order
        ResponseEntity<Long> response = restTemplate.postForEntity("/api/order/createOrder", orderDetailsList, Long.class);
    
        // Assert: Validate response and order creation
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long orderId = response.getBody();
        assertThat(orderId).isNotNull();
    
        // Fetch all orders and print them for debugging
        List<OrderDetails> found = orderDetailsRepo.findAll();
        found.forEach(orderDetails -> System.out.println("Found OrderDetails: " + orderDetails.getProduct().getName()));
    
        // Verify that the created order is present in the repository
        assertThat(found)
            .extracting(orderDetails -> orderDetails.getProduct().getName())
            .contains("Pizza");
    }
    
    @Test
    @Disabled
    void whenCreateOrder_thenShowOrdersIngredients() {
        // Arrange
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        Map<String, Boolean> orderDetailsMap = new HashMap<>();
        orderDetailsMap.put("Extra cheese", true);
        orderDetailsMap.put("Spicy", false);

        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setName("Pizza");
        dto.setQuantity(1);
        dto.setFoodId(201);
        dto.setOrderDetails(orderDetailsMap);

        orderDetailsList.add(dto);

        // Act
        ResponseEntity<Long> response = restTemplate.postForEntity("/api/order/createOrder", orderDetailsList, Long.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long orderId = response.getBody();
        assertThat(orderId).isNotNull();

        // Fetch all orders using the service to avoid LazyInitializationException
        List<Order> orders = orderService.getAllOrders();
        orders.forEach(order -> {
            System.out.println("Found Order: " + order.getOrderId());
            order.getOrderDetails().forEach(orderDetails -> {
                System.out.println("OrderDetails: " + orderDetails.getProduct().getName() + " - " + orderDetails.getCustomizations());
            });
        });

        // Verify that the created order is present in the repository
        assertThat(orders).extracting(Order::getOrderDetails)
            .flatExtracting(orderDetails -> orderDetails)
            .extracting(OrderDetails::getCustomizations)
            .flatExtracting(customizations -> customizations)
            .contains("Extra cheese");
    }

    @Test
    void whenGetOrder_thenReturnOrder() {
        ResponseEntity<OrderDetails> response = restTemplate
            .getForEntity("/api/order/getOrder?id=1", OrderDetails.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

        // @Test
    // @Disabled
    // void whenGetAllOrders_thenStatus200() {
    //     List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
    //     Map<String, Boolean> orderDetailsMap = new HashMap<>();
    //     orderDetailsMap.put("Extra wasabi", true);
    //     orderDetailsMap.put("Soy sauce", true);

    //     OrderDetailsDTO dtoSushi1 = new OrderDetailsDTO();
    //     dtoSushi1.setName("Sushi de Salmão");
    //     dtoSushi1.setQuantity(8);
    //     dtoSushi1.setFoodId(303); // Id do Sushi de Salmão
    //     dtoSushi1.setOrderDetails(orderDetailsMap);
    //     orderDetailsList.add(dtoSushi1);

    //     OrderDetailsDTO dtoSushi2 = new OrderDetailsDTO();
    //     dtoSushi2.setName("Sushi de Atum");
    //     dtoSushi2.setQuantity(8);
    //     dtoSushi2.setFoodId(304); // Id do Sushi de Atum
    //     dtoSushi2.setOrderDetails(orderDetailsMap);
    //     orderDetailsList.add(dtoSushi2);

    //     List<OrderDetailsDTO> orderDetailsList1 = new ArrayList<>();
    //     Map<String, Boolean> orderDetailsMap1 = new HashMap<>();
    //     orderDetailsMap1.put("Extra croutons", false);
    //     orderDetailsMap1.put("Extra dressing", true);

    //     OrderDetailsDTO dtoSalad = new OrderDetailsDTO();
    //     dtoSalad.setName("Salada Caesar");
    //     dtoSalad.setQuantity(1);
    //     dtoSalad.setFoodId(305); // Id da Salada Caesar
    //     dtoSalad.setOrderDetails(orderDetailsMap);
    //     orderDetailsList1.add(dtoSalad);

    //     OrderDetailsDTO dtoJuice = new OrderDetailsDTO();
    //     dtoJuice.setName("Suco de Laranja");
    //     dtoJuice.setQuantity(1);
    //     dtoJuice.setFoodId(306); // Id do Suco de Laranja
    //     dtoJuice.setOrderDetails(orderDetailsMap);
    //     orderDetailsList1.add(dtoJuice);

    //     List<OrderDetailsDTO> orderDetailsList2 = new ArrayList<>();
    //     Map<String, Boolean> orderDetailsMap2 = new HashMap<>();
    //     orderDetailsMap2.put("Extra cheese", false);
    //     orderDetailsMap2.put("Spicy", true);

    //     OrderDetailsDTO dtoBurger = new OrderDetailsDTO();
    //     dtoBurger.setName("Hambúrguer");
    //     dtoBurger.setQuantity(1);
    //     dtoBurger.setFoodId(301); // Id do Hambúrguer
    //     dtoBurger.setOrderDetails(orderDetailsMap);
    //     orderDetailsList2.add(dtoBurger);

    //     OrderDetailsDTO dtoFries = new OrderDetailsDTO();
    //     dtoFries.setName("Batata Frita");
    //     dtoFries.setQuantity(1);
    //     dtoFries.setFoodId(302); // Id da Batata Frita
    //     dtoFries.setOrderDetails(orderDetailsMap);
    //     orderDetailsList2.add(dtoFries);

    //     orderDetailsRepo.saveAndFlush(orderDetailsList);
    //     orderDetailsRepo.saveAndFlush(orderDetailsList1);
    //     orderDetailsRepo.saveAndFlush(orderDetailsList2);

    //     ResponseEntity<List<OrderDetails>> response = restTemplate
    //         .exchange("/api/orders", HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderDetails>>() {});

    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    //     assertThat(response.getBody()).extracting(OrderDetails::getProduct).containsExactly("Sushi de Salmão", "c3");
    // }

}
