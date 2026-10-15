
import java.util.*;

class Solution {
    public String simplifyPath(String path) {
        String[] parts = path.split("/");
        Deque<String> stack = new ArrayDeque<>();

        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            } 
            else if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } 
            else {
                stack.push(part);
            }
        }

        StringBuilder result = new StringBuilder();

        Iterator<String> it = stack.descendingIterator();

        while (it.hasNext()) {
            result.append("/").append(it.next());
        }

        return result.length() == 0 ? "/" : result.toString();
    }
}
