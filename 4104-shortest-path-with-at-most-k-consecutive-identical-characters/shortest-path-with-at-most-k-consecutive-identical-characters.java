
import java.util.*;

class Solution {

    public int shortestPath(int n, int[][] edges,
                            String labels, int k) {

        // Build directed graph
        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            graph[u].add(new int[]{v, w});
        }

        // dist[node][count]
        int[][] dist = new int[n][k + 1];

        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // {distance, node, consecutiveCount}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        // Starting node is included in the path
        dist[0][1] = 0;
        pq.offer(new int[]{0, 0, 1});

        while (!pq.isEmpty()) {

            int[] current = pq.poll();

            int cost = current[0];
            int u = current[1];
            int count = current[2];

            // Ignore outdated states
            if (cost != dist[u][count]) {
                continue;
            }

            // First destination state removed is optimal
            if (u == n - 1) {
                return cost;
            }

            for (int[] edge : graph[u]) {

                int v = edge[0];
                int weight = edge[1];

                int newCount;

                if (labels.charAt(u) == labels.charAt(v)) {
                    newCount = count + 1;
                } else {
                    newCount = 1;
                }

                // Invalid consecutive characters
                if (newCount > k) {
                    continue;
                }

                int newCost = cost + weight;

                if (newCost < dist[v][newCount]) {

                    dist[v][newCount] = newCost;

                    pq.offer(new int[]{
                        newCost, v, newCount
                    });
                }
            }
        }

        return -1;
    }
}
