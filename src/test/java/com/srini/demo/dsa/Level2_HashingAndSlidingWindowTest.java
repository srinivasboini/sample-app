package com.srini.demo.dsa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * LEVEL 2: HASHING & SLIDING WINDOW (LOOKUPS & SUBARRAYS)
 * ════════════════════════════════════════════════════════════════════════════
 * Focus:
 *  - O(1) Lookups & Complement search (Two Sum, Contains Duplicate, Anagrams)
 *  - Frequency Counting (First Unique Character, Group Anagrams)
 *  - Prefix Sum + HashMap (Subarray Sum Equals K)
 *  - Fixed Sliding Window (Max sum subarray of size K)
 *  - Variable Sliding Window (Longest Substring Without Repeating, Min Size Subarray Sum)
 *  - Intelligent Hash Search (Longest Consecutive Sequence)
 *
 * Mental Model & Memorization Anchors:
 *  - Fixed Window:
 *      for (int i = 0; i < k; i++) sum += nums[i];
 *      for (int i = k; i < n; i++) { sum += nums[i] - nums[i - k]; ans = max(ans, sum); }
 *  - Variable Window:
 *      int left = 0;
 *      for (int right = 0; right < n; right++) {
 *          // expand: add nums[right] to window state
 *          while (windowIsInvalid) {
 *              // shrink: remove nums[left] from window state
 *              left++;
 *          }
 *          // update answer: right - left + 1
 *      }
 *  - Prefix Sum + Map:
 *      map.put(0, 1);
 *      for (int x : nums) {
 *          currSum += x;
 *          count += map.getOrDefault(currSum - k, 0);
 *          map.put(currSum, map.getOrDefault(currSum, 0) + 1);
 *      }
 */
