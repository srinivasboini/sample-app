# 🚀 Java Streams Practice & Mastery Suite

Welcome to the comprehensive **Java Streams Kata Suite**! This repository is optimized for intensive technical interview preparation with **71 interview-level stream katas** covering flatMap, reductions, collectors, multi-level groupings, numeric streams, and realistic business domain logic.

---

## 📂 Project Structure

```
src/test/java/com/srini/demo/streams/
├── README.md                                  # Instructions & Streams Cheat Sheet
├── TestData.java                              # Realistic test dataset (Employees, Orders, etc.)
├── model/                                     # Domain models (Java 21 records)
│   ├── Employee.java
│   ├── Department.java
│   ├── Customer.java
│   ├── CustomerTier.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── OrderStatus.java
│   ├── Product.java
│   └── Transaction.java
│
├── Level2_FlatMapAndReductionExercisesTest.java# 18 exercises: FlatMap, Optionals, Reduce, Cartesian, Ties
├── Level3_CollectorsAndGroupingExercisesTest.java # 21 exercises: GroupingBy, Partitioning, Stats, Filtering, Teeing
├── Level4_NumericAndInfiniteStreamExercisesTest.java # 16 exercises: IntStream, Stats, Iterate, Math, Sequences
├── Level5_RealWorldKatasTest.java             # 16 exercises: E-Commerce, Multi-Dept, Lifetime Value, Frequency
│
└── solutions/                                 # 71 Working Reference Solutions
    ├── Level2_FlatMapAndReductionExercisesSolutionsTest.java
    ├── Level3_CollectorsAndGroupingExercisesSolutionsTest.java
    ├── Level4_NumericAndInfiniteStreamExercisesSolutionsTest.java
    └── Level5_RealWorldKatasSolutionsTest.java
```

---

## 🏃 How to Practice

1. Open one of the practice test files in your IDE (e.g. `Level2_FlatMapAndReductionExercisesTest.java`).
2. Pick an exercise (e.g., `ex01_flattenListOfLists`).
3. Replace the `TODO` with your stream pipeline.
4. Run the test:
   - **From IDE**: Click the green "Run Test" ▶ icon next to the method.
   - **From Terminal**:
     ```bash
     # Run a single exercise
     ./mvnw test -Dtest=Level2_FlatMapAndReductionExercisesTest#ex01_flattenListOfLists

     # Run an entire level
     ./mvnw test -Dtest=Level2_FlatMapAndReductionExercisesTest
     ```
5. If you want to check the reference solution, open the corresponding class under the `solutions/` folder.
6. To run all reference solution tests:
   ```bash
   ./mvnw test -Dtest="*SolutionsTest"
   ```

---

## 📚 Exercise Levels & Topics Covered

### **Level 2: FlatMapping, Optionals & Reductions (18 Exercises)**
- `flatMap(Function)`: Flattening 1-to-many collections (nested lists, sentences into words, orders into items).
- `flatMap(Optional::stream)`: Cleanly unwrapping `Optional` streams without null checks.
- `reduce(identity, accumulator)` & `reduce((a, b) -> ...)`: Summing, multiplying, custom string formatting.
- `reduce(identity, accumulator, combiner)`: 3-argument parallel/type-transforming reduction.
- `max(Comparator)` & `min(Comparator)`: Finding extremal elements with complex multi-field tie-breaking.
- Finding 2nd highest distinct elements, computing Cartesian products, and unwrapping deeply nested Optionals.

### **Level 3: Collectors, Grouping & Partitioning (21 Exercises)**
- `Collectors.toList()`, `toSet()`, `toCollection(TreeSet::new)`.
- `Collectors.toMap()`: Key collision resolution via merge functions `(existing, replacement) -> ...`.
- `Collectors.joining(delimiter, prefix, suffix)`: Pretty string output formatting.
- `Collectors.groupingBy()`:
  - Simple grouping: `Map<K, List<V>>`.
  - Grouping and counting: `groupingBy(classifier, counting())`.
  - Grouping and averaging / summing: `groupingBy(classifier, averagingDouble(...))`.
  - Grouping and reducing / max: `groupingBy(classifier, collectingAndThen(maxBy(...), Optional::get))`.
  - Grouping and mapping downstream: `groupingBy(classifier, mapping(field, toList()))`.
  - Grouping into summary statistics: `summarizingDouble(field)`.
- `Collectors.filtering()` (Java 9+): Downstream filtering preserving all keys with empty collections.
- `Collectors.partitioningBy()`: Splitting data into two groups based on boolean predicates and finding extremal elements in partitions.
- Multi-level nested grouping: `Map<Dept, Map<Gender, List<Employee>>>` and custom age buckets.
- `Collectors.teeing()` (Java 12+): Computing two independent downstream collectors (e.g. min & max, sum & average) in a single pass.
- Sorting frequency maps into `LinkedHashMap`.

### **Level 4: Numeric Streams, Math & Infinite Streams (16 Exercises)**
- `IntStream.range()` & `IntStream.rangeClosed()`.
- `summaryStatistics()`: Extracting count, sum, min, average, and max in $O(N)$ with primitive streams.
- Mathematical streaming: Primes test, Factorials with `LongStream`, Fibonacci generation, Pythagorean triples.
- `Stream.iterate()`: Seed and unary operator (infinite streams) or predicate (Java 9+ finite iterate) for Collatz sequence.
- String character processing with `String.chars()` (vowel counting, digit summing, palindrome check).
- Primitive array boxing with `.boxed()`.
- Vector calculations (dot product), missing number detection, and cumulative running prefix sums.

### **Level 5: Real-World Business Katas (16 Exercises)**
- **E-Commerce Analytics**: Top-selling products by quantity, top spenders, customer lifetime value by tier, order delivery fulfillment rate.
- **Financial Analytics**: Monthly sales trends, net transaction balance (`CREDIT` vs `DEBIT`).
- **HR & Organization**: Department budget rankings, top-N earners per department, most frequent skills, multi-department city operations.
- **Text & Algorithmic Processing**: Anagram grouping, word frequency mapping, order-preserving first non-repeated character.
- **Set Operations**: Detecting inactive customers with zero orders.

---

## 💡 Quick Java Streams Cheat Sheet

| Operation | Type | Example |
|-----------|------|---------|
| `filter` | Intermediate | `.filter(e -> e.salary() > 50000)` |
| `map` | Intermediate | `.map(Employee::name)` |
| `flatMap` | Intermediate | `.flatMap(order -> order.items().stream())` |
| `distinct` | Intermediate | `.distinct()` |
| `sorted` | Intermediate | `.sorted(Comparator.comparing(Employee::salary).reversed())` |
| `peek` | Intermediate | `.peek(System.out::println)` |
| `limit` / `skip` | Intermediate | `.skip(10).limit(5)` |
| `takeWhile` / `dropWhile` | Intermediate | `.takeWhile(n -> n < 100)` |
| `forEach` | Terminal | `.forEach(System.out::println)` |
| `toList()` | Terminal | `.toList()` (Java 16+ unmodifiable list) |
| `collect` | Terminal | `.collect(Collectors.groupingBy(...))` |
| `reduce` | Terminal | `.reduce(0, Integer::sum)` |
| `count` | Terminal | `.count()` |
| `anyMatch` / `allMatch` | Terminal | `.anyMatch(e -> e.age() > 60)` |
| `findFirst` / `findAny` | Terminal | `.findFirst()` |
| `summaryStatistics` | Terminal | `.mapToDouble(Employee::salary).summaryStatistics()` |
