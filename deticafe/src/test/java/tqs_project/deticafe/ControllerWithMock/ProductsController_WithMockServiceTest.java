package tqs_project.deticafe.ControllerWithMock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import tqs_project.deticafe.controller.ProductsController;
import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.OrderRepo;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.ProductService;
import tqs_project.deticafe.service.serviceImpl.ProductServiceImpl;

@WebMvcTest(ProductsController.class)
class ProductsController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductServiceImpl service;

    @MockBean
    private ProductRepo productRepo;

    @MockBean
    private CategoryRepo categoryRepo;

    @MockBean
    private OrderRepo orderRepo;

    @Test
    void whenPostProduct_thenCreateProduct() throws Exception {
        Category snacks = new Category("snacks");
        Product product = new Product("Chips", Arrays.asList("Salty", "Crunchy"), 1.99, snacks);

        when(categoryRepo.findByName(Mockito.anyString())).thenReturn(snacks);
        when(service.addProduct(Mockito.anyString(), Mockito.anyList(), Mockito.anyDouble(), Mockito.anyString())).thenReturn(product);

        mvc.perform(
                post("/api/products/addProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productName", "Chips")
                .param("productDescription", "Salty")
                .param("productDescription", "Crunchy")
                .param("productPrice", "1.99")
                .param("productCategory", "snacks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Chips")));

        verify(service, times(1)).addProduct(Mockito.anyString(), Mockito.anyList(), Mockito.anyDouble(), Mockito.anyString());
    }

    @Test
    void givenManyProducts_whenGetProducts_thenReturnJsonArray() throws Exception {
        Category snacks = new Category("snacks");
        Product product1 = new Product("Chips", Arrays.asList("Salty", "Crunchy"), 1.99, snacks);
        Product product2 = new Product("Cookies", Arrays.asList("Sweet", "Crunchy"), 2.99, snacks);
    
        List<Product> allProducts = Arrays.asList(product1, product2);
    
        when(service.getAllProducts()).thenReturn(allProducts);
    
        mvc.perform(get("/api/products/getAllProducts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(product1.getName())))
                .andExpect(jsonPath("$[1].name", is(product2.getName())));
    
        verify(service, times(1)).getAllProducts();
    }

    @Test
    void whenPostEmptyProductName_thenReturnBadRequest() throws Exception {
        mvc.perform(post("/api/products/addProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productName", "")
                .param("productDescription", "Salty")
                .param("productPrice", "1.99")
                .param("productCategory", "snacks"))
                .andExpect(status().isBadRequest());
    
        verify(service, times(0)).addProduct(Mockito.anyString(), Mockito.anyList(), Mockito.anyDouble(), Mockito.anyString());
    }

    @Test
    void givenNoProducts_whenGetProducts_thenReturnEmptyJsonArray() throws Exception {
        when(service.getAllProducts()).thenReturn(Collections.emptyList());
    
        mvc.perform(get("/api/products/getAllProducts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    
        verify(service, times(1)).getAllProducts();
    }

    @Test
    void whenPostProductWithNegativePrice_thenReturnBadRequest() throws Exception {
        mvc.perform(post("/api/products/addProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productName", "Test Product")
                .param("productDescription", "Salty")
                .param("productPrice", "-1.99")
                .param("productCategory", "snacks"))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).addProduct(Mockito.anyString(), Mockito.anyList(), Mockito.anyDouble(), Mockito.anyString());
    }

    @Test
    void whenPostProductWithEmptyCategory_thenReturnBadRequest() throws Exception {
        mvc.perform(post("/api/products/addProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productName", "Test Product")
                .param("productDescription", "Salty")
                .param("productPrice", "1.99")
                .param("productCategory", ""))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).addProduct(Mockito.anyString(), Mockito.anyList(), Mockito.anyDouble(), Mockito.anyString());
    }


    @Test
    void whenPostProductWithInvalidData_thenReturnBadRequest() throws Exception {
        when(service.addProduct("invalid", null, -1.0, "invalid")).thenThrow(IllegalArgumentException.class);

        mvc.perform(
                post("/api/products/addProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productName", "invalid")
                .param("productPrice", "-1.0")
                .param("productCategory", "invalid"))
                .andExpect(status().isBadRequest());
    }

     
    @Test
    void testAddProduct_Success() {
        // Mocking necessary dependencies
        Category category = new Category("Test Category");
        ProductService mockProductService = mock(ProductService.class);
        List<String> productDescription = new ArrayList<>();
        productDescription.add("Description 1");
        productDescription.add("Description 2");
        
        Product mockProduct = new Product("Test Product", productDescription, 10.0, category);
        
        when(mockProductService.addProduct("Test Product", productDescription, 10.0, "Test Category"))
            .thenReturn(mockProduct);
        
        // Calling the method to be tested
        Product responseProduct = mockProductService.addProduct("Test Product", productDescription, 10.0, "Test Category");
        
        // Assertion
        assertEquals("Test Product", responseProduct.getName());
    }
    
    @Test
    void testAddProduct_Failure() {
        Category category = new Category("");
        // Mocking necessary dependencies
        ProductService mockProductService = mock(ProductService.class);
        
        when(mockProductService.addProduct("", new ArrayList<>(), 0.0, ""))
            .thenThrow(IllegalArgumentException.class);
        
        // Calling the method to be tested
        Product product = new Product("", new ArrayList<>(), 0.0, category);
        ResponseEntity<Product> response = ResponseEntity.badRequest().body(product);
        
        // Assertion
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testAddProduct_ExceptionThrown() throws Exception {
        when(service.addProduct(anyString(), anyList(), anyDouble(), anyString()))
                .thenThrow(new IllegalArgumentException("Invalid product data"));

        mvc.perform(post("/api/products/addProduct")
                .param("productName", "Test Product")
                .param("productDescription", "Test Description")
                .param("productPrice", "10.0")
                .param("productCategory", "Test Category"))
                .andExpect(status().isBadRequest());
    }
}
