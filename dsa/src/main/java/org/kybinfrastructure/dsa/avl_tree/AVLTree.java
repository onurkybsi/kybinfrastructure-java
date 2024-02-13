package org.kybinfrastructure.dsa.avl_tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Non-thread-safe <a href="https://en.wikipedia.org/wiki/AVL_tree">AVL tree</a> implementation.
 *
 * @author o.kayabasi@outlook.com
 */
@SuppressWarnings({"java:S1192"})
public final class AVLTree<K extends Comparable<K>, T> implements Iterable<Node<K, T>> {

  private Node<K, T> root = null;
  private int length = 0;

  private AVLTree(List<Node<K, T>> nodes) {
    if (nodes == null) {
      this.root = null;
    } else {
      this.root = nodes.get(0);

      for (int i = 1; i < nodes.size(); i++) {
        Node<K, T> inserted = insert(nodes.get(i), this.root);
        if (inserted == null) {
          throw new IllegalArgumentException(
              "nodes cannot have duplicate keys: " + nodes.get(i).getKey());
        }

        rebalance(inserted);
      }

      this.length = nodes.size();
    }
  }

  /**
   * Builds an empty AVL tree.
   * 
   * @param <K> type of keys
   * @param <T> type of values
   * @return built AVL tree
   * @implNote time complexity: <i>O(1)</i>, space complexity: <i>O(1)</i>
   */
  public static <K extends Comparable<K>, T> AVLTree<K, T> empty() {
    return new AVLTree<>(null);
  }

  /**
   * Builds an AVL tree with the given key & value pairs.
   * 
   * @param <K> type of keys
   * @param <T> type of values
   * @param keyValues key & value pairs to insert to the tree
   * @return built AVL tree
   * @implNote time complexity: <i>O(nlog(n))</i>, space complexity: <i>O(n)</i>
   */
  public static <K extends Comparable<K>, T> AVLTree<K, T> from(Map<K, T> keyValues) {
    if (keyValues == null || keyValues.isEmpty()) {
      return new AVLTree<>(null);
    } else {
      List<Node<K, T>> nodesToInsert = new ArrayList<>(keyValues.size());
      for (Entry<K, T> node : keyValues.entrySet()) {
        Objects.requireNonNull(node.getKey(), "A key cannot be null!");
        nodesToInsert.add(new Node<>(node.getKey(), node.getValue()));
      }

      return new AVLTree<>(nodesToInsert);
    }
  }

  /**
   * Returns the number of values in the tree.
   *
   * @return number of values in the tree
   */
  public int length() {
    return this.length;
  }

  /**
   * Returns the value with given key from the tree.
   *
   * @param key key of the value to return
   * @return value with the given key if tree has a node with the given key, otherwise {@code null}
   * @implNote time complexity: <i>O(log n)</i>, space complexity: <i>O(1)</i>
   * @throws NullPointerException when given key is {@code null}
   */
  public T find(K key) {
    Objects.requireNonNull(key, "key cannot be null!");

    Node<K, T> node = findNode(key, root);
    if (node == null) {
      return null;
    }
    return node.getValue();
  }

  /**
   * Inserts a new value into the tree with the given key.
   *
   * @param key key of the value
   * @param value value to insert
   * @return inserted key if the tree doesn't have the given key, otherwise {@code null}
   * @implNote time complexity: <i>O(log n)</i>, space complexity: <i>O(1)</i>
   */
  public K insert(K key, T value) {
    Objects.requireNonNull(key, "key cannot be null!");

    if (this.root == null) {
      this.root = new Node<>(key, value);
      return this.root.getKey();
    } else {
      Node<K, T> inserted = insert(new Node<>(key, value), this.root);
      if (inserted == null) {
        return null;
      }

      rebalance(inserted);
      this.length++;

      return inserted.getKey();
    }
  }

  /**
   * Deletes the node with given key from the tree.
   *
   * @param key key of the node to delete
   * @return key of the deleted node, {@code null} if no such a node exists
   * @throws NullPointerException when given key is {@code null}
   */
  public K delete(K key) {
    Objects.requireNonNull(key, "key cannot be null!");

    Node<K, T> nodeToDelete = findNode(key, this.root);
    if (nodeToDelete == null) {
      return null;
    }

    Node<K, T> parentOfDeleted = delete(nodeToDelete);
    if (parentOfDeleted != null) {
      rebalance(parentOfDeleted);
    }
    this.length--;

    return nodeToDelete.getKey();
  }

  @Override
  public Iterator<Node<K, T>> iterator() {
    return new AVLIterator<>(nodesInTraversalOrder(root));
  }

  @Override
  public String toString() {
    return Arrays.toString(nodesInTraversalOrder(root).toArray());
  }

  // Time complexity: O(logn)
  private void rebalance(Node<K, T> from) {
    Node<K, T> cur = from;
    while (cur.getParent() != null) {
      cur = rebalanceLocally(cur);
      cur = cur.getParent();
    }
    this.root = rebalanceLocally(cur);
  }

