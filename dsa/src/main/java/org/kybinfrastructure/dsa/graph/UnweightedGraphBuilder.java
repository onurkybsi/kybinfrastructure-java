package org.kybinfrastructure.dsa.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Builder for {@link UnweightedGraph}.
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class UnweightedGraphBuilder<T> {

  private final HashMap<Vertex<T>, ArrayList<Vertex<T>>> vertices = new HashMap<>();
  private IteratorType iteratorType;

  /**
   * Adds a new vertex to the graph.
   * 
   * @param vertex vertex to add
   * @param neighbors neighbors of the vertex
   * @return {@code this} for chaining
   * @throws NullPointerException if {@code vertex} or {@code neighbors} is null, or
   *         {@code neighbors} contains null
   */
  public UnweightedGraphBuilder<T> vertex(Vertex<T> vertex, Collection<Vertex<T>> neighbors) {
    Objects.requireNonNull(vertex, "vertex cannot be null!");
    Objects.requireNonNull(neighbors, "neighbors cannot be null!");

    for (Vertex<T> neighbor : neighbors) {
      if (neighbor == null) {
        throw new NullPointerException("neighbors cannot contain null!");
      }

      var vertexNeighbors = this.vertices.getOrDefault(vertex, new ArrayList<>());
      if (!vertexNeighbors.contains(neighbor)) {
        vertexNeighbors.add(neighbor);
        this.vertices.put(vertex, vertexNeighbors);
      }
    }

    return this;
  }

  /**
   * Sets the iterator type of the graph.
   * 
   * @param iteratorType iterator type
   * @return {@code this} for chaining
   * @throws NullPointerException if given {@code iteratorType} is null
   */
  public UnweightedGraphBuilder<T> iterator(IteratorType iteratorType) {
    Objects.requireNonNull(iteratorType, "iteratorType cannot be null!");
    this.iteratorType = iteratorType;
    return this;
  }

  /**
   * Builds the graph.
   * 
   * @return built graph
   */
  public UnweightedGraph<T> build() {
    return new UnweightedGraph<>(vertices, iteratorType);
  }

}
