package tqs_project.deticafe.UnitTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.service.serviceImpl.CategoryServiceImpl;

public class CategoryServiceIMPL_UnitTest {

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
    // test to add a category by inserting the category name and returning the saved category
    public void whenAddCategory_thenReturnSavedCategory() {
        // Arrange
        
        // Act
        Category savedCategory = categoryService.addCategory("Desserts");

        // Assert
        assertThat(savedCategory.getName()).isEqualTo("Desserts");
        
    }

    @Test
    public void whenSaveCategory_thenReturnSavedCategory() {
        // Arrange
        String categoryName = "Beverages";
        Category category = new Category(categoryName);
        when(categoryRepo.save(any(Category.class))).thenReturn(category);

        // Act
        Category savedCategory = categoryService.save(categoryName);

        // Assert
        assertThat(savedCategory.getName()).isEqualTo(categoryName);
        verify(categoryRepo, times(1)).save(any(Category.class));
    }

    @Test
    public void whenAddCategoryWithNullName_thenThrowException() {
        // Arrange
        String categoryName = null;

        // Act & Assert
        assertThatThrownBy(() -> categoryService.addCategory(categoryName))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Category name cannot be null or empty");
    }

    @Test
    public void whenAddCategoryWithEmptyName_thenThrowException() {
        // Arrange
        String categoryName = "";

        // Act & Assert
        assertThatThrownBy(() -> categoryService.addCategory(categoryName))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Category name cannot be null or empty");
    }
}