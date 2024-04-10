package org.kybinfrastructure.dsa.binary_heap;

import java.util.Arrays;
import org.kybinfrastructure.dsa.dynamic_array.DynamicArray;

/**
 * Non-thread-safe <a href="https://en.wikipedia.org/wiki/Binary_heap">binary heap</a>
 * implementation.
 *
 * @author o.kayabasi@outlook.com
 */
public final class BinaryHeap<T extends Comparable<T>> {

  private final DynamicArray<T> src;

  private BinaryHeap(DynamicArray<T> src) {
    this.src = src;
  }

  /**
   * Builds an empty binary heap.
   * 
   * @param <T> type of elements
   * @return built binary heap
   */
  public static <T extends Comparable<T>> BinaryHeap<T> empty() {
    return new BinaryHeap<>(DynamicArray.empty());
  }

  /**
   * Builds a binary heap by given {@code elements}.
   * 
   * @param <T> type of elements
   * @param elements elements to insert to binary heap
   * @return built binary heap
   */
  public static <T extends Comparable<T>> BinaryHeap<T> from(T[] elements) {
    T[] _elements = Arrays.copyOf(elements, elements.length); // NOSONAR

    for (int i = (_elements.length / 2) - 1; i >= 0; i--)
      heapifyDown(_elements, i);

    return new BinaryHeap<>(DynamicArray.from(_elements));
  }

  public void insert(T element) {
    throw new UnsupportedOperationException("Unimplemented method 'insert'");
  }

  public T findMax() {
    throw new UnsupportedOperationException("Unimplemented method 'findMax'");
  }

  public T deleteMax() {
    if (this.src.size() == 0) {
      return null;
    }

    T first = this.src.getAt(0);
    T last = this.src.getAt(this.src.size() - 1);
    T temp = last;
    this.src.setAt(this.src.size() - 1, first);
    this.src.setAt(0, temp);
    this.src.deleteLast();

    heapifyDown(0);

    return first;
  }

  public int size() {
    return this.src.size();
  }

  private void heapifyDown(int ix) {
    int leftChildIx = (2 * ix) + 1;
    int rightChildIx = leftChildIx + 1;
    T leftChild = leftChildIx < this.src.size() ? this.src.getAt(leftChildIx) : null;
    T rightChild = rightChildIx < this.src.size() ? this.src.getAt(rightChildIx) : null;

    int biggerChildIx;
    if (leftChild != null && rightChild != null) {
      biggerChildIx = leftChild.compareTo(rightChild) > 0 ? leftChildIx : rightChildIx;
    } else if (leftChild != null) {
      biggerChildIx = leftChildIx;
    } else if (rightChild != null) {
      biggerChildIx = rightChildIx;
    } else {
      return;
    }

    if (this.src.getAt(biggerChildIx).compareTo(this.src.getAt(ix)) > 0) {
      swap(ix, biggerChildIx);
      heapifyDown(biggerChildIx);
    }
  }

  private void swap(int i, int j) {
    T iVal = this.src.getAt(i);
    T jVal = this.src.getAt(j);
    T temp = jVal;
    this.src.setAt(j, iVal);
    this.src.setAt(i, temp);
  }

  private static <T extends Comparable<T>> void heapifyDown(T[] elements, int ix) {
    int leftChildIx = (2 * ix) + 1;
    int rightChildIx = leftChildIx + 1;
    T leftChild = leftChildIx < elements.length ? elements[leftChildIx] : null;
    T rightChild = rightChildIx < elements.length ? elements[rightChildIx] : null;

    int biggerChildIx;
    if (leftChild != null && rightChild != null) {
      biggerChildIx = leftChild.compareTo(rightChild) > 0 ? leftChildIx : rightChildIx;
    } else if (leftChild != null) {
      biggerChildIx = leftChildIx;
    } else if (rightChild != null) {
      biggerChildIx = rightChildIx;
    } else {
      return;
    }

    if (elements[biggerChildIx].compareTo(elements[ix]) > 0) {
      swap(elements, ix, biggerChildIx);
      heapifyDown(elements, biggerChildIx);
    }
  }

  private static <T extends Comparable<T>> void swap(T[] elements, int i, int j) {
    T temp = elements[j];
    elements[j] = elements[i];
    elements[i] = temp;
  }

}
