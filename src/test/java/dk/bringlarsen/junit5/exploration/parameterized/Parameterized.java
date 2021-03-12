package dk.bringlarsen.junit5.exploration.parameterized;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.*;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class Parameterized {

    private Map<String, DefaultWeightedEdge> edges = new HashMap<>();
    private Graph<String, DefaultWeightedEdge> unweightedGraph;
    private String v1 = "v1";
    private String v2 = "v2";
    private String v3 = "v3";
    private String v4 = "v4";

    @BeforeEach
    void setupGragh() {
        Graph<String, DefaultWeightedEdge> undirectedWeightedGraph = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);
        unweightedGraph = new AsUnweightedGraph<>(undirectedWeightedGraph);

        undirectedWeightedGraph.addVertex(v1);
        undirectedWeightedGraph.addVertex(v2);
        undirectedWeightedGraph.addVertex(v3);
        undirectedWeightedGraph.addVertex(v4);

        edges.put("e12", Graphs.addEdge(undirectedWeightedGraph, v1, v2, 3d));
        edges.put("e23", Graphs.addEdge(undirectedWeightedGraph, v2, v3, 456d));
        edges.put("e24", Graphs.addEdge(undirectedWeightedGraph, v2, v4, 0.587d));
        edges.put("loop", Graphs.addEdge(undirectedWeightedGraph, v4, v4, 6781234453486d));
    }

    @ParameterizedTest
    @ValueSource(strings = {"e12", "e23", "e24", "loop"})
    void getEdgeWeight(String edge) {
        double edgeWeight = unweightedGraph.getEdgeWeight(edges.get(edge));

        assertEquals(Graph.DEFAULT_EDGE_WEIGHT, edgeWeight, 0);
    }
}
