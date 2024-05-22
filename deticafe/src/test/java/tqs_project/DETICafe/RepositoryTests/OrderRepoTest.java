package tqs_project.DETICafe.RepositoryTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs_project.DETICafe.model.Order;
import tqs_project.DETICafe.repository.OrderRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepo orderRepo;

    @Test
    void whenFindByOrderId_thenReturnOrder() {
        Order order = new Order();
        entityManager.persistAndFlush(order);

        Order found = orderRepo.findByOrderId(order.getOrderId());

        assertThat(found.getOrderId()).isEqualTo(order.getOrderId());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Order found = orderRepo.findByOrderId(-1L);

        assertThat(found).isNull();
    }

    @Test
    void givenSetOfOrders_whenFindAll_thenReturnAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();

        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(order3);
        entityManager.flush();

        List<Order> allOrders = orderRepo.findAll();

        assertThat(allOrders).hasSize(3).extracting(Order::getOrderId).containsOnly(order1.getOrderId(), order2.getOrderId(), order3.getOrderId());
    }
}
 