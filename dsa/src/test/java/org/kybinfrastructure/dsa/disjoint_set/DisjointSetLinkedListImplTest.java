package org.kybinfrastructure.dsa.disjoint_set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.kybinfrastructure.dsa.disjoint_set.DisjointSetLinkedListImpl.Node;

final class DisjointSetLinkedListImplTest {
  @Nested
  final class MakeSet {
    @Test
    void should_Return_New_Representative_When_Given_Value_Is_Not_Part_Of_Any_Set() {
      // given
      int value = 1;
      var set = DisjointSet.<Integer>backedByLinkedList();

      // when
      var actual = set.makeSet(value);

      // then
      var actualNode = (Node<Integer>) actual;
      assertEquals(value, actualNode.getValue());
      assertEquals(null, actualNode.getNext());
      assertEquals(actual, actualNode.getHead());
      assertEquals(actual, actualNode.getTail());
      assertEquals(1, actualNode.getSize());
    }

    @Test
    void should_Return_Existing_Representative_When_Given_Value_Is_Already_Part_Of_A_Set() {
      // given
      int value = 1;
      var set = DisjointSet.<Integer>backedByLinkedList();
      set.makeSet(value);
      set.makeSet(2);
      set.makeSet(3);
      set.union(2, 3);
      set.union(1, 2);

      // when
      var actual = set.makeSet(value);

      // then
      var actualNode = (Node<Integer>) actual;
      assertEquals(2, actualNode.getValue());
      assertEquals(3, actualNode.getNext().getValue());
      assertEquals(2, actualNode.getHead().getValue());
      assertEquals(value, actualNode.getTail().getValue());
      assertEquals(3, actualNode.getSize());
    }
  }

  @Nested
  final class Union {
    @Test
    void should_Append_Smaller_Set_To_Bigger_Set() {
      // given
      var set = DisjointSet.<Integer>backedByLinkedList();
      set.makeSet(1);
      set.makeSet(2);
      set.union(1, 2);
      set.makeSet(3);

      // when
      var actual = set.union(2, 3);

      // then
      var actualNode = (Node<Integer>) actual;
      assertEquals(1, actualNode.getValue());
      assertEquals(2, actualNode.getNext().getValue());
      assertEquals(1, actualNode.getHead().getValue());
      assertEquals(3, actualNode.getTail().getValue());
      assertEquals(3, actualNode.getSize());
    }

    @Test
    void should_Return_Representative_Only_When_Given_Values_Are_Already_In_The_Same_Set() {
      // given
      var set = DisjointSet.<Integer>backedByLinkedList();
      set.makeSet(1);
      set.makeSet(2);
      set.union(1, 2);

      // when
      var actual = set.union(1, 2);

      // then
      var actualNode = (Node<Integer>) actual;
      assertEquals(1, actualNode.getValue());
      assertEquals(2, actualNode.getNext().getValue());
      assertEquals(1, actualNode.getHead().getValue());
      assertEquals(2, actualNode.getTail().getValue());
      assertEquals(2, actualNode.getSize());
    }
  }

  @Nested
  final class Find {
    @Test
    void should_Return_Representative_Of_Set_That_Given_Value_Is_Part_Of() {
      // given
      var set = DisjointSet.<Integer>backedByLinkedList();
      set.makeSet(1);
      set.makeSet(2);
      set.union(1, 2);

      // when
      var actual = set.find(2);

      // then
      assertEquals(1, actual.value());
    }
  }
}
