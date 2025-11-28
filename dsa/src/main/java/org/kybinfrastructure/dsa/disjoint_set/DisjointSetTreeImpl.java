package org.kybinfrastructure.dsa.disjoint_set;

import java.util.HashMap;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class DisjointSetTreeImpl<T> implements DisjointSet<T> {
  private final HashMap<T, Node<T>> map;

  static <T> DisjointSetTreeImpl<T> of() {
    return new DisjointSetTreeImpl<>(new HashMap<>());
  }

  @Override
  public Member<T> makeSet(T value) {
    Objects.requireNonNull(value, "value may not be null!");
    if (map.containsKey(value)) {
      return find(value);
    } else {
      Node<T> member = new Node<>(value);
      member.setParent(member);
      member.setRank(0);
      map.put(value, member);
      return member;
    }
  }

  @Override
  public Member<T> union(T value, T anotherValue) {
    Objects.requireNonNull(value, "value may not be null!");
    Objects.requireNonNull(anotherValue, "anotherValue may not be null!");

    var firstMember = this.map.get(value);
    var secondMember = this.map.get(anotherValue);
    if (firstMember == null) {
      throw new IllegalArgumentException("No member found by given 'value': " + value);
    }
    if (secondMember == null) {
      throw new IllegalArgumentException("No member found by given 'anotherValue': " + anotherValue);
    }

    var parent = (Node<T>) find(value);
    var anotherParent = (Node<T>) find(anotherValue);
    if (parent.equals(anotherParent)) {
      return parent;
    } else if (parent.getRank() > anotherParent.getRank()) {
      anotherParent.setParent(parent);
      return parent;
    } else if (anotherParent.getRank() > parent.getRank()) {
      parent.setParent(anotherParent);
      return anotherParent;
    } else {
      anotherParent.setParent(parent);
      parent.setRank(parent.getRank() + 1);
      return parent;
    }
  }

  @Override
  public Member<T> find(T value) {
    Objects.requireNonNull(value, "value may not be null!");
    var member = this.map.get(value);
    if (member == null) {
      throw new IllegalArgumentException("No member found by given 'value': " + value);
    }
    if (member.parent != member) {
      member.setParent((Node<T>) find(member.getParent().getValue()));
    }
    return member.getParent();
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Getter(AccessLevel.PACKAGE)
  static final class Node<T> implements Member<T> {
    private final T value;
    @Setter(AccessLevel.PRIVATE)
    private Node<T> parent;
    @Setter(AccessLevel.PRIVATE)
    private int rank;

    @Override
    public T value() {
      return value;
    }
  }
}
