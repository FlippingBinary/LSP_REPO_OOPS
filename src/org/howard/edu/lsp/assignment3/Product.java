package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * A product data model representing a single row from the input CSV.
 * 
 * This immutable class encapsulates product information including identifier,
 * name, price, and category. Instances are created through the public constructor
 * and accessed via getter methods.
 */
public class Product {
  private final int productId;
  private final String name;
  private final BigDecimal price;
  private final String category;

  /**
   * Constructs a Product with the specified attributes.
   * 
   * @param productId the unique identifier for this product
   * @param name the name or title of the product
   * @param price the price of the product as a BigDecimal
   * @param category the category or classification of the product
   */
  public Product(int productId, String name, BigDecimal price, String category) {
    this.productId = productId;
    this.name = name;
    this.price = price;
    this.category = category;
  }

  /**
   * Returns the product ID.
   * 
   * @return the unique identifier for this product
   */
  public int getProductId() {
    return productId;
  }

  /**
   * Returns the product name.
   * 
   * @return the name or title of the product
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the product price.
   * 
   * @return the price of the product as a BigDecimal
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Returns the product category.
   * 
   * @return the category or classification of the product
   */
  public String getCategory() {
    return category;
  }
}
