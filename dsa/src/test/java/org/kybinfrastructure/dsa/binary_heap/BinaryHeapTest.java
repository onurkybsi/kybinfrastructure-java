package org.kybinfrastructure.dsa.binary_heap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

final class BinaryHeapTest {

  @Test
  void empty_Should_Build_EmptyBinaryHeap() {
    // given

    // when
    var actualResult = BinaryHeap.empty();

    // then
    assertEquals(0, actualResult.size());
  }

  @Test
  void from_Should_Build_BinaryHeap_By_GivenSourceArr() {
    // given
    Integer[] elements = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    // when
    var actualResult = BinaryHeap.from(elements);

    // then
    assertEquals(10, actualResult.size());
    assertEquals(9, actualResult.deleteMax());
    assertEquals(8, actualResult.deleteMax());
    assertEquals(7, actualResult.deleteMax());
    assertEquals(6, actualResult.deleteMax());
    assertEquals(5, actualResult.deleteMax());
    assertEquals(4, actualResult.deleteMax());
    assertEquals(3, actualResult.deleteMax());
    assertEquals(2, actualResult.deleteMax());
    assertEquals(1, actualResult.deleteMax());
    assertEquals(0, actualResult.deleteMax());
  }

  @Test
  void insert_Should_Insert_NewElement_Into_BinaryMap() {
    // given
    BinaryHeap<Integer> binaryHeap = BinaryHeap.empty();
    Integer elementToInsert = 5;

    // when
    binaryHeap.insert(elementToInsert);

    // then
    assertEquals(1, binaryHeap.size());
    assertEquals(elementToInsert, binaryHeap.findMax());
  }

  @Test
  void findMax_Should_Return_ElementWithMaxValue() {
    // given
    Integer[] elements = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    BinaryHeap<Integer> binaryHeap = BinaryHeap.from(elements);

    // when
    var actualResult = binaryHeap.findMax();

    // then
    assertEquals(9, actualResult);
  }

  @Test
  void findMax_Should_Return_Null_When_ThereIs_NoElement_In_BinaryHeap() {
    // given
    BinaryHeap<Integer> binaryHeap = BinaryHeap.empty();

    // when
    var actualResult = binaryHeap.findMax();

    // then
    assertNull(actualResult);
  }

  @Test
  void deleteMax_Should_Delete_ElementWithMaxValue_From_BinaryHeap() {
    // given
    Integer[] elements = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    BinaryHeap<Integer> binaryHeap = BinaryHeap.from(elements);

    // when
    var actualResult = binaryHeap.deleteMax();

    // then
    assertEquals(9, actualResult);
    assertEquals(9, binaryHeap.size());
  }

  @Test
  void deleteMax_Should_Return_Null_When_ThereIs_NoElement_In_BinaryHeap() {
    // given
    BinaryHeap<Integer> binaryHeap = BinaryHeap.empty();

    // when
    var actualResult = binaryHeap.deleteMax();

    // then
    assertNull(actualResult);
  }

  @Test
  void size_Should_Return_NumberOfElements_In_BinaryHeap() {
    // given
    Integer[] elements = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    BinaryHeap<Integer> binaryHeap = BinaryHeap.from(elements);

    // when
    var actualResult = binaryHeap.size();

    // then
    assertEquals(10, actualResult);
  }

}
