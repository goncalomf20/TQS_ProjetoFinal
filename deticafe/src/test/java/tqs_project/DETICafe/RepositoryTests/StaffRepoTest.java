package tqs_project.DETICafe.RepositoryTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs_project.DETICafe.model.Staff;
import tqs_project.DETICafe.model.Type;
import tqs_project.DETICafe.repository.StaffRepo;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StaffRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StaffRepo staffRepo;

    @BeforeEach
    void setUp() {
        staffRepo.deleteAll();
    }

    @Test
    void whenFindByName_thenReturnStaff() {
        Staff staff = new Staff("Maria João Vilas", "92765234", "mvilas@ua.pt", Type.WAITER);
        entityManager.persistAndFlush(staff);

        Staff found = staffRepo.findByName(staff.getName());

        assertThat(found.getName()).isEqualTo(staff.getName());
    }

    @Test
    void whenInvalidName_thenReturnNull() {
        Staff found = staffRepo.findByName("InvalidName");

        assertThat(found).isNull();
    }

    @Test
    void givenSetOfCategories_whenFindAll_thenReturnAllCategories() {
        Staff staff1 = new Staff("João Pereira", "924567890", "jpereira@ua.pt", Type.CHEF);
        Staff staff2 = new Staff("Ana Santos", "938765432", "asantos@ua.pt", Type.WAITER);
        Staff staff3 = new Staff("Carlos Lima", "917654321", "clima@ua.pt", Type.WAITER);
        

        entityManager.persist(staff1);
        entityManager.persist(staff2);
        entityManager.persist(staff3);
        entityManager.flush();

        List<Staff> allCategories = staffRepo.findAll();

        assertThat(allCategories).hasSize(3).extracting(Staff::getName).containsOnly(staff1.getName(), staff2.getName(), staff3.getName());
    }
    
}