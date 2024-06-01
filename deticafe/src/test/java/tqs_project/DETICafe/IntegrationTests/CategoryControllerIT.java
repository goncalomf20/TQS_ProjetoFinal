package tqs_project.deticafe.IntegrationTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.ProductRepo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class CategoryControllerIT {

    @LocalServerPort
    private int port;
    
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductRepo productRepo;
    

    @Autowired
    private TestRestTemplate restTemplate;
    
    @BeforeEach
        void setUp() {

            // Clean up and initialize data
            productRepo.deleteAll();
            categoryRepo.deleteAll();


            Category savedCategory1 = new Category("test1");
            Category savedCategory2 = new Category("test2");
            Category savedCategory3 = new Category("test3");

            categoryRepo.save(savedCategory1); 
            categoryRepo.save(savedCategory2);
            categoryRepo.save(savedCategory3);

            
        }

        @Test
        void whenGetAllCategories_thenReturnAllCategories() {
            ResponseEntity<Category[]> response = restTemplate
                .getForEntity("/api/category/getAllCategories", Category[].class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).hasSize(3);
            assertThat(response.getBody()[0].getName()).isEqualTo("test1");
            assertThat(response.getBody()[1].getName()).isEqualTo("test2");
            assertThat(response.getBody()[2].getName()).isEqualTo("test3");
        }

        @Test
        void whenAddCategory_thenReturnCategory() {
            // Use a Map to hold the request parameters
            Map<String, String> params = new HashMap<>();
            params.put("categoryName", "NewCategory");

            ResponseEntity<Category> response = restTemplate
                .postForEntity("/api/category/addCategory?categoryName={categoryName}", null, Category.class, params);

            System.out.println(response.getStatusCode());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().getName()).isEqualTo("NewCategory");
        }
 
}
