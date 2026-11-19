
class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;

        if (n <= 1) return 0;

        if (k >= n / 2) {
            int profit = 0;

            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) {
                    profit += prices[i] - prices[i - 1];
                }
            }

            return profit;
        }

        int[][] dp = new int[k + 1][n];

        for (int t = 1; t <= k; t++) {
            int maxDiff = -prices[0];

            for (int i = 1; i < n; i++) {
                dp[t][i] = Math.max(
                    dp[t][i - 1],
                    prices[i] + maxDiff
                );

                maxDiff = Math.max(
                    maxDiff,
                    dp[t - 1][i - 1] - prices[i]
                );
            }
        }

        return dp[k][n - 1];
    }
}
