
import java.util.*;

class Solution {

    public List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        boolean[] used = new boolean[nums.length];

        backtrack(nums, new ArrayList<>(), used, result);

        return result;
    }

    private void backtrack(
        int[] nums,
        List<Integer> current,
        boolean[] used,
        List<List<Integer>> result
    ) {

        // Base case: all numbers are selected
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Try every number
        for (int i = 0; i < nums.length; i++) {

            // Skip already used numbers
            if (used[i]) {
                continue;
            }

            // Choose
            current.add(nums[i]);
            used[i] = true;

            // Explore
            backtrack(nums, current, used, result);

            // Undo choice
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
}
