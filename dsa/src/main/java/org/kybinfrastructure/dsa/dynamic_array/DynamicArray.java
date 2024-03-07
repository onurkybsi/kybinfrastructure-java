package org.kybinfrastructure.dsa.dynamic_array;

import java.util.Arrays;

/**
 * Non-thread-safe <a href="https://en.wikipedia.org/wiki/Dynamic_array">dynamic array</a>
 * implementation.
 *
 * @author o.kayabasi@outlook.com
 */
public final class DynamicArray<T> {

  private static final int DEFAULT_CAPACITY = 10;
  private static final int GROWTH_FACTOR = 2;
  private static final float SHRINK_THRESHOLD = 0.75F;

  private int capacity = DEFAULT_CAPACITY;
  private int startIx = 0;
  private int endIx = -1;
  private Object[] src;

  private DynamicArray() {
    this.src = new Object[this.capacity];
  }

  private DynamicArray(T[] elements) {
    this.capacity = elements.length * GROWTH_FACTOR;
    this.startIx = 0;
    this.endIx = elements.length - 1;
    this.src = Arrays.copyOf(elements, this.capacity);
  }

  public static <T> DynamicArray<T> empty() {
    return new DynamicArray<>();
  }

  public static <T> DynamicArray<T> from(T[] elements) {
    return new DynamicArray<>(elements);
  }

  @SuppressWarnings({"unchecked"})
  public T getAt(int ix) {
    if (ix >= size()) {
      throw new IndexOutOfBoundsException("No element exists at index " + ix);
    }
    return (T) this.src[startIx + ix];
  }

  public void setAt(int ix, T valueToSet) {
    if (ix >= size()) {
      throw new IndexOutOfBoundsException("No element exists at index " + ix);
    }
    this.src[startIx + ix] = valueToSet;
  }

  public void insertFirst(T valueToInsert) {
    int size = size();

    if (size == capacity) {
      int newCapacity = size * GROWTH_FACTOR;

      Object[] grownSrc = new Object[newCapacity];
      grownSrc[0] = valueToInsert;
      for (int i = 0; i < size; i++) {
        grownSrc[i + 1] = this.src[startIx + i];
      }

      this.capacity = newCapacity;
      this.startIx = 0;
      this.endIx = size;
      this.src = grownSrc;
    } else if (this.startIx > 0) {
      this.startIx--;
      this.src[startIx] = valueToInsert;
    } else {
      for (int i = size - 1; i >= 0; i--) {
        this.src[i + 1] = this.src[i];
      }
      this.src[0] = valueToInsert;

      this.endIx = size;
    }
  }

  public void deleteFirst() {
    int newSize = size() - 1;
    if (newSize < 0) {
      return;
    }

    if (this.capacity > DEFAULT_CAPACITY && ((float) newSize / this.capacity) < SHRINK_THRESHOLD) {
      int newCapacity = Math.max(DEFAULT_CAPACITY, capacity / 2);
      Object[] shrunkSource = new Object[newCapacity];
      for (int i = 0; i < newSize; i++) {
        shrunkSource[i] = this.src[startIx + 1 + i];
      }

      this.capacity = newCapacity;
      this.startIx = 0;
      this.endIx = newSize - 1;
      this.src = shrunkSource;
    } else {
      this.startIx++;
    }
  }

  public void insertLast(T valueToInsert) {
    int size = size();

    if (size == capacity) {
      int newCapacity = size * GROWTH_FACTOR;

      Object[] grownSrc = new Object[newCapacity];
      for (int i = 0; i < size; i++) {
        grownSrc[i] = this.src[startIx + i];
      }
      grownSrc[size] = valueToInsert;

      this.capacity = newCapacity;
      this.startIx = 0;
      this.endIx = size;
      this.src = grownSrc;
    } else {
      this.endIx++;
      this.src[endIx] = valueToInsert;
    }
  }

  public void deleteLast() {
    int newSize = size() - 1;
    if (newSize < 0) {
      return;
    }

    if (this.capacity > DEFAULT_CAPACITY && ((float) newSize / this.capacity) < SHRINK_THRESHOLD) {
      int newCapacity = Math.max(DEFAULT_CAPACITY, capacity / 2);
      Object[] shrunkSource = new Object[newCapacity];
      for (int i = 0; i < newSize; i++) {
        shrunkSource[i] = this.src[startIx + i];
      }

      this.capacity = newCapacity;
      this.startIx = 0;
      this.endIx = newSize - 1;
      this.src = shrunkSource;
    } else {
      this.endIx--;
    }
  }

  public void insertAt(int ix, T valueToInsert) {
    int size = size();
    if (ix >= size) {
      throw new IndexOutOfBoundsException("ix cannot be bigger than size!");
    }

    if (size == capacity) {
      int newCapacity = size * GROWTH_FACTOR;

      Object[] grownSrc = new Object[newCapacity];
      for (int i = 0; i < ix; i++) {
        grownSrc[i] = this.src[startIx + i];
      }
      grownSrc[ix] = valueToInsert;
      for (int i = ix; i < size; i++) {
        grownSrc[ix + 1] = this.src[startIx + i];
      }

      this.capacity = newCapacity;
      this.startIx = 0;
      this.endIx = size;
      this.src = grownSrc;
    } else {
      for (int i = endIx; i >= startIx + ix; i--) {
        this.src[startIx + i + 1] = this.src[startIx + i];
      }
      this.src[startIx + ix] = valueToInsert;

      this.endIx = size;
    }
  }

  public int size() {
    if (this.endIx < 0) {
      return 0;
    }
    return this.endIx - this.startIx + 1;
  }

}
