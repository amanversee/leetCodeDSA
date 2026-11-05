import java.util.*;

class Solution {

    public int snakesAndLadders(int[][] board) {

        int n = board.length;
        int target = n * n;

        boolean[] visited = new boolean[target + 1];

        Queue<Integer> queue = new LinkedList<>();

        queue.offer(1);
        visited[1] = true;

        int moves = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {

                int current = queue.poll();

                // Destination reached
                if (current == target) {
                    return moves;
                }

                // Try all dice outcomes
                for (int dice = 1; dice <= 6; dice++) {

                    int next = current + dice;

                    if (next > target) {
                        break;
                    }

                    // Convert square number to board coordinates
                    int[] pos = getPosition(next, n);

                    int row = pos[0];
                    int col = pos[1];

                    // Apply snake or ladder
                    if (board[row][col] != -1) {
                        next = board[row][col];
                    }

                    // Visit only once
                    if (!visited[next]) {

                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }

            moves++;
        }

        return -1;
    }

    private int[] getPosition(int square, int n) {

        int rowFromBottom = (square - 1) / n;
        int col = (square - 1) % n;

        int row = n - 1 - rowFromBottom;

        // Reverse direction on alternate rows
        if (rowFromBottom % 2 == 1) {
            col = n - 1 - col;
        }

        return new int[]{row, col};
    }
}