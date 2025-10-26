package mstproject.utils;

import com.google.gson.*;
import mstproject.graph.Graph;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class JsonUtils {

    public static Graph loadGraph(String path) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(path)) {
            JsonObject obj = gson.fromJson(reader, JsonObject.class);
            if (obj == null) {
                throw new IOException("Empty or invalid JSON: " + path);
            }
            int vertices = obj.get("vertices").getAsInt();
            Graph graph = new Graph(vertices);

            JsonArray edges = obj.getAsJsonArray("edges");
            if (edges != null) {
                for (JsonElement e : edges) {
                    JsonObject edge = e.getAsJsonObject();
                    int src = edge.get("src").getAsInt();
                    int dest = edge.get("dest").getAsInt();
                    double weight = edge.get("weight").getAsDouble();
                    graph.addEdge(src, dest, weight);
                }
            }
            return graph;
        }
    }

    public static void writeJsonToFile(Object data, String path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(data, writer);
        }
    }
}
