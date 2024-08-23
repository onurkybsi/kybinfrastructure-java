package org.kybinfrastructure.dsa.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a graph.
 * 
 * @author o.kayabasi@outlook.com
 * @see <a href="https://en.wikipedia.org/wiki/Graph_theory">Graph theory</a>
 */
public final class Graph<TVertex, TWeight extends Comparable<TWeight>> { // NOSONAR

  public final static class Builder<TVertex, TWeight extends Comparable<TWeight>> {

    // TODO: Decide on which data structure to use?
    private final ArrayList<Vertex<TVertex>> vertices = new ArrayList<>();
    private final ArrayList<Edge<TVertex, TWeight>> edges = new ArrayList<>();

    public Builder<TVertex, TWeight> vertex(Vertex<TVertex> vertex) {
      Objects.requireNonNull(vertex, "vertex cannot be null!");
      vertices.add(vertex);
      return this;
    }

    public Builder<TVertex, TWeight> edge(Edge<TVertex, TWeight> edge) {
      Objects.requireNonNull(edge, "edge cannot be null!");
      edges.add(edge);
      return this;
    }

    public Builder<TVertex, TWeight> vertices(Collection<Vertex<TVertex>> vertices) {
      Objects.requireNonNull(vertices, "vertices cannot be null!");
      if (vertices.stream().anyMatch(Objects::isNull)) {
        throw new IllegalArgumentException("vertices cannot contain null!");
      }

      vertices.addAll(vertices);
      return this;
    }

    public Builder<TVertex, TWeight> edges(Collection<Edge<TVertex, TWeight>> edges) {
      Objects.requireNonNull(edges, "edges cannot be null!");
      if (edges.stream().anyMatch(Objects::isNull)) {
        throw new IllegalArgumentException("edges cannot contain null!");
      }

      edges.addAll(edges);
      return this;
    }

  }

}
