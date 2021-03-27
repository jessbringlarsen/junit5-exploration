package dk.bringlarsen.junit5.exploration.jgraph;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.Arrays;

import static org.jgrapht.util.SupplierUtil.DEFAULT_EDGE_SUPPLIER;
import static org.jgrapht.util.SupplierUtil.createRandomUUIDStringSupplier;

public class GraphDSL {

    private Graph<String, DefaultEdge> graph;

    public static GraphDSL aSimpleDirectedGraph() {
        return new GraphDSL(new SimpleDirectedGraph<>(createRandomUUIDStringSupplier(), DEFAULT_EDGE_SUPPLIER, false));
    }

    private GraphDSL(Graph graph) {
        this.graph = graph;
    }

    public GraphDSL withVertices(String... vertices) {
        Arrays.stream(vertices).forEach(vertex -> graph.addVertex(vertex));
        return this;
    }

    public GraphDSL withEdge(String source, String target) {
        graph.addEdge(source, target);
        return this;
    }

    public boolean containsEdge(String source, String target) {
        return graph.containsEdge(source, target);
    }

    public boolean containsVertex(String vertex) {
        return graph.containsVertex(vertex);
    }
}
