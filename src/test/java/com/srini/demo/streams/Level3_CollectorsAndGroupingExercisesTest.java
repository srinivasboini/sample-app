package com.srini.demo.streams;

import com.srini.demo.streams.model.Employee;
import com.srini.demo.streams.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * LEVEL 3: COLLECTORS, GROUPING & PARTITIONING
 * ════════════════════════════════════════════════════════════════════════════
 * Focus:
 *  - Collectors.toCollection, toMap (with key collision resolution)
 *  - Collectors.joining
 *  - Collectors.groupingBy (simple, counting, averaging, mapping, collectingAndThen)
 *  - Collectors.partitioningBy
 *  - Multi-level grouping & Downstream aggregations
 *  - Java 9 Collectors.filtering
 *  - Java 12 Collectors.teeing
 *
 * Instructions:
 *  1. Replace the placeholder / TODO with your Java Stream pipeline.
 *  2. Run the test in your IDE or via terminal:
 *     ./mvnw test -Dtest=Level3_CollectorsAndGroupingExercisesTest#ex01_collectToTreeSet
 *  3. Check 'solutions/Level3_CollectorsAndGroupingExercisesSolutionsTest.java' if you get stuck!
 */
@DisplayName("Level 3: Collectors, Grouping, and Partitioning")
public class Level3_CollectorsAndGroupingExercisesTest {

    @Test
    @DisplayName("01: Collect employee names into a TreeSet")
    void ex01_collectToTreeSet() {
        // Given: TestData.EMPLOYEES
        // Task: Collect names of employees into a sorted TreeSet

        TreeSet<String> sortedNames = null; // TODO: Implement using stream

        sortedNames = TestData.EMPLOYEES.stream().map(Employee::name).collect(Collectors.toCollection(TreeSet::new));

        assertThat(sortedNames).containsExactly(
                "Alice Johnson", "Bob Smith", "Charlie Brown", "Diana Prince",
                "Evan Wright", "Fiona Gallagher", "George Clark", "Hannah Abbott",
                "Ian Malcolm", "Julia Roberts"
        );
    }

    @Test
    @DisplayName("02: Collect employees into Map<Long, Employee> keyed by ID")
    void ex02_collectToMapById() {
        // Given: TestData.EMPLOYEES
        // Task: Transform employees into a Map where key = employee id and value = Employee

        Map<Long, Employee> map = null; // TODO: Implement using stream

        map = TestData.EMPLOYEES.stream().collect(Collectors.toMap(Employee::id, Function.identity()));
        assertThat(map).hasSize(10);
        assertThat(map.get(1L)).isEqualTo(TestData.ALICE);
        assertThat(map.get(3L)).isEqualTo(TestData.CHARLIE);
    }

    @Test
    @DisplayName("03: Map department to formatted employee string (handle duplicates with merge function)")
    void ex03_collectToMapHandlingDuplicates() {
        // Given: TestData.EMPLOYEES
        // Task: Create a map of department to employee name and formatted salary string "Name ($Salary)".
        // If a department has multiple employees, resolve collision by keeping the one with higher lexicographical value.

        Map<String, String> highestPaidPerDept = null; // TODO: Implement using stream

        highestPaidPerDept = TestData.EMPLOYEES.stream().collect(Collectors.toMap(e -> e.department(), e -> e.name() + " ("+e.salary()+")", (e1,e2) -> e1.compareTo(e2) > 0 ? e1 : e2));

        assertThat(highestPaidPerDept).containsKeys("IT", "HR", "Sales", "Finance");
    }

    @Test
    @DisplayName("04: Join employee names into formatted string with prefix, delimiter, suffix")
    void ex04_joiningFormattedString() {
        // Given: TestData.NAMES
        // Task: Join all names into "Employees: [Alice, Bob, Charlie, David, Eva, Frank, Grace, Hannah]"

        String result = null; // TODO: Implement using stream

        result = TestData.NAMES.stream().collect(Collectors.joining(", ", "Employees: [", "]"));

        assertThat(result).isEqualTo("Employees: [Alice, Bob, Charlie, David, Eva, Frank, Grace, Hannah]");
    }

