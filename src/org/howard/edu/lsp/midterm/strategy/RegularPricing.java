package org.howard.edu.lsp.midterm.strategy;

/**
 * Pricing strategy for regular customers. No discount is applied.
 *
 * @author Jon Musselwhite
 */
public class RegularPricing implements PricingStrategy {

  /**
   * Returns the original price unchanged.
   *
   * @param price the original price
   * @return the same price with no discount
   */
  @Override
  public double calculatePrice(double price) {
    return price;
  }
}
