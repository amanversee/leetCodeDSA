import java.util.*;

class Solution {
    public int ladderLength(String beginWord, String endWord,
                            List<String> wordList) {

        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(beginWord);
        visited.add(beginWord);

        int level = 1;

        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {

                String current = queue.poll();

                if (current.equals(endWord)) {
                    return level;
                }

                char[] chars = current.toCharArray();

                for (int j = 0; j < chars.length; j++) {

                    char original = chars[j];

                    for (char ch = 'a'; ch <= 'z'; ch++) {

                        if (ch == original) {
                            continue;
                        }

                        chars[j] = ch;

                        String next = new String(chars);

                        if (wordSet.contains(next)
                                && visited.add(next)) {

                            queue.offer(next);
                        }
                    }

                    chars[j] = original;
                }
            }

            level++;
        }

        return 0;
    }
}