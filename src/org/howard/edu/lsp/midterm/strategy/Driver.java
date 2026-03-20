package org.howard.edu.lsp.midterm.strategy;

/**
 * Demonstrates the Strategy Pattern implementation by calculating the
 * final price for each customer type using a purchase price of 100.0.
 *
 * @author Jon Musselwhite
 */
public class Driver {

  /**
   * Entry point. Prints the final price for REGULAR, MEMBER, VIP, and
   * HOLIDAY customers.
   *
   * @param args command-line arguments (unused)
   */
  public static void main(String[] args) {
    double price = 100.0;

    PriceCalculator calculator = new PriceCalculator(new RegularPricing());
    System.out.println("REGULAR: " + calculator.calculatePrice(price));

    calculator.setStrategy(new MemberPricing());
    System.out.println("MEMBER: " + calculator.calculatePrice(price));

    calculator.setStrategy(new VipPricing());
    System.out.println("VIP: " + calculator.calculatePrice(price));

    calculator.setStrategy(new HolidayPricing());
    System.out.println("HOLIDAY: " + calculator.calculatePrice(price));
  }
}
