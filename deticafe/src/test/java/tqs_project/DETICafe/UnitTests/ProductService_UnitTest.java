package tqs_project.DETICafe.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs_project.DETICafe.repository.ProductRepo;
import tqs_project.DETICafe.service.serviceImpl.ProductServiceImpl;
import tqs_project.DETICafe.model.Category;
import tqs_project.DETICafe.model.Product;


@ExtendWith(MockitoExtension.class)
class ProductService_UnitTest {
    
    @Mock(lenient = true)
    private ProductRepo product_repository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        Product product1 = new Product(1L,"Large Coffee", Arrays.asList("coffee", "water", "sugar"), 1.99, new Category("Coffee"));
        Product product2 = new Product(2L,"Ham and Cheese Croissant", Arrays.asList("croissant", "ham", "cheese"), 3.99, new Category("Pastry"));

        List<Product> products = Arrays.asList(product1, product2);

        when(product_repository.findByProductId(1L)).thenReturn(product1);
        when(product_repository.findByProductId(2L)).thenReturn(product2);
        when(product_repository.findByProductId(0)).thenReturn(null);
        when(product_repository.findByName("Large Coffee")).thenReturn(product1);
        when(product_repository.findByName("Ham and Cheese Croissant")).thenReturn(product2);
        when(product_repository.findAll()).thenReturn(products);
    }


    @Test
    @DisplayName("Test getProducts")
    @Disabled("Not implemented yet")
    void testGetProducts() {
        List<Product> found = productService.getAllProducts();
        assertNotNull(found);
        assertEquals(2, found.size());
    }


    @Test
    @DisplayName("Test getProductById with valid id")
    void testGetProductByIdValidId() {
        int id = 1;
        Product found = productService.getProductById(id);
        assertNotNull(found);
        assertEquals(id, found.getProductId());
        assertEquals("Large Coffee", found.getName());
        assertEquals(Arrays.asList("coffee", "water", "sugar"), found.getIngredients());
        assertEquals(1.99, found.getPrice());
        assertEquals("Coffee", found.getCategory().getName());
    }

    @Test
    @DisplayName("Test getProductById with invalid id")
    void testGetProductByIdInvalidId() {
        int id = 0;
        Product found = productService.getProductById(id);
        assertEquals(null, found);
    }

    @Test
    @Disabled("Not implemented yet")
    @DisplayName("Test getProductByName with valid name")
    void testGetProductByNameValidName() {
        String name = "Ham and Cheese Croissant";
        Product found = productService.getProductByName(name);
        assertNotNull(found);
        assertEquals(name, found.getName());
        assertEquals(2L, found.getProductId());
        assertEquals(Arrays.asList("croissant", "ham", "cheese"), found.getIngredients());
        assertEquals(3.99, found.getPrice());
        assertEquals("Pastry", found.getCategory().getName());
    }

    @Test
    @Disabled("Not implemented yet")
    @DisplayName("Test getProductByName with invalid name")
    void testGetProductByNameInvalidName() {
        String name = "Invalid Product";
        Product found = productService.getProductByName(name);
        assertEquals(null, found);
    }
}
