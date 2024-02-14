package org.kybinfrastructure.dsa.dynamic_array;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

final class DynamicArrayTest {

  @Test
  void empty_Should_Build_EmptyDynamicArray() {
    // given

    // when
    var actualResult = DynamicArray.empty();

    // then
    assertThrows(IndexOutOfBoundsException.class, () -> actualResult.getAt(0));
    assertEquals(0, actualResult.size());
  }

  @Test
  void from_Should_Build_DynamicArray_From_GivenElement() {
    // given
    Integer[] elements = new Integer[] {1, 2, 3, 4, 5};

    // when
    var actualResult = DynamicArray.from(elements);

    // then
    assertEquals(1, actualResult.getAt(0));
    assertEquals(2, actualResult.getAt(1));
    assertEquals(3, actualResult.getAt(2));
    assertEquals(4, actualResult.getAt(3));
    assertEquals(5, actualResult.getAt(4));
    assertThrows(IndexOutOfBoundsException.class, () -> actualResult.getAt(5));
    assertEquals(5, actualResult.size());
  }

}
