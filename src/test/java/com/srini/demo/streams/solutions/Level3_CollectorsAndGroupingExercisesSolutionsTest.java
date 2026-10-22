package com.srini.demo.streams.solutions;

import com.srini.demo.streams.TestData;
import com.srini.demo.streams.model.Employee;
import com.srini.demo.streams.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level 3 Solutions: Collectors, Grouping, and Partitioning")
public class Level3_CollectorsAndGroupingExercisesSolutionsTest {

    @Test
    @DisplayName("01: Collect employee names into a TreeSet")
    void ex01_collectToTreeSet() {
        TreeSet<String> sortedNames = TestData.EMPLOYEES.stream()
                .map(Employee::name)
                .collect(toCollection(TreeSet::new));

        assertThat(sortedNames).containsExactly(
                "Alice Johnson", "Bob Smith", "Charlie Brown", "Diana Prince",
                "Evan Wright", "Fiona Gallagher", "George Clark", "Hannah Abbott",
                "Ian Malcolm", "Julia Roberts"
        );
    }

    @Test
    @DisplayName("02: Collect employees into Map<Long, Employee> keyed by ID")
    void ex02_collectToMapById() {
        Map<Long, Employee> map = TestData.EMPLOYEES.stream()
                .collect(toMap(Employee::id, e -> e));

        assertThat(map).hasSize(10);
        assertThat(map.get(1L)).isEqualTo(TestData.ALICE);
        assertThat(map.get(3L)).isEqualTo(TestData.CHARLIE);
    }

    @Test
    @DisplayName("03: Map department to formatted employee string (handle duplicates with merge function)")
    void ex03_collectToMapHandlingDuplicates() {
        Map<String, String> highestPaidPerDept = TestData.EMPLOYEES.stream()
                .collect(toMap(
                        Employee::department,
                        e -> e.name() + " ($" + (int) e.salary() + ")",
                        (e1, e2) -> e1.compareTo(e2) > 0 ? e1 : e2
                ));

        assertThat(highestPaidPerDept).containsKeys("IT", "HR", "Sales", "Finance");
    }

    @Test
    @DisplayName("04: Join employee names into formatted string with prefix, delimiter, suffix")
    void ex04_joiningFormattedString() {
        String result = TestData.NAMES.stream()
                .collect(joining(", ", "Employees: [", "]"));

        assertThat(result).isEqualTo("Employees: [Alice, Bob, Charlie, David, Eva, Frank, Grace, Hannah]");
    }

    @Test
    @DisplayName("05: Group employees by department")
    void ex05_groupByDepartment() {
        Map<String, List<Employee>> byDept = TestData.EMPLOYEES.stream()
                .collect(groupingBy(Employee::department));

        assertThat(byDept.get("IT")).hasSize(4);
        assertThat(byDept.get("HR")).hasSize(2);
        assertThat(byDept.get("Sales")).hasSize(2);
        assertThat(byDept.get("Finance")).hasSize(2);
    }

    @Test
    @DisplayName("06: Count employees per department")
    void ex06_countEmployeesPerDepartment() {
        Map<String, Long> countByDept = TestData.EMPLOYEES.stream()
                .collect(groupingBy(Employee::department, counting()));

        assertThat(countByDept).containsEntry("IT", 4L);
        assertThat(countByDept).containsEntry("HR", 2L);
        assertThat(countByDept).containsEntry("Sales", 2L);
        assertThat(countByDept).containsEntry("Finance", 2L);
    }

    @Test
    @DisplayName("07: Average salary per department")
    void ex07_averageSalaryPerDepartment() {
        Map<String, Double> avgSalary = TestData.EMPLOYEES.stream()
                .collect(groupingBy(Employee::department, averagingDouble(Employee::salary)));

        assertThat(avgSalary.get("IT")).isEqualTo(100000.0);
        assertThat(avgSalary.get("Sales")).isEqualTo(75000.0);
        assertThat(avgSalary.get("HR")).isEqualTo(68500.0);
        assertThat(avgSalary.get("Finance")).isEqualTo(97500.0);
    }

    @Test
    @DisplayName("08: Find highest paid employee in each department (collectingAndThen)")
    void ex08_highestPaidPerDepartment() {
        Map<String, Employee> topByDept = TestData.EMPLOYEES.stream()
                .collect(groupingBy(
                        Employee::department,
                        collectingAndThen(
                                maxBy(Comparator.comparing(Employee::salary)),
                                Optional::get
                        )
                ));

        assertThat(topByDept.get("IT").name()).isEqualTo("Charlie Brown");
        assertThat(topByDept.get("Finance").name()).isEqualTo("Hannah Abbott");
        assertThat(topByDept.get("Sales").name()).isEqualTo("George Clark");
        assertThat(topByDept.get("HR").name()).isEqualTo("Diana Prince");
    }

