package com.srini.demo.dsa;

import com.srini.demo.dsa.model.ListNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * LEVEL 4: LINKED LISTS (POINTERS, IN-PLACE REVERSALS & CYCLES)
 * ════════════════════════════════════════════════════════════════════════════
 * Focus:
 *  - Standard In-place Reversal (3-pointer: prev, curr, next)
 *  - Fast & Slow Pointers / Tortoise & Hare (Midpoint, Cycle detection)
 *  - Sentinel / Dummy Head pattern (prevents null head edge cases)
 *  - List Merging & Two Pointers (Merge sorted, Intersection)
 *  - Composite List Operations (Palindrome check, Reorder list, Add two numbers)
 *
 * Mental Model & Memorization Anchors:
 *  - In-place Reversal:
 *      ListNode prev = null, curr = head;
 *      while (curr != null) {
 *          ListNode next = curr.next;
 *          curr.next = prev;
 *          prev = curr;
 *          curr = next;
 *      }
 *      return prev;
 *  - Fast & Slow Midpoint:
 *      ListNode slow = head, fast = head;
 *      while (fast != null && fast.next != null) {
 *          slow = slow.next;
 *          fast = fast.next.next;
 *      }
 *  - Dummy Head Pattern:
 *      ListNode dummy = new ListNode(0);
 *      ListNode tail = dummy;
 *      // append to tail...
 *      return dummy.next;
 */
