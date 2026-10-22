package com.srini.demo.dsa;

import com.srini.demo.dsa.model.TreeNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * LEVEL 5: BINARY SEARCH & TREES (LOG N & HIERARCHICAL REASONING)
 * ════════════════════════════════════════════════════════════════════════════
 * Focus:
 *  - Standard Binary Search Template (Avoiding overflow, termination conditions)
 *  - Lower Bound / Insertion Index & Range Searches
 *  - Search in Rotated / Unconventional Spaces (Rotated Array, Peak Element)
 *  - Recursive Tree DFS (Max Depth, Inversion, Symmetric Mirror)
 *  - Breadth-First Search Level-Order Traversal (Queue with batch sizing)
 *  - Binary Search Tree Properties (Lowest Common Ancestor in O(H))
 *
 * Mental Model & Memorization Anchors:
 *  - Standard Binary Search:
 *      int left = 0, right = nums.length - 1;
 *      while (left <= right) {
 *          int mid = left + (right - left) / 2; // Avoids integer overflow!
 *          if (nums[mid] == target) return mid;
 *          else if (nums[mid] < target) left = mid + 1;
 *          else right = mid - 1;
 *      }
 *      return -1; // (or `left` for insertion position)
 *
 *  - BFS Level-Order Traversal Template:
 *      Queue<TreeNode> queue = new ArrayDeque<>();
 *      queue.offer(root);
 *      while (!queue.isEmpty()) {
 *          int levelSize = queue.size();
 *          for (int i = 0; i < levelSize; i++) {
 *              TreeNode curr = queue.poll();
 *              if (curr.left != null) queue.offer(curr.left);
 *              if (curr.right != null) queue.offer(curr.right);
 *          }
 *      }
 *
 *  - Tree DFS Recursion:
 *      base case: if (node == null) return ...;
 *      combine: 1 + Math.max(depth(left), depth(right));
 */
