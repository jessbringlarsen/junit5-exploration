package dk.bringlarsen.junit5.exploration;


import dk.bringlarsen.junit5.explration.entities.Tool;
import org.junit.jupiter.api.*;

import javax.persistence.Query;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("A Tool record")
class NestedTest extends AbstractJpaTest {

    private static final String ID = UUID.randomUUID().toString();

    @Test
    @DisplayName("is created when persisted")
    void testPersist() {
        Tool tool = new Tool()
                .setId(ID)
                .setName("Hammer");

        tool = reget(tool);

        assertEquals(ID, tool.getId());
        assertEquals("Hammer", tool.getName());
    }

    @Nested
    @DisplayName("is updated")
    class WhenUpdated {

        @Test
        @DisplayName("when name is changed")
        void testUpdate() {
            Tool tool = findById(ID)
                .setName("Saw");

            tool = reget(tool);

            assertEquals(ID, tool.getId());
            Assertions.assertEquals("Saw", tool.getName());
        }

        @AfterEach
        void rollbackTransaction() {
            entityManager.getTransaction().setRollbackOnly();
        }
    }

    @Nested
    @DisplayName("is found")
    class WhenSearchedFor {

        @Test
        @DisplayName("when searched for by id")
        void testFindById() {
            Tool tool = findById(ID);

            assertEquals(ID, tool.getId());
        }

        @Test
        @DisplayName("when searched for by name")
        void testFindByName() {
            Query query = entityManager.createQuery("select t from Tool t where t.name=:name");
            query.setParameter("name", "Hammer");
            Tool tool = (Tool)query.getSingleResult();

            assertEquals("Hammer", tool.getName());
        }
    }
}