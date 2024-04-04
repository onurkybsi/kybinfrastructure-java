package org.kybinfrastructure.dsa.binary_heap;

/**
 * Non-thread-safe <a href="https://en.wikipedia.org/wiki/Binary_heap">binary heap</a>
 * implementation.
 *
 * @author o.kayabasi@outlook.com
 */
public final class BinaryHeap<T extends Comparable<T>> {

  public static <T extends Comparable<T>> BinaryHeap<T> empty() {
    throw new UnsupportedOperationException("Unimplemented method 'empty'");
  }

  public static <T extends Comparable<T>> BinaryHeap<T> from(T[] elements) {
    throw new UnsupportedOperationException("Unimplemented method 'from'");
  }

  public void insert(T element) {
    throw new UnsupportedOperationException("Unimplemented method 'insert'");
  }

  public T findMax() {
    throw new UnsupportedOperationException("Unimplemented method 'findMax'");
  }

  public T deleteMax() {
    throw new UnsupportedOperationException("Unimplemented method 'deleteMax'");
  }

}