    @Test
    @DisplayName("09: Group employees by department and extract only employee names")
    void ex09_employeeNamesByDepartment() {
        Map<String, List<String>> namesByDept = TestData.EMPLOYEES.stream()
                .collect(groupingBy(
                        Employee::department,
                        mapping(Employee::name, toList())
                ));

        assertThat(namesByDept.get("Sales")).containsExactly("Bob Smith", "George Clark");
        assertThat(namesByDept.get("HR")).containsExactly("Diana Prince", "Ian Malcolm");
    }

    @Test
    @DisplayName("10: Partition employees by salary >= 90,000")
    void ex10_partitionBySalaryThreshold() {
        Map<Boolean, List<Employee>> partitioned = TestData.EMPLOYEES.stream()
                .collect(partitioningBy(e -> e.salary() >= 90000));

        assertThat(partitioned.get(true)).hasSize(5);
        assertThat(partitioned.get(false)).hasSize(5);
    }

    @Test
    @DisplayName("11: Partition employees by age > 30 and count")
    void ex11_partitionAndCount() {
        Map<Boolean, Long> countByAge = TestData.EMPLOYEES.stream()
                .collect(partitioningBy(e -> e.age() > 30, counting()));

        assertThat(countByAge).containsEntry(true, 6L);
        assertThat(countByAge).containsEntry(false, 4L);
    }

    @Test
    @DisplayName("12: Multi-level grouping: Department -> Gender -> List<Employee>")
    void ex12_multiLevelGrouping() {
        Map<String, Map<String, List<Employee>>> multiLevel = TestData.EMPLOYEES.stream()
                .collect(groupingBy(
                        Employee::department,
                        groupingBy(Employee::gender)
                ));

        assertThat(multiLevel.get("IT").get("Female")).hasSize(3);
        assertThat(multiLevel.get("IT").get("Male")).hasSize(1);
    }

    @Test
    @DisplayName("13: Total inventory value per product category")
    void ex13_sumTotalStockPerCategory() {
        Map<String, Double> totalValueByCategory = TestData.PRODUCTS.stream()
                .collect(groupingBy(
                        Product::category,
                        summingDouble(p -> p.price() * p.stock())
                ));

        assertThat(totalValueByCategory.get("Electronics")).isEqualTo(71975.0);
        assertThat(totalValueByCategory.get("Books")).isEqualTo(7700.0);
        assertThat(totalValueByCategory.get("Groceries")).isEqualTo(6250.0);
        assertThat(totalValueByCategory.get("Clothing")).isEqualTo(1500.0);
    }

    @Test
    @DisplayName("14: Collectors.teeing: Calculate min and max salary employee in a single pass (Java 12+)")
    void ex14_collectorsTeeingMinMax() {
        record MinMaxEmployees(Employee lowest, Employee highest) {}

        MinMaxEmployees result = TestData.EMPLOYEES.stream()
                .collect(teeing(
                        collectingAndThen(minBy(Comparator.comparing(Employee::salary)), Optional::get),
                        collectingAndThen(maxBy(Comparator.comparing(Employee::salary)), Optional::get),
                        MinMaxEmployees::new
                ));

        assertThat(result.lowest().name()).isEqualTo("Bob Smith");
        assertThat(result.lowest().salary()).isEqualTo(60000.0);
        assertThat(result.highest().name()).isEqualTo("Charlie Brown");
        assertThat(result.highest().salary()).isEqualTo(120000.0);
    }

    @Test
    @DisplayName("15: Summarizing salary statistics by department")
    void ex15_groupDepartmentToSalaryStats() {
        Map<String, DoubleSummaryStatistics> statsByDept = TestData.EMPLOYEES.stream()
                .collect(groupingBy(
                        Employee::department,
                        summarizingDouble(Employee::salary)
                ));

        assertThat(statsByDept).isNotNull();
        DoubleSummaryStatistics itStats = statsByDept.get("IT");
        assertThat(itStats.getCount()).isEqualTo(4);
        assertThat(itStats.getSum()).isEqualTo(400000.0);
        assertThat(itStats.getAverage()).isEqualTo(100000.0);
        assertThat(itStats.getMax()).isEqualTo(120000.0);
        assertThat(itStats.getMin()).isEqualTo(80000.0);
    }