@DisplayName("Level 5: Binary Search & Trees")
public class Level5_BinarySearchAndTreesTest {

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 01: BINARY SEARCH (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Standard O(log N) Binary Search.
     * Given sorted array `nums` and `target`, return target index or -1 if not found.
     *
     * Snippet to memorize:
     *   int l = 0, r = nums.length - 1;
     *   while (l <= r) {
     *       int mid = l + (r - l) / 2;
     *       if (nums[mid] == target) return mid;
     *       if (nums[mid] < target) l = mid + 1;
     *       else r = mid - 1;
     *   }
     *   return -1;
     */
    public int search(int[] nums, int target) {
        // TODO: Implement binary search
        return -1;
    }

    @Test
    @DisplayName("01: Binary Search")
    void ex01_search() {
        assertThat(search(new int[]{-1, 0, 3, 5, 9, 12}, 9)).isEqualTo(4);
        assertThat(search(new int[]{-1, 0, 3, 5, 9, 12}, 2)).isEqualTo(-1);
        assertThat(search(new int[]{5}, 5)).isEqualTo(0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 02: SEARCH INSERT POSITION (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Binary Search Lower Bound (Insertion Point).
     * Given sorted array and target, return index if found. If not, return index
     * where it would be if inserted in order. O(log N) runtime.
     *
     * Key insight: When `left > right`, `left` is exactly the insert position!
     */
    public int searchInsert(int[] nums, int target) {
        // TODO: Return insertion point
        return 0;
    }

    @Test
    @DisplayName("02: Search Insert Position")
    void ex02_searchInsert() {
        assertThat(searchInsert(new int[]{1, 3, 5, 6}, 5)).isEqualTo(2);
        assertThat(searchInsert(new int[]{1, 3, 5, 6}, 2)).isEqualTo(1);
        assertThat(searchInsert(new int[]{1, 3, 5, 6}, 7)).isEqualTo(4);
        assertThat(searchInsert(new int[]{1, 3, 5, 6}, 0)).isEqualTo(0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 03: FIND FIRST AND LAST POSITION OF ELEMENT IN SORTED ARRAY (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Left-Biased and Right-Biased Binary Search.
     * Given sorted array `nums` and `target`, find starting and ending position of `target`.
     * If target not found, return [-1, -1]. Must run in O(log N).
     */
    public int[] searchRange(int[] nums, int target) {
        // TODO: Run two binary searches (one seeking leftmost, one seeking rightmost)
        return new int[]{-1, -1};
    }

    @Test
    @DisplayName("03: Find First and Last Position in Sorted Array")
    void ex03_searchRange() {
        assertThat(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)).containsExactly(3, 4);
        assertThat(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6)).containsExactly(-1, -1);
        assertThat(searchRange(new int[]{}, 0)).containsExactly(-1, -1);
        assertThat(searchRange(new int[]{1}, 1)).containsExactly(0, 0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 04: SEARCH IN ROTATED SORTED ARRAY (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Modified Binary Search (Find Sorted Half).
     * Array sorted in ascending order is rotated at an unknown pivot.
     * Return index of target if found, else -1. Must run in O(log N).
     */
    public int searchRotated(int[] nums, int target) {
        // TODO: Check if left half is sorted (nums[l] <= nums[mid]) or right half is sorted
        return -1;
    }

    @Test
    @DisplayName("04: Search in Rotated Sorted Array")
    void ex04_searchRotated() {
        assertThat(searchRotated(new int[]{4, 5, 6, 7, 0, 1, 2}, 0)).isEqualTo(4);
        assertThat(searchRotated(new int[]{4, 5, 6, 7, 0, 1, 2}, 3)).isEqualTo(-1);
        assertThat(searchRotated(new int[]{1}, 0)).isEqualTo(-1);
        assertThat(searchRotated(new int[]{3, 1}, 1)).isEqualTo(1);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 05: FIND PEAK ELEMENT (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Binary Search on Gradient / Slope.
     * A peak element is an element strictly greater than its neighbors.
     * Given integer array `nums`, find a peak element and return its index in O(log N) time.
     */
    public int findPeakElement(int[] nums) {
        // TODO: If nums[mid] > nums[mid+1], peak is to left (inclusive); else to right
        return 0;
    }

    @Test
    @DisplayName("05: Find Peak Element")
    void ex05_findPeakElement() {
        int peak1 = findPeakElement(new int[]{1, 2, 3, 1});
        assertThat(peak1).isEqualTo(2); // nums[2] == 3

        int peak2 = findPeakElement(new int[]{1, 2, 1, 3, 5, 6, 4});
        assertThat(peak2).isIn(1, 5); // index 1 (val 2) or index 5 (val 6)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 06: MAXIMUM DEPTH OF BINARY TREE (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Tree DFS (Divide and Conquer).
     * Given root of binary tree, return its maximum depth.
     *
     * Snippet to memorize:
     *   if (root == null) return 0;
     *   return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
     */
    public int maxDepth(TreeNode root) {
        // TODO: Implement recursive depth calculation
        return 0;
    }

    @Test
    @DisplayName("06: Maximum Depth of Binary Tree")
    void ex06_maxDepth() {
        // Tree: [3, 9, 20, null, null, 15, 7]
        TreeNode root1 = TreeNode.of(3, 9, 20, null, null, 15, 7);
        assertThat(maxDepth(root1)).isEqualTo(3);

        TreeNode root2 = TreeNode.of(1, null, 2);
        assertThat(maxDepth(root2)).isEqualTo(2);

        assertThat(maxDepth(null)).isEqualTo(0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 07: BINARY TREE LEVEL ORDER TRAVERSAL (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Tree BFS using Queue with Level Sizing.
     * Given root of binary tree, return level order traversal of its nodes' values.
     * (i.e., from left to right, level by level).
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        // TODO: BFS with Queue, iterate queue.size() times per level
        return new ArrayList<>();
    }

    @Test
    @DisplayName("07: Binary Tree Level Order Traversal (BFS)")
    void ex07_levelOrder() {
        // Tree: [3, 9, 20, null, null, 15, 7]
        TreeNode root1 = TreeNode.of(3, 9, 20, null, null, 15, 7);
        assertThat(levelOrder(root1)).containsExactly(
                List.of(3),
                List.of(9, 20),
                List.of(15, 7)
        );

        assertThat(levelOrder(TreeNode.of(1))).containsExactly(List.of(1));
        assertThat(levelOrder(null)).isEmpty();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 08: INVERT BINARY TREE (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Tree Mirroring / DFS Subtree Swap.
     * Given root of binary tree, invert the tree, and return its root.
     */
    public TreeNode invertTree(TreeNode root) {
        // TODO: Recursively swap left and right subtrees
        return null;
    }

    @Test
    @DisplayName("08: Invert Binary Tree")
    void ex08_invertTree() {
        // Tree: [4, 2, 7, 1, 3, 6, 9] -> Inverted: [4, 7, 2, 9, 6, 3, 1]
        TreeNode root = TreeNode.of(4, 2, 7, 1, 3, 6, 9);
        TreeNode inverted = invertTree(root);
        assertThat(inverted.toLevelOrderList()).containsExactly(4, 7, 2, 9, 6, 3, 1);

        assertThat(invertTree(null)).isNull();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 09: SYMMETRIC TREE (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Dual Tree DFS (Mirror Comparison).
     * Given root of binary tree, check whether it is a mirror of itself (symmetric around center).
     */
    public boolean isSymmetric(TreeNode root) {
        // TODO: Compare root.left and root.right with helper isMirror(t1, t2)
        return false;
    }

    @Test
    @DisplayName("09: Symmetric Tree")
    void ex09_isSymmetric() {
        // Tree: [1, 2, 2, 3, 4, 4, 3] -> symmetric
        TreeNode root1 = TreeNode.of(1, 2, 2, 3, 4, 4, 3);
        assertThat(isSymmetric(root1)).isTrue();

        // Tree: [1, 2, 2, null, 3, null, 3] -> not symmetric
        TreeNode root2 = TreeNode.of(1, 2, 2, null, 3, null, 3);
        assertThat(isSymmetric(root2)).isFalse();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 10: LOWEST COMMON ANCESTOR OF A BST (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: BST Property Traversal (No backtracking needed!).
     * Given a Binary Search Tree (BST), find lowest common ancestor (LCA) node of two given nodes p and q.
     *
     * Snippet to memorize:
     *   TreeNode curr = root;
     *   while (curr != null) {
     *       if (p.val < curr.val && q.val < curr.val) curr = curr.left;
     *       else if (p.val > curr.val && q.val > curr.val) curr = curr.right;
     *       else return curr; // Split point or match
     *   }
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // TODO: Exploit BST order: if both < curr, go left; if both > curr, go right; else curr is LCA
        return null;
    }

    @Test
    @DisplayName("10: Lowest Common Ancestor of a BST")
    void ex10_lowestCommonAncestor() {
        // BST: [6, 2, 8, 0, 4, 7, 9, null, null, 3, 5]
        TreeNode root = TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
        TreeNode p = root.left; // 2
        TreeNode q = root.right; // 8
        assertThat(lowestCommonAncestor(root, p, q).val).isEqualTo(6);

        TreeNode p2 = root.left; // 2
        TreeNode q2 = root.left.right; // 4
        assertThat(lowestCommonAncestor(root, p2, q2).val).isEqualTo(2);
    }
}
