class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;

        boolean increasing = true;
        boolean decreasing = true;

        for (int i = 0; i < n; i++) {
            if (nums[i] != (nums[0] + i) % n) {
                increasing = false;
            }

            if (nums[i] != (nums[0] - i + n) % n) {
                decreasing = false;
            }
        }

        int answer = Integer.MAX_VALUE;

        if (increasing) {
            int k = nums[0];
            answer = Math.min((n - k) % n, k + 2);
        }

        if (decreasing) {
            int k = (n - 1 - nums[0] + n) % n;
            answer = Math.min(answer, 1 + Math.min(k, (n - k) % n));
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}