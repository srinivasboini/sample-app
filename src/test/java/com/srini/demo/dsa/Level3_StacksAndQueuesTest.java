package com.srini.demo.dsa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * LEVEL 3: STACKS & QUEUES (LIFO & FIFO MASTERY)
 * ════════════════════════════════════════════════════════════════════════════
 * Focus:
 *  - Classic Stack Matching (Parentheses, Reverse Polish Notation)
 *  - Auxiliary State Tracking (Min Stack in O(1))
 *  - Monotonic Decreasing/Increasing Stack (Daily Temperatures, Next Greater Element)
 *  - Path Canonicalization (Simplify Unix Path)
 *  - Cross-Structure Emulation (Queue using Stacks, Stack using Queues)
 *  - Streaming Buffers (Moving Average)
 *
 * Mental Model & Memorization Anchors:
 *  - Modern Java Stack idiom:
 *      Deque<Integer> stack = new ArrayDeque<>(); // Avoid legacy java.util.Stack!
 *      stack.push(val); stack.pop(); stack.peek();
 *  - Monotonic Decreasing Stack (finding next greater element):
 *      for (int i = 0; i < n; i++) {
 *          while (!stack.isEmpty() && arr[i] > arr[stack.peek()]) {
 *              int prevIdx = stack.pop();
 *              result[prevIdx] = arr[i] (or i - prevIdx);
 *          }
 *          stack.push(i);
 *      }
 *  - 2-Stack Queue (Amortized O(1)):
 *      push to `in`; when pop/peek, if `out` is empty, transfer all from `in` to `out`.
 */
