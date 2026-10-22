package com.srini.demo.streams.solutions;

import com.srini.demo.streams.TestData;
import com.srini.demo.streams.model.Employee;
import com.srini.demo.streams.model.Order;
import com.srini.demo.streams.model.OrderItem;
import com.srini.demo.streams.model.OrderStatus;
import com.srini.demo.streams.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level 2 Solutions: FlatMap, Optionals, and Reductions")
public class Level2_FlatMapAndReductionExercisesSolutionsTest {

    @Test
    @DisplayName("01: Flatten nested list of lists into a single list")
    void ex01_flattenListOfLists() {
        List<List<String>> nested = List.of(
                List.of("Spring", "Quarkus"),
                List.of("Micronaut"),
                List.of("Ktor", "Vert.x")
        );

        List<String> result = nested.stream()
                .flatMap(List::stream)
                .toList();

        assertThat(result).containsExactly("Spring", "Quarkus", "Micronaut", "Ktor", "Vert.x");
    }

    @Test
    @DisplayName("02: Extract all unique employee skills sorted alphabetically")
    void ex02_extractAllUniqueSkills() {
        List<String> result = TestData.EMPLOYEES.stream()
                .flatMap(e -> e.skills().stream())
                .distinct()
                .sorted()
                .toList();

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
        List<String> result = TestData.SENTENCES.stream()
                .flatMap(s -> Arrays.stream(s.split("\\s+")))
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .toList();

        assertThat(result).contains(
                "and", "are", "capabilities", "concise", "execution", "fun",
                "in", "is", "java", "modern", "parallel", "powerful",
                "programming", "provide", "streams"
        );
    }

    @Test
    @DisplayName("04: Extract distinct product names ordered across all orders")
    void ex04_extractAllDistinctProductsOrdered() {
        List<String> result = TestData.ORDERS.stream()
                .flatMap(order -> order.items().stream())
                .map(OrderItem::product)
                .map(Product::name)
                .distinct()
                .sorted()
                .toList();

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

        List<String> result = optionals.stream()
                .flatMap(Optional::stream)
                .toList();

        assertThat(result).containsExactly("Java", "Streams", "API");
    }

    @Test
    @DisplayName("06: Sum numbers using reduce")
    void ex06_sumWithReduce() {
        int sum = TestData.NUMBERS.stream()
                .reduce(0, Integer::sum);

        assertThat(sum).isEqualTo(55);
    }

    @Test
    @DisplayName("07: Multiply all elements using reduce")
    void ex07_multiplyAllNumbers() {
        List<Integer> factors = List.of(1, 2, 3, 4, 5);

        int product = factors.stream()
                .reduce(1, (a, b) -> a * b);

        assertThat(product).isEqualTo(120);
    }

    @Test
    @DisplayName("08: Find employee with highest salary using reduce or max")
    void ex08_findEmployeeWithMaxSalary() {
        Optional<Employee> topEarner = TestData.EMPLOYEES.stream()
                .max(Comparator.comparing(Employee::salary));

        assertThat(topEarner).isPresent();
        assertThat(topEarner.get().name()).isEqualTo("Charlie Brown");
        assertThat(topEarner.get().salary()).isEqualTo(120000.0);
    }

    @Test
    @DisplayName("09: Calculate total revenue of all DELIVERED orders")
    void ex09_calculateTotalRevenueDeliveredOrders() {
        double totalDeliveredRevenue = TestData.ORDERS.stream()
                .filter(order -> order.status() == OrderStatus.DELIVERED)
                .map(Order::getTotalAmount)
                .reduce(0.0, Double::sum);

        assertThat(totalDeliveredRevenue).isEqualTo(9223.0);
    }

    @Test
    @DisplayName("10: Join strings with delimiter using reduce (without Collectors.joining)")
    void ex10_customStringJoinWithReduce() {
        List<String> words = List.of("Java", "Streams", "Are", "Cool");

        Optional<String> joined = words.stream()
                .reduce((a, b) -> a + " - " + b);

        assertThat(joined).isPresent().contains("Java - Streams - Are - Cool");
    }

    @Test
    @DisplayName("11: Find longest word using reduce")
    void ex11_findLongestWord() {
        List<String> words = List.of("cat", "elephant", "hippopotamus", "dog", "tiger");

        Optional<String> longest = words.stream()
                .reduce((w1, w2) -> w1.length() >= w2.length() ? w1 : w2);

        assertThat(longest).isPresent().contains("hippopotamus");
    }

    @Test
    @DisplayName("12: Calculate total salary for IT department using reduction")
    void ex12_totalSalaryInITDepartment() {
        double totalITSalary = TestData.EMPLOYEES.stream()
                .filter(e -> "IT".equals(e.department()))
                .map(Employee::salary)
                .reduce(0.0, Double::sum);

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

        List<String> result = deptSkills.values().stream()
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .toList();

        assertThat(result).containsExactly(
                "Accounting", "Compensation", "Docker", "Excel", "Java", "Kubernetes", "Recruiting"
        );
    }

    @Test
    @DisplayName("14: Find second highest distinct number in a list")
    void ex14_findSecondHighestNumber() {
        List<Integer> numbers = List.of(15, 22, 8, 45, 90, 45, 90, 31, 74);

        Optional<Integer> secondHighest = numbers.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst();

        assertThat(secondHighest).isPresent().contains(74);
    }

    @Test
    @DisplayName("15: Generate Cartesian product of colors and sizes")
    void ex15_cartesianProduct() {
        List<String> colors = List.of("Red", "Blue");
        List<String> sizes = List.of("S", "M", "L");

        List<String> combinations = colors.stream()
                .flatMap(color -> sizes.stream().map(size -> color + "-" + size))
                .toList();

        assertThat(combinations).containsExactly(
                "Red-S", "Red-M", "Red-L", "Blue-S", "Blue-M", "Blue-L"
        );
    }

    @Test
    @DisplayName("16: Compute total character count using three-argument reduce")
    void ex16_threeArgumentReduce() {
        List<String> words = List.of("apple", "banana", "cherry", "date");

        int totalLength = words.stream()
                .reduce(0, (sum, word) -> sum + word.length(), Integer::sum);

        assertThat(totalLength).isEqualTo(21);
    }

    @Test
    @DisplayName("17: Find shortest string with alphabetical tie-breaker")
    void ex17_findShortestStringWithTieBreaker() {
        List<String> words = List.of("banana", "pear", "fig", "kiwi", "plum", "apple", "cat");

        Optional<String> shortest = words.stream()
                .min(Comparator.comparingInt(String::length)
                        .thenComparing(Comparator.naturalOrder()));

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

        List<String> postalCodes = profiles.stream()
                .map(UserProfile::address)
                .flatMap(Optional::stream)
                .map(Address::postalCode)
                .flatMap(Optional::stream)
                .sorted()
                .toList();

        assertThat(postalCodes).containsExactly("10001", "73301", "94102");
    }
}
