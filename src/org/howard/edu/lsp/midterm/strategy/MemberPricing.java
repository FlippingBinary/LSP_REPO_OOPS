package org.howard.edu.lsp.midterm.strategy;

/**
 * Pricing strategy for members. Applies a 10% discount.
 *
 * @author Jon Musselwhite
 */
public class MemberPricing implements PricingStrategy {

  /**
   * Returns the price after a 10% member discount.
   *
   * @param price the original price
   * @return the discounted price
   */
  @Override
  public double calculatePrice(double price) {
    return price * 0.90;
  }
}
