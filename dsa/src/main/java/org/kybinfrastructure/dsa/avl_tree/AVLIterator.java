package org.kybinfrastructure.dsa.avl_tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class AVLIterator<K extends Comparable<K>, T> implements Iterator<Node<K, T>> {

  private final ArrayList<Node<K, T>> nodesInTraversalOrder;

  private int curIx = 0;

  AVLIterator(ArrayList<Node<K, T>> nodesInTraversalOrder) {
    this.nodesInTraversalOrder = nodesInTraversalOrder;
  }

  @Override
  public boolean hasNext() {
    return curIx < nodesInTraversalOrder.size();
  }

  @Override
  public Node<K, T> next() {
    if (curIx >= nodesInTraversalOrder.size()) {
      throw new NoSuchElementException();
    }
    return nodesInTraversalOrder.get(curIx++);
  }

}
