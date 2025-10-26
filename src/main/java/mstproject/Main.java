package mstproject;

import mstproject.graph.*;
import mstproject.algorithms.*;
import mstproject.utils.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Graph graph = JsonUtils.loadGraph("input/small_graph.json");

            long startPrim = System.currentTimeMillis();
            Prim.Result primRes = Prim.findMST(graph);
            long endPrim = System.currentTimeMillis();

            long startKruskal = System.currentTimeMillis();
            Kruskal.Result kruskalRes = Kruskal.findMST(graph);
            long endKruskal = System.currentTimeMillis();

            System.out.println("Prim's MST Cost: " + primRes.cost);
            System.out.println("Kruskal's MST Cost: " + kruskalRes.cost);
            System.out.println("Prim Time: " + (endPrim - startPrim) + "ms");
            System.out.println("Kruskal Time: " + (endKruskal - startKruskal) + "ms");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
