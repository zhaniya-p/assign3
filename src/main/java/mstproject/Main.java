package mstproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mstproject.algorithms.Kruskal;
import mstproject.algorithms.Prim;
import mstproject.graph.Graph;
import mstproject.graph.Edge;
import mstproject.utils.JsonUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] categories = {"small", "medium", "large", "extra_large"};
        List<Map<String, Object>> allResults = new ArrayList<>();

        for (String category : categories) {
            File folder = new File("input/" + category);
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
            if (files == null || files.length == 0) {
                System.out.println("No files found for category: " + category);
                continue;
            }
            System.out.println("\n=== Testing " + category + " graphs ===");

            for (File file : files) {
                try {
                    Graph graph = JsonUtils.loadGraph(file.getPath());

                    long startPrim = System.currentTimeMillis();
                    Prim.Result primRes = Prim.findMST(graph);
                    long endPrim = System.currentTimeMillis();

                    long startKruskal = System.currentTimeMillis();
                    Kruskal.Result kruskalRes = Kruskal.findMST(graph);
                    long endKruskal = System.currentTimeMillis();

                    // print a short summary
                    System.out.printf("✔ %s/%s | Prim: %dms | Kruskal: %dms | Cost: %.2f%n",
                            category, file.getName(),
                            primRes.timeMs,
                            kruskalRes.timeMs,
                            primRes.totalCost);

                    // collect detailed result
                    Map<String, Object> result = new LinkedHashMap<>();
                    result.put("category", category);
                    result.put("file", file.getName());
                    result.put("vertices", graph.getVerticesCount());
                    result.put("edges", graph.getEdgesCount());
                    result.put("prim", "unused placeholder");
                    // fill proper fields:
                    result.remove("prim"); // remove the placeholder we used to keep formatting consistent
                    result.put("prim_time_ms", primRes.timeMs);
                    result.put("prim_operations", primRes.operations);
                    result.put("prim_mst_cost", primRes.totalCost);
                    result.put("prim_mst_edges", edgesToList(primRes.mstEdges));

                    result.put("kruskal_time_ms", kruskalRes.timeMs);
                    result.put("kruskal_operations", kruskalRes.operations);
                    result.put("kruskal_mst_cost", kruskalRes.totalCost);
                    result.put("kruskal_mst_edges", edgesToList(kruskalRes.mstEdges));

                    result.put("prim_valid", primRes.valid);
                    result.put("kruskal_valid", kruskalRes.valid);

                    allResults.add(result);

                } catch (IOException e) {
                    System.err.println("Error reading " + file.getName() + ": " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Unexpected error for file " + file.getName() + ": " + e.getMessage());
                }
            }
        }

        // write aggregated results to output/output.json
        try {
            File outDir = new File("output");
            if (!outDir.exists()) outDir.mkdirs();
            try (FileWriter writer = new FileWriter("output/output.json")) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(allResults, writer);
                System.out.println("\nResults saved to output/output.json");
            }
        } catch (IOException e) {
            System.err.println("Could not save output: " + e.getMessage());
        }
    }

    private static List<Map<String, Object>> edgesToList(List<Edge> edges) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Edge e : edges) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("src", e.getSrc());
            m.put("dest", e.getDest());
            m.put("weight", e.getWeight());
            list.add(m);
        }
        return list;
    }
}