@DisplayName("Level 2: Hashing & Sliding Window")
public class Level2_HashingAndSlidingWindowTest {

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 01: TWO SUM - UNSORTED ARRAY (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Hash Map Complement Lookup.
     * Given an array of integers `nums` and an integer `target`, return indices
     * of the two numbers such that they add up to target.
     * Must run in O(N) time with a single pass.
     *
     * Snippet to memorize:
     *   Map<Integer, Integer> map = new HashMap<>();
     *   for (int i = 0; i < nums.length; i++) {
     *       int complement = target - nums[i];
     *       if (map.containsKey(complement)) return new int[]{map.get(complement), i};
     *       map.put(nums[i], i);
     *   }
     */
    public int[] twoSum(int[] nums, int target) {
        // TODO: Implement single-pass complement lookup
        return new int[]{};
    }

    @Test
    @DisplayName("01: Two Sum (Unsorted)")
    void ex01_twoSum() {
        assertThat(twoSum(new int[]{2, 7, 11, 15}, 9)).containsExactly(0, 1);
        assertThat(twoSum(new int[]{3, 2, 4}, 6)).containsExactly(1, 2);
        assertThat(twoSum(new int[]{3, 3}, 6)).containsExactly(0, 1);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 02: CONTAINS DUPLICATE (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: HashSet Existence Check.
     * Return true if any value appears at least twice in the array, false if distinct.
     */
    public boolean containsDuplicate(int[] nums) {
        // TODO: Implement using HashSet
        return false;
    }

    @Test
    @DisplayName("02: Contains Duplicate")
    void ex02_containsDuplicate() {
        assertThat(containsDuplicate(new int[]{1, 2, 3, 1})).isTrue();
        assertThat(containsDuplicate(new int[]{1, 2, 3, 4})).isFalse();
        assertThat(containsDuplicate(new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2})).isTrue();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 03: VALID ANAGRAM (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Character Frequency Array (Alphabet Bucket).
     * Given two strings `s` and `t`, return true if `t` is an anagram of `s`.
     *
     * Memory tip: Use int[26] counting chars (+ for s, - for t).
     */
    public boolean isAnagram(String s, String t) {
        // TODO: Implement using 26-char frequency counter
        int[] freq = new int[26];
        char[] sArray = s.toCharArray();
        for(char c : sArray){
            int idx = c - 'a';
            freq[idx]++;
        }

        char[] tArray = t.toCharArray();
        for(char c : tArray ){
            int idx = c - 'a';
            freq[idx]--;
        }

        for(int i : freq){
            if(i > 0){
                return false;
            }
        }
        return true;

    }

    @Test
    @DisplayName("03: Valid Anagram")
    void ex03_isAnagram() {
        assertThat(isAnagram("anagram", "nagaram")).isTrue();
        assertThat(isAnagram("rat", "car")).isFalse();
        assertThat(isAnagram("listen", "silent")).isTrue();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 04: GROUP ANAGRAMS (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Canonical Key Representation.
     * Given an array of strings `strs`, group the anagrams together.
     *
     * Intuition: Sorted character string (or character count signature) acts as the map key.
     */
    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();

        for(String str: strs){

            char[] strArr = str.toCharArray();
            Arrays.sort(strArr);
            String strSorted = new String(strArr);

            if(map.containsKey(strSorted)){
                List<String> value = map.get(strSorted);
                value.add(str);
            }else{
                List<String> value = new ArrayList<>();
                value.add(str);
                map.put(strSorted, value);
            }


        }


        return map.values().stream().collect(Collectors.toList());


    }

    @Test
    @DisplayName("04: Group Anagrams")
    void ex04_groupAnagrams() {
        List<List<String>> groups = groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
        // Normalize for assertion
        List<List<String>> normalized = groups.stream()
                .map(g -> g.stream().sorted().toList())
                .sorted(Comparator.comparing(List::toString))
                .toList();

        assertThat(normalized).containsExactly(
                List.of("ate", "eat", "tea"),
                List.of("bat"),
                List.of("nat", "tan")
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 05: FIRST UNIQUE CHARACTER IN A STRING (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Two-Pass Frequency Array.
     * Given a string `s`, find the first non-repeating character and return its index.
     * If it does not exist, return -1.
     */
    public int firstUniqChar(String s) {
        // TODO: Pass 1 count frequencies, Pass 2 find first with count 1
        return -1;
    }

    @Test
    @DisplayName("05: First Unique Character")
    void ex05_firstUniqChar() {
        assertThat(firstUniqChar("leetcode")).isEqualTo(0);
        assertThat(firstUniqChar("loveleetcode")).isEqualTo(2);
        assertThat(firstUniqChar("aabb")).isEqualTo(-1);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 06: SUBARRAY SUM EQUALS K (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Prefix Sum + Hash Map (Crucial DSA Archetype!).
     * Given an array of integers `nums` and an integer `k`, return the total number
     * of subarrays whose sum equals to `k`.
     *
     * Intuition:
     *   sum(i, j) = prefixSum[j] - prefixSum[i - 1] == k
     *   prefixSum[i - 1] == prefixSum[j] - k.
     *   Store count of each prefix sum seen so far in a HashMap! Initialize map.put(0, 1).
     */
    public int subarraySum(int[] nums, int k) {
        // TODO: Implement prefix sum frequency map
        return 0;
    }

    @Test
    @DisplayName("06: Subarray Sum Equals K")
    void ex06_subarraySum() {
        assertThat(subarraySum(new int[]{1, 1, 1}, 2)).isEqualTo(2);
        assertThat(subarraySum(new int[]{1, 2, 3}, 3)).isEqualTo(2); // [1,2] and [3]
        assertThat(subarraySum(new int[]{1, -1, 0}, 0)).isEqualTo(3);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 07: MAXIMUM SUM SUBARRAY OF SIZE K (EASY)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Fixed Sliding Window.
     * Given an array of positive integers and a positive integer `k`,
     * find the maximum sum of any contiguous subarray of size `k`.
     */
    public int maxSumSubarrayFixed(int[] nums, int k) {
        // TODO: Compute initial window sum of k, then slide by +nums[i] -nums[i-k]
        return 0;
    }

    @Test
    @DisplayName("07: Maximum Sum Subarray of Size K (Fixed Window)")
    void ex07_maxSumSubarrayFixed() {
        assertThat(maxSumSubarrayFixed(new int[]{2, 1, 5, 1, 3, 2}, 3)).isEqualTo(9); // [5,1,3]
        assertThat(maxSumSubarrayFixed(new int[]{2, 3, 4, 1, 5}, 2)).isEqualTo(7); // [3,4]
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 08: LONGEST SUBSTRING WITHOUT REPEATING CHARACTERS (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Variable Sliding Window with Character Index Map / Set.
     * Given a string `s`, find the length of the longest substring without repeating characters.
     *
     * Snippet to memorize:
     *   Map<Character, Integer> lastSeen = new HashMap<>();
     *   int maxLen = 0, left = 0;
     *   for (int right = 0; right < s.length(); right++) {
     *       char c = s.charAt(right);
     *       if (lastSeen.containsKey(c)) {
     *           left = Math.max(left, lastSeen.get(c) + 1);
     *       }
     *       lastSeen.put(c, right);
     *       maxLen = Math.max(maxLen, right - left + 1);
     *   }
     *   return maxLen;
     */
    public int lengthOfLongestSubstring(String s) {
        // TODO: Variable sliding window tracking characters
        return 0;
    }

    @Test
    @DisplayName("08: Longest Substring Without Repeating Characters")
    void ex08_lengthOfLongestSubstring() {
        assertThat(lengthOfLongestSubstring("abcabcbb")).isEqualTo(3); // "abc"
        assertThat(lengthOfLongestSubstring("bbbbb")).isEqualTo(1); // "b"
        assertThat(lengthOfLongestSubstring("pwwkew")).isEqualTo(3); // "wke"
        assertThat(lengthOfLongestSubstring("")).isEqualTo(0);
        assertThat(lengthOfLongestSubstring("abba")).isEqualTo(2); // edge case: 'a' reappears after left moved
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 09: MINIMUM SIZE SUBARRAY SUM (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Variable Sliding Window (Shrinking to minimize).
     * Given an array of positive integers `nums` and a positive integer `target`,
     * return the minimal length of a contiguous subarray whose sum is greater than or
     * equal to `target`. If there is no such subarray, return 0 instead.
     */
    public int minSubArrayLen(int target, int[] nums) {
        // TODO: Expand right to reach sum >= target, then shrink left to find minimal window
        return 0;
    }

    @Test
    @DisplayName("09: Minimum Size Subarray Sum")
    void ex09_minSubArrayLen() {
        assertThat(minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3})).isEqualTo(2); // [4,3]
        assertThat(minSubArrayLen(4, new int[]{1, 4, 4})).isEqualTo(1);
        assertThat(minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1})).isEqualTo(0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXERCISE 10: LONGEST CONSECUTIVE SEQUENCE (MEDIUM)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Pattern: Intelligent HashSet Sequence Detection.
     * Given an unsorted array of integers `nums`, return the length of the longest
     * consecutive elements sequence in O(N) time.
     *
     * Trick to achieve O(N):
     *   Only begin counting if `num` is the START of a sequence (i.e., `!set.contains(num - 1)`).
     */
    public int longestConsecutive(int[] nums) {
        // TODO: Add all to HashSet, iterate and expand only if (!set.contains(x - 1))
        return 0;
    }

    @Test
    @DisplayName("10: Longest Consecutive Sequence")
    void ex10_longestConsecutive() {
        assertThat(longestConsecutive(new int[]{100, 4, 200, 1, 3, 2})).isEqualTo(4); // [1,2,3,4]
        assertThat(longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1})).isEqualTo(9); // [0..8]
        assertThat(longestConsecutive(new int[]{})).isEqualTo(0);
    }
}
