package mstproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mstproject.graph.*;
import mstproject.algorithms.*;
import mstproject.utils.*;

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
            if (files == null) continue;

            for (File file : files) {
                try {
                    Graph graph = JsonUtils.loadGraph(file.getPath());

                    long startPrim = System.currentTimeMillis();
                    Prim.Result primRes = Prim.findMST(graph);
                    long endPrim = System.currentTimeMillis();

                    long startKruskal = System.currentTimeMillis();
                    Kruskal.Result kruskalRes = Kruskal.findMST(graph);
                    long endKruskal = System.currentTimeMillis();

                    Map<String, Object> result = new LinkedHashMap<>();
                    result.put("category", category);
                    result.put("file", file.getName());
                    result.put("prim_time_ms", endPrim - startPrim);
                    result.put("kruskal_time_ms", endKruskal - startKruskal);
                    result.put("mst_cost", primRes.cost);
                    allResults.add(result);

                    System.out.printf("✔ %s/%s | Prim: %dms | Kruskal: %dms | Cost: %.2f%n",
                            category, file.getName(),
                            (endPrim - startPrim),
                            (endKruskal - startKruskal),
                            primRes.cost);

                } catch (IOException e) {
                    System.err.println("Error reading " + file.getName() + ": " + e.getMessage());
                }
            }
        }

        // Save to JSON
        try (FileWriter writer = new FileWriter("output/output.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(allResults, writer);
            System.out.println("\n Results saved to output/output.json");
        } catch (IOException e) {
            System.err.println("Failed to save results: " + e.getMessage());
        }
    }
}
