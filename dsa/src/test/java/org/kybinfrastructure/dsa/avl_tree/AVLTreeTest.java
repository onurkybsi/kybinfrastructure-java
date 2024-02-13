package org.kybinfrastructure.dsa.avl_tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

final class AVLTreeTest {

  @Test
  void empty_Should_Build_EmptyAVLTree() {
    // given

    // when
    AVLTree<Integer, Integer> actualResult = AVLTree.empty();

    // then
    assertEquals(0, actualResult.length());
  }

  @ParameterizedTest
  @MethodSource("provideKeyValuesForTreeBuiltInDifferentCases")
  void from_Should_Build_AVLTree_By_GivenKeyValues(Map<Integer, Integer> keyValues,
      String expectedResult) {
    // given

    // when
    var actualResult = AVLTree.from(keyValues);

    // then
    assertEquals(expectedResult, actualResult.toString());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void from_Should_Build_EmptyTree_When_GivenKeyValues_Is_Empty(Map<Integer, Integer> keyValues) {
    // given

    // when
    AVLTree<Integer, Integer> actualResult = AVLTree.from(keyValues);

    // then
    assertEquals(0, actualResult.length());
  }

  @Test
  void length_Should_Return_NumberOfNodesInTree() {
    // given
    var tree = AVLTree.from(buildTestKeyValues());
    tree.insert(8, 9);
    tree.delete(3);

    // when
    int actualResult = tree.length();

    // then
    assertEquals(7, actualResult);
  }

  @Test
  void find_Should_Return_TreeNodeWithGivenKey() {
    // given
    var tree = AVLTree.from(buildTestKeyValues());

    // when
    var actualResult = tree.find(4);

    // then
    assertEquals(4, actualResult);
  }

  @Test
  void find_Should_Return_Null_When_NoNode_Exists_With_GivenKey_InTree() {
    // given
    var tree = AVLTree.from(buildTestKeyValues());

    // when
    var actualResult = tree.find(8);

    // then
    assertNull(actualResult);
  }

  @Test
  void insert_Should_Insert_GivenNewNode_Into_Tree() {
    // given
    var tree = AVLTree.from(buildTestKeyValues());

    // when
    var actualResult = tree.insert(8, 9);

    // then
    assertEquals(8, actualResult);
    assertEquals(9, tree.find(actualResult));
    assertEquals(8, tree.length());
  }

  @Test
  void insert_Should_Return_Null_When_GivenKey_Already_Exists_InTree() {
    // given
    var tree = AVLTree.from(buildTestKeyValues());

    // when
    var actualResult = tree.insert(1, 8);

    // then
    assertNull(actualResult);
    assertEquals(7, tree.length());
  }

  @Test
  void delete_Should_Delete_NodeFromTree_By_GivenKey() {
    // given
    var tree = AVLTree.from(buildTestKeyValues());
    Integer keyToDelete = 1;

    // when
    var actualResult = tree.delete(keyToDelete);

    // then
    assertEquals(1, actualResult);
    assertEquals("[Node[key=2,value=6,left=null,right=3,parent=4], "
        + "Node[key=3,value=5,left=null,right=null,parent=2], "
        + "Node[key=4,value=4,left=2,right=6,parent=null], "
        + "Node[key=5,value=3,left=null,right=null,parent=6], "
        + "Node[key=6,value=2,left=5,right=7,parent=4], "
        + "Node[key=7,value=1,left=null,right=null,parent=6]]", tree.toString());
  }

  @Test
  void delete_Should_Return_Null_When_NoNode_Exists_With_GivenKey_InTree() {
    // given
    var tree = AVLTree.from(buildTestKeyValues());
    Integer keyToDelete = 8;

    // when
    var actualResult = tree.delete(keyToDelete);

    // then
    assertNull(actualResult);
  }

  @Test
  void iterator_Should_Return_IteratorForTreeInTraversalOrder() {
    // given
    var tree = AVLTree.from(buildTestKeyValues());

    // when
    ArrayList<Integer> iteratedNodeKeysInOrder = new ArrayList<>();
    var it = tree.iterator();
    while (it.hasNext()) {
      iteratedNodeKeysInOrder.add(it.next().getKey());
    }

    assertEquals(1, iteratedNodeKeysInOrder.get(0));
    assertEquals(2, iteratedNodeKeysInOrder.get(1));
    assertEquals(3, iteratedNodeKeysInOrder.get(2));
    assertEquals(4, iteratedNodeKeysInOrder.get(3));
    assertEquals(5, iteratedNodeKeysInOrder.get(4));
    assertEquals(6, iteratedNodeKeysInOrder.get(5));
    assertEquals(7, iteratedNodeKeysInOrder.get(6));
  }

  private static Map<Integer, Integer> buildTestKeyValues() {
    LinkedHashMap<Integer, Integer> keyValues = new LinkedHashMap<>();
    keyValues.put(1, 7);
    keyValues.put(2, 6);
    keyValues.put(3, 5);
    keyValues.put(4, 4);
    keyValues.put(5, 3);
    keyValues.put(6, 2);
    keyValues.put(7, 1);
    return keyValues;
  }

  private static Stream<Arguments> provideKeyValuesForTreeBuiltInDifferentCases() {
    LinkedHashMap<Integer, Integer> case1 = new LinkedHashMap<>();
    case1.put(1, 7);
    case1.put(2, 6);
    case1.put(3, 5);
    case1.put(4, 4);
    case1.put(5, 3);
    case1.put(6, 2);
    case1.put(7, 1);

    LinkedHashMap<Integer, Integer> case2 = new LinkedHashMap<>();
    case2.put(4, 4);
    case2.put(5, 3);
    case2.put(6, 2);
    case2.put(7, 1);
    case2.put(1, 7);
    case2.put(2, 6);
    case2.put(3, 5);

    return Stream.of(
        Arguments.arguments(case1,
            "[Node[key=1,value=7,left=null,right=null,parent=2], "
                + "Node[key=2,value=6,left=1,right=3,parent=4], "
                + "Node[key=3,value=5,left=null,right=null,parent=2], "
                + "Node[key=4,value=4,left=2,right=6,parent=null], "
                + "Node[key=5,value=3,left=null,right=null,parent=6], "
                + "Node[key=6,value=2,left=5,right=7,parent=4], "
                + "Node[key=7,value=1,left=null,right=null,parent=6]]"),
        Arguments.arguments(case2,
            "[Node[key=1,value=7,left=null,right=null,parent=2], "
                + "Node[key=2,value=6,left=1,right=4,parent=5], "
                + "Node[key=3,value=5,left=null,right=null,parent=4], "
                + "Node[key=4,value=4,left=3,right=null,parent=2], "
                + "Node[key=5,value=3,left=2,right=6,parent=null], "
                + "Node[key=6,value=2,left=null,right=7,parent=5], "
                + "Node[key=7,value=1,left=null,right=null,parent=6]]"));
  }

}
