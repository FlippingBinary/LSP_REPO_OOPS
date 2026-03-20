package org.howard.edu.lsp.midterm.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PricingStrategyTest {

  @Test
  void regularPricing_returnsOriginalPrice() {
    PricingStrategy strategy = new RegularPricing();
    assertEquals(100.0, strategy.calculatePrice(100.0), 0.001);
  }

  @Test
  void regularPricing_noDiscountApplied() {
    PricingStrategy strategy = new RegularPricing();
    assertEquals(250.0, strategy.calculatePrice(250.0), 0.001);
    assertEquals(0.0, strategy.calculatePrice(0.0), 0.001);
  }

  @Test
  void memberPricing_applies10PercentDiscount() {
    PricingStrategy strategy = new MemberPricing();
    assertEquals(90.0, strategy.calculatePrice(100.0), 0.001);
  }

  @Test
  void memberPricing_discountOnVariousPrices() {
    PricingStrategy strategy = new MemberPricing();
    assertEquals(45.0, strategy.calculatePrice(50.0), 0.001);
    assertEquals(0.0, strategy.calculatePrice(0.0), 0.001);
    assertEquals(900.0, strategy.calculatePrice(1000.0), 0.001);
  }

  @Test
  void vipPricing_applies20PercentDiscount() {
    PricingStrategy strategy = new VipPricing();
    assertEquals(80.0, strategy.calculatePrice(100.0), 0.001);
  }

  @Test
  void vipPricing_discountOnVariousPrices() {
    PricingStrategy strategy = new VipPricing();
    assertEquals(40.0, strategy.calculatePrice(50.0), 0.001);
    assertEquals(0.0, strategy.calculatePrice(0.0), 0.001);
    assertEquals(800.0, strategy.calculatePrice(1000.0), 0.001);
  }

  @Test
  void holidayPricing_applies15PercentDiscount() {
    PricingStrategy strategy = new HolidayPricing();
    assertEquals(85.0, strategy.calculatePrice(100.0), 0.001);
  }

  @Test
  void holidayPricing_discountOnVariousPrices() {
    PricingStrategy strategy = new HolidayPricing();
    assertEquals(42.5, strategy.calculatePrice(50.0), 0.001);
    assertEquals(0.0, strategy.calculatePrice(0.0), 0.001);
    assertEquals(850.0, strategy.calculatePrice(1000.0), 0.001);
  }

  @Test
  void allStrategies_implementPricingStrategyInterface() {
    assertInstanceOf(PricingStrategy.class, new RegularPricing());
    assertInstanceOf(PricingStrategy.class, new MemberPricing());
    assertInstanceOf(PricingStrategy.class, new VipPricing());
    assertInstanceOf(PricingStrategy.class, new HolidayPricing());
  }

  @Test
  void driverScenario_allFourDiscountsMatchExpectedOutput() {
    double price = 100.0;

    assertEquals(100.0, new RegularPricing().calculatePrice(price), 0.001);
    assertEquals(90.0, new MemberPricing().calculatePrice(price), 0.001);
    assertEquals(80.0, new VipPricing().calculatePrice(price), 0.001);
    assertEquals(85.0, new HolidayPricing().calculatePrice(price), 0.001);
  }
}
