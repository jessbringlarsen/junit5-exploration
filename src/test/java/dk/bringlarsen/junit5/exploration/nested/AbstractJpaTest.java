package dk.bringlarsen.junit5.exploration.nested;

import dk.bringlarsen.junit5.explration.entities.Tool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.*;

abstract class AbstractJpaTest {

    static EntityManager entityManager;

    @BeforeAll
    static void setup() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Demo");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @BeforeEach
    void beforeEach() {
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void afterEach() {
        entityManager.getTransaction().rollback();
    }

    Tool reget(Tool tool) {
        entityManager.persist(tool);
        entityManager.flush();
        entityManager.clear();
        return findById(tool.getId());
    }

    Tool findById(String id) {
        return entityManager.find(Tool.class, id);
    }
}
