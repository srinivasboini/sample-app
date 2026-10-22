package com.srini.demo.streams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * LEVEL 4: NUMERIC STREAMS, MATH & INFINITE STREAMS
 * ════════════════════════════════════════════════════════════════════════════
 * Focus:
 *  - IntStream, LongStream, DoubleStream (range, rangeClosed)
 *  - summaryStatistics (min, max, average, sum, count)
 *  - boxed() primitive conversion
 *  - Infinite streams with Stream.iterate() and Stream.generate()
 *  - Math operations (primes, factorials, fibonacci, Pythagorean triples)
 *  - String chars() IntStream processing
 *
 * Instructions:
 *  1. Replace the placeholder / TODO with your Java Stream pipeline.
 *  2. Run the test in your IDE or via terminal:
 *     ./mvnw test -Dtest=Level4_NumericAndInfiniteStreamExercisesTest#ex01_rangeAndRangeClosedSum
 *  3. Check 'solutions/Level4_NumericAndInfiniteStreamExercisesSolutionsTest.java' if you get stuck!
 */
@DisplayName("Level 4: Numeric Streams, Math, and Infinite Streams")
public class Level4_NumericAndInfiniteStreamExercisesTest {

    @Test
    @DisplayName("01: Sum numbers from 1 to 100 inclusive using IntStream")
    void ex01_rangeAndRangeClosedSum() {
        // Task: Calculate the sum of 1 through 100 (inclusive) using IntStream.rangeClosed

        int sum = 0; // TODO: Implement using IntStream

        assertThat(sum).isEqualTo(5050);
    }

    @Test
    @DisplayName("02: Compute summary statistics for employee salaries")
    void ex02_summaryStatistics() {
        // Given: TestData.EMPLOYEES
        // Task: Compute DoubleSummaryStatistics for employee salaries using mapToDouble(...).summaryStatistics()

        DoubleSummaryStatistics stats = null; // TODO: Implement using stream

        assertThat(stats).isNotNull();
        assertThat(stats.getCount()).isEqualTo(10);
        assertThat(stats.getMin()).isEqualTo(60000.0);
        assertThat(stats.getMax()).isEqualTo(120000.0);
        assertThat(stats.getSum()).isEqualTo(882000.0);
        assertThat(stats.getAverage()).isEqualTo(88200.0);
    }

    @Test
    @DisplayName("03: Prime number check using IntStream")
    void ex03_isPrime() {
        // Task: Implement a predicate that checks whether a positive integer n > 1 is prime

        IntPredicate isPrime = null; // TODO: Implement using IntStream

        assertThat(isPrime).isNotNull();
        assertThat(isPrime.test(29)).isTrue();
        assertThat(isPrime.test(49)).isFalse();
        assertThat(isPrime.test(2)).isTrue();
        assertThat(isPrime.test(1)).isFalse();
    }

    @Test
    @DisplayName("04: Factorial using LongStream")
    void ex04_factorial() {
        int n = 6;
        // Task: Calculate factorial of 6 (6! = 720) using LongStream.rangeClosed and reduce

        long factorial = 0L; // TODO: Implement using LongStream

        assertThat(factorial).isEqualTo(720L);
    }

    @Test
    @DisplayName("05: Infinite stream iterate - first 8 powers of 2")
    void ex05_infiniteStreamIterate() {
        // Task: Generate [1, 2, 4, 8, 16, 32, 64, 128] using Stream.iterate and limit

        List<Integer> powersOfTwo = null; // TODO: Implement using Stream.iterate

        assertThat(powersOfTwo).containsExactly(1, 2, 4, 8, 16, 32, 64, 128);
    }

    @Test
    @DisplayName("06: Fibonacci sequence generator using Stream.iterate")
    void ex06_fibonacciSequence() {
        // Task: Generate first 10 Fibonacci numbers [0, 1, 1, 2, 3, 5, 8, 13, 21, 34]

        List<Integer> fibonacci = null; // TODO: Implement using Stream.iterate

        assertThat(fibonacci).containsExactly(0, 1, 1, 2, 3, 5, 8, 13, 21, 34);
    }

    @Test
    @DisplayName("07: Count vowels in a string using chars() IntStream")
    void ex07_countVowelsInString() {
        String input = "Functional Programming with Java Streams";

        // Task: Count the total number of vowels ('a', 'e', 'i', 'o', 'u') ignoring case

        long vowelCount = 0L; // TODO: Implement using chars() stream

        assertThat(vowelCount).isEqualTo(12L);
    }

