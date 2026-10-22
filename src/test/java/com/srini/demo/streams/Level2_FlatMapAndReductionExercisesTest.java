package com.srini.demo.streams;

import com.srini.demo.streams.model.Employee;
import com.srini.demo.streams.model.Order;
import com.srini.demo.streams.model.OrderStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * LEVEL 2: FLATMAP, OPTIONALS & REDUCTIONS
 * ════════════════════════════════════════════════════════════════════════════
 * Focus:
 *  - flatMap with collections, arrays, and Optionals
 *  - reduce operations with identity, accumulator, and binary operators
 *  - min and max reductions with custom comparators
 *  - Cartesian products and multi-level data unnesting
 *
 * Instructions:
 *  1. Replace the placeholder / TODO with your Java Stream pipeline.
 *  2. Run the test in your IDE or via terminal:
 *     ./mvnw test -Dtest=Level2_FlatMapAndReductionExercisesTest#ex01_flattenListOfLists
 *  3. Check 'solutions/Level2_FlatMapAndReductionExercisesSolutionsTest.java' if you get stuck!
 */
@DisplayName("Level 2: FlatMap, Optionals, and Reductions")
public class Level2_FlatMapAndReductionExercisesTest {

    @Test
    @DisplayName("01: Flatten nested list of lists into a single list")
    void ex01_flattenListOfLists() {
        List<List<String>> nested = List.of(
                List.of("Spring", "Quarkus"),
                List.of("Micronaut"),
                List.of("Ktor", "Vert.x")
        );

        // Task: Flatten the list of lists into a single List<String>

        List<String> result = null; // TODO: Implement using stream
        result = nested.stream().flatMap(List::stream).toList();

        assertThat(result).containsExactly("Spring", "Quarkus", "Micronaut", "Ktor", "Vert.x");
    }

    @Test
    @DisplayName("02: Extract all unique employee skills sorted alphabetically")
    void ex02_extractAllUniqueSkills() {
        // Given: TestData.EMPLOYEES
        // Task: Collect all distinct skills across every employee, sorted alphabetically

        List<String> result = null; // TODO: Implement using stream

        result = TestData.EMPLOYEES.stream().map(Employee::skills).flatMap(List::stream).distinct().sorted(Comparator.naturalOrder()).toList();

        assertThat(result).contains(
                "AWS", "Accounting", "Architecture", "Auditing", "B2B",
                "CRM", "Cloud", "Cold Calling", "Compensation", "Docker",
                "Employee Relations", "Enterprise Sales", "Excel",
                "Financial Modeling", "Go", "Java", "Kubernetes",
                "Microservices", "Negotiation", "Onboarding", "Python",
                "React", "Recruiting", "Spring", "Talent Acquisition", "Taxation"
        );
        assertThat(result).hasSize(26);
    }

    @Test
    @DisplayName("03: Split sentences into distinct lowercase words sorted alphabetically")
    void ex03_splitSentencesToWords() {
        // Given: TestData.SENTENCES
        // Task: Split sentences into individual words by whitespace ("\\s+"),
        //       convert to lowercase, remove duplicates, and sort alphabetically.

        List<String> result = null; // TODO: Implement using stream

        result = TestData.SENTENCES.stream().map(e -> e.split("\\s+")).flatMap(Arrays::stream).map(String::toLowerCase).distinct().sorted(Comparator.naturalOrder()).toList();

        assertThat(result).contains(
                "and", "are", "capabilities", "concise", "execution", "fun",
                "in", "is", "java", "modern", "parallel", "powerful",
                "programming", "provide", "streams"
        );
    }

    @Test
    @DisplayName("04: Extract distinct product names ordered across all orders")
    void ex04_extractAllDistinctProductsOrdered() {
        // Given: TestData.ORDERS
        // Task: Flatten orders into order items, then map to product names,
        //       remove duplicates, and sort alphabetically.

        List<String> result = null; // TODO: Implement using stream

        result = TestData.ORDERS.stream().map(Order::items).flatMap(List::stream).map(e -> e.product().name()).distinct().sorted().toList();

        assertThat(result).containsExactly(
                "Arabica Whole Beans", "Ceramic Mug", "Clean Code",
                "Developer Cotton T-Shirt", "Effective Java",
                "MacBook Pro", "Sony WH-1000XM5", "iPhone 15"
        );
    }

