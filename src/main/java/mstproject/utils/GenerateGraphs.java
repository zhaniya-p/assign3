package mstproject.utils;

import com.google.gson.*;
import java.io.*;
import java.util.*;

/**
 * Generates random connected graphs in JSON format
 * for MST algorithm testing (Prim & Kruskal).
 */
public class GenerateGraphs {
    static Random rand = new Random();

    public static void main(String[] args) throws IOException {
        generateGraphs("input/small", 5, 5, 30);
        generateGraphs("input/medium", 10, 30, 300);
        generateGraphs("input/large", 10, 300, 1000);
        generateGraphs("input/extra_large", 3, 1000, 2000);
        System.out.println("✅ Graph datasets generated successfully!");
    }

    public static void generateGraphs(String folder, int count, int minV, int maxV) throws IOException {
        File dir = new File(folder);
        if (!dir.exists()) dir.mkdirs();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (int i = 1; i <= count; i++) {
            int V = rand.nextInt(maxV - minV + 1) + minV;
            List<Map<String, Object>> edges = new ArrayList<>();

            // Ensure connectivity with a simple chain first
            for (int v = 0; v < V - 1; v++) {
                Map<String, Object> e = new HashMap<>();
                e.put("src", v);
                e.put("dest", v + 1);
                e.put("weight", rand.nextInt(50) + 1);
                edges.add(e);
            }

            // Add random extra edges for density
            int extraEdges = Math.min(V * 2, (V * (V - 1)) / 4); // control density
            for (int k = 0; k < extraEdges; k++) {
                int src = rand.nextInt(V);
                int dest = rand.nextInt(V);
                if (src == dest) continue;

                Map<String, Object> e = new HashMap<>();
                e.put("src", src);
                e.put("dest", dest);
                e.put("weight", rand.nextInt(100) + 1);
                edges.add(e);
            }

            Map<String, Object> graph = new LinkedHashMap<>();
            graph.put("vertices", V);
            graph.put("edges", edges);

            try (Writer writer = new FileWriter(folder + "/graph_" + i + ".json")) {
                gson.toJson(graph, writer);
            }

            System.out.println("Generated: " + folder + "/graph_" + i + ".json (" + V + " vertices)");
        }
    }
}
