package org.kybinfrastructure.dsa.disjoint_set;

/**
 * Represents the
 * <a href="https://en.wikipedia.org/wiki/Disjoint-set_data_structure"><i>Disjoint-set</i></a> data
 * structure.
 */
public sealed interface DisjointSet<T> permits DisjointSetLinkedListImpl {
  /**
   * Makes a new set whose only member is the given value if the given value is not part of any set
   * yet.
   * 
   * @param value value to make a new set for
   * @return representative of the set that given value is part of
   * @throws NullPointerException if given {@code value} is null
   */
  Member<T> makeSet(T value);

  /**
   * Takes the union of the sets that the given values are part of.
   * 
   * @param value first value
   * @param anotherValue second value
   * @return representative of the union set
   * @throws NullPointerException if one of given values is null
   * @throws IllegalArgumentException if one of given values is not part of any set
   */
  Member<T> union(T value, T anotherValue);

  /**
   * Returns the representative of the set that given value is part of.
   * 
   * @param value value of the member
   * @return representative of the set that contains given value
   * @throws NullPointerException if given {@code value} is null
   * @throws IllegalArgumentException if given value is not part of any set
   */
  Member<T> find(T value);

  /**
   * Builds a new {@code DisjointSet} which makes use of linked list data structure under the hood.
   * 
   * @param <T> type of the member values
   * @return a new {@code DisjointSet}
   */
  static <T> DisjointSet<T> backedByLinkedList() {
    return DisjointSetLinkedListImpl.of();
  }
}
