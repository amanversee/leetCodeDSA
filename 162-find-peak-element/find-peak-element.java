class Solution {
    public int findPeakElement(int[] nums) {
        int peak = Integer.MIN_VALUE;
        int n = nums.length;
        int max = 0;

        for(int i = 0; i<n; i++){
            if(peak < nums[i]){
                peak = nums[i];
                max = i;
                
            }
        }
        return max;
        
    }
}