    @Test
    @DisplayName("05: Unwrap Optional stream cleanly")
    void ex05_optionalStreamUnwrap() {
        List<Optional<String>> optionals = List.of(
                Optional.of("Java"),
                Optional.empty(),
                Optional.of("Streams"),
                Optional.empty(),
                Optional.of("API")
        );

        // Task: Filter present Optionals and unwrap values using Optional::stream in flatMap

        List<String> result = null; // TODO: Implement using stream
        result = optionals.stream().flatMap(Optional::stream).toList();

        assertThat(result).containsExactly("Java", "Streams", "API");
    }

    @Test
    @DisplayName("06: Sum numbers using reduce")
    void ex06_sumWithReduce() {
        // Given: TestData.NUMBERS (1..10)
        // Task: Sum all numbers using the reduce(identity, accumulator) method

        int sum = 0; // TODO: Implement using stream

        sum = TestData.NUMBERS.stream().reduce(0, Integer::sum);

        assertThat(sum).isEqualTo(55);
    }

    @Test
    @DisplayName("07: Multiply all elements using reduce")
    void ex07_multiplyAllNumbers() {
        List<Integer> factors = List.of(1, 2, 3, 4, 5);

        // Task: Compute product of all factors (1 * 2 * 3 * 4 * 5 = 120) using reduce

        int product = 0; // TODO: Implement using stream
        product = factors.stream().reduce((a,b) -> a*b).orElse(0);

        assertThat(product).isEqualTo(120);
    }

    @Test
    @DisplayName("08: Find employee with highest salary using reduce or max")
    void ex08_findEmployeeWithMaxSalary() {
        // Given: TestData.EMPLOYEES
        // Task: Find the employee who earns the highest salary

        Optional<Employee> topEarner = Optional.empty(); // TODO: Implement using stream

        topEarner = TestData.EMPLOYEES.stream().max(Comparator.comparing(Employee::salary));

        assertThat(topEarner).isPresent();
        assertThat(topEarner.get().name()).isEqualTo("Charlie Brown");
        assertThat(topEarner.get().salary()).isEqualTo(120000.0);
    }

    @Test
    @DisplayName("09: Calculate total revenue of all DELIVERED orders")
    void ex09_calculateTotalRevenueDeliveredOrders() {
        // Given: TestData.ORDERS
        // Task: Calculate total amount spent on all DELIVERED orders using reduce

        double totalDeliveredRevenue = 0.0; // TODO: Implement using stream

        totalDeliveredRevenue = TestData.ORDERS.stream().filter(e -> e.status() == OrderStatus.DELIVERED)
                .mapToDouble(Order::getTotalAmount)
                        .reduce(0.0, Double::sum);

        assertThat(totalDeliveredRevenue).isEqualTo(9223.0);
    }

    @Test
    @DisplayName("10: Join strings with delimiter using reduce (without Collectors.joining)")
    void ex10_customStringJoinWithReduce() {
        List<String> words = List.of("Java", "Streams", "Are", "Cool");

        // Task: Combine strings into a single string separated by " - " using reduce((a, b) -> ...)

        Optional<String> joined = Optional.empty(); // TODO: Implement using stream

        joined = words.stream().reduce((a,b) -> a+" - "+b);

        assertThat(joined).isPresent().contains("Java - Streams - Are - Cool");
    }

    @Test
    @DisplayName("11: Find longest word using reduce")
    void ex11_findLongestWord() {
        List<String> words = List.of("cat", "elephant", "hippopotamus", "dog", "tiger");

        // Task: Find the word with the maximum length using reduce

        Optional<String> longest = Optional.empty(); // TODO: Implement using stream

        longest = words.stream().max(Comparator.comparing(String::length));

        assertThat(longest).isPresent().contains("hippopotamus");
    }

    @Test
    @DisplayName("12: Calculate total salary for IT department using reduction")
    void ex12_totalSalaryInITDepartment() {
        // Given: TestData.EMPLOYEES
        // Task: Filter IT department employees, map to salary, and sum with reduce

        double totalITSalary = 0.0; // TODO: Implement using stream
        totalITSalary = TestData.EMPLOYEES.stream().filter(e -> e.department().equals("IT")).mapToDouble(Employee::salary).reduce(0.0, Double::sum);

        assertThat(totalITSalary).isEqualTo(400000.0);
    }

