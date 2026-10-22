# 🧠 Senior Engineer's DSA Mastery Suite: Patterns, Templates & Katas

Welcome to the **Data Structures & Algorithms (DSA) Practice Suite**! Tailored specifically for experienced engineers (10–15+ YOE) refreshing their problem-solving muscle memory, this suite cuts through academic fluff and focuses on **high-frequency algorithmic patterns**, **reusable code blueprints**, and **easy-to-memorize snippets**.

All 50 exercises are strictly **Easy to Medium**, designed to help you quickly draft clean snippets from memory and effortlessly map real-world interview problems to core archetypes.

---

## 🧭 Why DSA Feels Unnatural After 15 Years (And How to Fix It)

In day-to-day software architecture and enterprise engineering:
- You delegate data handling to databases, Spring Data, collections frameworks, or Kafka.
- You optimize for maintainability, domain modeling, API contracts, and observability.

In DSA interviews:
- You operate at the **bare-metal index and pointer level**.
- Success isn't about memorizing 500 individual LeetCode problems; it is about mastering **6 core algorithmic blueprints** and recognizing the **problem invariants**.

Once you recognize the pattern, writing the solution takes under 3 minutes.

---

## 📂 Project Structure

```
src/test/java/com/srini/demo/dsa/
├── README.md                                  # Cheat Sheet & Blueprint Guide
├── model/                                     # Minimal, ergonomic data structures
│   ├── ListNode.java                          # Singly-linked list with factory & toList()
│   └── TreeNode.java                          # Binary tree with level-order factory & toList()
│
├── Level1_ArraysAndTwoPointersTest.java        # 10 exercises: Opposite Ends, Fast/Slow, Kadane, Reversals
├── Level2_HashingAndSlidingWindowTest.java     # 10 exercises: Complements, Frequency, Fixed & Variable Windows
├── Level3_StacksAndQueuesTest.java            # 10 exercises: Parentheses, MinStack, Monotonic Stack, Queue/Stack
├── Level4_LinkedListsTest.java                 # 10 exercises: In-Place Reverse, Midpoint, Cycles, Dummy Head
├── Level5_BinarySearchAndTreesTest.java       # 10 exercises: Safe Binary Search, Bounds, BFS/DFS, Invert, LCA
│
└── solutions/                                 # 50 Clean, Idiomatic Reference Solutions
    ├── Level1_ArraysAndTwoPointersSolutionsTest.java
    ├── Level2_HashingAndSlidingWindowSolutionsTest.java
    ├── Level3_StacksAndQueuesSolutionsTest.java
    ├── Level4_LinkedListsSolutionsTest.java
    └── Level5_BinarySearchAndTreesSolutionsTest.java
```

---

## 🏃 How to Practice

1. Open any level practice file in your IDE (e.g. `Level1_ArraysAndTwoPointersTest.java`).
2. Read the problem description, **💡 Pattern**, and **🧠 Memorization Snippet**.
3. Replace the `TODO` with your implementation.
4. Run the test:
   - **From IDE**: Click the green "Run Test" ▶ icon next to any `@Test` method.
   - **From Terminal**:
     ```bash
     # Run a single exercise
     ./mvnw test -Dtest=Level1_ArraysAndTwoPointersTest#ex01_twoSumSorted

     # Run an entire practice level
     ./mvnw test -Dtest=Level1_ArraysAndTwoPointersTest

     # Run all reference solution tests (verify 50 tests pass)
     ./mvnw test -Dtest="*DSA*SolutionsTest"
     ```
5. If you get stuck or want to inspect the cleanest idiom, open the corresponding class under the `solutions/` folder.

---

## ⚡ The 6 Core Snippets to Memorize

### 1. Opposite-Ends Two Pointers (Collision / Shrinking Window)
Use when the array is **sorted** or searching for optimal boundaries (e.g. Two Sum II, Palindrome, Container With Most Water).
```java
int left = 0, right = nums.length - 1;
while (left < right) {
    int sum = nums[left] + nums[right];
    if (sum == target) {
        return new int[]{left, right};
    } else if (sum < target) {
        left++;
    } else {
        right--;
    }
}
```

### 2. Fast & Slow Pointer (In-Place Array Partition / Linked List)
Use for in-place deduplication, partitioning, or detecting list cycles & finding midpoints.
```java
// In-place array modification:
int slow = 0;
for (int fast = 1; fast < nums.length; fast++) {
    if (nums[fast] != nums[slow]) {
        nums[++slow] = nums[fast];
    }
}
return slow + 1;

// Linked list midpoint:
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
```

