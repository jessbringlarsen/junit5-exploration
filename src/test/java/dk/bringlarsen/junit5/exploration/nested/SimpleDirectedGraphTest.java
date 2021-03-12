package dk.bringlarsen.junit5.exploration.nested;

import org.jgrapht.Graph;
import org.jgrapht.graph.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.jgrapht.util.SupplierUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class SimpleDirectedGraphTest {

    private Graph<String, DefaultEdge> graph;
    private String v1 = "v1";
    private String v2 = "v2";

    @BeforeEach
    void createGraph() {
        graph = new SimpleDirectedGraph<>(createRandomUUIDStringSupplier(), DEFAULT_EDGE_SUPPLIER, false);
    }

    @Nested
    class AddVertexTests {

        @BeforeEach
        void addVertices() {
            graph.addVertex(v1);
            graph.addVertex(v2);
        }

        @Test
        @DisplayName("given two vertices expect a vertexSize of two")
        public void testAddVertex() {
            assertEquals(2, graph.vertexSet().size());
        }

        @Nested
        class AddEdgeTests {

            @BeforeEach
            void createEdge() {
                graph.addEdge(v1, v2);
            }

            @Test
            @DisplayName("given edge expect directed edge")
            void directedEdgeTest() {
                assertTrue(graph.containsEdge(v1, v2));
                assertFalse(graph.containsEdge(v2, v1));
            }

            @ParameterizedTest
            @CsvSource({
                    "v1, unknown",
                    "unknown, v1",
                    "unknown, unknown"
            })
            @DisplayName("given an unknown vertex expect failure")
            void unknownVertexTest(String vertex1, String vertex2) {
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> graph.addEdge(vertex1, vertex2));

                assertEquals("no such vertex in graph: unknown", exception.getMessage());
            }

            @Test
            @DisplayName("given a loop edge expect failure")
            void loopsNotAllowedTest() {
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> graph.addEdge(v1, v1));

                assertEquals("loops not allowed", exception.getMessage());
            }
        }
    }
}
