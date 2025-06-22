package leetcode.hotyibai;


import java.util.*;

/**
 * hot100
 */
public class Solution {


    /**
     *  <a href="https://leetcode.cn/problems/two-sum/description/?envType=problem-list-v2&envId=2cktkvj">两数之和</a>
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i< nums.length; i++) {
            if(map.containsKey(target - nums[i])) {
                return new int[] {map.get(target - nums[i]),i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");

    }


    /**
     *  <a href="https://leetcode.cn/problems/add-two-numbers/?envType=problem-list-v2&envId=2cktkvj">两数相加</a>
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    /**
     *  <a href="https://leetcode.cn/problems/longest-substring-without-repeating-characters/?envType=problem-list-v2&envId=2cktkvj">无重复字符的最长子串</a>
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if(s==null || s.isEmpty()) {
            return 0;
        }
        HashSet<Character> memo = new HashSet<>();
        int res = 1;
        memo.add(s.charAt(0));
        int right = 1;
        for (int i = 0; i < s.length(); i++) {
            if(i != 0) {
                memo.remove(s.charAt(i - 1));
            }
            while(right < s.length() && !memo.contains(s.charAt(right))) {
                memo.add(s.charAt(right));
                right++;
            }
            res = Math.max(res, (right - i));
            if (right == s.length() -1) break;
        }
        return res;
    }

    /**
     * <a href="https://leetcode.cn/problems/median-of-two-sorted-arrays/?envType=problem-list-v2&envId=2cktkvj">寻找两个正序数组的中位数</a>
     * @param nums1
     * @param nums2
     * <p>
     *     hard
     * </p>
     *
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            double median = getKthElement(nums1, nums2, midIndex + 1);
            return median;
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            double median = (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
            return median;
        }
    }
    private int getKthElement(int[] nums1, int[] nums2, int k) {
        /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
         * 这里的 "/" 表示整除
         * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
         * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
         * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
         * 这样 pivot 本身最大也只能是第 k-1 小的元素
         * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
         * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
         * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
         */

        int length1 = nums1.length, length2 = nums2.length;
        int index1 = 0, index2 = 0;
        int kthElement = 0;

        while (true) {
            // 边界情况
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }

            // 正常情况
            int half = k / 2;
            int newIndex1 = Math.min(index1 + half, length1) - 1;
            int newIndex2 = Math.min(index2 + half, length2) - 1;
            int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
            if (pivot1 <= pivot2) {
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }

    }


    /**
     * <a href="https://leetcode.cn/problems/longest-palindromic-substring/?envType=problem-list-v2&envId=2cktkvj">最长回文子串</a>
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s.length() <= 1) {
            return s;
        }
        int left = 0;
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            int singleCase = range(s, i, i);
            int doubleCase = range(s, i, i + 1);
            int len = Math.max(singleCase, doubleCase);
            if(len > right - left) {
                left = i - (len - 1)/2;
                right = i + (len)/2;
            }
        }

        return s.substring(left, right + 1);
    }
    private int range(String s, int left, int right) {
        int l = left;
        int r = right;
        while(l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }

        return r - l - 1;
    }

    /**
     * <a href="https://leetcode.cn/problems/regular-expression-matching/?envType=problem-list-v2&envId=2cktkvj">正则表达式匹配</a>
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        boolean[][] f = new boolean[m + 1][n + 1];
        f[0][0] = true;
        for (int i = 0; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (p.charAt(j - 1) == '*') {
                    f[i][j] = f[i][j - 2];
                    if (matches(s, p, i, j - 1)) {
                        f[i][j] = f[i][j] || f[i - 1][j];
                    }
                }
                else {
                    if (matches(s, p, i, j)) {
                        f[i][j] = f[i - 1][j - 1];
                    }
                }
            }
        }
        return f[m][n];
    }
    private boolean matches(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }

    /**
     * <a href="https://leetcode.cn/problems/container-with-most-water/?envType=problem-list-v2&envId=2cktkvj">盛最多水的容器</a>
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int ans = 0;
        while (l < r) {
            int area = Math.min(height[l], height[r]) * (r - l);
            ans = Math.max(ans, area);
            if (height[l] <= height[r]) {
                ++l;
            }
            else {
                --r;
            }
        }
        return ans;
    }

    /**
     * <a herf="https://leetcode.cn/problems/letter-combinations-of-a-phone-number/?envType=problem-list-v2&envId=2cktkvj">电话号码的字母组合</a>
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        if (digits.length() != 0)
            backtrack("", digits);
        return output;
    }
    Map<String, String> phone = new HashMap<String, String>() {{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
    }};
    List<String> output = new ArrayList<String>();
    public void backtrack(String combination, String next_digits) {
        if (next_digits.length() == 0) {
            output.add(combination);
        }
        else {
            String digit = next_digits.substring(0, 1);
            String letters = phone.get(digit);
            for (int i = 0; i < letters.length(); i++) {
                String letter = phone.get(digit).substring(i, i + 1);
                backtrack(combination + letter, next_digits.substring(1));
            }
        }
    }



}
