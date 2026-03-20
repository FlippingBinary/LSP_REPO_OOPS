package org.howard.edu.lsp.midterm.strategy;

/**
 * Context class that delegates price calculation to a
 * {@link PricingStrategy}. The strategy can be swapped at runtime to
 * change the pricing rule without modifying this class.
 *
 * @author Jon Musselwhite
 */
public class PriceCalculator {
  private PricingStrategy strategy;

  /**
   * Constructs a calculator with the given pricing strategy.
   *
   * @param strategy the pricing strategy to use
   */
  public PriceCalculator(PricingStrategy strategy) {
    this.strategy = strategy;
  }

  /**
   * Replaces the current pricing strategy.
   *
   * @param strategy the new pricing strategy
   */
  public void setStrategy(PricingStrategy strategy) {
    this.strategy = strategy;
  }

  /**
   * Calculates the final price by delegating to the current strategy.
   *
   * @param price the original price
   * @return the price after the strategy's rule is applied
   */
  public double calculatePrice(double price) {
    return strategy.calculatePrice(price);
  }
}
