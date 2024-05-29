package tqs_project.DETICafe.UnitTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs_project.DETICafe.model.Category;
import tqs_project.DETICafe.repository.CategoryRepo;
import tqs_project.DETICafe.service.serviceImpl.CategoryServiceImpl;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetAllCategories_thenReturnCategoryList() {
        // Arrange
        Category category1 = new Category();
        category1.setCategoryId(1L);
        category1.setName("Drinks");

        Category category2 = new Category();
        category2.setCategoryId(2L);
        category2.setName("Snacks");

        List<Category> expectedCategories = Arrays.asList(category1, category2);
        when(categoryRepo.findAll()).thenReturn(expectedCategories);

        // Act
        List<Category> actualCategories = categoryService.getAllCategories();

        // Assert
        assertThat(actualCategories).isEqualTo(expectedCategories);
    }

    @Test
    public void whenAddCategory_thenReturnSavedCategory() {
        // Arrange
        Category category = new Category();
        category.setCategoryId(3L);
        category.setName("Desserts");

        when(categoryRepo.save(category)).thenReturn(category);

        // Act
        Category savedCategory = categoryService.addCategory(category);

        // Assert
        assertThat(savedCategory).isEqualTo(category);
    }
}
