package org.howard.edu.lsp.midterm.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculatorTest {

  @Test
  void constructor_acceptsStrategy() {
    PriceCalculator calculator = new PriceCalculator(new RegularPricing());
    assertEquals(100.0, calculator.calculatePrice(100.0), 0.001);
  }

  @Test
  void calculatePrice_delegatesToCurrentStrategy() {
    PriceCalculator calculator = new PriceCalculator(new VipPricing());
    assertEquals(80.0, calculator.calculatePrice(100.0), 0.001);
  }

  @Test
  void setStrategy_changesCalculationBehavior() {
    PriceCalculator calculator = new PriceCalculator(new RegularPricing());
    assertEquals(100.0, calculator.calculatePrice(100.0), 0.001);

    calculator.setStrategy(new MemberPricing());
    assertEquals(90.0, calculator.calculatePrice(100.0), 0.001);

    calculator.setStrategy(new VipPricing());
    assertEquals(80.0, calculator.calculatePrice(100.0), 0.001);

    calculator.setStrategy(new HolidayPricing());
    assertEquals(85.0, calculator.calculatePrice(100.0), 0.001);
  }

  @Test
  void setStrategy_canSwitchBackToPreviousStrategy() {
    PriceCalculator calculator = new PriceCalculator(new RegularPricing());
    calculator.setStrategy(new VipPricing());
    assertEquals(80.0, calculator.calculatePrice(100.0), 0.001);

    calculator.setStrategy(new RegularPricing());
    assertEquals(100.0, calculator.calculatePrice(100.0), 0.001);
  }

  @Test
  void driverScenario_sequentialStrategySwapping() {
    double price = 100.0;

    PriceCalculator calculator = new PriceCalculator(new RegularPricing());
    assertEquals(100.0, calculator.calculatePrice(price), 0.001);

    calculator.setStrategy(new MemberPricing());
    assertEquals(90.0, calculator.calculatePrice(price), 0.001);

    calculator.setStrategy(new VipPricing());
    assertEquals(80.0, calculator.calculatePrice(price), 0.001);

    calculator.setStrategy(new HolidayPricing());
    assertEquals(85.0, calculator.calculatePrice(price), 0.001);
  }
}
