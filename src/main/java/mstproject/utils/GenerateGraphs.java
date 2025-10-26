package mstproject.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Generates multiple graph JSON files in:
 * input/small, input/medium, input/large, input/extra_large
 */
public class GenerateGraphs {
    private static final Random RAND = new Random();

    public static void main(String[] args) throws IOException {
        generateGraphs("input/small", 5, 5, 30);
        generateGraphs("input/medium", 10, 30, 300);
        generateGraphs("input/large", 10, 300, 1000);
        generateGraphs("input/extra_large", 3, 1000, 2000);
        System.out.println("Datasets generated.");
    }

    private static void generateGraphs(String folder, int count, int minV, int maxV) throws IOException {
        File dir = new File(folder);
        if (!dir.exists()) dir.mkdirs();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (int i = 1; i <= count; i++) {
            int V = RAND.nextInt(maxV - minV + 1) + minV;
            List<Map<String, Object>> edges = new ArrayList<>();
            // Ensure connectivity (chain)
            for (int v = 0; v < V - 1; v++) {
                Map<String, Object> e = new LinkedHashMap<>();
                e.put("src", v);
                e.put("dest", v + 1);
                e.put("weight", RAND.nextInt(100) + 1);
                edges.add(e);
            }
            // Add random edges (control density)
            int extra = Math.min(V * 2, (V * (V - 1)) / 8);
            Set<String> seen = new HashSet<>();
            for (int k = 0; k < extra; k++) {
                int a = RAND.nextInt(V);
                int b = RAND.nextInt(V);
                if (a == b) continue;
                int u = Math.min(a, b);
                int v = Math.max(a, b);
                String key = u + "_" + v;
                if (seen.contains(key)) continue;
                seen.add(key);
                Map<String, Object> e = new LinkedHashMap<>();
                e.put("src", u);
                e.put("dest", v);
                e.put("weight", RAND.nextInt(100) + 1);
                edges.add(e);
            }

            Map<String, Object> graph = new LinkedHashMap<>();
            graph.put("vertices", V);
            graph.put("edges", edges);

            File out = new File(dir, "graph_" + i + ".json");
            try (FileWriter fw = new FileWriter(out)) {
                gson.toJson(graph, fw);
            }
            System.out.println("Generated " + out.getPath() + " (V=" + V + ")");
        }
    }
}
