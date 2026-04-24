# Question 2 — Design Explanation

## How Template Method Is Used

The `Report` abstract class defines the template method `generateReport()`, which
is declared `final` so subclasses cannot alter the workflow. It calls four abstract
hook methods in a fixed order: `loadData()`, `formatHeader()`, `formatBody()`,
and `formatFooter()`. Concrete subclasses `StudentReport` and `CourseReport` override
these four methods to supply their own data and formatting. The `Driver` stores
both subclasses in a `List<Report>` and loops through it, calling `generateReport()`
on each element — demonstrating polymorphism because the same method call produces
different output depending on the runtime type.
