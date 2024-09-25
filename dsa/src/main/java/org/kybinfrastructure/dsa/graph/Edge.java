package org.kybinfrastructure.dsa.graph;

/**
 * Represents an edge in a graph.
 */
public record Edge<T>(Vertex<T> from, Vertex<T> to) {
}
