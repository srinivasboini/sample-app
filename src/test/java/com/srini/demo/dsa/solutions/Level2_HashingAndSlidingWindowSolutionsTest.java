package com.srini.demo.dsa.solutions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level 2 Solutions: Hashing & Sliding Window")
public class Level2_HashingAndSlidingWindowSolutionsTest {

    // 01: Two Sum (Unsorted)
    // Time: O(N) | Space: O(N)
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    @Test
    @DisplayName("01: Two Sum (Unsorted)")
    void ex01_twoSum() {
        assertThat(twoSum(new int[]{2, 7, 11, 15}, 9)).containsExactly(0, 1);
        assertThat(twoSum(new int[]{3, 2, 4}, 6)).containsExactly(1, 2);
        assertThat(twoSum(new int[]{3, 3}, 6)).containsExactly(0, 1);
    }

    // 02: Contains Duplicate
    // Time: O(N) | Space: O(N)
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (!seen.add(num)) {
                return true;
            }
        }
        return false;
    }

    @Test
    @DisplayName("02: Contains Duplicate")
    void ex02_containsDuplicate() {
        assertThat(containsDuplicate(new int[]{1, 2, 3, 1})).isTrue();
        assertThat(containsDuplicate(new int[]{1, 2, 3, 4})).isFalse();
        assertThat(containsDuplicate(new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2})).isTrue();
    }

    // 03: Valid Anagram
    // Time: O(N) | Space: O(1) (fixed alphabet size of 26)
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }
        for (int c : count) {
            if (c != 0) return false;
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

    // 04: Group Anagrams
    // Time: O(N * K log K) where K is max string length | Space: O(N * K)
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    @Test
    @DisplayName("04: Group Anagrams")
    void ex04_groupAnagrams() {
        List<List<String>> groups = groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
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

    // 05: First Unique Character in a String
    // Time: O(N) | Space: O(1) (fixed alphabet size of 26)
    public int firstUniqChar(String s) {
        int[] freq = new int[26];
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < s.length(); i++) {
            if (freq[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }

    @Test
    @DisplayName("05: First Unique Character")
    void ex05_firstUniqChar() {
        assertThat(firstUniqChar("leetcode")).isEqualTo(0);
        assertThat(firstUniqChar("loveleetcode")).isEqualTo(2);
        assertThat(firstUniqChar("aabb")).isEqualTo(-1);
    }

    // 06: Subarray Sum Equals K
    // Time: O(N) | Space: O(N)
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int currentSum = 0;
        Map<Integer, Integer> prefixSumCounts = new HashMap<>();
        prefixSumCounts.put(0, 1); // Base case: a prefix sum of 0 appears once before array starts

        for (int num : nums) {
            currentSum += num;
            if (prefixSumCounts.containsKey(currentSum - k)) {
                count += prefixSumCounts.get(currentSum - k);
            }
            prefixSumCounts.put(currentSum, prefixSumCounts.getOrDefault(currentSum, 0) + 1);
        }
        return count;
    }

    @Test
    @DisplayName("06: Subarray Sum Equals K")
    void ex06_subarraySum() {
        assertThat(subarraySum(new int[]{1, 1, 1}, 2)).isEqualTo(2);
        assertThat(subarraySum(new int[]{1, 2, 3}, 3)).isEqualTo(2);
        assertThat(subarraySum(new int[]{1, -1, 0}, 0)).isEqualTo(3);
    }

    // 07: Maximum Sum Subarray of Size K (Fixed Window)
    // Time: O(N) | Space: O(1)
    public int maxSumSubarrayFixed(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) return 0;
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        int maxSum = windowSum;
        for (int i = k; i < nums.length; i++) {
            windowSum += nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }
        return maxSum;
    }

    @Test
    @DisplayName("07: Maximum Sum Subarray of Size K (Fixed Window)")
    void ex07_maxSumSubarrayFixed() {
        assertThat(maxSumSubarrayFixed(new int[]{2, 1, 5, 1, 3, 2}, 3)).isEqualTo(9);
        assertThat(maxSumSubarrayFixed(new int[]{2, 3, 4, 1, 5}, 2)).isEqualTo(7);
    }

    // 08: Longest Substring Without Repeating Characters
    // Time: O(N) | Space: O(min(N, M)) where M is character alphabet size
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (lastSeen.containsKey(c)) {
                // Advance left pointer to past the duplicate's last index (never moving backwards)
                left = Math.max(left, lastSeen.get(c) + 1);
            }
            lastSeen.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    @Test
    @DisplayName("08: Longest Substring Without Repeating Characters")
    void ex08_lengthOfLongestSubstring() {
        assertThat(lengthOfLongestSubstring("abcabcbb")).isEqualTo(3);
        assertThat(lengthOfLongestSubstring("bbbbb")).isEqualTo(1);
        assertThat(lengthOfLongestSubstring("pwwkew")).isEqualTo(3);
        assertThat(lengthOfLongestSubstring("")).isEqualTo(0);
        assertThat(lengthOfLongestSubstring("abba")).isEqualTo(2);
    }

    // 09: Minimum Size Subarray Sum
    // Time: O(N) | Space: O(1)
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int currentSum = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];
            while (currentSum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                currentSum -= nums[left++];
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    @Test
    @DisplayName("09: Minimum Size Subarray Sum")
    void ex09_minSubArrayLen() {
        assertThat(minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3})).isEqualTo(2);
        assertThat(minSubArrayLen(4, new int[]{1, 4, 4})).isEqualTo(1);
        assertThat(minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1})).isEqualTo(0);
    }

    // 10: Longest Consecutive Sequence
    // Time: O(N) | Space: O(N)
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int maxStreak = 0;
        for (int num : numSet) {
            // Only begin counting if `num` is the start of a streak
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (numSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }
                maxStreak = Math.max(maxStreak, currentStreak);
            }
        }
        return maxStreak;
    }

    @Test
    @DisplayName("10: Longest Consecutive Sequence")
    void ex10_longestConsecutive() {
        assertThat(longestConsecutive(new int[]{100, 4, 200, 1, 3, 2})).isEqualTo(4);
        assertThat(longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1})).isEqualTo(9);
        assertThat(longestConsecutive(new int[]{})).isEqualTo(0);
    }
}
