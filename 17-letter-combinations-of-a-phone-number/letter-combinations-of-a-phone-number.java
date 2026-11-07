
import java.util.*;

class Solution {

    public List<String> letterCombinations(String digits) {

        List<String> result = new ArrayList<>();

        // Edge case
        if (digits.length() == 0) {
            return result;
        }

        String[] mapping = {
            "", "", "abc", "def",
            "ghi", "jkl", "mno",
            "pqrs", "tuv", "wxyz"
        };

        backtrack(digits, 0, new StringBuilder(),
                  mapping, result);

        return result;
    }

    private void backtrack(
        String digits,
        int index,
        StringBuilder current,
        String[] mapping,
        List<String> result
    ) {

        // Base case: combination is complete
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        // Get letters for current digit
        int digit = digits.charAt(index) - '0';
        String letters = mapping[digit];

        // Try every possible letter
        for (char ch : letters.toCharArray()) {

            // Choose
            current.append(ch);

            // Explore next digit
            backtrack(digits, index + 1,
                      current, mapping, result);

            // Undo choice (backtrack)
            current.deleteCharAt(current.length() - 1);
        }
    }
}
