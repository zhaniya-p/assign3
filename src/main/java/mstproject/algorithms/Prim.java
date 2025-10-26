package mstproject.algorithms;

import mstproject.graph.Edge;
import mstproject.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {

    public static class Result {
        public final List<Edge> mstEdges;
        public final double totalCost;
        public final long operations; // counted key operations (edge polls / vertex checks)
        public final long timeMs;
        public final boolean valid;

        public Result(List<Edge> mstEdges, double totalCost, long operations, long timeMs, boolean valid) {
            this.mstEdges = mstEdges;
            this.totalCost = totalCost;
            this.operations = operations;
            this.timeMs = timeMs;
            this.valid = valid;
        }
    }

    public static Result findMST(Graph graph) {
        long start = System.currentTimeMillis();
        int V = graph.getVerticesCount();
        if (V == 0) {
            return new Result(new ArrayList<>(), 0.0, 0, System.currentTimeMillis() - start, false);
        }

        boolean[] visited = new boolean[V];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        List<Edge> mst = new ArrayList<>();
        long ops = 0L;
        double totalCost = 0.0;

        // Start from vertex 0
        visited[0] = true;
        for (Edge e : graph.getAdjList().get(0)) pq.add(e);

        while (!pq.isEmpty() && mst.size() < V - 1) {
            Edge e = pq.poll();
            ops++; // one key operation: poll / consider an edge
            int to = e.getDest();
            if (visited[to]) continue;
            // accept edge
            mst.add(e);
            totalCost += e.getWeight();
            visited[to] = true;
            // add edges from new vertex
            for (Edge next : graph.getAdjList().get(to)) {
                if (!visited[next.getDest()]) {
                    pq.add(next);
                }
            }
        }

        boolean valid = (mst.size() == Math.max(0, V - 1));
        long timeMs = System.currentTimeMillis() - start;
        return new Result(mst, totalCost, ops, timeMs, valid);
    }
}
