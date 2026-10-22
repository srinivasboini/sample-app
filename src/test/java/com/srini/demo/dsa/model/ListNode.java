package com.srini.demo.dsa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Singly-linked list node for DSA practice.
 * Includes helper methods for effortless test construction and assertions.
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    /**
     * Factory method: create a linked list from varargs.
     * Example: ListNode.of(1, 2, 3) -> 1 -> 2 -> 3 -> null
     */
    public static ListNode of(int... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int v : values) {
            current.next = new ListNode(v);
            current = current.next;
        }
        return dummy.next;
    }

    /**
     * Converts linked list to a List<Integer> for easy AssertJ assertions.
     * Note: Do not call on cyclic lists!
     */
    public List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        ListNode curr = this;
        int guard = 0;
        while (curr != null && guard < 10_000) {
            list.add(curr.val);
            curr = curr.next;
            guard++;
        }
        if (guard == 10_000) {
            throw new IllegalStateException("Potential cycle detected in ListNode.toList()");
        }
        return list;
    }

    /**
     * Helper to get node at 0-based index. Useful for creating test cycles.
     */
    public ListNode getNodeAt(int index) {
        ListNode curr = this;
        int count = 0;
        while (curr != null && count < index) {
            curr = curr.next;
            count++;
        }
        return curr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListNode other = (ListNode) o;
        ListNode a = this;
        ListNode b = other;
        while (a != null && b != null) {
            if (a.val != b.val) return false;
            a = a.next;
            b = b.next;
        }
        return a == null && b == null;
    }

    @Override
    public int hashCode() {
        int result = 1;
        ListNode curr = this;
        int guard = 0;
        while (curr != null && guard < 1000) {
            result = 31 * result + curr.val;
            curr = curr.next;
            guard++;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode curr = this;
        int count = 0;
        while (curr != null && count < 20) {
            sb.append(curr.val);
            if (curr.next != null) sb.append(" -> ");
            curr = curr.next;
            count++;
        }
        if (curr != null) sb.append(" -> (truncated)");
        return sb.toString();
    }
}
