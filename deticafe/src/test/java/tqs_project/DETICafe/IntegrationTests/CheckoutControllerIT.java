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
import tqs_project.deticafe.model.OrderDetails;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.OrderDetailsRepo;
import tqs_project.deticafe.repository.ProductRepo;

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
    void whenGetOrder_thenReturnOrder() {
        ResponseEntity<OrderDetails> response = restTemplate
            .getForEntity("/api/order/getOrder?id=1", OrderDetails.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
