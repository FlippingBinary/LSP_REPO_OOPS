# Development Log - Q2

I used `opencode` to create a starting point since AI tools were explicitly allowed
and we had limited time to complete the assignment.

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
Please review `src/org/howard/edu/lsp/midterm/design/INSTRUCTIONS.md` and create the described `.md` files.
jon
I detect implementation intent — read instructions and create Markdown files as described. Let me start by reading the instructions.
→ Read src/org/howard/edu/lsp/midterm/design/doc/INSTRUCTIONS.md 
Let me check the existing CRC card format in the project for style consistency:
→ Read src/org/howard/edu/lsp/assignment4/doc/crc_cards.md 
→ Read src/org/howard/edu/lsp/midterm/crccards/doc/crccards.md 
Good — I have the instructions, the provided code, and the existing CRC card style. This is a writing task (two markdown files analyzing an OO design). Let me verify the doc directory exists, then create both files.
→ List src/org/howard/edu/lsp/midterm/design
The doc/ directory already exists. Creating both files:
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/design/doc/evaluation.md
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
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/design/doc/design.md
# Improved Design — CRC Cards
## **CRC Card**
Class: Order
Responsibilities:
- Store order data (customer name, email, item, price).
- Provide access to order attributes through getters.
Collaborators:
- None
---
## **CRC Card**
Class: TaxCalculator
Responsibilities:
- Calculate the tax amount for a given price.
- Return the total price including tax.
Collaborators:
- Order
---
## **CRC Card**
Class: DiscountCalculator
Responsibilities:
- Determine whether a discount applies based on the price.
- Calculate and return the discounted total.
Collaborators:
- Order
---
## **CRC Card**
Class: ReceiptPrinter
Responsibilities:
- Format and print a receipt showing customer name, item, and total.
Collaborators:
- Order
---
## **CRC Card**
Class: OrderRepository
Responsibilities:
- Persist a completed order to storage.
Collaborators:
- Order
---
## **CRC Card**
Class: EmailService
Responsibilities:
- Send a confirmation email to the customer after an order is processed.
Collaborators:
- Order
---
## **CRC Card**
Class: ActivityLogger
Responsibilities:
- Record a timestamped log entry when an order is processed.
Collaborators:
- Order
---
## **CRC Card**
Class: OrderProcessor
Responsibilities:
- Coordinate the order-processing workflow: calculate tax, apply discount, print receipt, persist order, send email, and log activity.
Collaborators:
- Order
- TaxCalculator
- DiscountCalculator
- ReceiptPrinter
- OrderRepository
- EmailService
- ActivityLogger
Done. Two files created in src/org/howard/edu/lsp/midterm/design/doc/:
| File | Content |
|---|---|
| evaluation.md | Identifies 5 design issues: god-class violation (too many responsibilities), public fields breaking encapsulation, discount applied after receipt/persistence (logic error), hard-coded I/O coupling, and mixing data with behavior. References Riel's heuristics throughout. |
| design.md | 8 CRC cards decomposing the system: Order (data), TaxCalculator, DiscountCalculator, ReceiptPrinter, OrderRepository, EmailService, ActivityLogger (single-responsibility services), and OrderProcessor (coordinator). Follows the existing CRC card format used in assignment4. |
```
