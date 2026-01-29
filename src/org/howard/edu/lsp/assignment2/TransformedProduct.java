package org.howard.edu.lsp.assignment2;

import java.math.BigDecimal;

/** A single product after transformations have been applied */
public class TransformedProduct {
  private final int productId;
  private final String name;
  private final BigDecimal price;
  private final String category;
  private final String priceRange;

  public TransformedProduct(int productId, String name, BigDecimal price,
      String category, String priceRange) {
    this.productId = productId;
    this.name = name;
    this.price = price;
    this.category = category;
    this.priceRange = priceRange;
  }

  public int getProductId() {
    return productId;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getCategory() {
    return category;
  }

  public String getPriceRange() {
    return priceRange;
  }

  public String toCsvRow() {
    return productId + "," + name + "," + price.toPlainString() + "," + category + "," + priceRange;
  }
}