    @Test
    @DisplayName("13: Flatten map of lists into a distinct, sorted list of skills")
    void ex13_flattenMapOfLists() {
        Map<String, List<String>> deptSkills = Map.of(
                "IT", List.of("Java", "Docker", "Kubernetes"),
                "Finance", List.of("Excel", "Java", "Accounting"),
                "HR", List.of("Recruiting", "Excel", "Compensation")
        );

        // Task: Extract all skills from all departments, remove duplicates, and sort alphabetically

        List<String> result = null; // TODO: Implement using stream

        result = deptSkills.values().stream().flatMap(List::stream).distinct().sorted().toList();

        assertThat(result).containsExactly(
                "Accounting", "Compensation", "Docker", "Excel", "Java", "Kubernetes", "Recruiting"
        );
    }

    @Test
    @DisplayName("14: Find second highest distinct number in a list")
    void ex14_findSecondHighestNumber() {
        List<Integer> numbers = List.of(15, 22, 8, 45, 90, 45, 90, 31, 74);

        // Task: Find the second highest unique number in the list using streams.
        // E.g., unique sorted descending: [90, 74, 45, 31, 22, 15, 8] -> second is 74.

        Optional<Integer> secondHighest = Optional.empty(); // TODO: Implement using stream

        secondHighest = numbers.stream().distinct().sorted(Comparator.reverseOrder()).skip(1).findFirst();

        assertThat(secondHighest).isPresent().contains(74);
    }

    @Test
    @DisplayName("15: Generate Cartesian product of colors and sizes")
    void ex15_cartesianProduct() {
        List<String> colors = List.of("Red", "Blue");
        List<String> sizes = List.of("S", "M", "L");

        // Task: Use flatMap to combine each color with each size as "Color-Size"
        // Result order: ["Red-S", "Red-M", "Red-L", "Blue-S", "Blue-M", "Blue-L"]

        List<String> combinations = null; // TODO: Implement using stream

        combinations = colors.stream().flatMap(e -> sizes.stream().map(i -> e+"-"+i)).toList();

        assertThat(combinations).containsExactly(
                "Red-S", "Red-M", "Red-L", "Blue-S", "Blue-M", "Blue-L"
        );
    }

    @Test
    @DisplayName("16: Compute total character count using three-argument reduce")
    void ex16_threeArgumentReduce() {
        List<String> words = List.of("apple", "banana", "cherry", "date");

        // Task: Compute total length of all words using Stream<String>.reduce(U identity, BiFunction accumulator, BinaryOperator combiner)
        // Lengths: 5 + 6 + 6 + 4 = 21

        int totalLength = 0; // TODO: Implement using 3-arg reduce

        totalLength = words.stream().reduce(0, (sum, word) -> sum + word.length(), Integer::sum );
        //words.stream().mapToInt(String::length).sum();

        assertThat(totalLength).isEqualTo(21);
    }

    @Test
    @DisplayName("17: Find shortest string with alphabetical tie-breaker")
    void ex17_findShortestStringWithTieBreaker() {
        List<String> words = List.of("banana", "pear", "fig", "kiwi", "plum",  "apple", "cat");

        // Task: Find the shortest word. If there are multiple words with the same minimum length,
        //       pick the one that appears first alphabetically (e.g. between "fig" and "cat", "cat" wins).

        Optional<String> shortest = Optional.empty(); // TODO: Implement using stream

        shortest = words.stream().sorted().min(Comparator.comparing(String::length));

        assertThat(shortest).isPresent().contains("cat");
    }

    @Test
    @DisplayName("18: Extract present postal codes from nested Optionals")
    void ex18_filterAndUnwrapNestedOptionals() {
        record Address(String city, Optional<String> postalCode) {}
        record UserProfile(String username, Optional<Address> address) {}

        List<UserProfile> profiles = List.of(
                new UserProfile("alice", Optional.of(new Address("New York", Optional.of("10001")))),
                new UserProfile("bob", Optional.of(new Address("Chicago", Optional.empty()))),
                new UserProfile("charlie", Optional.empty()),
                new UserProfile("diana", Optional.of(new Address("Austin", Optional.of("73301")))),
                new UserProfile("evan", Optional.of(new Address("San Francisco", Optional.of("94102"))))
        );

        // Task: From the profiles list, navigate nested Optionals to collect all existing postal codes,
        //       sorted alphabetically.

        List<String> postalCodes = null; // TODO: Implement using stream

        postalCodes = profiles.stream().map(e -> e.address()).flatMap(Optional::stream).map(e -> e.postalCode()).flatMap(Optional::stream).toList();

        assertThat(postalCodes).containsExactly("10001", "73301", "94102");
    }
}
