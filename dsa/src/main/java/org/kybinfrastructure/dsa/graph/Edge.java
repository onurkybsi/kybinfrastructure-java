package org.kybinfrastructure.dsa.graph;

/**
 * Represents an edge in a graph.
 */
public record Edge<TVertex, TWeight extends Comparable<TWeight>>(Vertex<TVertex> from, // NOSONAR
    Vertex<TVertex> to, Weight<TWeight> weight) {
}