    @Test
    @DisplayName("16: Partition employees by salary >= 90,000 and find the oldest employee in each partition")
    void ex16_partitionAndFindOldestInEach() {
        Map<Boolean, Employee> oldestBySalaryBracket = TestData.EMPLOYEES.stream()
                .collect(partitioningBy(
                        e -> e.salary() >= 90000,
                        collectingAndThen(
                                maxBy(Comparator.comparingInt(Employee::age)),
                                Optional::get
                        )
                ));

        assertThat(oldestBySalaryBracket).isNotNull();
        assertThat(oldestBySalaryBracket.get(true).name()).isEqualTo("Hannah Abbott");
        assertThat(oldestBySalaryBracket.get(true).age()).isEqualTo(45);
        assertThat(oldestBySalaryBracket.get(false).name()).isEqualTo("Bob Smith");
        assertThat(oldestBySalaryBracket.get(false).age()).isEqualTo(35);
    }

    @Test
    @DisplayName("17: Character frequency map (ignoring spaces)")
    void ex17_characterFrequencyMap() {
        String input = "interview stream mastery";

        Map<Character, Long> charFrequencies = input.chars()
                .filter(ch -> ch != ' ')
                .mapToObj(ch -> (char) ch)
                .collect(groupingBy(Function.identity(), counting()));

        assertThat(charFrequencies).isNotNull();
        assertThat(charFrequencies.get('e')).isEqualTo(4L);
        assertThat(charFrequencies.get('r')).isEqualTo(3L);
        assertThat(charFrequencies.get('t')).isEqualTo(3L);
        assertThat(charFrequencies.get('a')).isEqualTo(2L);
        assertThat(charFrequencies.get('s')).isEqualTo(2L);
        assertThat(charFrequencies.containsKey(' ')).isFalse();
    }

    @Test
    @DisplayName("18: Group employee names by custom age bracket")
    void ex18_groupByAgeBracket() {
        Map<String, List<String>> byAgeBracket = TestData.EMPLOYEES.stream()
                .collect(groupingBy(
                        e -> {
                            if (e.age() < 30) return "Under 30";
                            else if (e.age() <= 39) return "30-39";
                            else return "40+";
                        },
                        mapping(Employee::name, toList())
                ));

        assertThat(byAgeBracket).isNotNull();
        assertThat(byAgeBracket.get("Under 30")).containsExactlyInAnyOrder(
                "Alice Johnson", "Evan Wright", "Fiona Gallagher", "Ian Malcolm"
        );
        assertThat(byAgeBracket.get("30-39")).containsExactlyInAnyOrder(
                "Bob Smith", "Diana Prince", "George Clark", "Julia Roberts"
        );
        assertThat(byAgeBracket.get("40+")).containsExactlyInAnyOrder(
                "Charlie Brown", "Hannah Abbott"
        );
    }

    @Test
    @DisplayName("19: Count item frequencies and collect into a Map sorted descending by frequency")
    void ex19_collectToLinkedHashMapSortedByFrequency() {
        List<String> items = List.of(
                "apple", "banana", "apple", "orange", "banana",
                "apple", "pear", "orange", "banana", "banana"
        );

        Map<String, Long> sortedFrequencies = items.stream()
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        assertThat(sortedFrequencies).isNotNull();
        assertThat(new ArrayList<>(sortedFrequencies.keySet())).containsExactly("banana", "apple", "orange", "pear");
        assertThat(new ArrayList<>(sortedFrequencies.values())).containsExactly(4L, 3L, 2L, 1L);
    }

    @Test
    @DisplayName("20: Collectors.filtering: Group employees by department filtering high earners (Java 9+)")
    void ex20_groupingByFilteringJava9() {
        Map<String, List<Employee>> highEarnersByDept = TestData.EMPLOYEES.stream()
                .collect(groupingBy(
                        Employee::department,
                        filtering(e -> e.salary() > 80000, toList())
                ));

        assertThat(highEarnersByDept).isNotNull();
        assertThat(highEarnersByDept.get("HR")).isEmpty();
        assertThat(highEarnersByDept.get("IT")).hasSize(3);
        assertThat(highEarnersByDept.get("Sales")).hasSize(1);
        assertThat(highEarnersByDept.get("Finance")).hasSize(2);
    }

    @Test
    @DisplayName("21: Collectors.teeing: Calculate total sum and average of double list in single pass")
    void ex21_teeingTotalSumAndAverage() {
        record Summary(double sum, double average) {}
        List<Double> numbers = List.of(100.0, 200.0, 300.0, 400.0);

        Summary summary = numbers.stream()
                .collect(teeing(
                        summingDouble(Double::doubleValue),
                        averagingDouble(Double::doubleValue),
                        Summary::new
                ));

        assertThat(summary).isNotNull();
        assertThat(summary.sum()).isEqualTo(1000.0);
        assertThat(summary.average()).isEqualTo(250.0);
    }
}
