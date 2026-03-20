package org.howard.edu.lsp.midterm.strategy;

/**
 * Pricing strategy for holiday promotions. Applies a 15% discount.
 *
 * @author Jon Musselwhite
 */
public class HolidayPricing implements PricingStrategy {

  /**
   * Returns the price after a 15% holiday discount.
   *
   * @param price the original price
   * @return the discounted price
   */
  @Override
  public double calculatePrice(double price) {
    return price * 0.85;
  }
}
