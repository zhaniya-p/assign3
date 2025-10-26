package mstproject.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mstproject.graph.Graph;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JsonUtils {
    public static Graph loadGraph(String path) throws IOException {
        Gson gson = new Gson();
        Reader reader = new FileReader(path);
        JsonObject obj = gson.fromJson(reader, JsonObject.class);
        reader.close();

        int vertices = obj.get("vertices").getAsInt();
        Graph graph = new Graph(vertices);

        JsonArray edges = obj.getAsJsonArray("edges");
        for (JsonElement e : edges) {
            JsonObject edge = e.getAsJsonObject();
            graph.addEdge(edge.get("src").getAsInt(),
                    edge.get("dest").getAsInt(),
                    edge.get("weight").getAsDouble());
        }
        return graph;
    }
}
