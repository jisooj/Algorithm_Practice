public class TrappedWater {
	public static void main(String[] args) {
		int[][] cases = {
			{1,10,0,1,0,8},
			{3,3,1,5,0,3},
			{2,1,2},
			{0,1,5,3},
			{8,0,1,0,4}
		};
		for (int i = 0; i < cases.length; i++) {
			System.out.println("case " + i + " = " + getTrappedWaterVolume1(cases[i]));
		}
		
		for (int i = 0; i < cases.length; i++) {
			System.out.println("case " + i + " = " + getTrappedWaterVolume2(cases[i]));
		}
		
	}
	// soln1 
	public static int getTrappedWaterVolume1(int[] arr) { 
		int vol = 0;  // total returning volume
		for (int i = 0; i < arr.length;  i++) {
			int leftMax = arr[0];
			for (int j = 0; j <= i; j++) {
				leftMax = Math.max(leftMax, arr[j]);
			}
			int rightMax = arr[arr.length - 1];
			for (int j = i; j < arr.length; j++) {
				rightMax = Math.max(rightMax, arr[j]);
			}
			int volSingleCol = Math.min(leftMax, rightMax) - arr[i];
			if (volSingleCol > 0) {
				vol += volSingleCol;
			}
		}
		return vol;
	}

	// soln2
	public static int getTrappedWaterVolume2(int[] arr) {
		int vol = 0; // total volume
		int[] leftMaxSoFar = new int[arr.length];
		int[] rightMaxSoFar = new int[arr.length];
		
		// sweep array from left to right 
		for (int i = 0; i < arr.length; i++) {
			if (i == 0) {
				leftMaxSoFar[i] = arr[i];
			} else {
				leftMaxSoFar[i] = Math.max(leftMaxSoFar[i - 1], arr[i]);
			}
		}
		// sweep array from right to left
		for (int i = arr.length - 1; i >= 0; i--) {
			if (i == arr.length - 1) {
				rightMaxSoFar[i] = arr[i];
			} else {
				rightMaxSoFar[i] = Math.max(rightMaxSoFar[i + 1], arr[i]);
			}
		}
		
		for (int i = 0; i < arr.length; i++) {
			int volSingleCol = Math.min(leftMaxSoFar[i], rightMaxSoFar[i]) - arr[i];
			if (volSingleCol > 0) {
				vol += volSingleCol;
			}
		}
		return vol;
	}	
}