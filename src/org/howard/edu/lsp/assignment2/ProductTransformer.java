package org.howard.edu.lsp.assignment2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/** Name: Jon Musselwhite */
public class ProductTransformer {
    private static final BigDecimal ELECTRONICS_DISCOUNT = new BigDecimal("0.90");
    private static final BigDecimal PREMIUM_THRESHOLD = new BigDecimal("500.00");
    private static final BigDecimal LOW_THRESHOLD = new BigDecimal("10.00");
    private static final BigDecimal MEDIUM_THRESHOLD = new BigDecimal("100.00");
    private static final String ELECTRONICS = "Electronics";
    private static final String PREMIUM_ELECTRONICS = "Premium Electronics";

    public List<TransformedProduct> transform(List<Product> products) {
        List<TransformedProduct> transformed = new ArrayList<>();
        for (Product product : products) {
            transformed.add(transformProduct(product));
        }
        return transformed;
    }

    private TransformedProduct transformProduct(Product product) {
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
