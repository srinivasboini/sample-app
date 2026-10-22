package com.srini.demo.dsa.solutions;

import com.srini.demo.dsa.model.ListNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level 4 Solutions: Linked Lists")
public class Level4_LinkedListsSolutionsTest {

    // 01: Reverse Linked List
    // Time: O(N) | Space: O(1)
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
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

    // 02: Middle of the Linked List
    // Time: O(N) | Space: O(1)
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    @Test
    @DisplayName("02: Middle of the Linked List")
    void ex02_middleNode() {
        ListNode list1 = ListNode.of(1, 2, 3, 4, 5);
        assertThat(middleNode(list1).val).isEqualTo(3);

        ListNode list2 = ListNode.of(1, 2, 3, 4, 5, 6);
        assertThat(middleNode(list2).val).isEqualTo(4);
    }

    // 03: Linked List Cycle Detection
    // Time: O(N) | Space: O(1)
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    @Test
    @DisplayName("03: Linked List Cycle Detection")
    void ex03_hasCycle() {
        ListNode head = ListNode.of(3, 2, 0, -4);
        head.getNodeAt(3).next = head.getNodeAt(1);
        assertThat(hasCycle(head)).isTrue();

        ListNode noCycle = ListNode.of(1, 2, 3);
        assertThat(hasCycle(noCycle)).isFalse();

        assertThat(hasCycle(null)).isFalse();
    }

    // 04: Merge Two Sorted Lists
    // Time: O(N + M) | Space: O(1)
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                curr.next = list1;
                list1 = list1.next;
            } else {
                curr.next = list2;
                list2 = list2.next;
            }
            curr = curr.next;
        }

        curr.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }

    @Test
    @DisplayName("04: Merge Two Sorted Lists")
    void ex04_mergeTwoLists() {
        ListNode l1 = ListNode.of(1, 2, 4);
        ListNode l2 = ListNode.of(1, 3, 4);
        assertThat(mergeTwoLists(l1, l2).toList()).containsExactly(1, 1, 2, 3, 4, 4);

        assertThat(mergeTwoLists(null, ListNode.of(0)).toList()).containsExactly(0);
    }

    // 05: Remove Nth Node From End of List
    // Time: O(N) | Space: O(1)
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy;
        ListNode slow = dummy;

        // Advance fast by n + 1 steps
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // Move both until fast reaches null
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // slow is right before the target node to delete
        slow.next = slow.next.next;
        return dummy.next;
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

    // 06: Delete Duplicates from Sorted List
    // Time: O(N) | Space: O(1)
    public ListNode deleteDuplicates(ListNode head) {
        ListNode curr = head;
        while (curr != null && curr.next != null) {
            if (curr.val == curr.next.val) {
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
        }
        return head;
    }

    @Test
    @DisplayName("06: Delete Duplicates from Sorted List")
    void ex06_deleteDuplicates() {
        ListNode list1 = ListNode.of(1, 1, 2);
        assertThat(deleteDuplicates(list1).toList()).containsExactly(1, 2);

        ListNode list2 = ListNode.of(1, 1, 2, 3, 3);
        assertThat(deleteDuplicates(list2).toList()).containsExactly(1, 2, 3);
    }

    // 07: Palindrome Linked List
    // Time: O(N) | Space: O(1)
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        // 1. Find midpoint (slow stops at end of first half)
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse second half
        ListNode secondHalfHead = reverseList(slow.next);

        // 3. Compare first and second halves
        ListNode p1 = head;
        ListNode p2 = secondHalfHead;
        boolean isPal = true;
        while (p2 != null) {
            if (p1.val != p2.val) {
                isPal = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 4. Restore list (good engineering practice)
        slow.next = reverseList(secondHalfHead);
        return isPal;
    }

    @Test
    @DisplayName("07: Palindrome Linked List")
    void ex07_isPalindrome() {
        assertThat(isPalindrome(ListNode.of(1, 2, 2, 1))).isTrue();
        assertThat(isPalindrome(ListNode.of(1, 2))).isFalse();
        assertThat(isPalindrome(ListNode.of(1, 2, 3, 2, 1))).isTrue();
        assertThat(isPalindrome(ListNode.of(1))).isTrue();
    }

    // 08: Intersection of Two Linked Lists
    // Time: O(N + M) | Space: O(1)
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode pA = headA;
        ListNode pB = headB;

        while (pA != pB) {
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }
        return pA;
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

    // 09: Add Two Numbers
    // Time: O(max(N, M)) | Space: O(max(N, M))
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
        }
        return dummy.next;
    }

    @Test
    @DisplayName("09: Add Two Numbers")
    void ex09_addTwoNumbers() {
        ListNode l1 = ListNode.of(2, 4, 3);
        ListNode l2 = ListNode.of(5, 6, 4);
        assertThat(addTwoNumbers(l1, l2).toList()).containsExactly(7, 0, 8);

        ListNode a = ListNode.of(9, 9, 9, 9);
        ListNode b = ListNode.of(9, 9, 9);
        assertThat(addTwoNumbers(a, b).toList()).containsExactly(8, 9, 9, 0, 1);
    }

    // 10: Reorder List
    // Time: O(N) | Space: O(1)
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // 1. Find midpoint (slow stops at end of 1st half)
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse second half
        ListNode second = reverseList(slow.next);
        slow.next = null; // detach 1st half

        // 3. Merge alternately
        ListNode first = head;
        while (second != null) {
            ListNode tmp1 = first.next;
            ListNode tmp2 = second.next;

            first.next = second;
            second.next = tmp1;

            first = tmp1;
            second = tmp2;
        }
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
