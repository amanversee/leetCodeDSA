
import java.util.*;

class Solution {
    private int[] tin, tout, depth;
    private int[][] up;
    private int timer;
    private Fenwick bit;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        up = new int[LOG][n];

        char[] chars = s.toCharArray();
        int[] initialPrefix = new int[n];

        // Iterative DFS: enter and exit events
        int[] nodeStack = new int[2 * n + 5];
        int[] parentStack = new int[2 * n + 5];
        boolean[] exitStack = new boolean[2 * n + 5];
        int top = 0;

        nodeStack[top] = 0;
        parentStack[top] = 0;
        exitStack[top++] = false;

        timer = 0;

        while (top > 0) {
            --top;
            int u = nodeStack[top];
            int p = parentStack[top];
            boolean exiting = exitStack[top];

            if (exiting) {
                tout[u] = timer;
                continue;
            }

            tin[u] = ++timer;
            up[0][u] = p;
            initialPrefix[u] =
                    (u == 0 ? 0 : initialPrefix[p])
                    ^ (1 << (chars[u] - 'a'));

            for (int k = 1; k < LOG; k++) {
                up[k][u] = up[k - 1][up[k - 1][u]];
            }

            nodeStack[top] = u;
            parentStack[top] = p;
            exitStack[top++] = true;

            for (int v : adj[u]) {
                if (v == p) continue;

                depth[v] = depth[u] + 1;
                nodeStack[top] = v;
                parentStack[top] = u;
                exitStack[top++] = false;
            }
        }

        bit = new Fenwick(n + 1);
        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');
                int delta = oldMask ^ newMask;

                // All root-to-node paths in u's subtree change
                bit.rangeXor(tin[u], tout[u], delta);
                chars[u] = c;

            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int lca = getLCA(u, v);

                int prefixU =
                        initialPrefix[u] ^ bit.query(tin[u]);
                int prefixV =
                        initialPrefix[v] ^ bit.query(tin[v]);

                int pathMask = prefixU ^ prefixV
                        ^ (1 << (chars[lca] - 'a'));

                answer.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return answer;
    }

    private boolean isAncestor(int u, int v) {
        return tin[u] <= tin[v] && tout[v] <= tout[u];
    }

    private int getLCA(int u, int v) {
        if (isAncestor(u, v)) return u;
        if (isAncestor(v, u)) return v;

        for (int k = up.length - 1; k >= 0; k--) {
            if (!isAncestor(up[k][u], v)) {
                u = up[k][u];
            }
        }

        return up[0][u];
    }

    private static class Fenwick {
        private final int[] tree;
        private final int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 2];
        }

        private void update(int i, int value) {
            while (i <= n) {
                tree[i] ^= value;
                i += i & -i;
            }
        }

        private int query(int i) {
            int result = 0;

            while (i > 0) {
                result ^= tree[i];
                i -= i & -i;
            }

            return result;
        }

        void rangeXor(int left, int right, int value) {
            update(left, value);
            update(right + 1, value);
        }
    }
}
