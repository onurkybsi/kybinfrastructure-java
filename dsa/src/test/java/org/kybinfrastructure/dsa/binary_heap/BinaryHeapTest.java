package org.kybinfrastructure.dsa.binary_heap;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
