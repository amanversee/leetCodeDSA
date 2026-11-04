import java.util.*;

class Solution {

    class Fenwick {
        int n;
        long[] tree;

        Fenwick(int n) {
            this.n = n;
            tree = new long[n + 1];
        }

        void update(int i, long value) {
            while (i <= n) {
                tree[i] += value;
                i += i & -i;
            }
        }

        long query(int i) {
            long sum = 0;

            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }

            return sum;
        }

        // Find the index of the kth smallest element
        int kth(int k) {
            int index = 0;

            for (int bit = Integer.highestOneBit(n);
                 bit > 0; bit >>= 1) {

                int next = index + bit;

                if (next <= n && tree[next] < k) {
                    index = next;
                    k -= (int) tree[next];
                }
            }

            return index + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int m = 0;

        for (int num : sorted) {
            if (m == 0 || sorted[m - 1] != num) {
                sorted[m++] = num;
            }
        }

        int[] values = Arrays.copyOf(sorted, m);

        // Store compressed index of every element
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[i] = Arrays.binarySearch(values, nums[i]) + 1;
        }

        Fenwick countTree = new Fenwick(m);
        Fenwick sumTree = new Fenwick(m);

        long totalSum = 0;

        // Build first window
        for (int i = 0; i < x; i++) {
            countTree.update(rank[i], 1);
            sumTree.update(rank[i], nums[i]);
            totalSum += nums[i];
        }

        // Cost of each window, indexed by its ending position
        long[] cost = new long[n + 1];

        int medianRank = (x + 1) / 2;

        for (int end = x; end <= n; end++) {

            // Find median
            int medianIndex = countTree.kth(medianRank);
            long median = values[medianIndex - 1];

            // Calculate sum of elements <= median
            long leftCount = countTree.query(medianIndex);
            long leftSum = sumTree.query(medianIndex);

            long rightCount = x - leftCount;
            long rightSum = totalSum - leftSum;

            // Minimum cost to make this window equal
            cost[end] =
                median * leftCount - leftSum
                + rightSum - median * rightCount;

            // Slide window
            if (end < n) {

                int outgoing = end - x;

                countTree.update(rank[outgoing], -1);
                sumTree.update(rank[outgoing], -nums[outgoing]);
                totalSum -= nums[outgoing];

                countTree.update(rank[end], 1);
                sumTree.update(rank[end], nums[end]);
                totalSum += nums[end];
            }
        }

        // DP: minimum cost to select j windows
        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];
        long[] curr = new long[n + 1];

        for (int j = 1; j <= k; j++) {

            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                // Option 1: Skip current element
                curr[i] = curr[i - 1];

                // Option 2: Select window ending at i
                if (i >= x && prev[i - x] != INF) {

                    curr[i] = Math.min(
                        curr[i],
                        prev[i - x] + cost[i]
                    );
                }
            }

            // Move to next DP layer
            long[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }
}