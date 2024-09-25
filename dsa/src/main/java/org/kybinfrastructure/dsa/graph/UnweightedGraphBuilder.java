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

  public UnweightedGraphBuilder<T> vertex(Vertex<T> vertex, Collection<Vertex<T>> neighbors) {
    Objects.requireNonNull(vertex, "vertex cannot be null!");
    Objects.requireNonNull(neighbors, "neighbors cannot be null!");

    for (Vertex<T> neighbor : neighbors) {
      if (neighbor == null) {
        throw new IllegalArgumentException("neighbors cannot contain null!");
      }

      var vertexNeighbors = this.vertices.getOrDefault(vertex, new ArrayList<>());
      if (!vertexNeighbors.contains(neighbor)) {
        vertexNeighbors.add(neighbor);
        this.vertices.put(vertex, vertexNeighbors);
      }
    }

    return this;
  }

  public UnweightedGraph<T> build() {
    return new UnweightedGraph<>(vertices);
  }

}
