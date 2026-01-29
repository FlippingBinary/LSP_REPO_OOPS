package org.howard.edu.lsp.assignment2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTransformerTest {
  private ProductTransformer transformer;

  @BeforeEach
  void setUp() {
    transformer = new ProductTransformer();
  }

  @Test
  void transformProduct_convertsNameToUppercase() {
    Product product = new Product(1, "laptop", new BigDecimal("100.00"), "Home");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals("LAPTOP", result.getName());
  }

  @Test
  void transformProduct_electronicsReceive10PercentDiscount() {
    Product product = new Product(1, "Phone", new BigDecimal("100.00"), "Electronics");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals(new BigDecimal("90.00"), result.getPrice());
  }

  @Test
  void transformProduct_nonElectronicsNoDiscount() {
    Product product = new Product(1, "Chair", new BigDecimal("100.00"), "Furniture");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals(new BigDecimal("100.00"), result.getPrice());
  }

  @Test
  void transformProduct_electronicsOver500BecomePremiumElectronics() {
    Product product = new Product(1, "TV", new BigDecimal("600.00"), "Electronics");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals("Premium Electronics", result.getCategory());
    assertEquals(new BigDecimal("540.00"), result.getPrice());
  }

  @Test
  void transformProduct_electronicsExactly500AfterDiscountStaysElectronics() {
    Product product = new Product(1, "TV", new BigDecimal("555.56"), "Electronics");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals("Electronics", result.getCategory());
    assertEquals(new BigDecimal("500.00"), result.getPrice());
  }

  @Test
  void transformProduct_priceIsRoundedDownToTwoDecimalPlaces() {
    Product product = new Product(1, "Gadget", new BigDecimal("33.333"), "Electronics");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals(new BigDecimal("30.00"), result.getPrice());
  }

  @Test
  void transformProduct_priceIsRoundedUpToTwoDecimalPlaces() {
    Product product = new Product(1, "Gadget", new BigDecimal("33.339"), "Electronics");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals(new BigDecimal("30.01"), result.getPrice());
  }

  @Test
  void determinePriceRange_lowForPriceAtOrBelow10() {
    assertEquals("Low", transformer.determinePriceRange(new BigDecimal("10.00")));
    assertEquals("Low", transformer.determinePriceRange(new BigDecimal("5.00")));
  }

  @Test
  void determinePriceRange_mediumForPriceBetween10And100() {
    assertEquals("Medium", transformer.determinePriceRange(new BigDecimal("10.01")));
    assertEquals("Medium", transformer.determinePriceRange(new BigDecimal("100.00")));
  }

  @Test
  void determinePriceRange_highForPriceBetween100And500() {
    assertEquals("High", transformer.determinePriceRange(new BigDecimal("100.01")));
    assertEquals("High", transformer.determinePriceRange(new BigDecimal("500.00")));
  }

  @Test
  void determinePriceRange_premiumForPriceAbove500() {
    assertEquals("Premium", transformer.determinePriceRange(new BigDecimal("500.01")));
    assertEquals("Premium", transformer.determinePriceRange(new BigDecimal("1000.00")));
  }

  @Test
  void transformProduct_preservesProductId() {
    Product product = new Product(42, "Item", new BigDecimal("50.00"), "Home");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals(42, result.getProductId());
  }

  @Test
  void transformProduct_nonElectronicsCategoryUnchanged() {
    Product product = new Product(1, "Table", new BigDecimal("200.00"), "Furniture");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals("Furniture", result.getCategory());
  }
}
