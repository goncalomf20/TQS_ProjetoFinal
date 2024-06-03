package tqs_project.deticafe.ControllerWithMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import tqs_project.deticafe.config.WebSocketConfig;
import tqs_project.deticafe.controller.OrderController;
import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.model.Order;
import tqs_project.deticafe.model.OrderDetails;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.OrderRepo;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.OrderService;
import tqs_project.deticafe.service.serviceImpl.OrderServiceImpl;

@ContextConfiguration(classes = { WebSocketConfig.class, OrderController.class })
@WebMvcTest(OrderController.class)
public class OrderController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService service;

    @MockBean
    private OrderRepo orderRepo;

    @MockBean
    private ProductRepo productRepo;

    @MockBean
    private CategoryRepo categoryRepo;

    @MockBean
    private OrderServiceImpl orderService;

    @MockBean
    private OrderController orderController;


    private Category foodCategory;
    private Product pizza;
    private Order order;

    @BeforeEach
    void setUp() {
        foodCategory = new Category("Foods");

        List<String> pizzaIngredients = Arrays.asList("cheese", "tomato sauce", "flour");
        pizza = new Product("Pizza", pizzaIngredients, 5.99, foodCategory);

        List<String> customizations = Arrays.asList("cheese", "tomato sauce");
        OrderDetails orderDetails = new OrderDetails(customizations, pizza);

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetails);

        order = new Order(orderDetailsList);
    }

    @Test
    void testOrder() throws Exception {
        // Mock the Order object
        Order mockOrder = mock(Order.class);

        // Call the order method
        Order result = ((OrderController) new OrderController()).order(mockOrder);

        // Verify the result
        assertEquals(mockOrder.getOrderId(), result.getOrderId());

    }


    @Test
    void whenWebSocketConnectionFails_thenHandleError() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();

        assertThrows(Exception.class, () -> {
            CountDownLatch latch = new CountDownLatch(1);

            StompSessionHandlerAdapter sessionHandler = new StompSessionHandlerAdapter() {
                @Override
                public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                    session.subscribe("/topic/orders", new StompSessionHandlerAdapter() {
                        @Override
                        public Type getPayloadType(StompHeaders headers) {
                            return Order.class;
                        }

                        @Override
                        public void handleFrame(StompHeaders headers, Object payload) {
                            latch.countDown();
                        }
                    });

                    session.send("/app/wsorder", order);
                }
            };

            stompClient.connect("ws://invalidhost:8080/ws", handshakeHeaders, sessionHandler).get(5, TimeUnit.SECONDS);
        });
    }
}