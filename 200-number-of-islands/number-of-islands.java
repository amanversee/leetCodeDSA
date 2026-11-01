class Solution {
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == '1') {
                    count++;

                    dfs(grid, i, j);
                }
            }
        }

        return count;
    }

    private void dfs(char[][] grid, int i, int j) {

        int m = grid.length;
        int n = grid[0].length;

        // Boundary check and water check
        if (i < 0 || j < 0 || i >= m || j >= n
                || grid[i][j] == '0') {
            return;
        }

        // Mark as visited
        grid[i][j] = '0';

        // Explore all four directions
        dfs(grid, i + 1, j); // Down
        dfs(grid, i - 1, j); // Up
        dfs(grid, i, j + 1); // Right
        dfs(grid, i, j - 1); // Left
    }
}