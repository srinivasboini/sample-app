package com.srini.demo.dsa.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Binary Tree node for DSA practice.
 * Supports level-order factory creation (e.g., TreeNode.of(3, 9, 20, null, null, 15, 7)).
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /**
     * Factory method: constructs a binary tree from level-order representation with nulls.
     * Example: TreeNode.of(1, null, 2, 3)
     */
    public static TreeNode of(Integer... values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }

        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode current = queue.poll();

            // Left child
            if (i < values.length) {
                if (values[i] != null) {
                    current.left = new TreeNode(values[i]);
                    queue.offer(current.left);
                }
                i++;
            }

            // Right child
            if (i < values.length) {
                if (values[i] != null) {
                    current.right = new TreeNode(values[i]);
                    queue.offer(current.right);
                }
                i++;
            }
        }

        return root;
    }

    /**
     * Converts tree to level-order list (including trailing null omissions).
     */
    public List<Integer> toLevelOrderList() {
        List<Integer> result = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            result.add(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode other = (TreeNode) o;
        return isSame(this, other);
    }

    private static boolean isSame(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return p.val == q.val && isSame(p.left, q.left) && isSame(p.right, q.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, left != null ? left.val : 0, right != null ? right.val : 0);
    }

    @Override
    public String toString() {
        return "TreeNode(" + val + ")";
    }
}
