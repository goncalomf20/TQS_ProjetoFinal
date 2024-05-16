package tqs_project.DETICafe.UnitTests.ServicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.h2.security.AES;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import tqs_project.DETICafe.model.Order;
import tqs_project.DETICafe.model.OrderDetails;
import tqs_project.DETICafe.model.Product;
import tqs_project.DETICafe.repository.OrderRepo;
import tqs_project.DETICafe.repository.ProductRepo;
import tqs_project.DETICafe.service.serviceImpl.OrderServiceIMPL;





@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock(lenient = true)
    private ProductRepo ProductRepo;

    @Mock(lenient = true)
    private OrderRepo orderRepo;

    @InjectMocks
    private OrderServiceIMPL orderService;

    @Test
    public void getOrderTest() {

        long orderId = 456L;
        Product product1 = new Product();
        product1.setName("Cappuccino");
        product1.setPrice(2.5f);
        product1.setIngredients(List.of("Coffee", "Milk", "Sugar"));

        Product product2 = new Product();
        product2.setName("Tuna Sandwich");
        product2.setPrice(3.5f);
        product2.setIngredients(List.of("Tuna", "Bread", "Lettuce", "Tomato"));

        OrderDetails orderDetails1 = new OrderDetails();
        OrderDetails orderDetails2 = new OrderDetails();
        orderDetails1.setProduct(product1);
        orderDetails2.setProduct(product2);

        Order expectedOrder = new Order(orderId, List.of(orderDetails1, orderDetails2));

        when(orderRepo.findByOrderId(orderId)).thenReturn(expectedOrder);

        Order retrievedOrder = orderService.getOrder(orderId);

        assertEquals(expectedOrder, retrievedOrder);
    }

    @Test
    public void createOrderTest() {
        // Given
        OrderDetails orderDetails1 = new OrderDetails();
        OrderDetails orderDetails2 = new OrderDetails();
        List<OrderDetails> orderDetailsList = List.of(orderDetails1, orderDetails2);

        
        doAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setOrderId(123L); // Simula a geração de um ID pelo repositório
            return null;
        }).when(orderRepo).save(any(Order.class));

        // When
        Long orderId = orderService.createOrder(orderDetailsList);

        // Then
        assertNotNull(orderId); 
        assertEquals(123L, orderId); 
    }


    
}
