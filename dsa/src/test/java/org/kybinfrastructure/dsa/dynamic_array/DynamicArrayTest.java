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

  @Test
  void getAt_Should_Return_Item_At_Given_Ix() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4, 5});

    // when
    var actualResult = dynamicArray.getAt(3);

    // then
    assertEquals(4, actualResult);
  }

  @Test
  void getAt_Should_Throw_IndexOutOfBoundsException_When_GivenIx_OutOf_UnderlyingArray() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4, 5});

    // when & then
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(5));
  }

  @Test
  void setAt_Should_Set_Item_At_Given_Ix_By_GivenValue() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4, 5});

    // when
    dynamicArray.setAt(3, 6);

    // then
    assertEquals(6, dynamicArray.getAt(3));
  }

  @Test
  void setAt_Should_Throw_IndexOutOfBoundsException_When_GivenIx_OutOf_UnderlyingArray() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4, 5});

    // when & then
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.setAt(5, 6));
  }

  @Test
  void insertFirst_Should_Add_GivenItem_IntoArrayStart_By_Increasing_Capacity_When_Capacity_IsFull() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {2});
    dynamicArray.insertLast(3);

    // when
    dynamicArray.insertFirst(1);

    // then
    assertEquals(1, dynamicArray.getAt(0));
    assertEquals(2, dynamicArray.getAt(1));
    assertEquals(3, dynamicArray.getAt(2));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(3));
  }

  @Test
  void insertFirst_Should_Add_GivenItem_IntoArrayStart_By_Setting_StartIx_When_ThereIs_Room_At_Start_Of_UnderlyingArray() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 3, 4, 5});
    dynamicArray.deleteFirst();

    // when
    dynamicArray.insertFirst(2);

    // then
    assertEquals(2, dynamicArray.getAt(0));
    assertEquals(3, dynamicArray.getAt(1));
    assertEquals(4, dynamicArray.getAt(2));
    assertEquals(5, dynamicArray.getAt(3));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(4));
  }

  @Test
  void insertFirst_Should_Add_GivenItem_IntoArrayStart_By_Shifting_Items_When_ThereIs_NoRoom_At_Start_Of_UnderlyingArray() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {2, 3, 4, 5});

    // when
    dynamicArray.insertFirst(1);

    // then
    assertEquals(1, dynamicArray.getAt(0));
    assertEquals(2, dynamicArray.getAt(1));
    assertEquals(3, dynamicArray.getAt(2));
    assertEquals(4, dynamicArray.getAt(3));
    assertEquals(5, dynamicArray.getAt(4));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(5));
  }

  @Test
  void deleteFirst_Should_Not_Do_Something_When_Array_Has_NoItem() {
    // given
    var dynamicArray = DynamicArray.empty();

    // when
    dynamicArray.deleteFirst();

    // then
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(0));
  }

  @Test
  void deleteFirst_Should_Delete_FirstItem_By_Decreasing_Capacity_When_SizeCapacity_Rate_Is_Lower_Than_ShrinkThreshold() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4, 5, 6});

    // when
    dynamicArray.deleteFirst();

    // then
    assertEquals(2, dynamicArray.getAt(0));
    assertEquals(3, dynamicArray.getAt(1));
    assertEquals(4, dynamicArray.getAt(2));
    assertEquals(5, dynamicArray.getAt(3));
    assertEquals(6, dynamicArray.getAt(4));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(5));
  }

  @Test
  void deleteFirst_Should_Delete_FirstItem_By_Setting_StartIx_When_SizeCapacity_Rate_IsNot_Lower_Than_ShrinkThreshold() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4, 5, 6});
    dynamicArray.deleteLast();

    // when
    dynamicArray.deleteFirst();

    // then
    assertEquals(2, dynamicArray.getAt(0));
    assertEquals(3, dynamicArray.getAt(1));
    assertEquals(4, dynamicArray.getAt(2));
    assertEquals(5, dynamicArray.getAt(3));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(4));
  }

  @Test
  void deleteFirst_Should_Delete_FirstItem_By_Setting_StartIx_When_Capacity_Is_Lower_That_DefaultCapacity() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3});

    // when
    dynamicArray.deleteFirst();

    // then
    assertEquals(2, dynamicArray.getAt(0));
    assertEquals(3, dynamicArray.getAt(1));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(2));
  }

  @Test
  void insertLast_Should_Add_GivenItem_IntoArrayEnd_By_Increasing_Capacity_When_Capacity_IsFull() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {2});
    dynamicArray.insertFirst(1);

    // when
    dynamicArray.insertLast(3);

    // then
    assertEquals(1, dynamicArray.getAt(0));
    assertEquals(2, dynamicArray.getAt(1));
    assertEquals(3, dynamicArray.getAt(2));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(3));
  }

  @Test
  void insertLast_Should_Add_GivenItem_IntoArrayEnd_By_Setting_EndIx_When_ThereIs_Room_At_End_Of_UnderlyingArray() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4});
    dynamicArray.deleteLast();

    // when
    dynamicArray.insertLast(5);

    // then
    assertEquals(1, dynamicArray.getAt(0));
    assertEquals(2, dynamicArray.getAt(1));
    assertEquals(3, dynamicArray.getAt(2));
    assertEquals(5, dynamicArray.getAt(3));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(4));
  }

  @Test
  void deleteLast_Should_Not_Do_Something_When_Array_Has_NoItem() {
    // given
    var dynamicArray = DynamicArray.empty();

    // when
    dynamicArray.deleteLast();

    // then
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(0));
  }

  @Test
  void deleteLast_Should_Delete_LastItem_By_Decreasing_Capacity_When_SizeCapacity_Rate_Is_Lower_Than_ShrinkThreshold() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4, 5, 6});

    // when
    dynamicArray.deleteLast();

    // then
    assertEquals(1, dynamicArray.getAt(0));
    assertEquals(2, dynamicArray.getAt(1));
    assertEquals(3, dynamicArray.getAt(2));
    assertEquals(4, dynamicArray.getAt(3));
    assertEquals(5, dynamicArray.getAt(4));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(5));
  }

  @Test
  void deleteLast_Should_Delete_LastItem_By_Setting_EndIx_When_SizeCapacity_Rate_IsNot_Lower_Than_ShrinkThreshold() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4, 5, 6});
    dynamicArray.deleteFirst();

    // when
    dynamicArray.deleteLast();

    // then
    assertEquals(2, dynamicArray.getAt(0));
    assertEquals(3, dynamicArray.getAt(1));
    assertEquals(4, dynamicArray.getAt(2));
    assertEquals(5, dynamicArray.getAt(3));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(4));
  }

  @Test
  void deleteLast_Should_Delete_LastItem_By_Setting_EndIx_When_Capacity_Is_Lower_That_DefaultCapacity() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3});

    // when
    dynamicArray.deleteLast();

    // then
    assertEquals(1, dynamicArray.getAt(0));
    assertEquals(2, dynamicArray.getAt(1));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(2));
  }

  @Test
  void insertAt_Should_Add_GivenItem_IntoArrayGivenIx_By_Increasing_Capacity_When_Capacity_IsFull() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {2});
    dynamicArray.insertFirst(1);

    // when
    dynamicArray.insertAt(1, 3);

    // then
    assertEquals(1, dynamicArray.getAt(0));
    assertEquals(3, dynamicArray.getAt(1));
    assertEquals(2, dynamicArray.getAt(2));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(3));
  }

  @Test
  void insertAt_Should_Add_GivenItem_IntoArrayGivenIx_By_Shifting_Items_AfterIx_When_Capacity_IsNotFull() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4});
    dynamicArray.deleteLast();

    // when
    dynamicArray.insertAt(1, 5);

    // then
    assertEquals(1, dynamicArray.getAt(0));
    assertEquals(5, dynamicArray.getAt(1));
    assertEquals(2, dynamicArray.getAt(2));
    assertEquals(3, dynamicArray.getAt(3));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(4));
  }

  @Test
  void insertAt_Should_Throw_IndexOutOfBoundsException_When_GivenIx_OutOf_UnderlyingArray() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4, 5});

    // when & then
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.insertAt(5, 6));
  }

  @Test
  void deleteAt_Should_Delete_ItemAtGivenIx_By_Decreasing_Capacity_When_SizeCapacity_Rate_Is_Lower_Than_ShrinkThreshold() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4, 5, 6});

    // when
    dynamicArray.deleteAt(2);

    // then
    assertEquals(1, dynamicArray.getAt(0));
    assertEquals(2, dynamicArray.getAt(1));
    assertEquals(4, dynamicArray.getAt(2));
    assertEquals(5, dynamicArray.getAt(3));
    assertEquals(6, dynamicArray.getAt(4));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(5));
  }

  @Test
  void deleteAt_Should_Delete_ItemAtGivenIx_By_Shifting_Items_AfterIx_When_SizeCapacity_Rate_IsNot_Lower_Than_ShrinkThreshold() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3, 4, 5, 6});
    dynamicArray.deleteFirst();

    // when
    dynamicArray.deleteAt(2);

    // then
    assertEquals(2, dynamicArray.getAt(0));
    assertEquals(3, dynamicArray.getAt(1));
    assertEquals(5, dynamicArray.getAt(2));
    assertEquals(6, dynamicArray.getAt(3));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(4));
  }

  @Test
  void deleteAt_Should_Delete_ItemAtGivenIx_By_Shifting_Items_AfterIx_When_Capacity_Is_Lower_That_DefaultCapacity() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {1, 2, 3});

    // when
    dynamicArray.deleteAt(1);

    // then
    assertEquals(1, dynamicArray.getAt(0));
    assertEquals(3, dynamicArray.getAt(1));
    assertThrows(IndexOutOfBoundsException.class, () -> dynamicArray.getAt(2));
  }

  @Test
  void size_Should_Return_Zero_When_DynamicArray_Has_NoItem() {
    // given
    var dynamicArray = DynamicArray.empty();

    // when
    int actualResult = dynamicArray.size();

    // then
    assertEquals(0, actualResult);
  }

  @Test
  void size_Should_Return_NumberOfItems_In_DynamicArray_When_DynamicArray_Has_SomeItems() {
    // given
    var dynamicArray = DynamicArray.from(new Integer[] {2, 3, 4, 5});
    dynamicArray.insertFirst(1);

    // when
    int actualResult = dynamicArray.size();

    // then
    assertEquals(5, actualResult);
  }

}
