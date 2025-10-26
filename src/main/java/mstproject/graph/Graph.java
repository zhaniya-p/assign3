package mstproject.graph;

import java.util.*;

public class Graph {
    private int vertices;
    private List<Edge> edges;
    private List<List<Edge>> adjList;

    public Graph(int vertices) {
        this.vertices = vertices;
        edges = new ArrayList<>();
        adjList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest, double weight) {
        Edge e = new Edge(src, dest, weight);
        edges.add(e);
        adjList.get(src).add(new Edge(src, dest, weight));
        adjList.get(dest).add(new Edge(dest, src, weight)); // undirected
    }

    public int getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<List<Edge>> getAdjList() {
        return adjList;
    }
}