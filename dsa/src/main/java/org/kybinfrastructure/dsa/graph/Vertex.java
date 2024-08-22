package org.kybinfrastructure.dsa.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Represents the vertices in a graph.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Vertex<T> {

  private final T value;

  private final ArrayList<Vertex<T>> neighbors; // TODO: Decide on which data structure to use?

  /**
   * Builds a new vertex by given parameters.
   * 
   * @param <T> type of the vertex value
   * @param value vertex value
   * @param neighbors neighbors of the vertex
   * @return built vertex
   * @throws NullPointerException when given value is {@code null}
   */
  public static <T> Vertex<T> of(T value, Collection<Vertex<T>> neighbors) {
    Objects.requireNonNull(value, "value cannot be null!");
    return new Vertex<>(value, new ArrayList<>(neighbors));
  }

  /**
   * Returns the vertex value.
   * 
   * @return vertex value
   */
  public T value() {
    return value;
  }

}
