package tqs_project.deticafe.ControllerWithMock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
public class ProductsControllerTest_WithMockService {

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
}
