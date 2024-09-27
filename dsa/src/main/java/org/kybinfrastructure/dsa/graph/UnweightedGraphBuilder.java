package org.kybinfrastructure.dsa.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Builder for {@link UnweightedGraph}.
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class UnweightedGraphBuilder<T> {

  private final HashMap<Vertex<T>, ArrayList<Vertex<T>>> vertices = new HashMap<>();

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
    UnweightedGraph.add(this.vertices, vertex, neighbors);
    return this;
  }

  /**
   * Builds the graph.
   * 
   * @return built graph
   */
  public UnweightedGraph<T> build() {
    return new UnweightedGraph<>(vertices);
  }

}
