import java.util.*;
class Solution {
    Set<String> result = new HashSet<>();
    public List<String> removeInvalidParentheses(String s) {
      int removeLeft = 0;
      int removeRight = 0;

      for(char ch : s.toCharArray()){
        if(ch=='('){
            removeLeft++;
        } else if(ch==')'){
            if(removeLeft > 0){
                removeLeft--;
            } else{
                removeRight++;
            }
        }
      }
      backtrack(s,0,0, removeLeft, removeRight, new StringBuilder());
      return new ArrayList<>(result);
 
    }

    private void backtrack(String s, int index, int balance, int removeLeft, int removeRight, StringBuilder current){
        if(index == s.length()){
            if(balance == 0 && removeLeft == 0 && removeRight == 0){
                result.add(current.toString());
            }
            return;
        }
        char ch = s.charAt(index);
        if(ch == '('){
            if(removeLeft > 0){
                backtrack(s, index+1, balance, removeLeft-1, removeRight, current);
            }
            current.append(ch);

            backtrack(s, index+1, balance+1, removeLeft, removeRight, current);

            current.deleteCharAt(current.length() - 1);
        } else if(ch == ')'){
            if(removeRight > 0){
                backtrack(s, index+1, balance, removeLeft, removeRight-1, current);
            }
            if(balance > 0){
                current.append(ch);

                backtrack(s, index+1, balance-1, removeLeft, removeRight, current);

                current.deleteCharAt(current.length() - 1);
            }
        } else{
            current.append(ch);

            backtrack(s, index+1, balance, removeLeft, removeRight, current);

            current.deleteCharAt(current.length() -1);
        }
    }
}