package tqs_project.deticafe.RepositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.ProductRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @BeforeEach
    void setUp() {
        productRepo.deleteAll();
        categoryRepo.deleteAll(); 
    }

    @Test
    void whenFindByName_thenReturnCategory() {
        Category category = new Category("Coffee");
        entityManager.persistAndFlush(category);

        Category found = categoryRepo.findByName(category.getName());

        assertThat(found.getName()).isEqualTo(category.getName());
    }

    @Test
    void whenInvalidName_thenReturnNull() {
        Category found = categoryRepo.findByName("InvalidName");

        assertThat(found).isNull();
    }

    @Test
    void givenSetOfCategories_whenFindAll_thenReturnAllCategories() {
        Category coffee = new Category("Coffee");
        Category pastry = new Category("Pastry");
        Category sandwich = new Category("Sandwiches");

        entityManager.persist(coffee);
        entityManager.persist(pastry);
        entityManager.persist(sandwich);
        entityManager.flush();

        List<Category> allCategories = categoryRepo.findAll();

        assertThat(allCategories).hasSize(3).extracting(Category::getName).containsOnly(coffee.getName(), pastry.getName(), sandwich.getName());
    }
    
}
