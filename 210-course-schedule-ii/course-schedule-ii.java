import java.util.*;

class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {

        // Create adjacency list
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        int[] indegree = new int[numCourses];

        // Build graph and calculate indegree
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prerequisite = pre[1];

            graph.get(prerequisite).add(course);
            indegree[course]++;
        }

        // Add courses with zero indegree
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int[] result = new int[numCourses];
        int index = 0;

        // BFS
        while (!queue.isEmpty()) {

            int current = queue.poll();

            result[index++] = current;

            for (int neighbor : graph.get(current)) {

                indegree[neighbor]--;

                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // Cycle detection
        if (index != numCourses) {
            return new int[0];
        }

        return result;
    }
}