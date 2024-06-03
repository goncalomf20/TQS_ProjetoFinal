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
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs_project.deticafe.controller.CategoryController;
import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.OrderRepo;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.OrderService;
import tqs_project.deticafe.service.impl.CategoryServiceImpl;

@WebMvcTest(CategoryController.class)
class CategoryController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryServiceImpl service;

    @MockBean
    private CategoryRepo categoryRepo;

    @MockBean
    private ProductRepo productRepo;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderRepo orderRepo;

    @Test
    void whenPostCategory_thenCreateCategory() throws Exception {
        Category snacks = new Category("snacks");

        when(service.addCategory(Mockito.anyString())).thenReturn(snacks);

        mvc.perform(
                post("/api/category/addCategory")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("categoryName", "snacks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("snacks")));

        verify(service, times(1)).addCategory(Mockito.anyString());
    }

    @Test
    void givenManyCategories_whenGetCategories_thenReturnJsonArray1() throws Exception {
        Category snacks = new Category("snacks");
        Category beverages = new Category("beverages");
        Category juices = new Category("juices");

        List<Category> allCategories = Arrays.asList(snacks, beverages, juices);

        // Mocking the service layer
        when(service.getAllCategories()).thenReturn(allCategories);

        // Performing a GET request and validating the response
        mvc.perform(get("/api/category/getAllCategories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(snacks.getName())))
                .andExpect(jsonPath("$[1].name", is(beverages.getName())))
                .andExpect(jsonPath("$[2].name", is(juices.getName())));

        // Verifying the interaction with the mocked service
        verify(service, times(1)).getAllCategories();
    }

    @Test
    void whenPostEmptyCategoryName_thenReturnNotFound() throws Exception {
        mvc.perform(post("/api/category/addCategory")
                .contentType(MediaType.APPLICATION_JSON)
                .param("categoryName", ""))
                .andExpect(status().isNotFound());

        verify(service, times(0)).addCategory(Mockito.anyString());
    }

    @Test
    void givenNoCategories_whenGetCategories_thenReturnEmptyJsonArray() throws Exception {
        List<Category> allCategories = Arrays.asList();

        when(service.getAllCategories()).thenReturn(allCategories);

        mvc.perform(get("/api/category/getAllCategories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(service, times(1)).getAllCategories();
    }

    @Test
    void whenPostNullCategoryName_thenReturnBadRequest() throws Exception {
        mvc.perform(post("/api/category/addCategory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).addCategory(Mockito.anyString());
    }

    @Test
    void whenPostCategoryWithSpecialCharacters_thenCreateCategory() throws Exception {
        Category specialCategory = new Category("@@Special@@");

        when(service.addCategory(Mockito.anyString())).thenReturn(specialCategory);

        mvc.perform(post("/api/category/addCategory")
                .contentType(MediaType.APPLICATION_JSON)
                .param("categoryName", "@@Special@@"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("@@Special@@")));

        verify(service, times(1)).addCategory(Mockito.anyString());
    }

}
