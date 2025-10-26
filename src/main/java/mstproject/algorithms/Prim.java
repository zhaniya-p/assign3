package mstproject.algorithms;

import mstproject.graph.*;
import java.util.*;

public class Prim {
    public static Result findMST(Graph graph) {
        int V = graph.getVertices();
        boolean[] visited = new boolean[V];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        List<Edge> mst = new ArrayList<>();
        int operations = 0;
        double totalCost = 0;

        visited[0] = true;
        pq.addAll(graph.getAdjList().get(0));

        while (!pq.isEmpty() && mst.size() < V - 1) {
            Edge edge = pq.poll();
            operations++;
            if (visited[edge.dest]) continue;

            mst.add(edge);
            totalCost += edge.weight;
            visited[edge.dest] = true;

            for (Edge next : graph.getAdjList().get(edge.dest)) {
                if (!visited[next.dest]) pq.add(next);
            }
        }

        return new Result(mst, totalCost, operations);
    }

    public static class Result {
        public List<Edge> edges;
        public double cost;
        public int operations;

        public Result(List<Edge> edges, double cost, int operations) {
            this.edges = edges;
            this.cost = cost;
            this.operations = operations;
        }
    }
}
