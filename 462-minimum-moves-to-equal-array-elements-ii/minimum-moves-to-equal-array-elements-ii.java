import java.util.Arrays;

class Solution {
    public int minMoves2(int[] nums) {

        // Sort the array
        Arrays.sort(nums);

        // Find median
        int median = nums[nums.length / 2];

        int moves = 0;

        // Calculate total moves
        for (int num : nums) {
            moves += Math.abs(num - median);
        }

        return moves;
    }
}