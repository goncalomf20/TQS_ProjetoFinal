package tqs_project.DETICafe.IntegrationTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import tqs_project.DETICafe.DTO.OrderDetailsDTO;
import tqs_project.DETICafe.model.Category;
import tqs_project.DETICafe.model.Product;
import tqs_project.DETICafe.repository.CategoryRepo;
import tqs_project.DETICafe.repository.ProductRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class CheckoutControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        // Clean up and initialize data
        productRepo.deleteAll();
        categoryRepo.deleteAll();

        Category category = new Category("Foods");
        categoryRepo.save(category);  // Ensure the category is saved first

        Product product = new Product("Pizza", List.of("cheese", "tomato sauce", "flour"), 5.99, category);
        product.setProductId(201L);
        productRepo.save(product);
    }

    @Test
    void whenCreateOrder_thenReturnOk() {
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

        given()
                .contentType(ContentType.JSON)
                .body(orderDetailsList)
                .when()
                .post("/api/order/createOrder")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo("ok"));
    }

    @Test
    void whenGetOrder_thenReturnOrder() {
        // Assuming there is an order with ID 1 in the data.sql script
        given()
                .when()
                .get("/api/order/getOrder?id=1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1));
    }
}
