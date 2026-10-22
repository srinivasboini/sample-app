package com.srini.demo.streams.solutions;

import com.srini.demo.streams.TestData;
import com.srini.demo.streams.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level 4 Solutions: Numeric Streams, Math, and Infinite Streams")
public class Level4_NumericAndInfiniteStreamExercisesSolutionsTest {

    @Test
    @DisplayName("01: Sum numbers from 1 to 100 inclusive using IntStream")
    void ex01_rangeAndRangeClosedSum() {
        int sum = IntStream.rangeClosed(1, 100).sum();

        assertThat(sum).isEqualTo(5050);
    }

    @Test
    @DisplayName("02: Compute summary statistics for employee salaries")
    void ex02_summaryStatistics() {
        DoubleSummaryStatistics stats = TestData.EMPLOYEES.stream()
                .mapToDouble(Employee::salary)
                .summaryStatistics();

        assertThat(stats.getCount()).isEqualTo(10);
        assertThat(stats.getMin()).isEqualTo(60000.0);
        assertThat(stats.getMax()).isEqualTo(120000.0);
        assertThat(stats.getSum()).isEqualTo(882000.0);
        assertThat(stats.getAverage()).isEqualTo(88200.0);
    }

    @Test
    @DisplayName("03: Prime number check using IntStream")
    void ex03_isPrime() {
        IntPredicate isPrime = n -> n > 1 &&
                IntStream.rangeClosed(2, (int) Math.sqrt(n))
                        .noneMatch(d -> n % d == 0);

        assertThat(isPrime.test(29)).isTrue();
        assertThat(isPrime.test(49)).isFalse();
        assertThat(isPrime.test(2)).isTrue();
        assertThat(isPrime.test(1)).isFalse();
    }

    @Test
    @DisplayName("04: Factorial using LongStream")
    void ex04_factorial() {
        int n = 6;
        long factorial = LongStream.rangeClosed(1, n)
                .reduce(1L, (a, b) -> a * b);

        assertThat(factorial).isEqualTo(720L);
    }

    @Test
    @DisplayName("05: Infinite stream iterate - first 8 powers of 2")
    void ex05_infiniteStreamIterate() {
        List<Integer> powersOfTwo = Stream.iterate(1, x -> x * 2)
                .limit(8)
                .toList();

        assertThat(powersOfTwo).containsExactly(1, 2, 4, 8, 16, 32, 64, 128);
    }

    @Test
    @DisplayName("06: Fibonacci sequence generator using Stream.iterate")
    void ex06_fibonacciSequence() {
        List<Integer> fibonacci = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .map(t -> t[0])
                .toList();

        assertThat(fibonacci).containsExactly(0, 1, 1, 2, 3, 5, 8, 13, 21, 34);
    }

    @Test
    @DisplayName("07: Count vowels in a string using chars() IntStream")
    void ex07_countVowelsInString() {
        String input = "Functional Programming with Java Streams";

        long vowelCount = input.toLowerCase().chars()
                .filter(ch -> "aeiou".indexOf(ch) != -1)
                .count();

        assertThat(vowelCount).isEqualTo(12L);
    }

    @Test
    @DisplayName("08: Convert int[] to List<Integer> using boxed()")
    void ex08_boxedPrimitiveArray() {
        int[] numbers = {10, 20, 30, 40, 50};

        List<Integer> boxedList = Arrays.stream(numbers)
                .boxed()
                .toList();

        assertThat(boxedList).containsExactly(10, 20, 30, 40, 50);
    }

    @Test
    @DisplayName("09: Stream.iterate with predicate (Java 9+)")
    void ex09_iterateWithPredicate() {
        List<Integer> powersOfThree = Stream.iterate(1, n -> n < 100, n -> n * 3)
                .toList();

        assertThat(powersOfThree).containsExactly(1, 3, 9, 27, 81);
    }

    @Test
    @DisplayName("10: Dot product of two vectors using IntStream.range")
    void ex10_dotProductOfVectors() {
        int[] a = {1, 2, 3};
        int[] b = {4, 5, 6};

        int dotProduct = IntStream.range(0, a.length)
                .map(i -> a[i] * b[i])
                .sum();

        assertThat(dotProduct).isEqualTo(32);
    }

    @Test
    @DisplayName("11: Sum of digits of a number using chars()")
    void ex11_sumOfDigitsOfNumber() {
        long number = 9876543210L;

        int digitSum = String.valueOf(number).chars()
                .map(Character::getNumericValue)
                .sum();

        assertThat(digitSum).isEqualTo(45);
    }

    @Test
    @DisplayName("12: Generate the first 5 Pythagorean triples using IntStream")
    void ex12_pythagoreanTriples() {
        List<int[]> triples = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> {
                            double c = Math.sqrt(a * a + b * b);
                            return c == Math.floor(c);
                        })
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                )
                .limit(5)
                .toList();

        assertThat(triples).isNotNull().hasSize(5);
        assertThat(triples.get(0)).containsExactly(3, 4, 5);
        assertThat(triples.get(1)).containsExactly(5, 12, 13);
        assertThat(triples.get(2)).containsExactly(6, 8, 10);
        assertThat(triples.get(3)).containsExactly(7, 24, 25);
        assertThat(triples.get(4)).containsExactly(8, 15, 17);
    }

    @Test
    @DisplayName("13: Collatz conjecture sequence using Stream.iterate")
    void ex13_collatzConjectureSteps() {
        List<Long> sequence = Stream.iterate(13L, n -> n % 2 == 0 ? n / 2 : 3 * n + 1)
                .limit(10)
                .toList();

        assertThat(sequence).containsExactly(13L, 40L, 20L, 10L, 5L, 16L, 8L, 4L, 2L, 1L);
    }

    @Test
    @DisplayName("14: Find missing number in sequence 1 to N using IntStream")
    void ex14_findMissingNumberInSequence() {
        int[] numbers = {1, 2, 3, 4, 5, 7, 8, 9, 10};

        int n = 10;
        int expectedSum = n * (n + 1) / 2;
        int actualSum = Arrays.stream(numbers).sum();
        int missing = expectedSum - actualSum;

        assertThat(missing).isEqualTo(6);
    }

    @Test
    @DisplayName("15: Palindrome check using IntStream.range")
    void ex15_isPalindromeUsingIntStream() {
        Predicate<String> isPalindrome = str -> IntStream.range(0, str.length() / 2)
                .allMatch(i -> str.charAt(i) == str.charAt(str.length() - 1 - i));

        assertThat(isPalindrome.test("racecar")).isTrue();
        assertThat(isPalindrome.test("rotor")).isTrue();
        assertThat(isPalindrome.test("stream")).isFalse();
        assertThat(isPalindrome.test("noon")).isTrue();
    }

    @Test
    @DisplayName("16: Running cumulative sum of integer list")
    void ex16_runningCumulativeSum() {
        List<Integer> numbers = List.of(3, 5, 2, 8, 10);

        int[] acc = new int[1];
        List<Integer> cumulativeSum = numbers.stream()
                .map(n -> acc[0] += n)
                .toList();

        assertThat(cumulativeSum).containsExactly(3, 8, 10, 18, 28);
    }
}
