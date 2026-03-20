package org.howard.edu.lsp.midterm.strategy;

/**
 * Pricing strategy for VIP customers. Applies a 20% discount.
 *
 * @author Jon Musselwhite
 */
public class VipPricing implements PricingStrategy {

  /**
   * Returns the price after a 20% VIP discount.
   *
   * @param price the original price
   * @return the discounted price
   */
  @Override
  public double calculatePrice(double price) {
    return price * 0.80;
  }
}
