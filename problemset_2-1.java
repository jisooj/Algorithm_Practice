/*
Q1 Search in a sorted but circularly shifted array
Given an array which is sorted, but with the elements circularly shifted by some amount, how can you efficiently search for an element?

overview of the solution in English
Let's suppose the array is in ascending order before it was rotated. 

Brute force searching in array takes linear time, but we want faster than this approach. 
Binary search speeds up to O(log n) in sorted, but unrotated array. Sorted, rotated array will have ascending order everywhere except a place where a jump happens from max to min value. So we're going to repeatedly divide the array into half and figure out which sub-array holds the element we're looking for. 

use 3 indices: low = 0, mid = (low+high)/2, high = arr.len - 1
array is divided into half using mid as a separator

loop() {
	Look for the region without the jump (region A) by comparing 
	arr[low] and arr[mid], arr[mid] and arr[high]

	if E falls within region A 
		do more search in region A; update indices
	else 
		do more search in region B; update indices
}

Return -1 if E doesn't exist in the array
*/
// ---- 30 min mark -----
/*
runtime complexity
O(log n), n: total number of elements in the array

space complexity
O(1)

test cases
[1], 3            -> -1
[4,1,2,3], 4      -> 0
[4,1,2,3], 5      -> -1
[3,4,7,0,1], 0    -> 3
[1,1,1], 0        -> -1
*/

public int searchSortedRotated(int[] arr, int e) {
	int low = 0;
	int high= arr.length - 1;
	while (low <= high) {
		int mid = (low + high) / 2;
		// check current index
		if (e == arr[mid]) {
			return mid;
		}
		// search for region A: no jump included
		if (arr[low] <= arr[mid]) {  // region A in lower half of array
			if (arr[low] <= e && e <= arr[mid]) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		} else { // if (arr[mid] <= arr[high]) { // region A in upper half of array
			if (arr[mid] <= e && e <= arr[high]) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
	}
	return -1;
}


/*
Q2 Implement Insert() and GetMedian() for a collection
Implement a collection that supports two operations: Insert(value) and GetMedian() returns (value). If there are multiple ways of implementing it, compare those options.


overview of the solution in English
Design1: LinkedList<Integer> 
	insert: append to the end of the list 
	getMedian: sort the list and then take the middle value(s)
Design2: LinkedList<Integer> 
	insert: sort every time element is inserted like insertion sort. 
	getMedian: take the middle value(s)
Design3: TreeMap<value, freq> -> using set removes duplicate. 
using map of value and count keeps track of duplicates
	insert: insert element as a key and its frequency as value 
	getMedian: iterate through the keys until it reaches the middle element(s)

runtime complexity
n : total number of elements in this collection
Design1: 
	insert: O(1)
	getMedian: O(n log n)
Design2:
	insert: O(n)
	getMedian: O(1)
Design3:
	insert: O(log n)
	getMedian: O(n)
	
space complexity
All the designs have O(n) to store inserted elements

test cases
insert in this order [3,5,1,2,0]
getMedian after each insert: 3,4,3,2,2
*/

// 30 min mark: everything except for test cases

// Design 2
public class MedianContainer {
	private List<Integer> elements;
	
	public MedianContainer() {
		elements = new LinkedList<>();
	}
	public void insert(int value) {
		int i = 0;
		while (!elements.isEmpty() && value < elements.get(i)) {
			i++;
		}
		elements.add(value, i);
	}
	public int getMedian() {
		if (elements.isEmpty()) {
			return 0;
		}
		int mid = elements.size() / 2;
		if (elements.size() % 2 == 1) {
			return elements.get(mid);
		} else {
			return (elements.get(mid) + elements.get(mid - 1)) / 2;
		}
	}
}



/*
Q3 How much water would a bar graph hold?
Imagine an island that is in the shape of a bar graph. When it rains, certain areas of the island fill up with rainwater to form lakes. Any excess rainwater the island cannot hold in lakes will run off the island to the west or east and drain into the ocean. Design an algorithm (or write a function) that can compute the total volume (capacity) of water that could be held in all lakes on such an island given an array of the heights of the bars.

overview of the solution in English
The amount of volume to be filled in a single column can be computed by searching the highest bar on both sides
getVolumeInOneCol(x) = {
	vol = min(max_left, max_right) - arr[x]
	if (vol < 0) return 0; 
	else 
		return vol
}

soln1: for each column, compute max_left and max_right, which results O(n2) runtime and O(1) space 
soln2: sweep from left and record the max_left seen so far. Repeat for the right side as well.
Then for each column apply the above logic to find the volume. O(n) runtime, O(n) space 

code until 30 min mark
	Could not come up with the algorithm

runtime complexity
n: number of elements in the array
soln1: O(n^2)
soln2: O(n)

space complexity
soln1: O(1) 
soln2: O(n)

test cases
[1,10,0,1,0,8]
[3,3,1,5,0,3]
[2,1,2]
[0,1,5,3]
[8,0,1,0,4]
*/
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
	public int getTrappedWaterVolume1(int[] arr) { 
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
	public int getTrappedWaterVolume2(int[] arr) {
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