@DisplayName("Level 3: Stacks & Queues")
public class Level3_StacksAndQueuesTest {

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 01: VALID PARENTHESES (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Stack Matching.
     * Given string `s` containing '(', ')', '{', '}', '[' and ']',
     * determine if the input string is valid.
     *
     * Snippet to memorize:
     *   Deque<Character> stack = new ArrayDeque<>();
     *   for (char c : s.toCharArray()) {
     *       if (c == '(') stack.push(')');
     *       else if (c == '{') stack.push('}');
     *       else if (c == '[') stack.push(']');
     *       else if (stack.isEmpty() || stack.pop() != c) return false;
     *   }
     *   return stack.isEmpty();
     */
    public boolean isValid(String s) {
        // TODO: Implement bracket matching using Deque
        return false;
    }

    @Test
    @DisplayName("01: Valid Parentheses")
    void ex01_isValid() {
        assertThat(isValid("()")).isTrue();
        assertThat(isValid("()[]{}")).isTrue();
        assertThat(isValid("(]")).isFalse();
        assertThat(isValid("([)]")).isFalse();
        assertThat(isValid("{[]}")).isTrue();
        assertThat(isValid("]")).isFalse();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 02: EVALUATE REVERSE POLISH NOTATION (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Stack-based Arithmetic Evaluation.
     * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
     * Valid operators are '+', '-', '*', and '/'. Division truncates toward zero.
     */
    public int evalRPN(String[] tokens) {
        // TODO: Push numbers; when operator, pop b then a, evaluate a op b, push result
        return 0;
    }

    @Test
    @DisplayName("02: Evaluate Reverse Polish Notation")
    void ex02_evalRPN() {
        assertThat(evalRPN(new String[]{"2", "1", "+", "3", "*"})).isEqualTo(9); // (2+1)*3
        assertThat(evalRPN(new String[]{"4", "13", "5", "/", "+"})).isEqualTo(6); // 4 + (13/5)
        assertThat(evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"})).isEqualTo(22);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 03: MIN STACK (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Parallel Tracking or Node-Pair State.
     * Design a stack that supports push, pop, top, and retrieving the minimum element in O(1) time.
     */
    public static class MinStack {
        // TODO: Maintain data stack and min tracking

        public MinStack() {
        }

        public void push(int val) {
            // TODO
        }

        public void pop() {
            // TODO
        }

        public int top() {
            // TODO
            return 0;
        }

        public int getMin() {
            // TODO
            return 0;
        }
    }

    @Test
    @DisplayName("03: Min Stack")
    void ex03_minStack() {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        assertThat(minStack.getMin()).isEqualTo(-3);
        minStack.pop();
        assertThat(minStack.top()).isEqualTo(0);
        assertThat(minStack.getMin()).isEqualTo(-2);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 04: DAILY TEMPERATURES (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Monotonic Decreasing Stack (Indexes).
     * Given an array of integers `temperatures`, return an array `answer` such that
     * `answer[i]` is the number of days you have to wait after the `i-th` day to get a warmer temperature.
     * If there is no future day for which this is possible, keep `answer[i] == 0`.
     */
    public int[] dailyTemperatures(int[] temperatures) {
        // TODO: Use monotonic decreasing stack of indices
        return new int[]{};
    }

    @Test
    @DisplayName("04: Daily Temperatures")
    void ex04_dailyTemperatures() {
        assertThat(dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73}))
                .containsExactly(1, 1, 4, 2, 1, 1, 0, 0);
        assertThat(dailyTemperatures(new int[]{30, 40, 50, 60}))
                .containsExactly(1, 1, 1, 0);
        assertThat(dailyTemperatures(new int[]{30, 60, 90}))
                .containsExactly(1, 1, 0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 05: NEXT GREATER ELEMENT I (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Monotonic Stack + HashMap.
     * `nums1` is a subset of `nums2`. For each element in `nums1`, find the first
     * greater element to its right in `nums2`. If none exists, return -1.
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // TODO: Build next greater map for nums2 using monotonic stack, then map nums1
        return new int[]{};
    }

    @Test
    @DisplayName("05: Next Greater Element I")
    void ex05_nextGreaterElement() {
        assertThat(nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2}))
                .containsExactly(-1, 3, -1);
        assertThat(nextGreaterElement(new int[]{2, 4}, new int[]{1, 2, 3, 4}))
                .containsExactly(3, -1);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 06: SIMPLIFY UNIX PATH (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Deque Token Navigation.
     * Given an absolute path for a Unix-style file system, convert it to the simplified canonical path.
     * Rules: "." refers to current dir, ".." refers to parent dir, "//" is treated as single "/".
     */
    public String simplifyPath(String path) {
        // TODO: Split by "/", push directory names, pop on "..", ignore "" and "."
        return "";
    }

    @Test
    @DisplayName("06: Simplify Unix Path")
    void ex06_simplifyPath() {
        assertThat(simplifyPath("/home/")).isEqualTo("/home");
        assertThat(simplifyPath("/../")).isEqualTo("/");
        assertThat(simplifyPath("/home//foo/")).isEqualTo("/home/foo");
        assertThat(simplifyPath("/a/./b/../../c/")).isEqualTo("/c");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 07: IMPLEMENT QUEUE USING STACKS (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Two-Stack FIFO Emulation (Amortized O(1)).
     * Implement a FIFO queue using only two stacks.
     */
    public static class MyQueue {
        // TODO: Use two Deque<Integer> (in and out)

        public MyQueue() {
        }

        public void push(int x) {
            // TODO
        }

        public int pop() {
            // TODO
            return 0;
        }

        public int peek() {
            // TODO
            return 0;
        }

        public boolean empty() {
            // TODO
            return true;
        }
    }

    @Test
    @DisplayName("07: Implement Queue Using Stacks")
    void ex07_queueUsingStacks() {
        MyQueue queue = new MyQueue();
        queue.push(1);
        queue.push(2);
        assertThat(queue.peek()).isEqualTo(1);
        assertThat(queue.pop()).isEqualTo(1);
        assertThat(queue.empty()).isFalse();
        assertThat(queue.pop()).isEqualTo(2);
        assertThat(queue.empty()).isTrue();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 08: IMPLEMENT STACK USING QUEUES (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Single or Double Queue LIFO Emulation.
     * Implement a LIFO stack using queues.
     */
    public static class MyStack {
        // TODO: Use Queue<Integer>

        public MyStack() {
        }

        public void push(int x) {
            // TODO
        }

        public int pop() {
            // TODO
            return 0;
        }

        public int top() {
            // TODO
            return 0;
        }

        public boolean empty() {
            // TODO
            return true;
        }
    }

    @Test
    @DisplayName("08: Implement Stack Using Queues")
    void ex08_stackUsingQueues() {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        assertThat(stack.top()).isEqualTo(2);
        assertThat(stack.pop()).isEqualTo(2);
        assertThat(stack.empty()).isFalse();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 09: MOVING AVERAGE FROM DATA STREAM (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Fixed-Size Queue Sliding Buffer.
     * Given a stream of integers and a window size, calculate the moving average
     * of all integers in the sliding window.
     */
    public static class MovingAverage {
        // TODO: Store size, running sum, and Queue<Integer>

        public MovingAverage(int size) {
            // TODO
        }

        public double next(int val) {
            // TODO
            return 0.0;
        }
    }

    @Test
    @DisplayName("09: Moving Average From Data Stream")
    void ex09_movingAverage() {
        MovingAverage ma = new MovingAverage(3);
        assertThat(ma.next(1)).isEqualTo(1.0);
        assertThat(ma.next(10)).isEqualTo((1 + 10) / 2.0);
        assertThat(ma.next(3)).isEqualTo((1 + 10 + 3) / 3.0);
        assertThat(ma.next(5)).isEqualTo((10 + 3 + 5) / 3.0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 10: BACKSPACE STRING COMPARE (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Stack Simulation or Two Pointers backwards.
     * Given two strings `s` and `t`, return true if they are equal when both are typed
     * into empty text editors. '#' means a backspace character.
     */
    public boolean backspaceCompare(String s, String t) {
        // TODO: Build clean string using stack or compare from end
        return false;
    }

    @Test
    @DisplayName("10: Backspace String Compare")
    void ex10_backspaceCompare() {
        assertThat(backspaceCompare("ab#c", "ad#c")).isTrue(); // both become "ac"
        assertThat(backspaceCompare("ab##", "c#d#")).isTrue(); // both become ""
        assertThat(backspaceCompare("a#c", "b")).isFalse();
    }
}