  // Time complexity: O(logn)
  private static <K extends Comparable<K>, T> Node<K, T> insert(Node<K, T> nodeToInsert,
      Node<K, T> startingFrom) {
    if (startingFrom.getKey().compareTo(nodeToInsert.getKey()) > 0) {
      if (startingFrom.getLeft() == null) {
        startingFrom.setLeft(nodeToInsert);
        nodeToInsert.setParent(startingFrom);
        return nodeToInsert;
      } else {
        return insert(nodeToInsert, startingFrom.getLeft());
      }
    } else if (startingFrom.getKey().compareTo(nodeToInsert.getKey()) < 0) {
      if (startingFrom.getRight() == null) {
        startingFrom.setRight(nodeToInsert);
        nodeToInsert.setParent(startingFrom);
        return nodeToInsert;
      } else {
        return insert(nodeToInsert, startingFrom.getRight());
      }
    } else {
      return null;
    }
  }

  // Time complexity: O(logn)
  private static <K extends Comparable<K>, T> Node<K, T> delete(Node<K, T> nodeToDelete) {
    if (nodeToDelete.getLeft() != null) {
      Node<K, T> predecessor = nodeToDelete.getLeft();
      while (nodeToDelete.getRight() != null)
        predecessor = nodeToDelete.getRight();

      T predecessorValue = predecessor.getValue();
      predecessor.setValue(nodeToDelete.getValue());
      nodeToDelete.setValue(predecessorValue);

      return delete(nodeToDelete);
    } else if (nodeToDelete.getRight() != null) {
      Node<K, T> successor = nodeToDelete.getRight();
      while (nodeToDelete.getLeft() != null)
        successor = nodeToDelete.getLeft();

      T successorValue = successor.getValue();
      successor.setValue(nodeToDelete.getValue());
      nodeToDelete.setValue(successorValue);

      return delete(nodeToDelete);
    } else {
      Node<K, T> parent = nodeToDelete.getParent();
      nodeToDelete.setParent(null);

      if (parent != null && parent.getLeft() == nodeToDelete) {
        parent.setLeft(null);
      }
      if (parent != null && parent.getRight() == nodeToDelete) {
        parent.setRight(null);
      }
      return parent;
    }
  }

  // Time complexity: O(logn)
  private static <K extends Comparable<K>, T> Node<K, T> findNode(K key, Node<K, T> startingFrom) {
    Node<K, T> cur = startingFrom;
    while (cur != null) {
      if (key.compareTo(cur.getKey()) < 0) {
        cur = cur.getLeft();
      } else if (key.compareTo(cur.getKey()) > 0) {
        cur = cur.getRight();
      } else {
        return cur;
      }
    }
    return null;
  }

  // Time complexity: O(1)
  private static <K extends Comparable<K>, T> Node<K, T> rebalanceLocally(Node<K, T> cur) {
    int skew = cur.skew();

    if (skew > 1) {
      if (cur.getRight().skew() == -1) {
        cur.setRight(rotateRight(cur.getRight()));
        return rotateLeft(cur);
      } else {
        return rotateLeft(cur);
      }
    }

    if (skew < -1) {
      if (cur.getLeft().skew() == 1) {
        cur.setLeft(rotateLeft(cur.getLeft()));
        return rotateRight(cur);
      } else {
        return rotateRight(cur);
      }
    }

    return cur;
  }

  private static <K extends Comparable<K>, T> Node<K, T> rotateLeft(Node<K, T> cur) {
    Node<K, T> rightChild = cur.getRight();
    Node<K, T> rightChildLeftChild = rightChild.getLeft();
    Node<K, T> parent = cur.getParent();

    cur.setRight(rightChildLeftChild);
    if (rightChildLeftChild != null) {
      rightChildLeftChild.setParent(cur);
    }
    cur.setParent(rightChild);

    rightChild.setLeft(cur);
    rightChild.setParent(parent);
    if (parent != null && parent.getLeft() == cur) {
      parent.setLeft(rightChild);
    }
    if (parent != null && parent.getRight() == cur) {
      parent.setRight(rightChild);
    }

    return rightChild;
  }

  private static <K extends Comparable<K>, T> Node<K, T> rotateRight(Node<K, T> cur) {
    Node<K, T> leftChild = cur.getLeft();
    Node<K, T> leftChildRightChild = leftChild.getRight();
    Node<K, T> parent = cur.getParent();

    cur.setLeft(leftChildRightChild);
    if (leftChildRightChild != null) {
      leftChildRightChild.setParent(cur);
    }
    cur.setParent(leftChild);

    leftChild.setRight(cur);
    leftChild.setParent(parent);
    if (parent != null && parent.getLeft() == cur) {
      parent.setLeft(leftChild);
    }
    if (parent != null && parent.getRight() == cur) {
      parent.setRight(leftChild);
    }

    return leftChild;
  }

  // Time complexity: O(n)
  private static <K extends Comparable<K>, T> ArrayList<Node<K, T>> nodesInTraversalOrder(
      Node<K, T> startingFrom) {
    ArrayList<Node<K, T>> nodesInTraversalOrder = new ArrayList<>();
    addInTraversalOrder(startingFrom, nodesInTraversalOrder);
    return nodesInTraversalOrder;
  }

  private static <K extends Comparable<K>, T> void addInTraversalOrder(Node<K, T> cur,
      Collection<Node<K, T>> nodesInTraversalOrder) {
    if (cur == null) {
      return;
    }

    addInTraversalOrder(cur.getLeft(), nodesInTraversalOrder);
    nodesInTraversalOrder.add(cur);
    addInTraversalOrder(cur.getRight(), nodesInTraversalOrder);
  }

}
