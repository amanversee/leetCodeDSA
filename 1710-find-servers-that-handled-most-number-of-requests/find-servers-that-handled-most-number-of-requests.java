
import java.util.*;

class Solution {

    public List<Integer> busiestServers(
        int k, int[] arrival, int[] load
    ) {

        // Available servers in sorted order
        TreeSet<Integer> available = new TreeSet<>();

        // Busy servers: [finishTime, serverIndex]
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        int[] count = new int[k];

        // Initially, all servers are available
        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        // Process requests in arrival order
        for (int i = 0; i < arrival.length; i++) {

            int time = arrival[i];

            // Release servers that have finished
            while (!busy.isEmpty() &&
                   busy.peek()[0] <= time) {

                long[] server = busy.poll();
                available.add((int) server[1]);
            }

            // No server is available: drop request
            if (available.isEmpty()) {
                continue;
            }

            // Preferred server for this request
            int preferred = i % k;

            // Find next available server
            Integer server = available.ceiling(preferred);

            // Wrap around if necessary
            if (server == null) {
                server = available.first();
            }

            // Assign request
            available.remove(server);

            count[server]++;

            // Mark server as busy
            busy.offer(new long[]{
                (long) time + load[i], server
            });
        }

        // Find maximum requests handled by a server
        int maxRequests = 0;

        for (int c : count) {
            maxRequests = Math.max(maxRequests, c);
        }

        // Collect all servers with maximum requests
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == maxRequests) {
                result.add(i);
            }
        }

        return result;
    }
}
