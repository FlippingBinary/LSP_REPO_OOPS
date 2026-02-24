package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * A product with transformation results applied, extending the base Product
 * class.
 *
 * This class demonstrates inheritance by extending Product and adding a single
 * new field (priceRange) to represent transformed product data. The priceRange
 * field captures the price category assigned during the transformation process.
 */
public class TransformedProduct extends Product {
  private final String priceRange;

  /**
   * Constructs a TransformedProduct with the specified attributes.
   *
   * @param productId  the unique identifier for this product
   * @param name       the name or title of the product
   * @param price      the price of the product as a BigDecimal
   * @param category   the category or classification of the product
   * @param priceRange the price range category assigned during transformation
   */
  public TransformedProduct(int productId, String name, BigDecimal price,
      String category, String priceRange) {
    super(productId, name, price, category);
    this.priceRange = priceRange;
  }

  /**
   * Returns the price range category for this transformed product.
   *
   * @return the price range classification
   */
  public String getPriceRange() {
    return priceRange;
  }
}
