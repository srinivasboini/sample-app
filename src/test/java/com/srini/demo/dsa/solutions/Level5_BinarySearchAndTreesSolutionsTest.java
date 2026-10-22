package com.srini.demo.dsa.solutions;

import com.srini.demo.dsa.model.TreeNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level 5 Solutions: Binary Search & Trees")
public class Level5_BinarySearchAndTreesSolutionsTest {

    // 01: Binary Search
    // Time: O(log N) | Space: O(1)
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    @Test
    @DisplayName("01: Binary Search")
    void ex01_search() {
        assertThat(search(new int[]{-1, 0, 3, 5, 9, 12}, 9)).isEqualTo(4);
        assertThat(search(new int[]{-1, 0, 3, 5, 9, 12}, 2)).isEqualTo(-1);
        assertThat(search(new int[]{5}, 5)).isEqualTo(0);
    }

    // 02: Search Insert Position
    // Time: O(log N) | Space: O(1)
    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left; // left is the insertion point
    }

    @Test
    @DisplayName("02: Search Insert Position")
    void ex02_searchInsert() {
        assertThat(searchInsert(new int[]{1, 3, 5, 6}, 5)).isEqualTo(2);
        assertThat(searchInsert(new int[]{1, 3, 5, 6}, 2)).isEqualTo(1);
        assertThat(searchInsert(new int[]{1, 3, 5, 6}, 7)).isEqualTo(4);
        assertThat(searchInsert(new int[]{1, 3, 5, 6}, 0)).isEqualTo(0);
    }

    // 03: Find First and Last Position in Sorted Array
    // Time: O(log N) | Space: O(1)
    public int[] searchRange(int[] nums, int target) {
        int first = findBound(nums, target, true);
        if (first == -1) {
            return new int[]{-1, -1};
        }
        int last = findBound(nums, target, false);
        return new int[]{first, last};
    }

    private int findBound(int[] nums, int target, boolean isFirst) {
        int left = 0, right = nums.length - 1;
        int bound = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                bound = mid;
                if (isFirst) {
                    right = mid - 1; // seek leftwards
                } else {
                    left = mid + 1;  // seek rightwards
                }
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return bound;
    }

    @Test
    @DisplayName("03: Find First and Last Position in Sorted Array")
    void ex03_searchRange() {
        assertThat(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)).containsExactly(3, 4);
        assertThat(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6)).containsExactly(-1, -1);
        assertThat(searchRange(new int[]{}, 0)).containsExactly(-1, -1);
        assertThat(searchRange(new int[]{1}, 1)).containsExactly(0, 0);
    }

    // 04: Search in Rotated Sorted Array
    // Time: O(log N) | Space: O(1)
    public int searchRotated(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            // Check if left half is sorted
            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else { // Right half is sorted
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
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

    // 05: Find Peak Element
    // Time: O(log N) | Space: O(1)
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) {
                // Slope is decreasing, peak is at mid or to the left
                right = mid;
            } else {
                // Slope is increasing, peak is strictly to the right
                left = mid + 1;
            }
        }
        return left;
    }

    @Test
    @DisplayName("05: Find Peak Element")
    void ex05_findPeakElement() {
        int peak1 = findPeakElement(new int[]{1, 2, 3, 1});
        assertThat(peak1).isEqualTo(2);

        int peak2 = findPeakElement(new int[]{1, 2, 1, 3, 5, 6, 4});
        assertThat(peak2).isIn(1, 5);
    }

    // 06: Maximum Depth of Binary Tree
    // Time: O(N) | Space: O(H) call stack
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    @Test
    @DisplayName("06: Maximum Depth of Binary Tree")
    void ex06_maxDepth() {
        TreeNode root1 = TreeNode.of(3, 9, 20, null, null, 15, 7);
        assertThat(maxDepth(root1)).isEqualTo(3);

        TreeNode root2 = TreeNode.of(1, null, 2);
        assertThat(maxDepth(root2)).isEqualTo(2);

        assertThat(maxDepth(null)).isEqualTo(0);
    }

    // 07: Binary Tree Level Order Traversal (BFS)
    // Time: O(N) | Space: O(N)
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>(levelSize);

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(currentLevel);
        }
        return result;
    }

    @Test
    @DisplayName("07: Binary Tree Level Order Traversal (BFS)")
    void ex07_levelOrder() {
        TreeNode root1 = TreeNode.of(3, 9, 20, null, null, 15, 7);
        assertThat(levelOrder(root1)).containsExactly(
                List.of(3),
                List.of(9, 20),
                List.of(15, 7)
        );

        assertThat(levelOrder(TreeNode.of(1))).containsExactly(List.of(1));
        assertThat(levelOrder(null)).isEmpty();
    }

    // 08: Invert Binary Tree
    // Time: O(N) | Space: O(H) call stack
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    @Test
    @DisplayName("08: Invert Binary Tree")
    void ex08_invertTree() {
        TreeNode root = TreeNode.of(4, 2, 7, 1, 3, 6, 9);
        TreeNode inverted = invertTree(root);
        assertThat(inverted.toLevelOrderList()).containsExactly(4, 7, 2, 9, 6, 3, 1);

        assertThat(invertTree(null)).isNull();
    }

    // 09: Symmetric Tree
    // Time: O(N) | Space: O(H)
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return (t1.val == t2.val)
                && isMirror(t1.left, t2.right)
                && isMirror(t1.right, t2.left);
    }

    @Test
    @DisplayName("09: Symmetric Tree")
    void ex09_isSymmetric() {
        TreeNode root1 = TreeNode.of(1, 2, 2, 3, 4, 4, 3);
        assertThat(isSymmetric(root1)).isTrue();

        TreeNode root2 = TreeNode.of(1, 2, 2, null, 3, null, 3);
        assertThat(isSymmetric(root2)).isFalse();
    }

    // 10: Lowest Common Ancestor of a BST
    // Time: O(H) where H is height | Space: O(1) iterative
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;
        while (curr != null) {
            if (p.val < curr.val && q.val < curr.val) {
                curr = curr.left;
            } else if (p.val > curr.val && q.val > curr.val) {
                curr = curr.right;
            } else {
                return curr; // Split point or direct match
            }
        }
        return null;
    }

    @Test
    @DisplayName("10: Lowest Common Ancestor of a BST")
    void ex10_lowestCommonAncestor() {
        TreeNode root = TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
        TreeNode p = root.left; // 2
        TreeNode q = root.right; // 8
        assertThat(lowestCommonAncestor(root, p, q).val).isEqualTo(6);

        TreeNode p2 = root.left; // 2
        TreeNode q2 = root.left.right; // 4
        assertThat(lowestCommonAncestor(root, p2, q2).val).isEqualTo(2);
    }
}