    @Test
    @DisplayName("08: Convert int[] to List<Integer> using boxed()")
    void ex08_boxedPrimitiveArray() {
        int[] numbers = {10, 20, 30, 40, 50};

        // Task: Convert primitive int array to a boxed List<Integer>

        List<Integer> boxedList = null; // TODO: Implement using boxed()

        assertThat(boxedList).containsExactly(10, 20, 30, 40, 50);
    }

    @Test
    @DisplayName("09: Stream.iterate with predicate (Java 9+)")
    void ex09_iterateWithPredicate() {
        // Task: Generate powers of 3 strictly less than 100: [1, 3, 9, 27, 81]

        List<Integer> powersOfThree = null; // TODO: Implement using Stream.iterate

        assertThat(powersOfThree).containsExactly(1, 3, 9, 27, 81);
    }

    @Test
    @DisplayName("10: Dot product of two vectors using IntStream.range")
    void ex10_dotProductOfVectors() {
        int[] a = {1, 2, 3};
        int[] b = {4, 5, 6};

        // Task: Compute dot product (1*4 + 2*5 + 3*6 = 32)

        int dotProduct = 0; // TODO: Implement using IntStream.range

        assertThat(dotProduct).isEqualTo(32);
    }

    @Test
    @DisplayName("11: Sum of digits of a number using chars()")
    void ex11_sumOfDigitsOfNumber() {
        long number = 9876543210L;

        // Task: Calculate the sum of digits of the given number (9+8+7+6+5+4+3+2+1+0 = 45)

        int digitSum = 0; // TODO: Implement using chars() stream

        assertThat(digitSum).isEqualTo(45);
    }

    @Test
    @DisplayName("12: Generate the first 5 Pythagorean triples using IntStream")
    void ex12_pythagoreanTriples() {
        // Task: Generate the first 5 Pythagorean triples (a, b, c) such that a^2 + b^2 = c^2,
        //       with 1 <= a <= b. Return each triple as int[] {a, b, c}.

        List<int[]> triples = null; // TODO: Implement using streams

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
        // Task: Generate the Collatz sequence starting at 13 for 10 elements:
        //       Rule: if n is even -> n / 2, if n is odd -> 3*n + 1
        // Sequence: [13, 40, 20, 10, 5, 16, 8, 4, 2, 1]

        List<Long> sequence = null; // TODO: Implement using Stream.iterate

        assertThat(sequence).containsExactly(13L, 40L, 20L, 10L, 5L, 16L, 8L, 4L, 2L, 1L);
    }

    @Test
    @DisplayName("14: Find missing number in sequence 1 to N using IntStream")
    void ex14_findMissingNumberInSequence() {
        int[] numbers = {1, 2, 3, 4, 5, 7, 8, 9, 10}; // Missing 6, total N = 10

        // Task: Using IntStream arithmetic, find the missing number in the range 1..10

        int missing = 0; // TODO: Implement using IntStream

        assertThat(missing).isEqualTo(6);
    }

    @Test
    @DisplayName("15: Palindrome check using IntStream.range")
    void ex15_isPalindromeUsingIntStream() {
        // Task: Check if strings are palindromes using IntStream.range comparing symmetrical indices

        java.util.function.Predicate<String> isPalindrome = null; // TODO: Implement using IntStream.range

        assertThat(isPalindrome).isNotNull();
        assertThat(isPalindrome.test("racecar")).isTrue();
        assertThat(isPalindrome.test("rotor")).isTrue();
        assertThat(isPalindrome.test("stream")).isFalse();
        assertThat(isPalindrome.test("noon")).isTrue();
    }

    @Test
    @DisplayName("16: Running cumulative sum of integer list")
    void ex16_runningCumulativeSum() {
        List<Integer> numbers = List.of(3, 5, 2, 8, 10);

        // Task: Produce the running cumulative prefix sum list:
        //       [3, 3+5=8, 8+2=10, 10+8=18, 18+10=28] -> [3, 8, 10, 18, 28]

        List<Integer> cumulativeSum = null; // TODO: Implement using streams

        assertThat(cumulativeSum).containsExactly(3, 8, 10, 18, 28);
    }
}
