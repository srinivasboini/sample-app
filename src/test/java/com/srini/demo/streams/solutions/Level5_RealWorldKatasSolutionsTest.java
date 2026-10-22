package com.srini.demo.streams.solutions;

import com.srini.demo.streams.TestData;
import com.srini.demo.streams.model.*;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level 5 Solutions: Real-World Business Katas")
public class Level5_RealWorldKatasSolutionsTest {

    @Test
    @DisplayName("01: Top-selling product by quantity across DELIVERED orders")
    void ex01_topSellingProductByQuantity() {
        Product topProduct = TestData.ORDERS.stream()
                .filter(order -> order.status() == OrderStatus.DELIVERED)
                .flatMap(order -> order.items().stream())
                .collect(groupingBy(
                        OrderItem::product,
                        summingInt(OrderItem::quantity)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();

        assertThat(topProduct).isEqualTo(TestData.PROD_COFFEE_BEANS);
    }

    @Test
    @DisplayName("02: Find top spending customer on DELIVERED orders")
    void ex02_topSpendingCustomer() {
        Customer topCustomer = TestData.ORDERS.stream()
                .filter(order -> order.status() == OrderStatus.DELIVERED)
                .collect(groupingBy(
                        Order::customer,
                        summingDouble(Order::getTotalAmount)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();

        assertThat(topCustomer).isEqualTo(TestData.CUST_MICHAEL);
    }

    @Test
    @DisplayName("03: Monthly revenue trend for DELIVERED orders")
    void ex03_monthlyRevenueTrend() {
        Map<YearMonth, Double> monthlyRevenue = TestData.ORDERS.stream()
                .filter(order -> order.status() == OrderStatus.DELIVERED)
                .collect(groupingBy(
                        order -> YearMonth.from(order.orderDate()),
                        summingDouble(Order::getTotalAmount)
                ));

        assertThat(monthlyRevenue).containsEntry(YearMonth.of(2024, 1), 2625.0);
        assertThat(monthlyRevenue).containsEntry(YearMonth.of(2024, 2), 2198.0);
        assertThat(monthlyRevenue).containsEntry(YearMonth.of(2024, 3), 4400.0);
    }

    @Test
    @DisplayName("04: Find customers who have made zero orders")
    void ex04_findCustomersWithZeroOrders() {
        Set<Customer> orderingCustomers = TestData.ORDERS.stream()
                .map(Order::customer)
                .collect(toSet());

        List<Customer> inactiveCustomers = TestData.CUSTOMERS.stream()
                .filter(c -> !orderingCustomers.contains(c))
                .toList();

        assertThat(inactiveCustomers).containsExactly(TestData.CUST_TOM);
    }

    @Test
    @DisplayName("05: Department with highest total salary expense")
    void ex05_departmentWithHighestSalaryExpense() {
        String highestCostDept = TestData.EMPLOYEES.stream()
                .collect(groupingBy(
                        Employee::department,
                        summingDouble(Employee::salary)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();

        assertThat(highestCostDept).isEqualTo("IT");
    }

    @Test
    @DisplayName("06: Most frequent skill across all employees")
    void ex06_mostFrequentSkill() {
        String topSkill = TestData.EMPLOYEES.stream()
                .flatMap(e -> e.skills().stream())
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();

        assertThat(topSkill).isEqualTo("Java");
    }

    @Test
    @DisplayName("07: Group words by anagrams")
    void ex07_anagramGrouping() {
        Map<String, List<String>> anagramMap = TestData.ANAGRAM_WORDS.stream()
                .collect(groupingBy(word -> {
                    char[] chars = word.toCharArray();
                    Arrays.sort(chars);
                    return new String(chars);
                }));

        assertThat(anagramMap.get("eilnst")).containsExactlyInAnyOrder("listen", "silent", "enlist");
        assertThat(anagramMap.get("art")).containsExactlyInAnyOrder("rat", "tar", "art");
        assertThat(anagramMap.get("eilv")).containsExactlyInAnyOrder("evil", "vile", "live");
        assertThat(anagramMap.get("ehllo")).containsExactly("hello");
    }

    @Test
    @DisplayName("08: Word frequency count in text paragraph")
    void ex08_wordFrequencyCount() {
        String text = "Java is great and Streams make Java even greater. Streams are concise.";

        Map<String, Long> frequencyMap = Arrays.stream(text.split("\\s+"))
                .map(w -> w.replaceAll("[^a-zA-Z]", "").toLowerCase())
                .filter(w -> !w.isBlank())
                .collect(groupingBy(Function.identity(), counting()));

        assertThat(frequencyMap).containsEntry("java", 2L);
        assertThat(frequencyMap).containsEntry("streams", 2L);
        assertThat(frequencyMap).containsEntry("great", 1L);
        assertThat(frequencyMap).containsEntry("greater", 1L);
    }

    @Test
    @DisplayName("09: Order count by status")
    void ex09_orderStatusCount() {
        Map<OrderStatus, Long> statusCounts = TestData.ORDERS.stream()
                .collect(groupingBy(Order::status, counting()));

        assertThat(statusCounts).containsEntry(OrderStatus.DELIVERED, 4L);
        assertThat(statusCounts).containsEntry(OrderStatus.CANCELLED, 1L);
        assertThat(statusCounts).containsEntry(OrderStatus.PENDING, 1L);
    }

    @Test
    @DisplayName("10: Net transaction balance by year (CREDIT - DEBIT)")
    void ex10_netTransactionBalanceByYear() {
        Map<Integer, Double> balanceByYear = TestData.TRANSACTIONS.stream()
                .collect(groupingBy(
                        Transaction::year,
                        summingDouble(t -> t.type() == Transaction.Type.CREDIT ? t.value() : -t.value())
                ));

        assertThat(balanceByYear).containsEntry(2022, 750.0);
        assertThat(balanceByYear).containsEntry(2023, 350.0);
        assertThat(balanceByYear).containsEntry(2024, 1700.0);
    }

    @Test
    @DisplayName("11: Total revenue grouped by CustomerTier on DELIVERED orders")
    void ex11_totalRevenueByCustomerTier() {
        Map<CustomerTier, Double> revenueByTier = TestData.ORDERS.stream()
                .filter(order -> order.status() == OrderStatus.DELIVERED)
                .collect(groupingBy(
                        order -> order.customer().tier(),
                        summingDouble(Order::getTotalAmount)
                ));

        assertThat(revenueByTier).isNotNull();
        assertThat(revenueByTier).containsEntry(CustomerTier.PLATINUM, 6598.0);
        assertThat(revenueByTier).containsEntry(CustomerTier.GOLD, 2550.0);
        assertThat(revenueByTier).containsEntry(CustomerTier.REGULAR, 75.0);
    }

    @Test
    @DisplayName("12: First non-repeated character in string (order-preserving)")
    void ex12_firstNonRepeatedCharacter() {
        String input = "swiss";

        Optional<Character> firstUniqueChar = input.chars()
                .mapToObj(c -> (char) c)
                .collect(groupingBy(Function.identity(), LinkedHashMap::new, counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1L)
                .map(Map.Entry::getKey)
                .findFirst();

        assertThat(firstUniqueChar).isPresent().contains('w');
    }

    @Test
    @DisplayName("13: Top 2 highest-paid employees per department")
    void ex13_top2HighestPaidEmployeesPerDepartment() {
        Map<String, List<Employee>> top2ByDept = TestData.EMPLOYEES.stream()
                .collect(groupingBy(
                        Employee::department,
                        collectingAndThen(
                                toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparingDouble(Employee::salary).reversed())
                                        .limit(2)
                                        .toList()
                        )
                ));

        assertThat(top2ByDept).isNotNull();
        assertThat(top2ByDept.get("IT")).extracting(Employee::name)
                .containsExactly("Charlie Brown", "Julia Roberts");
        assertThat(top2ByDept.get("Finance")).extracting(Employee::name)
                .containsExactly("Hannah Abbott", "Evan Wright");
        assertThat(top2ByDept.get("Sales")).extracting(Employee::name)
                .containsExactly("George Clark", "Bob Smith");
        assertThat(top2ByDept.get("HR")).extracting(Employee::name)
                .containsExactly("Diana Prince", "Ian Malcolm");
    }

    @Test
    @DisplayName("14: Find cities where employees from more than one department operate")
    void ex14_findCitiesWithMultipleDepartments() {
        List<String> citiesWithMultipleDepts = TestData.EMPLOYEES.stream()
                .collect(groupingBy(
                        Employee::city,
                        mapping(Employee::department, toSet())
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();

        assertThat(citiesWithMultipleDepts).containsExactly("Austin", "Chicago", "New York");
    }

    @Test
    @DisplayName("15: Order delivery fulfillment rate percentage")
    void ex15_orderDeliveryPercentage() {
        double deliveryPercentage = TestData.ORDERS.stream()
                .mapToDouble(order -> order.status() == OrderStatus.DELIVERED ? 100.0 : 0.0)
                .average()
                .orElse(0.0);

        assertThat(deliveryPercentage).isCloseTo(66.67, Offset.offset(0.01));
    }

    @Test
    @DisplayName("16: Average order value by customer on DELIVERED orders")
    void ex16_averageOrderValueByCustomer() {
        Map<String, Double> avgOrderValueByCustomer = TestData.ORDERS.stream()
                .filter(order -> order.status() == OrderStatus.DELIVERED)
                .collect(groupingBy(
                        order -> order.customer().name(),
                        averagingDouble(Order::getTotalAmount)
                ));

        assertThat(avgOrderValueByCustomer).isNotNull();
        assertThat(avgOrderValueByCustomer).containsEntry("Michael Scott", 3299.0);
        assertThat(avgOrderValueByCustomer).containsEntry("Emily Davis", 2550.0);
        assertThat(avgOrderValueByCustomer).containsEntry("John Doe", 75.0);
    }
}
