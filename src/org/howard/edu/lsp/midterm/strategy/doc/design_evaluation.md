# Design Evaluation — `PriceCalculator`

The original design of `PriceCalculator` was brittle and failed to leverage the
object-oriented capabilities of the language.

## 1. Adding a new customer type requires modifying the calculator

Every pricing rule is an `if` block inside `calculatePrice`. Adding a new
customer type — say "EMPLOYEE" — means opening the class, inserting another
conditional, and retesting the entire method. This violates the separation of
concerns.

## 2. Pricing logic cannot be reused or tested independently

Each discount calculation is embedded in the body of `calculatePrice` rather
than isolated in its own unit. There is no way to unit-test the VIP discount
without also exercising the regular, member, and holiday branches. If a pricing
rule becomes more complex (e.g. tiered VIP discounts), the method grows longer
and harder to reason about.

## 3. String-based dispatch is fragile

Customer types are identified by raw strings (`"REGULAR"`, `"MEMBER"`, etc.).
A typo like `"Vip"` silently falls through every `if` block and returns the
original price with no indication that something went wrong. There is no
compile-time guarantee that valid customer types are used. Furthermore, there
is nothing inherently preventing a developer from accidentally repeating a
discount. As written, the conditional statements aren't even chained together
to prevent it.

## 4. The method will grow linearly with new requirements

Each new pricing rule adds another `if` block to the same method. Over time this
produces a long, flat conditional chain that is difficult to read, easy to
mis-order, and likely to cause merge conflicts when multiple developers add rules
at the same time.
