package org.howard.edu.lsp.assignment3;

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

  // Price range tests - verified via transformProduct() since determinePriceRange is private
  @Test
  void transformProduct_priceRangeLowForPriceAtOrBelow10() {
    Product product = new Product(1, "Cheap", new BigDecimal("10.00"), "Home");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals("Low", result.getPriceRange());

    Product product2 = new Product(2, "Cheap", new BigDecimal("5.00"), "Home");
    TransformedProduct result2 = transformer.transformProduct(product2);
    assertEquals("Low", result2.getPriceRange());
  }

  @Test
  void transformProduct_priceRangeMediumForPriceBetween10And100() {
    Product product = new Product(1, "Medium", new BigDecimal("10.01"), "Home");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals("Medium", result.getPriceRange());

    Product product2 = new Product(2, "Medium", new BigDecimal("100.00"), "Home");
    TransformedProduct result2 = transformer.transformProduct(product2);
    assertEquals("Medium", result2.getPriceRange());
  }

  @Test
  void transformProduct_priceRangeHighForPriceBetween100And500() {
    Product product = new Product(1, "Expensive", new BigDecimal("100.01"), "Home");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals("High", result.getPriceRange());

    Product product2 = new Product(2, "Expensive", new BigDecimal("500.00"), "Home");
    TransformedProduct result2 = transformer.transformProduct(product2);
    assertEquals("High", result2.getPriceRange());
  }

  @Test
  void transformProduct_priceRangePremiumForPriceAbove500() {
    Product product = new Product(1, "Luxury", new BigDecimal("500.01"), "Home");
    TransformedProduct result = transformer.transformProduct(product);
    assertEquals("Premium", result.getPriceRange());

    Product product2 = new Product(2, "Luxury", new BigDecimal("1000.00"), "Home");
    TransformedProduct result2 = transformer.transformProduct(product2);
    assertEquals("Premium", result2.getPriceRange());
  }
}
