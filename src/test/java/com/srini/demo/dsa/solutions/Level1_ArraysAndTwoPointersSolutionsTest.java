package com.srini.demo.dsa.solutions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level 1 Solutions: Arrays & Two Pointers")
public class Level1_ArraysAndTwoPointersSolutionsTest {

    // 01: Two Sum II (Sorted Array)
    // Time: O(N) | Space: O(1)
    public int[] twoSumSorted(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1}; // 1-indexed
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
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

    // 02: Valid Palindrome
    // Time: O(N) | Space: O(1)
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
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

    // 03: Reverse Vowels of a String
    // Time: O(N) | Space: O(N) for char array
    public String reverseVowels(String s) {
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        String vowels = "aeiouAEIOU";

        while (left < right) {
            while (left < right && vowels.indexOf(chars[left]) == -1) {
                left++;
            }
            while (left < right && vowels.indexOf(chars[right]) == -1) {
                right--;
            }
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

    @Test
    @DisplayName("03: Reverse Vowels of a String")
    void ex03_reverseVowels() {
        assertThat(reverseVowels("IceCreAm")).isEqualTo("AceCreIm");
        assertThat(reverseVowels("leetcode")).isEqualTo("leotcede");
        assertThat(reverseVowels("a.")).isEqualTo("a.");
    }

    // 04: Container With Most Water
    // Time: O(N) | Space: O(1)
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int max = 0;
        while (left < right) {
            int width = right - left;
            int minHeight = Math.min(height[left], height[right]);
            max = Math.max(max, width * minHeight);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return max;
    }

    @Test
    @DisplayName("04: Container With Most Water")
    void ex04_containerWithMostWater() {
        assertThat(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7})).isEqualTo(49);
        assertThat(maxArea(new int[]{1, 1})).isEqualTo(1);
        assertThat(maxArea(new int[]{4, 3, 2, 1, 4})).isEqualTo(16);
    }

    // 05: Remove Duplicates from Sorted Array
    // Time: O(N) | Space: O(1)
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                nums[++slow] = nums[fast];
            }
        }
        return slow + 1;
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

    // 06: Move Zeroes
    // Time: O(N) | Space: O(1)
    public void moveZeroes(int[] nums) {
        int writeIndex = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[writeIndex++] = num;
            }
        }
        while (writeIndex < nums.length) {
            nums[writeIndex++] = 0;
        }
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

    // 07: Maximum Subarray Sum (Kadane's Algorithm)
    // Time: O(N) | Space: O(1)
    public int maxSubArray(int[] nums) {
        int currentSum = nums[0];
        int maxSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }

    @Test
    @DisplayName("07: Maximum Subarray Sum (Kadane's)")
    void ex07_maxSubArray() {
        assertThat(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})).isEqualTo(6);
        assertThat(maxSubArray(new int[]{1})).isEqualTo(1);
        assertThat(maxSubArray(new int[]{5, 4, -1, 7, 8})).isEqualTo(23);
        assertThat(maxSubArray(new int[]{-5, -2, -8})).isEqualTo(-2);
    }

    // 08: Product of Array Except Self
    // Time: O(N) | Space: O(1) extra space (output array doesn't count as extra per convention)
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        // 1st pass: res[i] contains prefix product of all elements to the left
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }

        // 2nd pass: maintain running suffix product from the right
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            res[i] = res[i] * rightProduct;
            rightProduct *= nums[i];
        }
        return res;
    }

    @Test
    @DisplayName("08: Product of Array Except Self")
    void ex08_productExceptSelf() {
        assertThat(productExceptSelf(new int[]{1, 2, 3, 4})).containsExactly(24, 12, 8, 6);
        assertThat(productExceptSelf(new int[]{-1, 1, 0, -3, 3})).containsExactly(0, 0, 9, 0, 0);
    }

    // 09: 3Sum
    // Time: O(N^2) | Space: O(1) extra (ignoring output)
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            // Early exit: if smallest element is > 0, sum can't be 0
            if (nums[i] > 0) break;
            // Skip duplicate outer elements
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1, right = n - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(List.of(nums[i], nums[left], nums[right]));
                    // Skip duplicates for left & right
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return res;
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

    // 10: Rotate Array In-Place
    // Time: O(N) | Space: O(1)
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        // 1. Reverse entire array
        reverse(nums, 0, n - 1);
        // 2. Reverse first k elements
        reverse(nums, 0, k - 1);
        // 3. Reverse remaining n - k elements
        reverse(nums, k, n - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
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
        rotate(nums3, 5);
        assertThat(nums3).containsExactly(2, 1);
    }
}
