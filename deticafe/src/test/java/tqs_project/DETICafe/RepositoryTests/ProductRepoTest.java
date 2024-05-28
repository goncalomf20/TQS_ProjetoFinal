package tqs_project.DETICafe.RepositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs_project.DETICafe.model.Product;
import tqs_project.DETICafe.repository.ProductRepo;
import tqs_project.DETICafe.model.Category;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepo productRepo;

    @BeforeEach
    void setUp() {
        productRepo.deleteAll(); 
    }

    @Test
    void whenFindByName_thenReturnProduct() {
        Category category = new Category("Coffee");
        entityManager.persistAndFlush(category);
        Product product = new Product("Short Coffee", List.of("water", "sugar", "coffee"), 1.0, category);
        entityManager.persistAndFlush(product);

        Product found = productRepo.findByName(product.getName());

        assertThat(found.getName()).isEqualTo(product.getName());
    }

    @Test
    void whenInvalidName_thenReturnNull() {
        Product found = productRepo.findByName("InvalidName");

        assertThat(found).isNull();
    }

    @Test
    void givenSetOfProducts_whenFindAll_thenReturnAllProducts() {
        Category coffee = new Category("Coffee");
        Category pastry = new Category("Pastry");
        Category sandwich = new Category("Sandwiches");

        entityManager.persist(coffee);
        entityManager.persist(pastry);
        entityManager.persist(sandwich);
        entityManager.flush();

        Product product1 = new Product("Large Coffee", List.of("water", "sugar", "coffee"), 1.0, coffee);
        Product product2 = new Product("Ham and Cheese Croissant", List.of("croissant", "ham", "cheese"), 3.0, pastry);
        Product product3 = new Product("Tuna Sandwich", List.of("tuna", "bread", "lettuce", "tomato"), 4.0, sandwich);

        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.persist(product3);
        entityManager.flush();

        List<Product> allProducts = productRepo.findAll();

        assertThat(allProducts).hasSize(3).extracting(Product::getName).containsOnly(product1.getName(), product2.getName(), product3.getName());
    }


    
}
