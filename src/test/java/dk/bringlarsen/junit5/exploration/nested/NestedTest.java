package dk.bringlarsen.junit5.exploration.nested;


import dk.bringlarsen.junit5.explration.entities.Tool;
import org.junit.jupiter.api.*;

import javax.persistence.Query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("A Tool record")
class NestedTest extends AbstractJpaTest {
    Tool tool;

    @BeforeEach
    void createTool() {
        tool = new Tool().setName("Hammer");
        tool = reget(tool);
    }

    @Test
    @DisplayName("is created when persisted")
    void testPersist() {
        Tool hammer = findById(tool.getId());

        assertEquals(hammer.getId(), tool.getId());
        assertEquals(hammer.getName(), tool.getName());
    }

    @Nested
    @DisplayName("is updated")
    class WhenUpdated {

        @Test
        @DisplayName("when name is changed")
        void testUpdate() {
            tool.setName("Saw");
            tool = reget(tool);

            Assertions.assertEquals("Saw", tool.getName());
        }
    }

    @Nested
    @DisplayName("is found")
    class WhenSearchedFor {

        @Test
        @DisplayName("when searched for by id")
        void testFindById() {
            tool = findById(tool.getId());

            assertNotNull(tool.getId());
        }

        @Test
        @DisplayName("when searched for by name")
        void testFindByName() {
            Query query = entityManager.createQuery("select t from Tool t where t.name=:name");
            query.setParameter("name", "Hammer");
            tool = (Tool)query.getSingleResult();

            assertEquals("Hammer", tool.getName());
        }
    }
}