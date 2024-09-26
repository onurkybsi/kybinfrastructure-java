package org.kybinfrastructure.dsa.graph;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

final class UnweightedGraphTest {

  @Test
  void should_Return_Shortest_Path_Between_Two_Vertices_Through_BFS() {
    // given
    var vertexA = new Vertex<String>("A");
    var vertexB = new Vertex<String>("B");
    var vertexC = new Vertex<String>("C");
    var vertexD = new Vertex<String>("D");
    var vertexE = new Vertex<String>("E");
    var vertexF = new Vertex<String>("F");
    var graph =
        UnweightedGraph.<String>builder().vertex(vertexA, List.of(vertexB, vertexC, vertexD))
            .vertex(vertexB, List.of(vertexA, vertexE, vertexF))
            .vertex(vertexC, List.of(vertexA, vertexE)).vertex(vertexD, List.of(vertexA, vertexE))
            .vertex(vertexE, List.of(vertexB, vertexC, vertexD, vertexF))
            .vertex(vertexF, List.of(vertexB, vertexE)).build();

    // when
    var actual = graph.shortestPath(vertexA, vertexF);

    // then
    assertThat(actual).isEqualTo(List.of(vertexA, vertexB, vertexF));
  }

  // TODO: Fix this, iterator doesn't guarantee that it will start with A!
  @Test
  void should_Return_Iterator_Through_BFS() {
    // given
    var vertexA = new Vertex<String>("A");
    var vertexB = new Vertex<String>("B");
    var vertexC = new Vertex<String>("C");
    var vertexD = new Vertex<String>("D");
    var vertexE = new Vertex<String>("E");
    var vertexF = new Vertex<String>("F");
    var graph =
        UnweightedGraph.<String>builder().vertex(vertexA, List.of(vertexB, vertexC, vertexD))
            .vertex(vertexB, List.of(vertexA, vertexE, vertexF))
            .vertex(vertexC, List.of(vertexA, vertexE)).vertex(vertexD, List.of(vertexA, vertexE))
            .vertex(vertexE, List.of(vertexB, vertexC, vertexD, vertexF))
            .vertex(vertexF, List.of(vertexB, vertexE)).build();

    // when
    var actual = new ArrayList<Vertex<String>>();
    for (Vertex<String> vertex : graph)
      actual.add(vertex);

    // then
    assertThat(actual.stream().map(Vertex::value).toList())
        .isEqualTo(Arrays.asList("A", "B", "C", "D", "E", "F"));
  }

}
