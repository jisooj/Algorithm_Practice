public class MaxProdSubarray {
	public static void main(String[] args) {
		int[] arr = {2,3,-1,4};
		System.out.println(maxProduct(arr));
	}
    public static int maxProduct(int[] nums) {
		  if (nums.length == 1) {
			  return nums[0];
		  }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
        }
        int[] prev = nums;
        for (int i = 1; i < nums.length; i++) {
            int[] newSub = new int[nums.length - i];
            int newIndex = 0;
            for (int j = i; j < nums.length; j++) {
                newSub[newIndex] = nums[j] * prev[newIndex];
                max = Math.max(max, newSub[newIndex]);
                newIndex++;
            }
				prev = newSub;
        }
        return max;
    }
}