### 3. Variable Sliding Window
Use for "Longest / Shortest substring / contiguous subarray meeting condition X".
```java
int left = 0, ans = 0;
for (int right = 0; right < n; right++) {
    // 1. Expand: Include nums[right] in window state
    addState(nums[right]);

    // 2. Shrink: While window condition is violated (or to minimize)
    while (windowConditionViolated()) {
        removeState(nums[left]);
        left++;
    }

    // 3. Update answer
    ans = Math.max(ans, right - left + 1);
}
```

### 4. Prefix Sum + HashMap Frequency Pattern
Use for **"Number of subarrays with sum equal to K"** (handles negative numbers where sliding window fails!).
```java
Map<Integer, Integer> prefixCounts = new HashMap<>();
prefixCounts.put(0, 1); // Crucial base case: empty prefix has sum 0
int currSum = 0, count = 0;

for (int x : nums) {
    currSum += x;
    count += prefixCounts.getOrDefault(currSum - k, 0);
    prefixCounts.put(currSum, prefixCounts.getOrDefault(currSum, 0) + 1);
}
```

### 5. In-Place Linked List Reversal (3-Pointer Dance)
The most common linked list building block (used in Reversal, Palindrome List, Reorder List).
```java
ListNode prev = null, curr = head;
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
return prev; // new head
```

### 6. Standard Binary Search Template (Safe from Integer Overflow)
Use for sorted arrays, searching range bounds, or search space reduction.
```java
int left = 0, right = nums.length - 1;
while (left <= right) {
    int mid = left + (right - left) / 2; // Prevents (left + right) integer overflow!
    if (nums[mid] == target) return mid;
    else if (nums[mid] < target) left = mid + 1;
    else right = mid - 1;
}
return -1; // Or return `left` for lower-bound insertion position
```

---

## 📊 Summary of Exercises by Level

| Level | Topic | Key Exercises | Key Mental Anchor |
|:---|:---|:---|:---|
| **Level 1** | **Arrays & Two Pointers** | Two Sum II, Valid Palindrome, Reverse Vowels, Container Most Water, Remove Duplicates, Move Zeroes, Kadane's Max Subarray, Product Except Self, 3Sum, Rotate Array | Opposite ends, Fast/slow runner, 3-step reverse trick, Local vs Global max |
| **Level 2** | **Hashing & Sliding Window** | Two Sum, Contains Duplicate, Anagrams, Group Anagrams, First Unique, Subarray Sum K, Fixed Window Max, Longest Substring Without Repeating, Min Size Subarray, Longest Consecutive | Complement lookup, Alphabet bucket `int[26]`, Window expand & shrink, Prefix map |
| **Level 3** | **Stacks & Queues** | Valid Parentheses, Reverse Polish Notation, MinStack, Daily Temperatures, Next Greater Element, Simplify Unix Path, Queue using Stacks, Stack using Queues, Moving Average, Backspace Compare | `ArrayDeque` over `Stack`, Monotonic decreasing stack, 2-stack amortized $O(1)$ |
| **Level 4** | **Linked Lists** | Reverse List, Middle Node, Detect Cycle, Merge Sorted Lists, Remove Nth from End, Delete Duplicates, Palindrome List, Intersection, Add Two Numbers, Reorder List | 3-pointer reversal, Tortoise & Hare, Sentinel Dummy Head `new ListNode(0)` |
| **Level 5** | **Binary Search & Trees** | Binary Search, Search Insert, First & Last Position, Rotated Array Search, Find Peak, Max Depth, Level Order (BFS), Invert Tree, Symmetric Tree, Lowest Common Ancestor (BST) | Mid formula, Level size batching `queue.size()`, Recursive DFS mirror, BST splitting |

---

## 🎯 Complexity Rule of Thumb (Interview Sizing)

When given the constraint on array length $N$:

| Input Size $N$ | Target Time Complexity | Typical Algorithms |
|:---|:---|:---|
| $N \le 10^5$ | $O(N)$ or $O(N \log N)$ | Two Pointers, Sliding Window, HashMap, Sorting, Monotonic Stack, Divide & Conquer |
| $N \le 10^9$ | $O(\log N)$ or $O(1)$ | Binary Search, Bit Manipulation, Math |
| $N \le 10^3$ | $O(N^2)$ | 2D Matrix traversals, 3Sum, Nested loops |

Happy coding! Work through one level per sitting and let the pattern recognition become second nature.
