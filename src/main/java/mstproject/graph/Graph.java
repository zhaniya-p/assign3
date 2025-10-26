package mstproject.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {
    private final int vertices;
    private final List<Edge> edges;
    private final List<List<Edge>> adjList;

    public Graph(int vertices) {
        if (vertices < 0) throw new IllegalArgumentException("vertices must be >= 0");
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest, double weight) {
        if (src < 0 || src >= vertices || dest < 0 || dest >= vertices)
            throw new IndexOutOfBoundsException("vertex index out of bounds");
        Edge e = new Edge(src, dest, weight);
        edges.add(e);
        // since undirected, add both directions to adjacency list
        adjList.get(src).add(new Edge(src, dest, weight));
        adjList.get(dest).add(new Edge(dest, src, weight));
    }

    public int getVerticesCount() {
        return vertices;
    }

    public int getEdgesCount() {
        return edges.size();
    }

    public List<Edge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public List<List<Edge>> getAdjList() {
        return adjList;
    }
}
