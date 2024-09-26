package org.kybinfrastructure.dsa.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Represents a simple unweighted graph.
 * 
 * @author Onur Kayabasi(o.kayabasi@outlook.com)
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class UnweightedGraph<T> {

  private final HashMap<Vertex<T>, ArrayList<Vertex<T>>> vertices;
  private final IteratorType iteratorType;

  /**
   * Returns the builder for {@link UnweightedGraph}.
   * 
   * @param <T> type of the vertex values
   * @return builder for {@link UnweightedGraph}
   */
  public static <T> UnweightedGraphBuilder<T> builder() {
    return new UnweightedGraphBuilder<>();
  }

  /**
   * Returns the shortest path between {@code from} and {@code to}.
   * 
   * @param from vertex the path starts from
   * @param to vertex the path ends at
   * @return shortest path between given vertices
   * @throws NullPointerException if one of given vertices is null
   * @apiNote The <a href="https://en.wikipedia.org/wiki/Breadth-first_search">BFS</a> is applied to
   *          find the shortest path.
   */
  public ArrayList<Vertex<T>> shortestPath(Vertex<T> from, Vertex<T> to) {
    Objects.requireNonNull(from, "from cannot be null!");
    Objects.requireNonNull(to, "to cannot be null!");

    HashSet<Vertex<T>> visited = new HashSet<>();
    Queue<ArrayList<Vertex<T>>> temp = new LinkedList<>();
    temp.add(new ArrayList<>(Arrays.asList(from)));
    while (!temp.isEmpty()) {
      var curPath = temp.remove();
      var curVertex = curPath.get(curPath.size() - 1);
      if (curVertex.equals(to)) {
        return curPath;
      }

      var neighbors = this.vertices.get(curVertex);
      for (var neighbor : neighbors) {
        var anotherPath = new ArrayList<>(curPath);
        anotherPath.add(neighbor);
        temp.add(anotherPath);
      }

      visited.add(curVertex);
    }

    return null;
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    for (var vertex : vertices.entrySet()) {
      b.append("\n");
      b.append("\t");
      b.append(vertex.getKey().value().toString());
      b.append("=");
      b.append(Arrays.toString(vertex.getValue().stream().map(Vertex::value).toArray()));
    }
    return "UnweightedGraph[%s\n]".formatted(b);
  }

}
