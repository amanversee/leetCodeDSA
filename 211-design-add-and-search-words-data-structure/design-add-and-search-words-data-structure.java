
class WordDictionary {

    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }

    private TrieNode root;

    public WordDictionary() {
        root = new TrieNode();
    }

    // Add a word to the Trie
    public void addWord(String word) {

        TrieNode current = root;

        for (char ch : word.toCharArray()) {

            int index = ch - 'a';

            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }

            current = current.children[index];
        }

        current.isEnd = true;
    }

    // Search a word, supporting '.'
    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    private boolean dfs(String word, int index, TrieNode node) {

        // All characters matched
        if (index == word.length()) {
            return node.isEnd;
        }

        char ch = word.charAt(index);

        // Wildcard: try every possible character
        if (ch == '.') {

            for (TrieNode child : node.children) {

                if (child != null &&
                    dfs(word, index + 1, child)) {
                    return true;
                }
            }

            return false;
        }

        // Normal character
        int position = ch - 'a';

        if (node.children[position] == null) {
            return false;
        }

        return dfs(word, index + 1,
                   node.children[position]);
    }
}
