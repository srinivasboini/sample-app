package com.srini.demo.streams;

import com.srini.demo.streams.model.*;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * LEVEL 5: REAL-WORLD BUSINESS KATAS
 * ════════════════════════════════════════════════════════════════════════════
 * Focus:
 *  - E-commerce and retail analytics (Orders, Products, Customers)
 *  - Financial trends and balance calculations
 *  - HR organizational metrics and cross-department queries
 *  - Text analysis, word frequency, and anagrams
 *  - Multi-criteria data transformations
 *
 * Instructions:
 *  1. Replace the placeholder / TODO with your Java Stream pipeline.
 *  2. Run the test in your IDE or via terminal:
 *     ./mvnw test -Dtest=Level5_RealWorldKatasTest#ex01_topSellingProductByQuantity
 *  3. Check 'solutions/Level5_RealWorldKatasSolutionsTest.java' if you get stuck!
 */
@DisplayName("Level 5: Real-World Business Katas")
public class Level5_RealWorldKatasTest {

    @Test
    @DisplayName("01: Top-selling product by quantity across DELIVERED orders")
    void ex01_topSellingProductByQuantity() {
        // Given: TestData.ORDERS
        // Task: Filter only DELIVERED orders, aggregate the total quantity sold for each Product,
        //       and find the Product with the highest total quantity.

        Product topProduct = null; // TODO: Implement using stream

        assertThat(topProduct).isEqualTo(TestData.PROD_COFFEE_BEANS);
    }

    @Test
    @DisplayName("02: Find top spending customer on DELIVERED orders")
    void ex02_topSpendingCustomer() {
        // Given: TestData.ORDERS
        // Task: Find the Customer who spent the most money across all DELIVERED orders.

        Customer topCustomer = null; // TODO: Implement using stream

        assertThat(topCustomer).isEqualTo(TestData.CUST_MICHAEL);
    }

    @Test
    @DisplayName("03: Monthly revenue trend for DELIVERED orders")
    void ex03_monthlyRevenueTrend() {
        // Given: TestData.ORDERS
        // Task: Calculate total revenue per month (YearMonth) for DELIVERED orders

        Map<YearMonth, Double> monthlyRevenue = null; // TODO: Implement using stream

        assertThat(monthlyRevenue).isNotNull();
        assertThat(monthlyRevenue).containsEntry(YearMonth.of(2024, 1), 2625.0);
        assertThat(monthlyRevenue).containsEntry(YearMonth.of(2024, 2), 2198.0);
        assertThat(monthlyRevenue).containsEntry(YearMonth.of(2024, 3), 4400.0);
    }

    @Test
    @DisplayName("04: Find customers who have made zero orders")
    void ex04_findCustomersWithZeroOrders() {
        // Given: TestData.CUSTOMERS and TestData.ORDERS
        // Task: Find all customers from TestData.CUSTOMERS who have not placed any orders

        List<Customer> inactiveCustomers = null; // TODO: Implement using stream

        assertThat(inactiveCustomers).containsExactly(TestData.CUST_TOM);
    }

    @Test
    @DisplayName("05: Department with highest total salary expense")
    void ex05_departmentWithHighestSalaryExpense() {
        // Given: TestData.EMPLOYEES
        // Task: Find which department spends the highest total amount on employee salaries

        String highestCostDept = null; // TODO: Implement using stream

        assertThat(highestCostDept).isEqualTo("IT");
    }

    @Test
    @DisplayName("06: Most frequent skill across all employees")
    void ex06_mostFrequentSkill() {
        // Given: TestData.EMPLOYEES
        // Task: Find which individual skill appears most frequently among all employees

        String topSkill = null; // TODO: Implement using stream

        assertThat(topSkill).isEqualTo("Java");
    }

    @Test
    @DisplayName("07: Group words by anagrams")
    void ex07_anagramGrouping() {
        // Given: TestData.ANAGRAM_WORDS
        // Task: Group words into lists of anagrams (e.g., words with identical sorted letters)
        // Example: "listen", "silent", "enlist" all sort to "eilnst"

        Map<String, List<String>> anagramMap = null; // TODO: Implement using stream

        assertThat(anagramMap).isNotNull();
        assertThat(anagramMap.get("eilnst")).containsExactlyInAnyOrder("listen", "silent", "enlist");
        assertThat(anagramMap.get("art")).containsExactlyInAnyOrder("rat", "tar", "art");
        assertThat(anagramMap.get("eilv")).containsExactlyInAnyOrder("evil", "vile", "live");
        assertThat(anagramMap.get("ehllo")).containsExactly("hello");
    }