@DisplayName("Level 4: Linked Lists")
public class Level4_LinkedListsTest {

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 01: REVERSE LINKED LIST (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: In-Place 3-Pointer Reversal.
     * Given the head of a singly linked list, reverse the list, and return the reversed list.
     *
     * Snippet to memorize:
     *   ListNode prev = null, curr = head;
     *   while (curr != null) {
     *       ListNode next = curr.next;
     *       curr.next = prev;
     *       prev = curr;
     *       curr = next;
     *   }
     *   return prev;
     */
    public ListNode reverseList(ListNode head) {
        // TODO: Implement in-place reversal
        return null;
    }

    @Test
    @DisplayName("01: Reverse Linked List")
    void ex01_reverseList() {
        ListNode list1 = ListNode.of(1, 2, 3, 4, 5);
        assertThat(reverseList(list1).toList()).containsExactly(5, 4, 3, 2, 1);

        ListNode list2 = ListNode.of(1, 2);
        assertThat(reverseList(list2).toList()).containsExactly(2, 1);

        assertThat(reverseList(null)).isNull();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 02: MIDDLE OF THE LINKED LIST (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Fast & Slow Pointer (Tortoise & Hare).
     * Given the head of a singly linked list, return the middle node.
     * If there are two middle nodes, return the second middle node.
     */
    public ListNode middleNode(ListNode head) {
        // TODO: Advance slow by 1, fast by 2
        return null;
    }

    @Test
    @DisplayName("02: Middle of the Linked List")
    void ex02_middleNode() {
        ListNode list1 = ListNode.of(1, 2, 3, 4, 5);
        assertThat(middleNode(list1).val).isEqualTo(3);

        ListNode list2 = ListNode.of(1, 2, 3, 4, 5, 6);
        assertThat(middleNode(list2).val).isEqualTo(4); // second middle
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 03: LINKED LIST CYCLE (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Floyd's Cycle-Finding Algorithm.
     * Return true if there is a cycle in the linked list, false otherwise.
     * Must solve with O(1) extra space.
     */
    public boolean hasCycle(ListNode head) {
        // TODO: If fast and slow meet, there is a cycle
        return false;
    }

    @Test
    @DisplayName("03: Linked List Cycle Detection")
    void ex03_hasCycle() {
        // List: 3 -> 2 -> 0 -> -4 -> points back to node at index 1 (val 2)
        ListNode head = ListNode.of(3, 2, 0, -4);
        head.getNodeAt(3).next = head.getNodeAt(1);
        assertThat(hasCycle(head)).isTrue();

        ListNode noCycle = ListNode.of(1, 2, 3);
        assertThat(hasCycle(noCycle)).isFalse();

        assertThat(hasCycle(null)).isFalse();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 04: MERGE TWO SORTED LISTS (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Dummy Head + Two Pointers.
     * Merge two sorted linked lists and return it as a sorted list.
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // TODO: Use dummy node, compare heads, attach smaller, advance
        return null;
    }

    @Test
    @DisplayName("04: Merge Two Sorted Lists")
    void ex04_mergeTwoLists() {
        ListNode l1 = ListNode.of(1, 2, 4);
        ListNode l2 = ListNode.of(1, 3, 4);
        assertThat(mergeTwoLists(l1, l2).toList()).containsExactly(1, 1, 2, 3, 4, 4);

        assertThat(mergeTwoLists(null, ListNode.of(0)).toList()).containsExactly(0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 05: REMOVE NTH NODE FROM END OF LIST (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Fast & Slow Pointer with N-Step Gap + Dummy Node.
     * Given the head of a linked list, remove the n-th node from the end and return its head.
     * Single pass O(N) time with O(1) space.
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // TODO: Advance fast pointer by n steps from dummy, then advance slow and fast together
        return null;
    }

    @Test
    @DisplayName("05: Remove Nth Node From End of List")
    void ex05_removeNthFromEnd() {
        ListNode list1 = ListNode.of(1, 2, 3, 4, 5);
        assertThat(removeNthFromEnd(list1, 2).toList()).containsExactly(1, 2, 3, 5);

        ListNode list2 = ListNode.of(1);
        assertThat(removeNthFromEnd(list2, 1)).isNull();

        ListNode list3 = ListNode.of(1, 2);
        assertThat(removeNthFromEnd(list3, 1).toList()).containsExactly(1);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 06: DELETE DUPLICATES FROM SORTED LIST (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Adjacent Pointer Comparison.
     * Given the head of a sorted linked list, delete all duplicates such that
     * each element appears only once.
     */
    public ListNode deleteDuplicates(ListNode head) {
        // TODO: If curr.val == curr.next.val, skip curr.next
        return null;
    }

    @Test
    @DisplayName("06: Delete Duplicates from Sorted List")
    void ex06_deleteDuplicates() {
        ListNode list1 = ListNode.of(1, 1, 2);
        assertThat(deleteDuplicates(list1).toList()).containsExactly(1, 2);

        ListNode list2 = ListNode.of(1, 1, 2, 3, 3);
        assertThat(deleteDuplicates(list2).toList()).containsExactly(1, 2, 3);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 07: PALINDROME LINKED LIST (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Fast/Slow to Mid + Reverse Second Half + Compare.
     * Given head of a singly linked list, return true if it is a palindrome.
     * Must run in O(N) time and O(1) space.
     */
    public boolean isPalindrome(ListNode head) {
        // TODO: Find mid, reverse second half, compare, (optional restore)
        return false;
    }

    @Test
    @DisplayName("07: Palindrome Linked List")
    void ex07_isPalindrome() {
        assertThat(isPalindrome(ListNode.of(1, 2, 2, 1))).isTrue();
        assertThat(isPalindrome(ListNode.of(1, 2))).isFalse();
        assertThat(isPalindrome(ListNode.of(1, 2, 3, 2, 1))).isTrue();
        assertThat(isPalindrome(ListNode.of(1))).isTrue();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 08: INTERSECTION OF TWO LINKED LISTS (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Two Pointers Cycle-Alignment (A+B == B+A).
     * Given the heads of two singly linked-lists headA and headB, return the node
     * at which the two lists intersect. If they do not intersect, return null.
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // TODO: Pointer a traverses A then B; pointer b traverses B then A
        return null;
    }

    @Test
    @DisplayName("08: Intersection of Two Linked Lists")
    void ex08_getIntersectionNode() {
        ListNode common = ListNode.of(8, 4, 5);
        ListNode headA = ListNode.of(4, 1);
        headA.next.next = common;

        ListNode headB = ListNode.of(5, 6, 1);
        headB.next.next.next = common;

        assertThat(getIntersectionNode(headA, headB)).isSameAs(common);

        ListNode noIntersectA = ListNode.of(2, 6, 4);
        ListNode noIntersectB = ListNode.of(1, 5);
        assertThat(getIntersectionNode(noIntersectA, noIntersectB)).isNull();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 09: ADD TWO NUMBERS (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Elementary Math with Carry + Dummy Node.
     * Two non-empty linked lists representing two non-negative integers.
     * Digits are stored in reverse order. Add the two numbers and return sum as a linked list.
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // TODO: Sum digits + carry, create new nodes, carry = sum / 10
        return null;
    }

    @Test
    @DisplayName("09: Add Two Numbers")
    void ex09_addTwoNumbers() {
        // 342 + 465 = 807
        ListNode l1 = ListNode.of(2, 4, 3);
        ListNode l2 = ListNode.of(5, 6, 4);
        assertThat(addTwoNumbers(l1, l2).toList()).containsExactly(7, 0, 8);

        // 9999 + 999 = 10998
        ListNode a = ListNode.of(9, 9, 9, 9);
        ListNode b = ListNode.of(9, 9, 9);
        assertThat(addTwoNumbers(a, b).toList()).containsExactly(8, 9, 9, 0, 1);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 10: REORDER LIST (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Find Midpoint + Reverse Second Half + Interweave / Merge.
     * Reorder list: L0 → L1 → … → Ln - 1 → Ln into: L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 …
     * Modify list in-place without altering node values.
     */
    public void reorderList(ListNode head) {
        // TODO: Split at mid, reverse second half, merge alternately
    }

    @Test
    @DisplayName("10: Reorder List")
    void ex10_reorderList() {
        ListNode l1 = ListNode.of(1, 2, 3, 4);
        reorderList(l1);
        assertThat(l1.toList()).containsExactly(1, 4, 2, 3);

        ListNode l2 = ListNode.of(1, 2, 3, 4, 5);
        reorderList(l2);
        assertThat(l2.toList()).containsExactly(1, 5, 2, 4, 3);
    }
}
