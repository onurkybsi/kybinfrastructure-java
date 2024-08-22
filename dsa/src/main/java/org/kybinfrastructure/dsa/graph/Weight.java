package org.kybinfrastructure.dsa.graph;

/**
 * Represents the weight of an edge in a graph.
 */
public record Weight<T extends Comparable<T>>(T value) {

  /**
   * Represents the infinite weight between two vertices.
   */
  public static final Weight<?> INFINITY = null;

}
