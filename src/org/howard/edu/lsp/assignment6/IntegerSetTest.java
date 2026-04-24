package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IntegerSetTest {

  private IntegerSet setA;
  private IntegerSet setB;

  @BeforeEach
  void setUp() {
    setA = new IntegerSet();
    setB = new IntegerSet();
  }

  @Test
  @DisplayName("add: inserts distinct values")
  void addDistinctValues() {
    setA.add(1);
    setA.add(2);
    assertEquals(2, setA.length());
    assertTrue(setA.contains(1));
    assertTrue(setA.contains(2));
  }

  @Test
  @DisplayName("add: duplicate value does not increase size")
  void addDuplicate() {
    setA.add(5);
    setA.add(5);
    assertEquals(1, setA.length());
  }

  @Test
  @DisplayName("remove: removes existing element")
  void removeExisting() {
    setA.add(10);
    setA.add(20);
    setA.remove(10);
    assertFalse(setA.contains(10));
    assertEquals(1, setA.length());
  }

  @Test
  @DisplayName("remove: value not present does nothing")
  void removeNotPresent() {
    setA.add(1);
    setA.remove(99);
    assertEquals(1, setA.length());
    assertTrue(setA.contains(1));
  }

  @Test
  @DisplayName("clear: empties the set")
  void clearSet() {
    setA.add(1);
    setA.add(2);
    setA.clear();
    assertEquals(0, setA.length());
    assertTrue(setA.isEmpty());
  }

  @Test
  @DisplayName("length: returns correct size")
  void length() {
    assertEquals(0, setA.length());
    setA.add(3);
    setA.add(7);
    assertEquals(2, setA.length());
  }

  @Test
  @DisplayName("equals: identical content returns true")
  void equalsIdentical() {
    setA.add(1);
    setA.add(2);
    setB.add(1);
    setB.add(2);
    assertTrue(setA.equals(setB));
  }

  @Test
  @DisplayName("equals: same elements different order returns true")
  void equalsDifferentOrder() {
    setA.add(3);
    setA.add(1);
    setA.add(2);
    setB.add(2);
    setB.add(3);
    setB.add(1);
    assertTrue(setA.equals(setB));
  }

  @Test
  @DisplayName("equals: different sets returns false")
  void equalsDifferent() {
    setA.add(1);
    setB.add(2);
    assertFalse(setA.equals(setB));
  }

  @Test
  @DisplayName("contains: returns true for present value")
  void containsPresent() {
    setA.add(42);
    assertTrue(setA.contains(42));
  }

  @Test
  @DisplayName("contains: returns false for absent value")
  void containsAbsent() {
    setA.add(1);
    assertFalse(setA.contains(99));
  }

  @Test
  @DisplayName("largest: returns max element")
  void largestNormal() {
    setA.add(5);
    setA.add(20);
    setA.add(3);
    assertEquals(20, setA.largest());
  }

  @Test
  @DisplayName("largest: single element returns that element")
  void largestSingleElement() {
    setA.add(7);
    assertEquals(7, setA.largest());
  }

  @Test
  @DisplayName("largest: empty set throws RuntimeException")
  void largestEmptyThrows() {
    assertThrows(RuntimeException.class, () -> setA.largest());
  }

  @Test
  @DisplayName("smallest: returns min element")
  void smallestNormal() {
    setA.add(5);
    setA.add(20);
    setA.add(3);
    assertEquals(3, setA.smallest());
  }

  @Test
  @DisplayName("smallest: single element returns that element")
  void smallestSingleElement() {
    setA.add(7);
    assertEquals(7, setA.smallest());
  }

  @Test
  @DisplayName("smallest: empty set throws RuntimeException")
  void smallestEmptyThrows() {
    assertThrows(RuntimeException.class, () -> setA.smallest());
  }

  @Test
  @DisplayName("union: combines two non-empty sets without duplicates")
  void unionNormal() {
    setA.add(1);
    setA.add(2);
    setB.add(2);
    setB.add(3);
    IntegerSet result = setA.union(setB);
    assertEquals(3, result.length());
    assertTrue(result.contains(1));
    assertTrue(result.contains(2));
    assertTrue(result.contains(3));
  }

  @Test
  @DisplayName("union: with empty set returns copy of non-empty set")
  void unionWithEmpty() {
    setA.add(1);
    setA.add(2);
    IntegerSet result = setA.union(setB);
    assertEquals(2, result.length());
    assertTrue(result.contains(1));
    assertTrue(result.contains(2));
  }

  @Test
  @DisplayName("intersect: returns common elements")
  void intersectNormal() {
    setA.add(1);
    setA.add(2);
    setA.add(3);
    setB.add(2);
    setB.add(3);
    setB.add(4);
    IntegerSet result = setA.intersect(setB);
    assertEquals(2, result.length());
    assertTrue(result.contains(2));
    assertTrue(result.contains(3));
  }

  @Test
  @DisplayName("intersect: no common elements returns empty set")
  void intersectNoCommon() {
    setA.add(1);
    setA.add(2);
    setB.add(3);
    setB.add(4);
    IntegerSet result = setA.intersect(setB);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("diff: returns elements in this but not other")
  void diffNormal() {
    setA.add(1);
    setA.add(2);
    setA.add(3);
    setB.add(2);
    setB.add(4);
    IntegerSet result = setA.diff(setB);
    assertEquals(2, result.length());
    assertTrue(result.contains(1));
    assertTrue(result.contains(3));
  }

  @Test
  @DisplayName("diff: identical sets returns empty set")
  void diffIdentical() {
    setA.add(1);
    setA.add(2);
    setB.add(1);
    setB.add(2);
    IntegerSet result = setA.diff(setB);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("complement: returns elements in other but not this")
  void complementNormal() {
    setA.add(1);
    setA.add(2);
    setB.add(2);
    setB.add(3);
    setB.add(4);
    IntegerSet result = setA.complement(setB);
    assertEquals(2, result.length());
    assertTrue(result.contains(3));
    assertTrue(result.contains(4));
  }

  @Test
  @DisplayName("complement: disjoint sets returns all of other set")
  void complementDisjoint() {
    setA.add(1);
    setA.add(2);
    setB.add(3);
    setB.add(4);
    IntegerSet result = setA.complement(setB);
    assertEquals(2, result.length());
    assertTrue(result.contains(3));
    assertTrue(result.contains(4));
  }

  @Test
  @DisplayName("isEmpty: new set is empty")
  void isEmptyTrue() {
    assertTrue(setA.isEmpty());
  }

  @Test
  @DisplayName("isEmpty: non-empty set returns false")
  void isEmptyFalse() {
    setA.add(1);
    assertFalse(setA.isEmpty());
  }

  @Test
  @DisplayName("toString: formats elements in sorted order")
  void toStringNormal() {
    setA.add(3);
    setA.add(1);
    setA.add(2);
    assertEquals("[1, 2, 3]", setA.toString());
  }

  @Test
  @DisplayName("toString: empty set returns []")
  void toStringEmpty() {
    assertEquals("[]", setA.toString());
  }
}
