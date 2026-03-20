# Development Log - Q2

I used `opencode` to create a starting point since AI tools were explicitly allowed
and we had limited time to complete the assignment.

Given more time, I'd probably want to give it a bit more thought and design it
a little differently.

## `opencode` Transcript

```md
[analyze-mode]
ANALYSIS MODE. Gather context before diving deep:
CONTEXT GATHERING (parallel):
- 1-2 explore agents (codebase patterns, implementations)
- 1-2 librarian agents (if external library involved)
- Direct tools: Grep, AST-grep, LSP for targeted searches
IF COMPLEX - DO NOT STRUGGLE ALONE. Consult specialists:
- **Oracle**: Conventional problems (architecture, debugging, complex logic)
- **Artistry**: Non-conventional problems (different approach needed)
SYNTHESIZE findings before proceeding.
---
Please review `src/org/howard/edu/lsp/midterm/strategy/INSTRUCTIONS.md` and create the described `.java` and `.md` files.
jon
I detect implementation intent — read instructions and create Java/Markdown files as described. Let me start by reading the instructions.
→ Read src/org/howard/edu/lsp/midterm/strategy/INSTRUCTIONS.md 
Clear requirements. Let me check the existing codebase style for consistency:
→ Read src/org/howard/edu/lsp/midterm/crccards/Task.java 
Good — 2-space indent, full Javadoc, @author Jon Musselwhite. Let me check the existing directory structure:
→ List src/org/howard/edu/lsp/midterm/strategy
[ ] Create PricingStrategy interface (single method: calculatePrice)
[ ] Create RegularPricing, MemberPricing, VipPricing, HolidayPricing strategy implementations
[ ] Create PriceCalculator context class that delegates to a PricingStrategy
[ ] Create Driver class producing exact expected output
[ ] Create doc/design_evaluation.md
[ ] Build and run to verify expected output matches
Creating the doc directory and all files in parallel:
[ ] Create PricingStrategy interface (single method: calculatePrice)
[ ] Create RegularPricing, MemberPricing, VipPricing, HolidayPricing strategy implementations
[ ] Create PriceCalculator context class that delegates to a PricingStrategy
[ ] Create Driver class producing exact expected output
[ ] Create doc/design_evaluation.md
[ ] Build and run to verify expected output matches
# Create strategy doc directory
$ export CI=true DEBIAN_FRONTEND=noninteractive GIT_TERMINAL_PROMPT=0 GCM_INTERACTIVE=never HOMEBREW_NO_AUTO_UPDATE=1 GIT_EDITOR=: EDITOR=: VISUAL='' GIT_SEQUENCE_EDITOR=: GIT_MERGE_AUTOEDIT=no GIT_PAGER=cat PAGER=cat npm_config_yes=true PIP_NO_INPUT=1 YARN_ENABLE_IMMUTABLE_INSTALLS=false; mkdir -p /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/strategy/doc
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/strategy/PricingStrategy.java
package org.howard.edu.lsp.midterm.strategy;
/**
 * Strategy interface for calculating a final price.
 *
 * <p>Each implementation encapsulates a single pricing rule (e.g. no
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
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/strategy/RegularPricing.java
package org.howard.edu.lsp.midterm.strategy;
/**
 * Pricing strategy for regular customers. No discount is applied.
 *
 * @author Jon Musselwhite
 */
public class RegularPricing implements PricingStrategy {
  /**
   * Returns the original price unchanged.
   *
   * @param price the original price
   * @return the same price with no discount
   */
  @Override
  public double calculatePrice(double price) {
    return price;
  }
}
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/strategy/MemberPricing.java
package org.howard.edu.lsp.midterm.strategy;
/**
 * Pricing strategy for members. Applies a 10% discount.
 *
 * @author Jon Musselwhite
 */
public class MemberPricing implements PricingStrategy {
  /**
   * Returns the price after a 10% member discount.
   *
   * @param price the original price
   * @return the discounted price
   */
  @Override
  public double calculatePrice(double price) {
    return price * 0.90;
  }
}
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/strategy/VipPricing.java
package org.howard.edu.lsp.midterm.strategy;
/**
 * Pricing strategy for VIP customers. Applies a 20% discount.
 *
 * @author Jon Musselwhite
 */
public class VipPricing implements PricingStrategy {
  /**
   * Returns the price after a 20% VIP discount.
   *
   * @param price the original price
   * @return the discounted price
   */
  @Override
  public double calculatePrice(double price) {
    return price * 0.80;
  }
}
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/strategy/HolidayPricing.java
package org.howard.edu.lsp.midterm.strategy;
/**
 * Pricing strategy for holiday promotions. Applies a 15% discount.
 *
 * @author Jon Musselwhite
 */
public class HolidayPricing implements PricingStrategy {
  /**
   * Returns the price after a 15% holiday discount.
   *
   * @param price the original price
   * @return the discounted price
   */
  @Override
  public double calculatePrice(double price) {
    return price * 0.85;
  }
}
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/strategy/PriceCalculator.java
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
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/strategy/Driver.java
```
