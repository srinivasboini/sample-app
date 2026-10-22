package com.srini.demo.dsa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * LEVEL 1: ARRAYS & TWO POINTERS (THE FUNDAMENTAL TOOLKIT)
 * ════════════════════════════════════════════════════════════════════════════
 * Focus:
 *  - Opposite-ends two pointers (shrinking window / collision)
 *  - Fast & Slow pointers (in-place array modifications)
 *  - Prefix & Suffix techniques (Kadane's algorithm, Product Except Self)
 *  - Array transformations (Reverse trick for rotations)
 *
 * Mental Model & Memorization Anchors:
 *  - Opposite ends: `int left = 0, right = n - 1; while (left < right) { ... }`
 *  - Fast/Slow: `int slow = 0; for (int fast = 0; fast < n; fast++) { if (keep) nums[slow++] = nums[fast]; }`
 *  - Kadane: `currSum = Math.max(x, currSum + x); maxSum = Math.max(maxSum, currSum);`
 *
 * Instructions:
 *  1. Replace the TODOs with your implementation.
 *  2. Run the test:
 *     ./mvnw test -Dtest=Level1_ArraysAndTwoPointersTest#ex01_twoSumSorted
 *  3. Compare with 'solutions/Level1_ArraysAndTwoPointersSolutionsTest.java'.
 */
@DisplayName("Level 1: Arrays & Two Pointers")
public class Level1_ArraysAndTwoPointersTest {

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 01: TWO SUM II - INPUT ARRAY IS SORTED (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Opposite Ends Two Pointers.
     * Given a 1-indexed sorted array of integers `numbers`, find two numbers that sum to `target`.
     * Return 1-based indices [index1, index2]. Exactly one solution exists. O(1) extra space.
     *
     * Snippet to memorize:
     *   int l = 0, r = nums.length - 1;
     *   while (l < r) {
     *       int sum = nums[l] + nums[r];
     *       if (sum == target) return new int[]{l + 1, r + 1};
     *       else if (sum < target) l++;
     *       else r--;
     *   }
     */
    public int[] twoSumSorted(int[] numbers, int target) {
        // TODO: Implement using opposite-ends two pointers
        /*int left=0;
        int right = numbers.length - 1 ;

        while(left < right){
            int sum = numbers[left] + numbers[right] ;
            if(sum == target){
                return new int[]{left + 1, right +1};
            }else if(sum < target){
                left++;
            }else{
                right--;
            }
        }*/
        Map<Integer, Integer> freq = new HashMap<>();
        for(int i=0; i<numbers.length; i++){
            int complement = target - numbers[i]; // 9 - 2 = 7
            if(freq.containsKey(complement)){
                return new int[]{freq.get(complement) + 1, i + 1};
            }

            freq.put(numbers[i], i);
        }


        return new int[]{};
    }

    @Test
    @DisplayName("01: Two Sum II - Sorted Array")
    void ex01_twoSumSorted() {
        assertThat(twoSumSorted(new int[]{2, 7, 11, 15}, 9)).containsExactly(1, 2);
        assertThat(twoSumSorted(new int[]{2, 3, 4}, 6)).containsExactly(1, 3);
        assertThat(twoSumSorted(new int[]{-1, 0}, -1)).containsExactly(1, 2);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 02: VALID PALINDROME (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Two Pointers skipping non-alphanumeric chars.
     * Return true if `s` is a palindrome, considering only letters/digits and ignoring cases.
     *
     */
    public boolean isPalindrome(String s) {
        // TODO: Implement using two pointers from outer ends
        s = s.toLowerCase();
        int left =0;
        int right = s.length() - 1;
        while(left < right){
            if(!Character.isLetterOrDigit(s.charAt(left))){
                left++;
                continue;
            }
            if(!Character.isLetterOrDigit(s.charAt(right))){
                right--;
                continue;
            }

            if(s.charAt(left) == s.charAt(right)){
                left++;
                right--;
                continue;
            }else{
                return false;
            }

        }


        return true;
    }

    @Test
    @DisplayName("02: Valid Palindrome")
    void ex02_validPalindrome() {
        assertThat(isPalindrome("A man, a plan, a canal: Panama")).isTrue();
        assertThat(isPalindrome("race a car")).isFalse();
        assertThat(isPalindrome(" ")).isTrue();
        assertThat(isPalindrome("0P")).isFalse();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 03: REVERSE VOWELS OF A STRING (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Two pointers converging inward to swap matching elements.
     * Given string `s`, reverse only all the vowels in the string.
     */
    public String reverseVowels(String s) {
        // TODO: Implement by scanning inward and swapping vowels
        int left = 0;
        int right = s.length() - 1;

        char[] chars = s.toCharArray(); // leetcode = [l, e,e,t,c,o,d,e]
        while(left < right){
            if(!isVowel(chars[left])){
                left++;
            }

            if(!isVowel(chars[right])){
                right--;
            }


            if(isVowel(chars[left]) && isVowel(chars[right])){
                char temp = chars[left];
                chars[left] = chars[right];
                chars[right] = temp;
                left++;
                right--;
            }

        }

        return new String(chars);
    }

    private boolean isVowel(char c){
        return "aeiouAEIOU".indexOf(c) != -1;
    }

    @Test
    @DisplayName("03: Reverse Vowels of a String")
    void ex03_reverseVowels() {
        assertThat(reverseVowels("IceCreAm")).isEqualTo("AceCreIm");
        assertThat(reverseVowels("leetcode")).isEqualTo("leotcede");
        assertThat(reverseVowels("a.")).isEqualTo("a.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 04: CONTAINER WITH MOST WATER (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Greedy Two Pointers.
     * Given array `height`, find two lines that together with x-axis form a container
     * containing the most water: area = (right - left) * min(height[left], height[right]).
     *
     * Intuition: Moving the taller side can never increase area; moving the shorter side might.
     */
    public int maxArea(int[] height) {
        // TODO: Implement greedy two pointers
        return 0;
    }

    @Test
    @DisplayName("04: Container With Most Water")
    void ex04_containerWithMostWater() {
        assertThat(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7})).isEqualTo(49);
        assertThat(maxArea(new int[]{1, 1})).isEqualTo(1);
        assertThat(maxArea(new int[]{4, 3, 2, 1, 4})).isEqualTo(16);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 05: REMOVE DUPLICATES FROM SORTED ARRAY (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Fast & Slow pointers for in-place array modification.
     * Remove duplicates in-place such that each unique element appears only once.
     * Return $k$ (number of unique elements). First $k$ elements must hold the result.
     *
     * Snippet to memorize:
     *   int slow = 0;
     *   for (int fast = 1; fast < n; fast++) {
     *       if (nums[fast] != nums[slow]) {
     *           nums[++slow] = nums[fast];
     *       }
     *   }
     *   return slow + 1;
     */
    public int removeDuplicates(int[] nums) {
        // TODO: Implement in-place slow/fast runner
        return 0;
    }

    @Test
    @DisplayName("05: Remove Duplicates from Sorted Array")
    void ex05_removeDuplicates() {
        int[] nums1 = {1, 1, 2};
        int k1 = removeDuplicates(nums1);
        assertThat(k1).isEqualTo(2);
        assertThat(Arrays.copyOf(nums1, k1)).containsExactly(1, 2);

        int[] nums2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int k2 = removeDuplicates(nums2);
        assertThat(k2).isEqualTo(5);
        assertThat(Arrays.copyOf(nums2, k2)).containsExactly(0, 1, 2, 3, 4);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 06: MOVE ZEROES (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: In-place partition / write-pointer.
     * Move all 0's to the end of array `nums` while maintaining relative order of non-zeroes.
     * Must be done in-place without making a copy of the array.
     */
    public void moveZeroes(int[] nums) {
        // TODO: Shift non-zeros forward with write pointer, then pad trailing zeroes
    }

    @Test
    @DisplayName("06: Move Zeroes")
    void ex06_moveZeroes() {
        int[] nums1 = {0, 1, 0, 3, 12};
        moveZeroes(nums1);
        assertThat(nums1).containsExactly(1, 3, 12, 0, 0);

        int[] nums2 = {0};
        moveZeroes(nums2);
        assertThat(nums2).containsExactly(0);

        int[] nums3 = {2, 1};
        moveZeroes(nums3);
        assertThat(nums3).containsExactly(2, 1);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 07: MAXIMUM SUBARRAY SUM - KADANE'S ALGORITHM (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Kadane's Algorithm (Dynamic Programming on Arrays).
     * Find the contiguous subarray (containing at least one number) which has the largest sum.
     *
     * Snippet to memorize:
     *   int curr = nums[0], max = nums[0];
     *   for (int i = 1; i < nums.length; i++) {
     *       curr = Math.max(nums[i], curr + nums[i]);
     *       max = Math.max(max, curr);
     *   }
     *   return max;
     */
    public int maxSubArray(int[] nums) {
        // TODO: Implement Kadane's algorithm
        return 0;
    }

    @Test
    @DisplayName("07: Maximum Subarray Sum (Kadane's)")
    void ex07_maxSubArray() {
        assertThat(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})).isEqualTo(6); // [4,-1,2,1]
        assertThat(maxSubArray(new int[]{1})).isEqualTo(1);
        assertThat(maxSubArray(new int[]{5, 4, -1, 7, 8})).isEqualTo(23);
        assertThat(maxSubArray(new int[]{-5, -2, -8})).isEqualTo(-2);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 08: PRODUCT OF ARRAY EXCEPT SELF (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Prefix & Suffix Products in O(N) time with O(1) extra space.
     * Given an integer array `nums`, return an array `answer` such that `answer[i]` is equal
     * to the product of all the elements of `nums` except `nums[i]`.
     * Constraint: DO NOT use division operation!
     */
    public int[] productExceptSelf(int[] nums) {
        // TODO: 1st pass build prefix products, 2nd pass multiply suffix product
        return new int[]{};
    }

    @Test
    @DisplayName("08: Product of Array Except Self")
    void ex08_productExceptSelf() {
        assertThat(productExceptSelf(new int[]{1, 2, 3, 4})).containsExactly(24, 12, 8, 6);
        assertThat(productExceptSelf(new int[]{-1, 1, 0, -3, 3})).containsExactly(0, 0, 9, 0, 0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 09: 3SUM (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Sort + Two Pointers with Duplicate Pruning.
     * Return all unique triplets [nums[i], nums[j], nums[k]] such that:
     *   i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
     * Solution set must not contain duplicate triplets.
     */
    public List<List<Integer>> threeSum(int[] nums) {
        // TODO: Sort array, loop i, then two pointers (left, right) skipping duplicate values
        return new ArrayList<>();
    }

    @Test
    @DisplayName("09: 3Sum")
    void ex09_threeSum() {
        List<List<Integer>> res1 = threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        assertThat(res1).containsExactlyInAnyOrder(
                List.of(-1, -1, 2),
                List.of(-1, 0, 1)
        );

        List<List<Integer>> res2 = threeSum(new int[]{0, 1, 1});
        assertThat(res2).isEmpty();

        List<List<Integer>> res3 = threeSum(new int[]{0, 0, 0});
        assertThat(res3).containsExactly(List.of(0, 0, 0));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 10: ROTATE ARRAY IN-PLACE (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Array Reversal Trick.
     * Rotate array to the right by `k` steps in-place with O(1) extra space.
     *
     * Snippet to memorize:
     *   k %= n;
     *   reverse(nums, 0, n - 1);
     *   reverse(nums, 0, k - 1);
     *   reverse(nums, k, n - 1);
     */
    public void rotate(int[] nums, int k) {
        // TODO: Implement 3-reversal trick
    }

    @Test
    @DisplayName("10: Rotate Array In-Place")
    void ex10_rotate() {
        int[] nums1 = {1, 2, 3, 4, 5, 6, 7};
        rotate(nums1, 3);
        assertThat(nums1).containsExactly(5, 6, 7, 1, 2, 3, 4);

        int[] nums2 = {-1, -100, 3, 99};
        rotate(nums2, 2);
        assertThat(nums2).containsExactly(3, 99, -1, -100);

        int[] nums3 = {1, 2};
        rotate(nums3, 5); // k > n
        assertThat(nums3).containsExactly(2, 1);
    }
}
