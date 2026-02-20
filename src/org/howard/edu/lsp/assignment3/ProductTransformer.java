package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Transforms product data according to business rules and requirements.
 *
 * This class handles batch and single product transformations, applying
 * business logic including name normalization, electronics discount, price
 * rounding, premium category reclassification, and price range categorization.
 * The price range determination is an internal implementation detail and is not
 * exposed as part of the public API.
 */
public class ProductTransformer {
  private static final BigDecimal ELECTRONICS_DISCOUNT = new BigDecimal("0.90");
  private static final BigDecimal PREMIUM_THRESHOLD = new BigDecimal("500.00");
  private static final BigDecimal LOW_THRESHOLD = new BigDecimal("10.00");
  private static final BigDecimal MEDIUM_THRESHOLD = new BigDecimal("100.00");
  private static final String ELECTRONICS = "Electronics";
  private static final String PREMIUM_ELECTRONICS = "Premium Electronics";

  /**
   * Transforms a list of products according to business transformation rules.
   *
   * @param products the list of products to transform
   * @return a list of transformed products with applied business logic
   */
  public List<TransformedProduct> transform(List<Product> products) {
    List<TransformedProduct> transformed = new ArrayList<>();
    for (Product product : products) {
      transformed.add(transformProduct(product));
    }
    return transformed;
  }

  /**
   * Transforms a single product according to business transformation rules.
   *
   * Applies the following transformations:
   * - Name is converted to uppercase
   * - Electronics products receive a 10% discount
   * - Prices are rounded to 2 decimal places using HALF_UP rounding
   * - Electronics products priced at or above 500 are reclassified as Premium
   * Electronics
   * - Price range is determined based on thresholds
   *
   * @param product the product to transform
   * @return a transformed product with all business rules applied
   */
  public TransformedProduct transformProduct(Product product) {
    String originalCategory = product.getCategory();

    String name = product.getName().toUpperCase();

    BigDecimal price = product.getPrice();
    if (originalCategory.equals(ELECTRONICS)) {
      price = price.multiply(ELECTRONICS_DISCOUNT);
    }

    price = price.setScale(2, RoundingMode.HALF_UP);

    String category = originalCategory;
    if (price.compareTo(PREMIUM_THRESHOLD) > 0 && originalCategory.equals(ELECTRONICS)) {
      category = PREMIUM_ELECTRONICS;
    }

    String priceRange = determinePriceRange(price);

    return new TransformedProduct(product.getProductId(), name, price, category, priceRange);
  }

  /**
   * Determines the price range category for a given price.
   *
   * This is a private implementation detail used internally by the transformer
   * to categorize prices. The price ranges are:
   * - Low: price <= 10.00
   * - Medium: 10.00 < price <= 100.00
   * - High: 100.00 < price <= 500.00
   * - Premium: price > 500.00
   *
   * @param price the price to categorize
   * @return the price range category as a string
   */
  private String determinePriceRange(BigDecimal price) {
    if (price.compareTo(LOW_THRESHOLD) <= 0) {
      return "Low";
    } else if (price.compareTo(MEDIUM_THRESHOLD) <= 0) {
      return "Medium";
    } else if (price.compareTo(PREMIUM_THRESHOLD) <= 0) {
      return "High";
    } else {
      return "Premium";
    }
  }
}
