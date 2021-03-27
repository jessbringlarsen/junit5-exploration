package dk.bringlarsen.junit5.exploration.nested;

import dk.bringlarsen.junit5.exploration.jgraph.GraphDSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static dk.bringlarsen.junit5.exploration.jgraph.GraphDSL.aSimpleDirectedGraph;
import static org.junit.jupiter.api.Assertions.*;

class SimpleDirectedGraphTest {

    @Nested
    class AddVertexTests {

        @Test
        @DisplayName("given two vertices expect a vertexSize of two")
        public void addVertexTest() {
            GraphDSL graph = aSimpleDirectedGraph()
                    .withVertices("v1", "v2");

            assertTrue(graph.containsVertex("v1"));
            assertTrue(graph.containsVertex("v2"));
        }

        @Nested
        class AddEdgeTests {

            @Test
            @DisplayName("given edge expect directed edge")
            void directedEdgeTest() {
                GraphDSL graph = aSimpleDirectedGraph()
                        .withVertices("v1", "v2")
                        .withEdge("v1", "v2");

                assertTrue(graph.containsEdge("v1", "v2"));
                assertFalse(graph.containsEdge("v2", "v1"));
            }

            @ParameterizedTest
            @CsvSource({
                    "v1, unknown",
                    "unknown, v1",
                    "unknown, unknown"
            })
            @DisplayName("given an unknown vertex expect failure")
            void unknownVertexTest(String source, String target) {
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        aSimpleDirectedGraph()
                            .withVertices("v1")
                            .withEdge(source, target));

                assertTrue(exception.getMessage().contains("unknown"));
            }

            @Test
            @DisplayName("given a loop edge expect failure")
            void loopsNotAllowedTest() {
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        aSimpleDirectedGraph()
                            .withVertices("v1")
                            .withEdge("v1", "v1"));

                assertEquals("loops not allowed", exception.getMessage());
            }
        }
    }
}