    @Test
    @DisplayName("08: Word frequency count in text paragraph")
    void ex08_wordFrequencyCount() {
        String text = "Java is great and Streams make Java even greater. Streams are concise.";

        // Task: Split text into words, strip non-alphabetic chars, convert to lowercase,
        //       filter blank strings, and count frequency of each word into Map<String, Long>.

        Map<String, Long> frequencyMap = null; // TODO: Implement using stream

        assertThat(frequencyMap).isNotNull();
        assertThat(frequencyMap).containsEntry("java", 2L);
        assertThat(frequencyMap).containsEntry("streams", 2L);
        assertThat(frequencyMap).containsEntry("great", 1L);
        assertThat(frequencyMap).containsEntry("greater", 1L);
    }

    @Test
    @DisplayName("09: Order count by status")
    void ex09_orderStatusCount() {
        // Given: TestData.ORDERS
        // Task: Group and count orders by their OrderStatus

        Map<OrderStatus, Long> statusCounts = null; // TODO: Implement using stream

        assertThat(statusCounts).isNotNull();
        assertThat(statusCounts).containsEntry(OrderStatus.DELIVERED, 4L);
        assertThat(statusCounts).containsEntry(OrderStatus.CANCELLED, 1L);
        assertThat(statusCounts).containsEntry(OrderStatus.PENDING, 1L);
    }

    @Test
    @DisplayName("10: Net transaction balance by year (CREDIT - DEBIT)")
    void ex10_netTransactionBalanceByYear() {
        // Given: TestData.TRANSACTIONS
        // Task: Calculate the net balance for each year, where CREDIT adds value and DEBIT subtracts value

        Map<Integer, Double> balanceByYear = null; // TODO: Implement using stream

        assertThat(balanceByYear).isNotNull();
        assertThat(balanceByYear).containsEntry(2022, 750.0);
        assertThat(balanceByYear).containsEntry(2023, 350.0);
        assertThat(balanceByYear).containsEntry(2024, 1700.0);
    }

    @Test
    @DisplayName("11: Total revenue grouped by CustomerTier on DELIVERED orders")
    void ex11_totalRevenueByCustomerTier() {
        // Given: TestData.ORDERS
        // Task: Calculate total spend across all DELIVERED orders grouped by CustomerTier

        Map<CustomerTier, Double> revenueByTier = null; // TODO: Implement using stream

        assertThat(revenueByTier).isNotNull();
        assertThat(revenueByTier).containsEntry(CustomerTier.PLATINUM, 6598.0);
        assertThat(revenueByTier).containsEntry(CustomerTier.GOLD, 2550.0);
        assertThat(revenueByTier).containsEntry(CustomerTier.REGULAR, 75.0);
    }

    @Test
    @DisplayName("12: First non-repeated character in string (order-preserving)")
    void ex12_firstNonRepeatedCharacter() {
        String input = "swiss";

        // Task: Find the first non-repeated character in input, preserving order.
        // In "swiss", 's' occurs 3 times, 'w' occurs 1 time, 'i' occurs 1 time -> 'w' is first.

        Optional<Character> firstUniqueChar = Optional.empty(); // TODO: Implement using stream

        assertThat(firstUniqueChar).isPresent().contains('w');
    }

    @Test
    @DisplayName("13: Top 2 highest-paid employees per department")
    void ex13_top2HighestPaidEmployeesPerDepartment() {
        // Given: TestData.EMPLOYEES
        // Task: For each department, find the top 2 highest-paid employees, sorted descending by salary

        Map<String, List<Employee>> top2ByDept = null; // TODO: Implement using stream

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
        // Given: TestData.EMPLOYEES
        // Task: Find all distinct cities that have employees working across more than one distinct department,
        //       sorted alphabetically.

        List<String> citiesWithMultipleDepts = null; // TODO: Implement using stream

        assertThat(citiesWithMultipleDepts).containsExactly("Austin", "Chicago", "New York");
    }

    @Test
    @DisplayName("15: Order delivery fulfillment rate percentage")
    void ex15_orderDeliveryPercentage() {
        // Given: TestData.ORDERS
        // Task: Calculate the percentage of orders with status DELIVERED across all orders (4 / 6 = ~66.67%)

        double deliveryPercentage = 0.0; // TODO: Implement using stream

        assertThat(deliveryPercentage).isCloseTo(66.67, Offset.offset(0.01));
    }

    @Test
    @DisplayName("16: Average order value by customer on DELIVERED orders")
    void ex16_averageOrderValueByCustomer() {
        // Given: TestData.ORDERS
        // Task: Calculate average total amount spent per order for each customer on DELIVERED orders,
        //       keyed by customer name (Map<String, Double>).

        Map<String, Double> avgOrderValueByCustomer = null; // TODO: Implement using stream

        assertThat(avgOrderValueByCustomer).isNotNull();
        assertThat(avgOrderValueByCustomer).containsEntry("Michael Scott", 3299.0);
        assertThat(avgOrderValueByCustomer).containsEntry("Emily Davis", 2550.0);
        assertThat(avgOrderValueByCustomer).containsEntry("John Doe", 75.0);
    }
}
