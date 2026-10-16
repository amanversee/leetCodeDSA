
import java.util.*;

class Solution {
    private static final long MOD = 1_000_000_007L;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        int[] lastSeen = new int[100001];
        Arrays.fill(lastSeen, -1);

        SegmentTree seg = new SegmentTree(n);
        long answer = 0;

        for (int i = 0; i < n; i++) {
            int value = nums[i];

            int left = lastSeen[value] + 1;
            seg.update(1, 0, n - 1, left, i, 1);

            answer = (answer + seg.query(1, 0, n - 1, 0, i)) % MOD;

            lastSeen[value] = i;
        }

        return (int) answer;
    }

    static class SegmentTree {
        long[] sum, sumSq, lazy;

        SegmentTree(int n) {
            sum = new long[4 * n];
            sumSq = new long[4 * n];
            lazy = new long[4 * n];
        }

        void apply(int node, int left, int right, long delta) {
            long len = right - left + 1L;

            sumSq[node] = (
                sumSq[node]
                + 2 * delta % MOD * sum[node] % MOD
                + delta * delta % MOD * len % MOD
            ) % MOD;

            sum[node] = (sum[node] + delta * len) % MOD;
            lazy[node] = (lazy[node] + delta) % MOD;
        }

        void pushDown(int node, int left, int right) {
            if (lazy[node] == 0 || left == right) return;

            int mid = left + (right - left) / 2;

            apply(node * 2, left, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, right, lazy[node]);

            lazy[node] = 0;
        }

        void pullUp(int node) {
            sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
            sumSq[node] = (sumSq[node * 2] + sumSq[node * 2 + 1]) % MOD;
        }

        void update(int node, int left, int right,
                    int ql, int qr, long delta) {
            if (ql > right || qr < left) return;

            if (ql <= left && right <= qr) {
                apply(node, left, right, delta);
                return;
            }

            pushDown(node, left, right);

            int mid = left + (right - left) / 2;

            update(node * 2, left, mid, ql, qr, delta);
            update(node * 2 + 1, mid + 1, right, ql, qr, delta);

            pullUp(node);
        }

        long query(int node, int left, int right,
                   int ql, int qr) {
            if (ql > right || qr < left) return 0;

            if (ql <= left && right <= qr) {
                return sumSq[node];
            }

            pushDown(node, left, right);

            int mid = left + (right - left) / 2;

            long a = query(node * 2, left, mid, ql, qr);
            long b = query(node * 2 + 1, mid + 1, right, ql, qr);

            return (a + b) % MOD;
        }
    }
}
