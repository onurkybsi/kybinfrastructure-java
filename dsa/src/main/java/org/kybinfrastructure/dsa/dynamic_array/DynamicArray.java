package org.kybinfrastructure.dsa.dynamic_array;

import java.util.Arrays;

/**
 * Non-thread-safe <a href="https://en.wikipedia.org/wiki/Dynamic_array">dynamic array</a>
 * implementation.
 *
 * @author o.kayabasi@outlook.com
 */
public final class DynamicArray<T> {

  private static final float GROWTH_RATE = 1.5F; // TODO: What is the optimal value?

  private int capacity = 16; // TODO: What is the optimal value?
  private int size = 0;
  private Object[] src;

  private DynamicArray() {
    this.src = new Object[this.capacity];
  }

  private DynamicArray(T[] elements) {
    this.capacity = Math.round(elements.length * GROWTH_RATE);
    this.size = elements.length;
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
    if (ix >= this.size) {
      throw new IndexOutOfBoundsException("No element exists at index " + ix);
    }
    return (T) this.src[ix];
  }

  public void setAt(int ix, T valueToSet) {
    if (ix >= this.size) {
      throw new IndexOutOfBoundsException("No element exists at index " + ix);
    }
    this.src[ix] = valueToSet;
  }

  public int size() {
    return this.size;
  }

}
