import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        // Sort logs by timestamp
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        // Store queries with their original indices
        int[][] sortedQueries = new int[queries.length][2];

        for (int i = 0; i < queries.length; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }

        Arrays.sort(sortedQueries, (a, b) ->
            Integer.compare(a[0], b[0])
        );

        // Server ID -> number of active requests
        Map<Integer, Integer> map = new HashMap<>();

        int[] answer = new int[queries.length];

        int left = 0;
        int right = 0;

        for (int[] query : sortedQueries) {

            int time = query[0];
            int index = query[1];

            // Add logs inside the current time window
            while (right < logs.length && logs[right][1] <= time) {

                int server = logs[right][0];

                map.put(server, map.getOrDefault(server, 0) + 1);

                right++;
            }

            // Remove logs outside the window
            while (left < right && logs[left][1] < time - x) {

                int server = logs[left][0];

                map.put(server, map.get(server) - 1);

                if (map.get(server) == 0) {
                    map.remove(server);
                }

                left++;
            }

            // Servers with zero requests
            answer[index] = n - map.size();
        }

        return answer;
    }
}