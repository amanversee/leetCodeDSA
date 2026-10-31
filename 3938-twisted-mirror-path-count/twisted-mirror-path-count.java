class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int MOD = 1_000_000_007;

        long[][] fromLeft = new long[m][n];
        long[][] fromTop = new long[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // Starting position
                long ways;

                if (i == 0 && j == 0) {
                    ways = 1;
                } else {
                    ways = (fromLeft[i][j] + fromTop[i][j]) % MOD;
                }

                if (grid[i][j] == 1) {

                    // Mirror: moving right -> turn down
                    if (i + 1 < m) {
                        fromTop[i + 1][j] =
                            (fromTop[i + 1][j] + fromLeft[i][j]) % MOD;
                    }

                    // Mirror: moving down -> turn right
                    if (j + 1 < n) {
                        fromLeft[i][j + 1] =
                            (fromLeft[i][j + 1] + fromTop[i][j]) % MOD;
                    }

                } else {

                    // Destination reached
                    if (i == m - 1 && j == n - 1) {
                        return (int) ways;
                    }

                    // Move right
                    if (j + 1 < n) {
                        fromLeft[i][j + 1] =
                            (fromLeft[i][j + 1] + ways) % MOD;
                    }

                    // Move down
                    if (i + 1 < m) {
                        fromTop[i + 1][j] =
                            (fromTop[i + 1][j] + ways) % MOD;
                    }
                }
            }
        }

        return 0;
    }
}