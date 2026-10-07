class Solution {
    public String minWindow(String s, String t) {
        if(s.length() < t.length()){
            return "";
        }

        int[] need = new int[128];
        int[] window = new int[128];

        for(char ch: t.toCharArray()){
            need[ch]++;
        }

        int left = 0;
        int required = t.length();

        int minLength = Integer.MAX_VALUE;
        int start = 0;

        for(int right = 0; right < s.length(); right++){
            char ch = s.charAt(right);
            window[ch]++;

            if(need[ch] > 0 && window[ch] <= need[ch]){
                required--;
            }
            while (required == 0){
                int length = right - left + 1;

                if(length < minLength){
                    minLength = length;
                    start = left;

                }
                char leftChar = s.charAt(left);
                window[leftChar]--;

                if(need[leftChar] > 0 && window[leftChar] < need[leftChar]){
                    required++;

                }
                left++;
            }
        }
        if(minLength ==  Integer.MAX_VALUE){
            return "";
        }
        return s.substring(start, start + minLength);
        
    }
}