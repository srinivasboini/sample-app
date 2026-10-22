package com.srini.demo.dsa.solutions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level 3 Solutions: Stacks & Queues")
public class Level3_StacksAndQueuesSolutionsTest {

    // 01: Valid Parentheses
    // Time: O(N) | Space: O(N)
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else {
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
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

    // 02: Evaluate Reverse Polish Notation
    // Time: O(N) | Space: O(N)
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String token : tokens) {
            switch (token) {
                case "+" -> stack.push(stack.pop() + stack.pop());
                case "-" -> {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                }
                case "*" -> stack.push(stack.pop() * stack.pop());
                case "/" -> {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a / b);
                }
                default -> stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    @Test
    @DisplayName("02: Evaluate Reverse Polish Notation")
    void ex02_evalRPN() {
        assertThat(evalRPN(new String[]{"2", "1", "+", "3", "*"})).isEqualTo(9);
        assertThat(evalRPN(new String[]{"4", "13", "5", "/", "+"})).isEqualTo(6);
        assertThat(evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"})).isEqualTo(22);
    }

    // 03: Min Stack
    // Time: O(1) all ops | Space: O(N)
    public static class MinStack {
        private final Deque<Integer> stack = new ArrayDeque<>();
        private final Deque<Integer> minStack = new ArrayDeque<>();

        public MinStack() {
        }

        public void push(int val) {
            stack.push(val);
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }

        public void pop() {
            int popped = stack.pop();
            if (popped == minStack.peek()) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
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

    // 04: Daily Temperatures
    // Time: O(N) | Space: O(N)
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        Deque<Integer> stack = new ArrayDeque<>(); // stores indices of temperatures

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevIdx = stack.pop();
                answer[prevIdx] = i - prevIdx;
            }
            stack.push(i);
        }
        return answer;
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

    // 05: Next Greater Element I
    // Time: O(N + M) | Space: O(M)
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextGreater = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();

        for (int num : nums2) {
            while (!stack.isEmpty() && num > stack.peek()) {
                nextGreater.put(stack.pop(), num);
            }
            stack.push(num);
        }

        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreater.getOrDefault(nums1[i], -1);
        }
        return result;
    }

    @Test
    @DisplayName("05: Next Greater Element I")
    void ex05_nextGreaterElement() {
        assertThat(nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2}))
                .containsExactly(-1, 3, -1);
        assertThat(nextGreaterElement(new int[]{2, 4}, new int[]{1, 2, 3, 4}))
                .containsExactly(3, -1);
    }

    // 06: Simplify Unix Path
    // Time: O(N) | Space: O(N)
    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] parts = path.split("/");

        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            }
            if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(part);
            }
        }

        if (stack.isEmpty()) {
            return "/";
        }

        StringBuilder sb = new StringBuilder();
        // Since ArrayDeque push inserts at head, iterating in reverse (pollLast) produces canonical order
        while (!stack.isEmpty()) {
            sb.append("/").append(stack.pollLast());
        }
        return sb.toString();
    }

    @Test
    @DisplayName("06: Simplify Unix Path")
    void ex06_simplifyPath() {
        assertThat(simplifyPath("/home/")).isEqualTo("/home");
        assertThat(simplifyPath("/../")).isEqualTo("/");
        assertThat(simplifyPath("/home//foo/")).isEqualTo("/home/foo");
        assertThat(simplifyPath("/a/./b/../../c/")).isEqualTo("/c");
    }

    // 07: Implement Queue Using Stacks
    // Time: Push O(1), Pop/Peek Amortized O(1) | Space: O(N)
    public static class MyQueue {
        private final Deque<Integer> in = new ArrayDeque<>();
        private final Deque<Integer> out = new ArrayDeque<>();

        public MyQueue() {
        }

        public void push(int x) {
            in.push(x);
        }

        public int pop() {
            shift();
            return out.pop();
        }

        public int peek() {
            shift();
            return out.peek();
        }

        public boolean empty() {
            return in.isEmpty() && out.isEmpty();
        }

        private void shift() {
            if (out.isEmpty()) {
                while (!in.isEmpty()) {
                    out.push(in.pop());
                }
            }
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

    // 08: Implement Stack Using Queues
    // Time: Push O(N), Pop/Top O(1) | Space: O(N)
    public static class MyStack {
        private final Queue<Integer> queue = new ArrayDeque<>();

        public MyStack() {
        }

        public void push(int x) {
            queue.offer(x);
            int size = queue.size();
            for (int i = 0; i < size - 1; i++) {
                queue.offer(queue.poll());
            }
        }

        public int pop() {
            return queue.poll();
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
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

    // 09: Moving Average From Data Stream
    // Time: O(1) per call | Space: O(size)
    public static class MovingAverage {
        private final int maxSize;
        private final Queue<Integer> window = new ArrayDeque<>();
        private double windowSum = 0.0;

        public MovingAverage(int size) {
            this.maxSize = size;
        }

        public double next(int val) {
            window.offer(val);
            windowSum += val;
            if (window.size() > maxSize) {
                windowSum -= window.poll();
            }
            return windowSum / window.size();
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

    // 10: Backspace String Compare
    // Time: O(N + M) | Space: O(1) using two pointers backwards
    public boolean backspaceCompare(String s, String t) {
        int i = s.length() - 1;
        int j = t.length() - 1;
        int skipS = 0;
        int skipT = 0;

        while (i >= 0 || j >= 0) {
            // Find next valid character in s
            while (i >= 0) {
                if (s.charAt(i) == '#') {
                    skipS++;
                    i--;
                } else if (skipS > 0) {
                    skipS--;
                    i--;
                } else {
                    break;
                }
            }

            // Find next valid character in t
            while (j >= 0) {
                if (t.charAt(j) == '#') {
                    skipT++;
                    j--;
                } else if (skipT > 0) {
                    skipT--;
                    j--;
                } else {
                    break;
                }
            }

            // Compare characters
            if (i >= 0 && j >= 0 && s.charAt(i) != t.charAt(j)) {
                return false;
            }
            // If one string ended while the other hasn't
            if ((i >= 0) != (j >= 0)) {
                return false;
            }

            i--;
            j--;
        }
        return true;
    }

    @Test
    @DisplayName("10: Backspace String Compare")
    void ex10_backspaceCompare() {
        assertThat(backspaceCompare("ab#c", "ad#c")).isTrue();
        assertThat(backspaceCompare("ab##", "c#d#")).isTrue();
        assertThat(backspaceCompare("a#c", "b")).isFalse();
    }
}
