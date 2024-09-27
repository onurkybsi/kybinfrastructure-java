package org.kybinfrastructure.dsa.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
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
public final class UnweightedGraph<T> implements Iterable<Vertex<T>> {

  private final HashMap<Vertex<T>, ArrayList<Vertex<T>>> vertices;

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
   * Returns the number of vertices in the graph.
   * 
   * @return number of vertices in the graph
   */
  public int size() {
    return vertices.size();
  }

  /**
   * Returns the neighbors of given vertex.
   * 
   * @param vertex vertex that its neighbors to return
   * @return neighbors of the vertex, {@code null} if no such a vertex exists
   * @throws NullPointerException if given {@code vertex} is null
   */
  public Collection<Vertex<T>> neighbors(Vertex<T> vertex) {
    Objects.requireNonNull(vertex, "vertex cannot be null!");

    var neighbors = this.vertices.get(vertex);
    if (neighbors == null) {
      return null;
    }
    return neighbors.stream().toList(); // We don't want to return the actual list.
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
    temp.add(new ArrayList<>(List.of(from)));
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
  public Iterator<Vertex<T>> iterator() {
    return new BfsIterator<>(bfs(this.vertices));
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

  private static <T> ArrayList<Vertex<T>> bfs(HashMap<Vertex<T>, ArrayList<Vertex<T>>> vertices) {
    ArrayList<Vertex<T>> visited = new ArrayList<>();

    Queue<ArrayList<Vertex<T>>> temp = new LinkedList<>();
    Vertex<T> from = vertices.entrySet().stream().findAny().map(v -> v.getKey()).orElse(null);
    if (from == null)
      return visited;
    temp.add(new ArrayList<Vertex<T>>(List.of(from)));
    while (visited.size() < vertices.size()) {
      var curPath = temp.remove();
      var curVertex = curPath.get(curPath.size() - 1);

      var neighbors = vertices.get(curVertex);
      for (var neighbor : neighbors) {
        if (visited.contains(neighbor))
          continue;

        var anotherPath = new ArrayList<>(curPath);
        anotherPath.add(neighbor);
        temp.add(anotherPath);
      }

      visited.add(curVertex);
    }

    return visited;
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  private static class BfsIterator<T> implements Iterator<Vertex<T>> {

    private final ArrayList<Vertex<T>> vertices;

    private int curIx = 0;

    @Override
    public boolean hasNext() {
      return curIx < vertices.size();
    }

    @Override
    public Vertex<T> next() {
      if (curIx >= vertices.size()) {
        throw new NoSuchElementException();
      }
      return vertices.get(curIx++);
    }

  }

}
