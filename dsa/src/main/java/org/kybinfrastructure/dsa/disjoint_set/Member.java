package org.kybinfrastructure.dsa.disjoint_set;

/**
 * Represents a member in a disjoint set.
 */
interface Member<T> {
  /**
   * Returns the value that {@code this} member contains.
   * 
   * @return value of the member
   */
  T value();
}
