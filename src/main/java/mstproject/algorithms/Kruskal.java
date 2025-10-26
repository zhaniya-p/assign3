package mstproject.algorithms;

import mstproject.graph.Edge;
import mstproject.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {

    public static class Result {
        public final List<Edge> mstEdges;
        public final double totalCost;
        public final long operations; // count of union/find key calls
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

    private static class UnionFind {
        private final int[] parent;
        private final int[] rank;
        private long ops = 0;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        public int find(int x) {
            ops++;
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public boolean union(int x, int y) {
            ops++;
            int rx = find(x);
            int ry = find(y);
            if (rx == ry) return false;
            if (rank[rx] < rank[ry]) parent[rx] = ry;
            else if (rank[rx] > rank[ry]) parent[ry] = rx;
            else {
                parent[ry] = rx;
                rank[rx]++;
            }
            return true;
        }

        public long getOps() {
            return ops;
        }
    }

    public static Result findMST(Graph graph) {
        long start = System.currentTimeMillis();
        int V = graph.getVerticesCount();
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);
        UnionFind uf = new UnionFind(V);
        List<Edge> mst = new ArrayList<>();
        double total = 0.0;

        for (Edge e : edges) {
            if (mst.size() == Math.max(0, V - 1)) break;
            int u = e.getSrc();
            int v = e.getDest();
            int ru = uf.find(u);
            int rv = uf.find(v);
            if (ru != rv) {
                boolean merged = uf.union(ru, rv);
                if (merged) {
                    mst.add(e);
                    total += e.getWeight();
                }
            }
        }

        boolean valid = (mst.size() == Math.max(0, V - 1));
        long timeMs = System.currentTimeMillis() - start;
        return new Result(mst, total, uf.getOps(), timeMs, valid);
    }
}
