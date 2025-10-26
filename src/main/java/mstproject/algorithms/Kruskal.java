package mstproject.algorithms;

import mstproject.graph.*;
import java.util.*;

public class Kruskal {
    static class UnionFind {
        int[] parent, rank;
        int ops = 0;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            ops++;
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            ops++;
            int rootX = find(x), rootY = find(y);
            if (rootX == rootY) return;

            if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
            else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    public static Result findMST(Graph graph) {
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);
        UnionFind uf = new UnionFind(graph.getVertices());

        List<Edge> mst = new ArrayList<>();
        double totalCost = 0;

        for (Edge e : edges) {
            int srcRoot = uf.find(e.src);
            int destRoot = uf.find(e.dest);

            if (srcRoot != destRoot) {
                uf.union(srcRoot, destRoot);
                mst.add(e);
                totalCost += e.weight;
            }

            if (mst.size() == graph.getVertices() - 1) break;
        }

        return new Result(mst, totalCost, uf.ops);
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
