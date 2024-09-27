package org.kybinfrastructure.dsa.graph;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class UnweightedGraphTest {

  private UnweightedGraph<String> testGraph;

  @BeforeEach
  void setUp() {
    var vertexA = new Vertex<String>("A");
    var vertexB = new Vertex<String>("B");
    var vertexC = new Vertex<String>("C");
    var vertexD = new Vertex<String>("D");
    var vertexE = new Vertex<String>("E");
    var vertexF = new Vertex<String>("F");
    testGraph =
        UnweightedGraph.<String>builder().vertex(vertexA, List.of(vertexB, vertexC, vertexD))
            .vertex(vertexB, List.of(vertexA, vertexE, vertexF))
            .vertex(vertexC, List.of(vertexA, vertexE)).vertex(vertexD, List.of(vertexA, vertexE))
            .vertex(vertexE, List.of(vertexB, vertexC, vertexD, vertexF))
            .vertex(vertexF, List.of(vertexB, vertexE)).build();
  }

  @Test
  void should_Return_Shortest_Path_Between_Two_Vertices_Through_BFS() {
    // given
    var vertexA = new Vertex<String>("A");
    var vertexB = new Vertex<String>("B");
    var vertexF = new Vertex<String>("F");

    // when
    var actual = testGraph.shortestPath(vertexA, vertexF);

    // then
    assertThat(actual).isEqualTo(List.of(vertexA, vertexB, vertexF));
  }

  @Test
  void should_Return_Number_Of_Vertices() {
    // given

    // when
    var actual = testGraph.size();

    // then
    assertThat(actual).isEqualTo(6);
  }

  @Test
  void should_Return_Neighbors_Of_Given_Vertex() {
    // given
    var vertexA = new Vertex<String>("A");

    // when
    var actual = testGraph.neighbors(vertexA);

    // then
    assertThat(actual.stream().map(Vertex::value).toList()).isEqualTo(List.of("B", "C", "D"));
  }

  @Test
  void should_Return_Null_When_No_Vertex_Exists_To_Return_Its_Neighbors() {
    // given
    var vertexG = new Vertex<String>("G");

    // when
    var actual = testGraph.neighbors(vertexG);

    // then
    assertThat(actual).isNull();
  }

  @Test
  void should_Add_New_Vertices_To_Graph() {
    // given
    var vertex = new Vertex<>("G");
    var vertexA = new Vertex<String>("A");
    var vertexB = new Vertex<String>("B");
    var neighbors = List.of(vertexA, vertexB);

    // when
    testGraph.add(vertex, neighbors);

    // then
    assertThat(testGraph.size()).isEqualTo(7);
    assertThat(testGraph.neighbors(vertex)).isEqualTo(neighbors);
    assertThat(testGraph.neighbors(vertexA).stream().map(Vertex::value).toList())
        .isEqualTo(List.of("B", "C", "D", "G"));
    assertThat(testGraph.neighbors(vertexB).stream().map(Vertex::value).toList())
        .isEqualTo(List.of("A", "E", "F", "G"));
  }

  // TODO: Fix this, iterator doesn't guarantee that it will start with A!
  @Test
  void should_Return_Iterator_Through_BFS() {
    // given

    // when
    var actual = new ArrayList<Vertex<String>>();
    for (Vertex<String> vertex : testGraph)
      actual.add(vertex);

    // then
    assertThat(actual.stream().map(Vertex::value).toList())
        .isEqualTo(Arrays.asList("A", "B", "C", "D", "E", "F"));
  }

}
