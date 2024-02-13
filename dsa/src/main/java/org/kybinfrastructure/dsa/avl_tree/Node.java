package org.kybinfrastructure.dsa.avl_tree;

import lombok.Getter;
import lombok.Setter;

/**
 * {@link AVLTree} node.
 */
@Getter(lombok.AccessLevel.PUBLIC)
@Setter(lombok.AccessLevel.PACKAGE)
public final class Node<K, T> {

  private K key;
  private T value;
  private Node<K, T> left;
  private Node<K, T> right;
  private Node<K, T> parent;

  Node(K key, T value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((key == null) ? 0 : key.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    result = prime * result + ((left == null) ? 0 : left.hashCode());
    result = prime * result + ((right == null) ? 0 : right.hashCode());
    result = prime * result + ((parent == null) ? 0 : parent.hashCode());
    return result;
  }

  @Override
  @SuppressWarnings({"rawtypes"})
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Node other = (Node) obj;
    if (key == null) {
      if (other.key != null)
        return false;
    } else if (!key.equals(other.key))
      return false;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    if (left == null) {
      if (other.left != null)
        return false;
    } else if (!left.equals(other.left))
      return false;
    if (right == null) {
      if (other.right != null)
        return false;
    } else if (!right.equals(other.right))
      return false;
    if (parent == null) {
      if (other.parent != null)
        return false;
    } else if (!parent.equals(other.parent))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Node[key=%s,value=%s,left=%s,right=%s,parent=%s]".formatted(key, value,
        left != null ? left.key : null, right != null ? right.key : null,
        parent != null ? parent.key : null);
  }

  int height() {
    int leftSubtreeHeight = left != null ? left.height() : 0;
    int rightSubtreeHeight = right != null ? right.height() : 0;
    return Math.max(leftSubtreeHeight, rightSubtreeHeight) + 1;
  }

  int skew() {
    int leftSubtreeHeight = left != null ? left.height() : 0;
    int rightSubtreeHeight = right != null ? right.height() : 0;
    return rightSubtreeHeight - leftSubtreeHeight;
  }

}
