package org.howard.edu.lsp.midterm.strategy;

/**
 * Strategy interface for calculating a final price.
 *
 * Each implementation encapsulates a single pricing rule (e.g. no
 * discount, member discount, VIP discount). The {@link PriceCalculator}
 * delegates to a {@code PricingStrategy} at runtime.
 *
 * @author Jon Musselwhite
 */
public interface PricingStrategy {

  /**
   * Calculates the final price after applying this strategy's pricing
   * rule.
   *
   * @param price the original price before any adjustment
   * @return the adjusted price
   */
  double calculatePrice(double price);
}
