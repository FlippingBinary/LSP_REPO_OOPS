package org.howard.edu.lsp.example.question3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorTest {

  private final GradeCalculator calc = new GradeCalculator();

  // 1. Test for average()
  @Test
  void average_returnsCorrectMean() {
    assertEquals(80.0, calc.average(70, 80, 90), 0.001);
  }

  // 2. Test for letterGrade()
  @Test
  void letterGrade_returnsCorrectGrade() {
    assertEquals("A", calc.letterGrade(95));
    assertEquals("B", calc.letterGrade(85));
    assertEquals("C", calc.letterGrade(75));
    assertEquals("D", calc.letterGrade(65));
    assertEquals("F", calc.letterGrade(50));
  }

  // 3. Test for isPassing()
  @Test
  void isPassing_returnsCorrectStatus() {
    assertTrue(calc.isPassing(75));
    assertFalse(calc.isPassing(50));
  }

  // 4a. Boundary-value test: score of exactly 0 (lower bound)
  @Test
  void average_boundaryLowerLimit_scoresOfZero() {
    assertEquals(0.0, calc.average(0, 0, 0), 0.001);
  }

  // 4b. Boundary-value test: score of exactly 100 (upper bound)
  @Test
  void average_boundaryUpperLimit_scoresOfHundred() {
    assertEquals(100.0, calc.average(100, 100, 100), 0.001);
  }

  // 5a. Exception test: negative score
  @Test
  void average_throwsForNegativeScore() {
    assertThrows(IllegalArgumentException.class, () -> calc.average(-1, 50, 50));
  }

  // 5b. Exception test: score above 100
  @Test
  void average_throwsForScoreAboveHundred() {
    assertThrows(IllegalArgumentException.class, () -> calc.average(50, 101, 50));
  }
}
