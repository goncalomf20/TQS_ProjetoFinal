package tqs_project.deticafe.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs_project.deticafe.model.Order;
import tqs_project.deticafe.model.OrderDetails;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.OrderRepo;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.serviceImpl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderService_UnitTest {

    @Mock(lenient = true)
    private ProductRepo ProductRepo;

    @Mock(lenient = true)
    private OrderRepo orderRepo;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
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
        when(orderRepo.findByOrderId(45L)).thenReturn(null);
    }

    @Test
    void getOrderTest() {
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
        assertNotNull(retrievedOrder);
        assertEquals(456L, retrievedOrder.getOrderId());
    }

    @Test
    void getOrderTestWithInvalidId() {
        Order retrievedOrder = orderService.getOrder(45L);
        assertEquals(null, retrievedOrder);
    }

    @Test
    void saveOrderTest() {

        Order order = new Order();
        order.setOrderId(123L); 

        when(orderRepo.save(order)).thenReturn(order);

        Order savedOrder = orderService.save(order);

        verify(orderRepo, times(1)).save(order);

        assertEquals(order, savedOrder);
    }

    @Test
    void createOrderTest() {

        OrderDetails orderDetails1 = new OrderDetails();
        OrderDetails orderDetails2 = new OrderDetails();
        List<OrderDetails> orderDetailsList = List.of(orderDetails1, orderDetails2);

        
        doAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setOrderId(123L);
            return null;
        }).when(orderRepo).save(any(Order.class));

        Long orderId = orderService.createOrder(orderDetailsList);

        assertNotNull(orderId); 
        assertEquals(123L, orderId); 
        verify(orderRepo, times(1)).save(any(Order.class));
    }


    @Test
    void createOrderTestWithEmptyOrderDetails() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(List.of());
        });

        String expectedMessage = "Order details list cannot be null or empty";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        verify(orderRepo, times(0)).save(any(Order.class));
    }

    @Test
    void createOrderTestWithNullOrderDetails() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(null);
        });

        String expectedMessage = "Order details list cannot be null or empty";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        verify(orderRepo, times(0)).save(any(Order.class));
    }

    @Test
    void getAllOrdersTest() {
        OrderDetails orderDetails1 = new OrderDetails();
        OrderDetails orderDetails2 = new OrderDetails();

        Order order1 = new Order(1L, List.of(orderDetails1));
        Order order2 = new Order(2L, List.of(orderDetails2));

        when(orderRepo.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(orderRepo, times(1)).findAll();
    }

    @Test
    void deleteAllOrdersTest() {
        orderService.deleteAllOrders();
        verify(orderRepo, times(1)).deleteAll();
    }
    
}