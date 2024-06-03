package tqs_project.deticafe.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductService_UnitTest {
    
    @Mock
    private ProductRepo product_repository;

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    private Category coffeeCategory;
    private Category pastryCategory;
    private Product product1;
    private Product product2;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        coffeeCategory = new Category("Coffee");
        pastryCategory = new Category("Pastry");

        product1 = new Product(1L, "Large Coffee", Arrays.asList("coffee", "water", "sugar"), 1.99, coffeeCategory);
        product2 = new Product(2L, "Ham and Cheese Croissant", Arrays.asList("croissant", "ham", "cheese"), 3.99, pastryCategory);

        products = Arrays.asList(product1, product2);
    }

    @Test
    @DisplayName("Test getProducts")
    void testGetProducts() {
        when(product_repository.findAll()).thenReturn(products);

        List<Product> found = productService.getAllProducts();
        assertNotNull(found);
        assertEquals(2, found.size());

        verify(product_repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test getProductById with valid id")
    void testGetProductByIdValidId() {
        int id = 1;
        when(product_repository.findByProductId(1L)).thenReturn(product1);

        Product found = productService.getProductById(id);
        assertNotNull(found);
        assertEquals(id, found.getProductId());
        assertEquals("Large Coffee", found.getName());
        assertEquals(Arrays.asList("coffee", "water", "sugar"), found.getIngredients());
        assertEquals(1.99, found.getPrice());
        assertEquals("Coffee", found.getCategory().getName());

        verify(product_repository, times(1)).findByProductId(1L);
    }

    @Test
    @DisplayName("Test getProductById with invalid id")
    void testGetProductByIdInvalidId() {
        int id = 0;
        when(product_repository.findByProductId(0L)).thenReturn(null);

        Product found = productService.getProductById(id);
        assertEquals(null, found);

        verify(product_repository, times(1)).findByProductId(0L);
    }

    @Test
    @DisplayName("Test getProductByName with valid name")
    void testGetProductByNameValidName() {
        String name = "Ham and Cheese Croissant";
        when(product_repository.findByName(name)).thenReturn(product2);

        Product found = productService.getProductByName(name);
        assertNotNull(found);
        assertEquals(name, found.getName());
        assertEquals(2L, found.getProductId());
        assertEquals(Arrays.asList("croissant", "ham", "cheese"), found.getIngredients());
        assertEquals(3.99, found.getPrice());
        assertEquals("Pastry", found.getCategory().getName());

        verify(product_repository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Test getProductByName with invalid name")
    void testGetProductByNameInvalidName() {
        String name = "Invalid Product";
        when(product_repository.findByName(name)).thenReturn(null);

        Product found = productService.getProductByName(name);
        assertEquals(null, found);

        verify(product_repository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Test addProduct with valid category")
    void testAddProductValidCategory() {
        String name = "Espresso";
        List<String> description = Arrays.asList("coffee", "water");
        double price = 2.99;
        String categoryName = "Coffee";

        when(categoryRepo.findByName(categoryName)).thenReturn(coffeeCategory);
        when(product_repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product addedProduct = productService.addProduct(name, description, price, categoryName);

        assertNotNull(addedProduct);
        assertEquals(name, addedProduct.getName());
        assertEquals(description, addedProduct.getIngredients());
        assertEquals(price, addedProduct.getPrice());
        assertEquals(categoryName, addedProduct.getCategory().getName());

        verify(categoryRepo, times(1)).findByName(categoryName);
        verify(product_repository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Test addProduct with invalid category")
    void testAddProductInvalidCategory() {
        String name = "Green Tea";
        List<String> description = Arrays.asList("tea", "water");
        double price = 1.99;
        String categoryName = "Invalid Category";

        when(categoryRepo.findByName(categoryName)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(name, description, price, categoryName);
        });

        String expectedMessage = "Category not found";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        verify(categoryRepo, times(1)).findByName(categoryName);
        verify(product_repository, times(0)).save(any(Product.class));
    }
}
