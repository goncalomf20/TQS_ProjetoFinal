package tqs_project.deticafe.RepositoryTests;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs_project.deticafe.model.Customer;
import tqs_project.deticafe.repository.CustomerRepo;

@DataJpaTest
public class CustomerRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepo customerRepo;

    @BeforeEach
    void setUp() {
        customerRepo.deleteAll();
    }

    @Test
    void whenFindByName_thenReturnCustomer() {
        Customer customer1 = new Customer(123456, "Pedro Almeida", "912345678", "palmeida@ua.pt");
        entityManager.persistAndFlush(customer1);

        Customer found = customerRepo.findByName(customer1.getName());

        assertThat(found.getName()).isEqualTo(customer1.getName());
    }

    @Test
    void whenInvalidName_thenReturnNull() {
        Customer found = customerRepo.findByName("InvalidName");

        assertThat(found).isNull();
    }

    @Test
    void givenSetOfCustomer_whenFindAll_thenReturnAllCustomers() {
        Customer customer2 = new Customer(987654, "Sara Moreira", "915432678", "smoreira@ua.pt");
        Customer customer3 = new Customer(456789, "Bruno Silva", "923456789", "bsilva@ua.pt");
        Customer customer4 = new Customer(321987, "Catarina Ferreira", "934567890", "cferreira@ua.pt");

        

        entityManager.persist(customer2);
        entityManager.persist(customer3);
        entityManager.persist(customer4);
        entityManager.flush();

        List<Customer> allCustomers = customerRepo.findAll();

        assertThat(allCustomers).hasSize(3).extracting(Customer::getName).containsOnly(customer2.getName(), customer3.getName(), customer4.getName());
    }
    
}