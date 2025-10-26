package mstproject;

import mstproject.algorithms.Kruskal;
import mstproject.algorithms.Prim;
import mstproject.graph.Edge;
import mstproject.graph.Graph;
import mstproject.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MstTest {

    @Test
    public void testPrimAndKruskalCostEquality() throws IOException {
        Graph graph = JsonUtils.loadGraph("input/small/graph_1.json");
        Prim.Result p = Prim.findMST(graph);
        Kruskal.Result k = Kruskal.findMST(graph);
        assertTrue(p.valid && k.valid, "Both results must be valid MSTs");
        assertEquals(p.totalCost, k.totalCost, 1e-6);
    }

    @Test
    public void testMstEdgeCountVminusOne() throws IOException {
        Graph graph = JsonUtils.loadGraph("input/small/graph_1.json");
        Prim.Result p = Prim.findMST(graph);
        assertEquals(graph.getVerticesCount() == 0 ? 0 : graph.getVerticesCount() - 1, p.mstEdges.size());
    }

    @Test
    public void testMstIsAcyclic() throws IOException {
        Graph graph = JsonUtils.loadGraph("input/small/graph_1.json");
        Kruskal.Result k = Kruskal.findMST(graph);
        // check cycle via union-find on edges in mst
        int n = graph.getVerticesCount();
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        for (Edge e : k.mstEdges) {
            int u = find(parent, e.getSrc());
            int v = find(parent, e.getDest());
            assertNotEquals(u, v, "Edge introduces a cycle");
            parent[u] = v;
        }
    }

    @Test
    public void testDisconnectedGraphHandledGracefully() throws IOException {
        // create a tiny disconnected graph in code
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        // vertices 2 and 3 disconnected
        Prim.Result p = Prim.findMST(g);
        Kruskal.Result k = Kruskal.findMST(g);
        assertFalse(p.valid, "Prim should mark invalid for disconnected graph");
        assertFalse(k.valid, "Kruskal should mark invalid for disconnected graph");
    }

    private int find(int[] parent, int x) {
        if (parent[x] == x) return x;
        parent[x] = find(parent, parent[x]);
        return parent[x];
    }
}
