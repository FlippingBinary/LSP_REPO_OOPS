# Improved Design — CRC Cards

## **CRC Card**

**Class**: `Order`

**Responsibilities**:

- Store order data (customer name, email, item, price).
- Provide access to order attributes through getters.

**Collaborators**:

- None

---

## **CRC Card**

**Class**: `TaxCalculator`

**Responsibilities**:

- Calculate the tax amount for a given price.
- Return the total price including tax.

**Collaborators**:

- `Order`

---

## **CRC Card**

**Class**: `DiscountCalculator`

**Responsibilities**:

- Determine whether a discount applies based on the price.
- Calculate and return the discounted total.

**Collaborators**:

- `Order`

---

## **CRC Card**

**Class**: `ReceiptPrinter`

**Responsibilities**:

- Format and print a receipt showing customer name, item, and total.

**Collaborators**:

- `Order`

---

## **CRC Card**

**Class**: `OrderRepository`

**Responsibilities**:

- Persist a completed order to storage.

**Collaborators**:

- `Order`

---

## **CRC Card**

**Class**: `EmailService`

**Responsibilities**:

- Send a confirmation email to the customer after an order is processed.

**Collaborators**:

- `Order`

---

## **CRC Card**

**Class**: `ActivityLogger`

**Responsibilities**:

- Record a timestamped log entry when an order is processed.

**Collaborators**:

- `Order`

---

## **CRC Card**

**Class**: `OrderProcessor`

**Responsibilities**:

- Coordinate the order-processing workflow: calculate tax, apply discount, print receipt, persist order, send email, and log activity.

**Collaborators**:

- `Order`
- `TaxCalculator`
- `DiscountCalculator`
- `ReceiptPrinter`
- `OrderRepository`
- `EmailService`
- `ActivityLogger`
