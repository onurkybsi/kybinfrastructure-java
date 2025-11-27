package org.kybinfrastructure.dsa.disjoint_set;

import java.util.HashMap;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class DisjointSetLinkedListImpl<T> implements DisjointSet<T> {
  private final HashMap<T, Node<T>> map;

  static <T> DisjointSetLinkedListImpl<T> of() {
    return new DisjointSetLinkedListImpl<>(new HashMap<>());
  }

  @Override
  public Member<T> makeSet(T value) {
    Objects.requireNonNull(value, "value may not be null!");

    if (map.containsKey(value)) {
      return map.get(value).head;
    }
    Node<T> member = new Node<>(value);
    member.head = member;
    member.tail = member;
    member.size = 1;
    map.put(value, member);
    return member;
  }

  @Override
  public Member<T> union(T value, T anotherValue) {
    Objects.requireNonNull(value, "value may not be null!");
    Objects.requireNonNull(anotherValue, "anotherValue may not be null!");

    var firstMember = this.map.get(value);
    var secondMember = this.map.get(anotherValue);
    if (firstMember == null) {
      throw new IllegalArgumentException("No member found by given 'value': %s" + value);
    }
    if (secondMember == null) {
      throw new IllegalArgumentException("No member found by given 'anotherValue': %s" + anotherValue);
    }

    if (firstMember.head == secondMember.head) {
      return firstMember.head;
    } else if (firstMember.head.size >= secondMember.head.size) {
      add(secondMember.head, firstMember.head);
      return firstMember.head;
    } else {
      add(firstMember.head, secondMember.head);
      return secondMember.head;
    }
  }

  @Override
  public Member<T> find(T value) {
    Objects.requireNonNull(value, "value may not be null!");
    var member = this.map.get(value);
    if (member == null) {
      throw new IllegalArgumentException("No member found by given 'value': %s" + value);
    }
    return member.head;
  }

  private static <T> void add(Node<T> from, Node<T> to) {
    to.setSize(to.getSize() + from.getSize());

    Node<T> previous = to.tail;
    Node<T> next = from.head;
    while (next != null) {
      previous.setNext(next);
      next.setHead(to);
      next.setTail(null);
      next.setSize(0);

      previous = next;
      next = next.next;
    }
    to.setTail(previous);
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Getter(AccessLevel.PACKAGE)
  static final class Node<T> implements Member<T> {
    private final T value;
    @Setter(AccessLevel.PRIVATE)
    private Node<T> next;
    @Setter(AccessLevel.PRIVATE)
    private Node<T> head;
    @Setter(AccessLevel.PRIVATE)
    private Node<T> tail; // Only with the head
    @Setter(AccessLevel.PRIVATE)
    private int size; // Only with the head

    @Override
    public T value() {
      return value;
    }
  }
}
