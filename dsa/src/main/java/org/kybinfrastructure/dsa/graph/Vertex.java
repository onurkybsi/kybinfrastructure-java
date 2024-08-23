package org.kybinfrastructure.dsa.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Represents the vertices in a graph.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public final class Vertex<T> {

  private final T value;
  private final ArrayList<Vertex<T>> neighbors; // TODO: Decide on which data structure to use?

  /**
   * Returns the builder for {@link Vertex}.
   * 
   * @param <T> type of the value
   * @param value vertex value
   * @return builder for {@link Vertex}
   * @throws NullPointerException when given value is {@code null}
   */
  public static <T> Builder<T> builder(T value) {
    Objects.requireNonNull(value, "value cannot be null!");
    return new Builder<>(value, new ArrayList<>());
  }

  /**
   * Returns the vertex value.
   * 
   * @return vertex value
   */
  public T value() {
    return value;
  }

  /**
   * Returns the vertex neighbors.
   * 
   * @return vertex neighbors
   */
  public Collection<Vertex<T>> neighbors() {
    return neighbors.stream().toList();
  }

  /**
   * Adds a neighbor to the vertex.
   * 
   * @param neighbor neighbor of the vertex
   * @throws NullPointerException when given neighbor is {@code null}
   */
  public void addNeighbor(Vertex<T> neighbor) {
    Objects.requireNonNull(neighbor, "neighbor cannot be null!");
    this.neighbors.add(neighbor);
  }

  Collection<Vertex<T>> neighborsWithoutCopying() {
    return neighbors;
  }

  @Override
  public String toString() {
    return "Vertex [value=" + value + ", neighbors=" + Arrays.toString(neighbors.toArray()) + "]";
  }

  /**
   * Builder for {@link Vertex}.
   */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Builder<T> {

    private final T value;
    private final ArrayList<Vertex<T>> neighbors;

    /**
     * Adds a neighbor to the vertex.
     *
     * @param neighbor neighbor of the vertex
     * @return builder
     * @throws NullPointerException when given neighbor is {@code null}
     */
    public Builder<T> neighbor(Vertex<T> neighbor) {
      Objects.requireNonNull(neighbor, "neighbor cannot be null!");
      this.neighbors.add(neighbor);
      return this;
    }

    /**
     * Adds the neighbors to the vertex.
     *
     * @param neighbor neighbors of the vertex
     * @return builder
     * @throws NullPointerException when given neighbors is {@code null}
     * @throws IllegalArgumentException when given neighbors contains {@code null}
     */
    public Builder<T> neighbors(Collection<Vertex<T>> neighbors) {
      Objects.requireNonNull(neighbors, "neighbor cannot be null!");
      if (neighbors.stream().anyMatch(Objects::isNull)) {
        throw new IllegalArgumentException("neighbors cannot contain null!");
      }

      this.neighbors.addAll(neighbors);
      return this;
    }

    /**
     * Builds {@link Vertex} with given values.
     *
     * @return built {@link Vertex}
     */
    public Vertex<T> build() {
      return new Vertex<>(this.value, this.neighbors);
    }

  }

}