    @Test
    @DisplayName("05: Group employees by department")
    void ex05_groupByDepartment() {
        // Given: TestData.EMPLOYEES
        // Task: Group employees by their department into Map<String, List<Employee>>

        Map<String, List<Employee>> byDept = null; // TODO: Implement using stream

        byDept = TestData.EMPLOYEES.stream().collect(Collectors.groupingBy(Employee::department));

        assertThat(byDept).isNotNull();
        assertThat(byDept.get("IT")).hasSize(4);
        assertThat(byDept.get("HR")).hasSize(2);
        assertThat(byDept.get("Sales")).hasSize(2);
        assertThat(byDept.get("Finance")).hasSize(2);
    }

    @Test
    @DisplayName("06: Count employees per department")
    void ex06_countEmployeesPerDepartment() {
        // Given: TestData.EMPLOYEES
        // Task: Calculate the number of employees in each department

        Map<String, Long> countByDept = null; // TODO: Implement using stream

        countByDept = TestData.EMPLOYEES.stream().collect(Collectors.groupingBy(Employee::department, Collectors.counting()));

        assertThat(countByDept).containsEntry("IT", 4L);
        assertThat(countByDept).containsEntry("HR", 2L);
        assertThat(countByDept).containsEntry("Sales", 2L);
        assertThat(countByDept).containsEntry("Finance", 2L);
    }

    @Test
    @DisplayName("07: Average salary per department")
    void ex07_averageSalaryPerDepartment() {
        // Given: TestData.EMPLOYEES
        // Task: Calculate the average salary for each department

        Map<String, Double> avgSalary = null; // TODO: Implement using stream

        avgSalary = TestData.EMPLOYEES.stream().collect(Collectors.groupingBy(Employee::department, Collectors.averagingDouble(Employee::salary)));

        assertThat(avgSalary).isNotNull();
        assertThat(avgSalary.get("IT")).isEqualTo(100000.0);
        assertThat(avgSalary.get("Sales")).isEqualTo(75000.0);
        assertThat(avgSalary.get("HR")).isEqualTo(68500.0);
        assertThat(avgSalary.get("Finance")).isEqualTo(97500.0);
    }

