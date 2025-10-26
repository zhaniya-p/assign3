package mstproject.graph;

public class Edge implements Comparable<Edge> {
    public int src, dest;
    public double weight;

    public Edge(int src, int dest, double weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return String.format("(%d - %d : %.2f)", src, dest, weight);
    }
}