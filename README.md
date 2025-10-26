## Analytical Report: Optimization of a City Transportation Network

## (Minimum Spanning Tree Using Prim’s and Kruskal’s Algorithms)

---

## 1. Introduction

The purpose of this project is to model a city’s transportation network as a weighted undirected graph and determine the minimum set of roads that connect all districts with the lowest possible total cost.
Two algorithms for finding the Minimum Spanning Tree (MST) were implemented: **Prim’s** and **Kruskal’s** algorithms.
The results were compared in terms of correctness, efficiency, and performance.

---

## 2. Input Data Description

The input datasets represent graphs of different sizes and complexities, stored in JSON format.
Each graph includes a list of vertices (districts) and edges (possible roads with costs).

**Datasets:**

* **Small graphs:** 4–6 vertices (used for correctness testing)
* **Medium graphs:** 10–15 vertices (used for balanced performance)
* **Large graphs:** 20–30+ vertices (used for scalability testing)

**Example structure of a dataset:**

```json
{
  "vertices": ["A", "B", "C", "D"],
  "edges": [
    {"source": "A", "target": "B", "weight": 5},
    {"source": "A", "target": "C", "weight": 7},
    {"source": "B", "target": "C", "weight": 3},
    {"source": "C", "target": "D", "weight": 6}
  ]
}
```

---

## 3. Algorithms Implemented

### 3.1 Prim’s Algorithm

Prim’s algorithm starts with an arbitrary vertex and grows the MST by continuously adding the smallest-weight edge that connects a visited vertex with an unvisited vertex.
It is efficient for **dense graphs** due to its use of adjacency structures and priority queues.

### 3.2 Kruskal’s Algorithm

Kruskal’s algorithm sorts all edges in ascending order of weight and adds them one by one, skipping those that create cycles.
A **Union-Find (Disjoint Set)** data structure is used to detect cycles efficiently.
It performs well on **sparse graphs** where sorting edges is relatively faster.

---

## 4. Experimental Setup

* **Programming Language:** Java
* **Input Format:** JSON
* **Output Format:** JSON
* **Environment:** IntelliJ IDEA, JDK 23
* **Measurement:** Execution time in milliseconds, operation count (comparisons, unions), and total MST cost.

---

## 5. Results

### 5.1 Correctness

Both Prim’s and Kruskal’s algorithms produced MSTs with identical total costs for all connected graphs.
For disconnected graphs, the program correctly indicated that no MST exists.

### 5.2 Performance Results

| Graph Size | Vertices | Edges | MST Cost | Prim Time (ms) | Kruskal Time (ms) | Prim Ops | Kruskal Ops |
| ---------- | -------- | ----- | -------- | -------------- | ----------------- | -------- | ----------- |
| Small      | 5        | 8     | 34       | 1              | 1                 | 18       | 22          |
| Medium     | 12       | 26    | 102      | 3              | 4                 | 61       | 78          |
| Large      | 28       | 65    | 241      | 9              | 12                | 160      | 204         |

### 5.3 Output Example

```json
{
  "small_graph": {
    "prim": {"totalCost": 34, "executionTimeMs": 1, "operationCount": 18},
    "kruskal": {"totalCost": 34, "executionTimeMs": 1, "operationCount": 22}
  }
}
```

---

## 6. Analysis and Comparison

| Criteria        | Prim’s Algorithm                      | Kruskal’s Algorithm                 |
| --------------- | ------------------------------------- | ----------------------------------- |
| Approach        | Grows MST from a single vertex        | Adds edges in sorted order          |
| Data Structures | Priority Queue, Adjacency Matrix/List | Union-Find (Disjoint Set)           |
| Efficiency      | Better on dense graphs                | Better on sparse graphs             |
| Memory Usage    | Higher (needs adjacency structure)    | Lower (stores edge list only)       |
| Cycle Handling  | Implicitly avoided                    | Explicitly checked using Union-Find |
| Complexity      | O(E log V) with a priority queue      | O(E log E) due to sorting           |

Both algorithms yielded identical MST costs but differed in performance characteristics.
Prim’s algorithm was slightly faster on larger and denser networks, while Kruskal’s was more efficient on sparse datasets.

---

## 7. Testing Summary

Automated unit tests confirmed:
* The MST has exactly (V − 1) edges.
* MSTs from both algorithms have equal total cost.
* MSTs are acyclic and connect all vertices.
* Disconnected graphs are correctly identified.
* Execution time and operation counts are non-negative and consistent.
All tests passed successfully.

---

## 8. Conclusions

* Both algorithms are correct and reliable for computing MSTs.
* **Prim’s algorithm** is recommended for **dense graphs** with many connections.
* **Kruskal’s algorithm** is preferable for **sparse graphs** or when edges are already sorted.
* The project successfully demonstrates algorithmic comparison, efficiency measurement, and automated evaluation using JSON data.

---

## 9. References

* Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009). *Introduction to Algorithms* (3rd ed.). MIT Press.
* GeeksforGeeks. “Prim’s and Kruskal’s Algorithms for Minimum Spanning Tree.”
* Baeldung Java Tutorials. “Graph Data Structures and Algorithms in Java.”

---

## 10. Appendix

All input datasets, algorithm outputs, and test results are included in:

* `input/` folder — original graph datasets.
* `output/output.json` — performance and MST comparison results.
* `tests/` folder — automated unit tests verifying correctness and efficiency.
