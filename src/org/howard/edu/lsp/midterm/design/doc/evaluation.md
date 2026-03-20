# Design Evaluation — `OrderProcessor`

## 1. The class does too many things

`OrderProcessor` calculates tax, prints a receipt, writes to a file, sends an
email, applies a discount, and logs the timestamp — all inside one method. Riel's
heuristic that a class should capture a single abstraction is violated because
the class is really acting as a tax calculator, a receipt printer, a file writer,
an email sender, a discount engine, and a logger all at once. When any one of
those responsibilities changes (say, a new tax rate or a different email
provider), the entire `processOrder` method must be edited, increasing the risk
of breaking unrelated behavior.

## 2. Public fields break encapsulation

`customerName`, `email`, `item`, and `price` are all declared `public`. Any
external code can change them at any time, even in the middle of processing.
Riel's guidance that all data should be hidden within its class is directly
violated. If the representation of price changes from `double` to `BigDecimal`,
every class that touches `price` must also change. Private fields with getters
would restrict the surface area of that change to `OrderProcessor` itself.

## 3. Discount is applied after the receipt and the file write

The method prints the total and persists it to `orders.txt` before applying the
10% discount. The customer sees one total on the receipt while the post-discount
total exists only in a local variable that is never used again. This is a logic
error enabled by the monolithic design: because every step lives in one method,
there is no clear pipeline that ensures all pricing adjustments happen before
output.

## 4. The class depends on concrete I/O

`processOrder` directly constructs a `FileWriter` and hard-codes the file name
`"orders.txt"`. The class cannot be tested without creating a real file on disk,
and it cannot be reused in a context where orders should go to a database or a
network service. Hiding the storage mechanism behind an interface would allow the
class to work with any persistence strategy.

## 5. No separation of data from behavior

Order data (customer, item, price) and order processing logic live in the same
class. According to Riel, a class should model either an entity or a service, not
both simultaneously. Mixing the two means the data cannot be passed to a
different processor, and the processor cannot operate on a different data shape,
without modifying this one class.