    @Test
    @DisplayName("08: Find highest paid employee in each department (collectingAndThen)")
    void ex08_highestPaidPerDepartment() {
        // Given: TestData.EMPLOYEES
        // Task: Find the Employee with the highest salary in each department

        Map<String, Employee> topByDept = null; // TODO: Implement using stream

        topByDept = TestData.EMPLOYEES.stream().collect(Collectors.groupingBy(Employee::department,
                Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparing(Employee::salary)),
                        Optional::get
                )
                ));

        assertThat(topByDept).isNotNull();
        assertThat(topByDept.get("IT").name()).isEqualTo("Charlie Brown");
        assertThat(topByDept.get("Finance").name()).isEqualTo("Hannah Abbott");
        assertThat(topByDept.get("Sales").name()).isEqualTo("George Clark");
        assertThat(topByDept.get("HR").name()).isEqualTo("Diana Prince");
    }

    @Test
    @DisplayName("09: Group employees by department and extract only employee names")
    void ex09_employeeNamesByDepartment() {
        // Given: TestData.EMPLOYEES
        // Task: Map each department to a list of its employee names (List<String>)

        Map<String, List<String>> namesByDept = null; // TODO: Implement using stream

        namesByDept = TestData.EMPLOYEES.stream()
                .collect(Collectors.groupingBy(Employee::department, Collectors.mapping(Employee::name, Collectors.toCollection(ArrayList::new))));

        assertThat(namesByDept).isNotNull();
        assertThat(namesByDept.get("Sales")).containsExactly("Bob Smith", "George Clark");
        assertThat(namesByDept.get("HR")).containsExactly("Diana Prince", "Ian Malcolm");
    }

    @Test
    @DisplayName("10: Partition employees by salary >= 90,000")
    void ex10_partitionBySalaryThreshold() {
        // Given: TestData.EMPLOYEES
        // Task: Partition employees into two groups: those earning >= 90000 and those earning < 90000

        Map<Boolean, List<Employee>> partitioned = null; // TODO: Implement using stream
        partitioned = TestData.EMPLOYEES.stream().collect(Collectors.partitioningBy(e -> e.salary() >= 90000));

        assertThat(partitioned).isNotNull();
        assertThat(partitioned.get(true)).hasSize(5);
        assertThat(partitioned.get(false)).hasSize(5);
    }

    @Test
    @DisplayName("11: Partition employees by age > 30 and count")
    void ex11_partitionAndCount() {
        // Given: TestData.EMPLOYEES
        // Task: Count how many employees are older than 30 vs <= 30

        Map<Boolean, Long> countByAge = null; // TODO: Implement using stream

        countByAge = TestData.EMPLOYEES.stream().collect(Collectors.partitioningBy(e -> e.age() > 30 , Collectors.counting()));

        assertThat(countByAge).containsEntry(true, 6L);
        assertThat(countByAge).containsEntry(false, 4L);
    }

    @Test
    @DisplayName("12: Multi-level grouping: Department -> Gender -> List<Employee>")
    void ex12_multiLevelGrouping() {
        // Given: TestData.EMPLOYEES
        // Task: Group employees by department first, and then within each department by gender

        Map<String, Map<String, List<Employee>>> multiLevel = null; // TODO: Implement using stream

        multiLevel = TestData.EMPLOYEES.stream().collect(Collectors.groupingBy(Employee::department, Collectors.groupingBy(Employee::gender, Collectors.toList())));

        assertThat(multiLevel).isNotNull();
        assertThat(multiLevel.get("IT").get("Female")).hasSize(3);
        assertThat(multiLevel.get("IT").get("Male")).hasSize(1);
    }

    @Test
    @DisplayName("13: Total inventory value per product category")
    void ex13_sumTotalStockPerCategory() {
        // Given: TestData.PRODUCTS
        // Task: Group by product category and calculate total inventory valuation (price * stock)

        Map<String, Double> totalValueByCategory = null; // TODO: Implement using stream // Map<String, List<Product>>

        totalValueByCategory = TestData.PRODUCTS.stream()
                .collect(Collectors.groupingBy(Product::category, Collectors.summingDouble( e -> e.price() * e.stock()))) ;

        assertThat(totalValueByCategory).isNotNull();
        assertThat(totalValueByCategory.get("Electronics")).isEqualTo(71975.0);
        assertThat(totalValueByCategory.get("Books")).isEqualTo(7700.0);
        assertThat(totalValueByCategory.get("Groceries")).isEqualTo(6250.0);
        assertThat(totalValueByCategory.get("Clothing")).isEqualTo(1500.0);
    }

    @Test
    @DisplayName("14: Collectors.teeing: Calculate min and max salary employee in a single pass (Java 12+)")
    void ex14_collectorsTeeingMinMax() {
        record MinMaxEmployees(Employee lowest, Employee highest) {}

        // Given: TestData.EMPLOYEES
        // Task: In a single stream pass, find both the employee with the lowest salary
        //       and the employee with the highest salary using Collectors.teeing(...)

        MinMaxEmployees result = null; // TODO: Implement using Collectors.teeing

        result = TestData.EMPLOYEES.stream().collect(Collectors.teeing(
                Collectors.minBy(Comparator.comparing(Employee::salary)),
                Collectors.maxBy(Comparator.comparing(Employee::salary)),
                (minOpt, maxOpt) -> new MinMaxEmployees(minOpt.get(), maxOpt.get())
        ));

        assertThat(result).isNotNull();
        assertThat(result.lowest().name()).isEqualTo("Bob Smith");
        assertThat(result.lowest().salary()).isEqualTo(60000.0);
        assertThat(result.highest().name()).isEqualTo("Charlie Brown");
        assertThat(result.highest().salary()).isEqualTo(120000.0);
    }

    @Test
    @DisplayName("15: Summarizing salary statistics by department")
    void ex15_groupDepartmentToSalaryStats() {
        // Given: TestData.EMPLOYEES
        // Task: Group employees by department and obtain DoubleSummaryStatistics for salaries in each department

        Map<String, DoubleSummaryStatistics> statsByDept = null; // TODO: Implement using stream

        statsByDept = TestData.EMPLOYEES.stream().collect(Collectors.groupingBy(Employee::department, Collectors.summarizingDouble(Employee::salary)));

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
        // Given: TestData.EMPLOYEES
        // Task: Partition employees into two groups (salary >= 90000 vs < 90000),
        //       and find the oldest employee in each partition.

        Map<Boolean, Employee> oldestBySalaryBracket = null; // TODO: Implement using stream

        oldestBySalaryBracket = TestData.EMPLOYEES.stream().collect(
                Collectors.partitioningBy(e -> e.salary() >= 90000,  Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparing(Employee::age)),
                        Optional::get
                ))
        );

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

        // Task: Count occurrences of each character in input (ignoring spaces ' ') into Map<Character, Long>

        Map<Character, Long> charFrequencies = null; // TODO: Implement using stream

        charFrequencies = input.chars()
                .filter(c -> !Character.isWhitespace(c))
                        .mapToObj(ch -> (char) ch)
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


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
        // Given: TestData.EMPLOYEES
        // Task: Group employee names into age brackets:
        //       - "Under 30" (age < 30)
        //       - "30-39"    (30 <= age <= 39)
        //       - "40+"      (age >= 40)

        Map<String, List<String>> byAgeBracket = null; // TODO: Implement using stream

        byAgeBracket = TestData.EMPLOYEES.stream().collect(Collectors.groupingBy(
                e -> {
                    if(e.age() < 30){
                        return "Under 30";
                    }else if(e.age() >=30 && e.age() <=39){
                        return "30-39";
                    }else{
                        return "40+";
                    }
                },
                Collectors.mapping(Employee::name, Collectors.toCollection(ArrayList::new))
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

        // Task: Count occurrences of each item, then sort entries in descending order of frequency,
        //       collecting into a Map preserving that order (LinkedHashMap).

        Map<String, Long> sortedFrequencies = null; // TODO: Implement using stream
        sortedFrequencies = items.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet()
                                .stream() //Stream<Map.Entry>
                                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                                                .collect(Collectors.toMap(
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
        // Given: TestData.EMPLOYEES
        // Task: Group employees by department, but only include employees with salary > 80,000 in the value list.
        // Departments with no employees earning > 80,000 must still be present in the map with an empty list [].

        Map<String, List<Employee>> highEarnersByDept = null; // TODO: Implement using stream

        highEarnersByDept = TestData.EMPLOYEES.stream().collect(Collectors.groupingBy(Employee::department,
                Collectors.filtering( e -> e.salary() > 80000, Collectors.toCollection(ArrayList::new))
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

        // Task: In a single stream pass using Collectors.teeing, compute both the sum and average
        //       into the Summary record.

        Summary summary = null; // TODO: Implement using Collectors.teeing

        summary = numbers.stream().collect(Collectors.teeing(
                Collectors.summingDouble(Double::doubleValue),
                Collectors.averagingDouble(Double::doubleValue),
                Summary::new
        ));

        assertThat(summary).isNotNull();
        assertThat(summary.sum()).isEqualTo(1000.0);
        assertThat(summary.average()).isEqualTo(250.0);
    }
}
