package com.example.rh.leetcode.twosum;

import java.util.HashMap;



/**
 * @author hui
 * @descption [LeetCode] 1. Two Sum 两数之和
 * @date 2021-2-22
 */
public class Solution {
    public static void main(String[] args) {
        int[] nums = {5, 2, 3 ,2 ,4};
        int target = 4;
        int[] ints = twoSum(nums, target);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i] + "---" + i);
        }

    }

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();
        int[] res = new int[2];
        for (int i = 0; i < nums.length; ++i) {
            if (m.containsKey(target - nums[i])) {
                res[0] = i;
                res[1] = m.get(target - nums[i]);
                break;
            }
            m.put(nums[i], i);
        }
        return res;
    }
}