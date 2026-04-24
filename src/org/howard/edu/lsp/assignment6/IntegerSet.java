package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a mathematical set of integers backed by an {@link ArrayList}.
 *
 * A set cannot contain duplicate elements. Standard set operations such as
 * union, intersection, difference, and complement are supported. All set
 * operations return a new {@code IntegerSet} and do not modify the
 * original operands.
 *
 * @author Jon Musselwhite
 */
public class IntegerSet {

  private final List<Integer> set;

  /**
   * Constructs an empty {@code IntegerSet}.
   */
  public IntegerSet() {
    set = new ArrayList<>();
  }

  /**
   * Removes all elements from this set, leaving it empty.
   */
  public void clear() {
    set.clear();
  }

  /**
   * Returns the number of elements in this set.
   *
   * @return the number of elements
   */
  public int length() {
    return set.size();
  }

  /**
   * Returns {@code true} if this set contains exactly the same elements as
   * the specified set, regardless of order.
   *
   * @param b the set to compare with
   * @return {@code true} if both sets contain the same elements
   */
  public boolean equals(IntegerSet b) {
    if (b == null) {
      return false;
    }
    if (this.length() != b.length()) {
      return false;
    }
    List<Integer> sortedThis = new ArrayList<>(this.set);
    List<Integer> sortedB = new ArrayList<>(b.set);
    Collections.sort(sortedThis);
    Collections.sort(sortedB);
    return sortedThis.equals(sortedB);
  }

  /**
   * Returns {@code true} if this set contains the specified value.
   *
   * @param value the value to search for
   * @return {@code true} if {@code value} is an element of this set
   */
  public boolean contains(int value) {
    return set.contains(value);
  }

  /**
   * Returns the largest element in this set.
   *
   * @return the largest element
   * @throws RuntimeException if the set is empty
   */
  public int largest() {
    if (set.isEmpty()) {
      throw new RuntimeException("The set is empty");
    }
    return Collections.max(set);
  }

  /**
   * Returns the smallest element in this set.
   *
   * @return the smallest element
   * @throws RuntimeException if the set is empty
   */
  public int smallest() {
    if (set.isEmpty()) {
      throw new RuntimeException("The set is empty");
    }
    return Collections.min(set);
  }

  /**
   * Adds an item to this set. If the item already exists, no action is taken.
   *
   * @param item the integer to add
   */
  public void add(int item) {
    if (!set.contains(item)) {
      set.add(item);
    }
  }

  /**
   * Removes an item from this set. If the item is not present, no action is
   * taken.
   *
   * @param item the integer to remove
   */
  public void remove(int item) {
    set.remove(Integer.valueOf(item));
  }

  /**
   * Returns a new set that is the union of this set and the specified set.
   * The union contains all elements that appear in either set.
   *
   * <p>Neither this set nor {@code intSetb} is modified.
   *
   * @param intSetb the other set
   * @return a new {@code IntegerSet} representing the union
   */
  public IntegerSet union(IntegerSet intSetb) {
    IntegerSet result = new IntegerSet();
    result.set.addAll(this.set);
    for (int item : intSetb.set) {
      if (!result.set.contains(item)) {
        result.set.add(item);
      }
    }
    return result;
  }

  /**
   * Returns a new set that is the intersection of this set and the specified
   * set. The intersection contains only elements common to both sets.
   *
   * <p>Neither this set nor {@code intSetb} is modified.
   *
   * @param intSetb the other set
   * @return a new {@code IntegerSet} representing the intersection
   */
  public IntegerSet intersect(IntegerSet intSetb) {
    IntegerSet result = new IntegerSet();
    result.set.addAll(this.set);
    result.set.retainAll(intSetb.set);
    return result;
  }

  /**
   * Returns a new set that is the difference of this set minus the specified
   * set. The difference contains elements in this set but not in {@code intSetb}.
   *
   * <p>Neither this set nor {@code intSetb} is modified.
   *
   * @param intSetb the other set
   * @return a new {@code IntegerSet} representing the difference
   */
  public IntegerSet diff(IntegerSet intSetb) {
    IntegerSet result = new IntegerSet();
    result.set.addAll(this.set);
    result.set.removeAll(intSetb.set);
    return result;
  }

  /**
   * Returns a new set that is the complement of this set with respect to the
   * specified set. The complement contains elements in {@code intSetb} but
   * not in this set.
   *
   * <p>Neither this set nor {@code intSetb} is modified.
   *
   * @param intSetb the other set
   * @return a new {@code IntegerSet} representing the complement
   */
  public IntegerSet complement(IntegerSet intSetb) {
    IntegerSet result = new IntegerSet();
    result.set.addAll(intSetb.set);
    result.set.removeAll(this.set);
    return result;
  }

  /**
   * Returns {@code true} if this set contains no elements.
   *
   * @return {@code true} if the set is empty
   */
  public boolean isEmpty() {
    return set.isEmpty();
  }

  /**
   * Returns a string representation of this set in the format
   * {@code [1, 2, 3]}. Elements appear in ascending order, separated by a
   * comma and a single space. An empty set is represented as {@code []}.
   *
   * @return the string representation of this set
   */
  @Override
  public String toString() {
    List<Integer> sorted = new ArrayList<>(set);
    Collections.sort(sorted);
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < sorted.size(); i++) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append(sorted.get(i));
    }
    sb.append("]");
    return sb.toString();
  }
}
