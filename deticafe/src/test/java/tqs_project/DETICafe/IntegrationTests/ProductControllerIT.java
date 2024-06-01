package tqs_project.deticafe.IntegrationTests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.ProductRepo;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class ProductControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        // Clean up and initialize data
        productRepo.deleteAll();
        categoryRepo.deleteAll();

        List<String> ingredients1 = List.of("cheese", "tomato sauce", "flour");
        List<String> ingredients2 = List.of("cheese", "tomato sauce", "flour");
        List<String> ingredients3 = List.of("cheese", "tomato sauce", "flour");

        Category savedCategory = new Category("Foods");
        categoryRepo.save(savedCategory);

        Product savedProduct1 = new Product("Product1", ingredients1, 10.0, savedCategory);
        Product savedProduct2 = new Product("Product2", ingredients2, 20.0, savedCategory);
        Product savedProduct3 = new Product("Product3", ingredients3, 30.0, savedCategory);

        productRepo.save(savedProduct1);
        productRepo.save(savedProduct2);
        productRepo.save(savedProduct3);


    }

    @Test
    void whenGetAllProducts_thenReturnAllProducts() {
        ResponseEntity<Product[]> response = restTemplate
            .getForEntity("/api/products/getAllProducts", Product[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(3);
        assertThat(response.getBody()[0].getName()).isEqualTo("Product1");
        assertThat(response.getBody()[1].getName()).isEqualTo("Product2");
        assertThat(response.getBody()[2].getName()).isEqualTo("Product3");
    }

    @Test
    void whenAddProduct_thenReturnProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Criação dos parâmetros de URL codificados
        String params = "productName=NewProduct&productDescription=NewDescription&productPrice=40.0&productCategory=Foods";

        HttpEntity<String> request = new HttpEntity<>(params, headers);

        ResponseEntity<Product> response = restTemplate.postForEntity("/api/products/addProduct", request, Product.class);

        System.out.println(response.getStatusCode());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("NewProduct");
    }

    @Test
    void whenAddProductWithWrongCategory_thenBadRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Criação dos parâmetros de URL codificados
        String params = "productName=NewProduct&productDescription=NewDescription&productPrice=40.0&productCategory=WrongCategory";

        HttpEntity<String> request = new HttpEntity<>(params, headers);

        ResponseEntity<Product> response = restTemplate.postForEntity("/api/products/addProduct", request, Product.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }
}
