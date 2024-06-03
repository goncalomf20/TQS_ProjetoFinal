package tqs_project.deticafe.UnitTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

public class CategoryService_UnitTest {

    @Mock(lenient = true)
    private CategoryRepo categoryRepo;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetAllCategories_thenReturnCategoryList() {
        Category category1 = new Category();
        category1.setCategoryId(1L);
        category1.setName("Drinks");

        Category category2 = new Category();
        category2.setCategoryId(2L);
        category2.setName("Snacks");

        List<Category> expectedCategories = Arrays.asList(category1, category2);
        when(categoryRepo.findAll()).thenReturn(expectedCategories);

        List<Category> actualCategories = categoryService.getAllCategories();

        assertThat(actualCategories).isEqualTo(expectedCategories);
        assertThat(actualCategories).hasSize(2);
        assertThat(actualCategories.get(0).getName()).isEqualTo("Drinks");
        assertThat(actualCategories.get(1).getName()).isEqualTo("Snacks");
    }

    @Test
    public void whenAddCategory_thenReturnSavedCategory() {
        Category savedCategory = categoryService.addCategory("Desserts");

        assertNotNull(savedCategory);
        assertThat(savedCategory.getName()).isEqualTo("Desserts");

        verify(categoryRepo, times(1)).save(any(Category.class));
    }

    @Test
    public void whenSaveCategory_thenReturnSavedCategory() {
        String categoryName = "Drinks";
        Category category = new Category(categoryName);
        when(categoryRepo.save(any(Category.class))).thenReturn(category);

        Category savedCategory = categoryService.save(categoryName);

        assertThat(savedCategory.getName()).isEqualTo(categoryName);
        verify(categoryRepo, times(1)).save(any(Category.class));
    }


    @Test
    public void testSaveCategory_NullName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.save(null);
        });

        assertEquals("Category name cannot be null or empty", exception.getMessage());

        verify(categoryRepo, never()).save(any(Category.class));
    }

    @Test
    public void testSaveCategory_EmptyName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.save("");
        });

        assertEquals("Category name cannot be null or empty", exception.getMessage());

        verify(categoryRepo, never()).save(any(Category.class));
    }

    @Test
    public void whenAddCategoryWithNullName_thenThrowException() {
        String categoryName = null;

        assertThatThrownBy(() -> categoryService.addCategory(categoryName))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Category name cannot be null or empty");
    }

    @Test
    public void whenAddCategoryWithEmptyName_thenThrowException() {
        String categoryName = "";

        assertThatThrownBy(() -> categoryService.addCategory(categoryName))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Category name cannot be null or empty");
    }
}