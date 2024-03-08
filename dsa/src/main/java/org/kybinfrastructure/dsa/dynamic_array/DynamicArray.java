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

  /**
   * Builds an empty dynamic array.
   *
   * @param <T> type of elements
   * @return built dynamic array
   */
  public static <T> DynamicArray<T> empty() {
    return new DynamicArray<>();
  }

  /**
   * Builds a dynamic array from given elements.
   * 
   * @param <T> type of elements
   * @param elements elements to be inserted into the dynamic array
   * @return built dynamic array
   */
  public static <T> DynamicArray<T> from(T[] elements) {
    return new DynamicArray<>(elements);
  }

  /**
   * Returns the element from dynamic array with given index.
   * 
   * @param ix element index
   * @return element with given index
   * @throws IndexOutOfBoundsException
   *         <ul>
   *         <li>when given {@code ix} is lower than zero</li>
   *         <li>when given {@code ix} is equal or greater than dynamic array size</li>
   *         </ul>
   */
  @SuppressWarnings({"unchecked"})
  public T getAt(int ix) {
    if (ix < 0 || ix >= size()) {
      throw new IndexOutOfBoundsException(ix);
    }
    return (T) this.src[this.startIx + ix];
  }

  /**
   * Sets the element with given index as given value.
   * 
   * @param ix element index
   * @param valueToSet value to set for the element at the index {@code ix}
   * @throws IndexOutOfBoundsException
   *         <ul>
   *         <li>when given {@code ix} is lower than zero</li>
   *         <li>when given {@code ix} is equal or greater than dynamic array size</li>
   *         </ul>
   */
  public void setAt(int ix, T valueToSet) {
    if (ix < 0 || ix >= size()) {
      throw new IndexOutOfBoundsException(ix);
    }
    this.src[this.startIx + ix] = valueToSet;
  }

  /**
   * Inserts given value into the start.
   * 
   * @param valueToInsert value to insert
   */
  public void insertFirst(T valueToInsert) {
    int size = size();

    if (size == this.capacity) {
      int newCapacity = size * GROWTH_FACTOR;

      Object[] grownSrc = new Object[newCapacity];
      grownSrc[0] = valueToInsert;
      for (int i = 0; i < size; i++) {
        grownSrc[i + 1] = this.src[this.startIx + i];
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

      this.endIx++;
    }
  }

  /**
   * Deletes the first element. If there is no element in the dynamic array, it does nothing.
   */
  public void deleteFirst() {
    int newSize = size() - 1;
    if (newSize < 0) {
      return;
    }

    if (this.capacity > DEFAULT_CAPACITY && ((float) newSize / this.capacity) < SHRINK_THRESHOLD) {
      int newCapacity = Math.max(DEFAULT_CAPACITY, capacity / 2);

      Object[] shrunkSource = new Object[newCapacity];
      for (int i = 0; i < newSize; i++) {
        shrunkSource[i] = this.src[this.startIx + i + 1];
      }

      this.capacity = newCapacity;
      this.startIx = 0;
      this.endIx = newSize - 1;
      this.src = shrunkSource;
    } else {
      this.startIx++;
    }
  }

  /**
   * Inserts given value into the end.
   * 
   * @param valueToInsert value to insert
   */
  public void insertLast(T valueToInsert) {
    int size = size();

    if (size == this.capacity) {
      int newCapacity = size * GROWTH_FACTOR;

      Object[] grownSrc = new Object[newCapacity];
      for (int i = 0; i < size; i++) {
        grownSrc[i] = this.src[this.startIx + i];
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

  /**
   * Deletes the last element. If there is no element in the dynamic array, it does nothing.
   */
  public void deleteLast() {
    int newSize = size() - 1;
    if (newSize < 0) {
      return;
    }

    if (this.capacity > DEFAULT_CAPACITY && ((float) newSize / this.capacity) < SHRINK_THRESHOLD) {
      int newCapacity = Math.max(DEFAULT_CAPACITY, capacity / 2);

      Object[] shrunkSource = new Object[newCapacity];
      for (int i = 0; i < newSize; i++) {
        shrunkSource[i] = this.src[this.startIx + i];
      }

      this.capacity = newCapacity;
      this.startIx = 0;
      this.endIx = newSize - 1;
      this.src = shrunkSource;
    } else {
      this.endIx--;
    }
  }

  /**
   * Inserts given value into the position with given index.
   * 
   * @param ix index position to insert
   * @param valueToInsert value to insert
   * @throws IndexOutOfBoundsException
   *         <ul>
   *         <li>when given {@code ix} is lower than zero</li>
   *         <li>when given {@code ix} is equal or greater than dynamic array size</li>
   *         </ul>
   */
  public void insertAt(int ix, T valueToInsert) {
    int size = size();
    if (ix < 0 || ix >= size) {
      throw new IndexOutOfBoundsException(ix);
    }

    if (size == this.capacity) {
      int newCapacity = size * GROWTH_FACTOR;

      Object[] grownSrc = new Object[newCapacity];
      for (int i = 0; i < ix; i++) {
        grownSrc[i] = this.src[this.startIx + i];
      }
      grownSrc[ix] = valueToInsert;
      for (int i = ix; i < size; i++) {
        grownSrc[i + 1] = this.src[this.startIx + i];
      }

      this.capacity = newCapacity;
      this.startIx = 0;
      this.endIx = size;
      this.src = grownSrc;
    } else {
      for (int i = this.endIx; i >= this.startIx + ix; i--) {
        this.src[i + 1] = this.src[i];
      }
      this.src[this.startIx + ix] = valueToInsert;

      this.endIx++;
    }
  }

  /**
   * Delete element at the position with given index.
   * 
   * @param ix index position to delete
   * @throws IndexOutOfBoundsException
   *         <ul>
   *         <li>when given {@code ix} is lower than zero</li>
   *         <li>when given {@code ix} is equal or greater than dynamic array size</li>
   *         </ul>
   */
  public void deleteAt(int ix) {
    int size = size();
    if (ix < 0 || ix >= size) {
      throw new IndexOutOfBoundsException(ix);
    }
    int newSize = size - 1;

    if (this.capacity > DEFAULT_CAPACITY && ((float) newSize / this.capacity) < SHRINK_THRESHOLD) {
      int newCapacity = Math.max(DEFAULT_CAPACITY, capacity / 2);

      Object[] shrunkSource = new Object[newCapacity];
      for (int i = 0; i < ix; i++) {
        shrunkSource[i] = this.src[this.startIx + i];
      }
      for (int i = ix; i < size - 1; i++) {
        shrunkSource[i] = this.src[this.startIx + i + 1];
      }

      this.capacity = newCapacity;
      this.startIx = 0;
      this.endIx = newSize - 1;
      this.src = shrunkSource;
    } else {
      for (int i = this.startIx + ix + 1; i <= this.endIx; i++) {
        this.src[i - 1] = this.src[i];
      }

      this.endIx--;
    }

  }

  /**
   * Returns the number of elements in the dynamic array.
   * 
   * @return number of elements in the dynamic array
   */
  public int size() {
    if (this.endIx < 0) {
      return 0;
    }
    return this.endIx - this.startIx + 1;
  }

}
