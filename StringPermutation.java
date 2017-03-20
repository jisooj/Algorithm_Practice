import java.util.*;

public class StringPermutation {
	public static void main(String[] args) {
		String test = "1234";
		printPermutation(test);
		System.out.println("Total number of permutation = " + total);
	}
	static int total = 0;
	// runtime: O(n!), n : number of characters in the given string
	public static void printPermutation(String s) {
		total = 0;
		printPermutation("", s);
	}
	
	private static void printPermutation(String curr, String remain) {
		if (remain.length() == 0) {
			System.out.println(curr);
			total++;
		} else {
			for (int i = 0; i < remain.length(); i++) {
				printPermutation(curr + remain.charAt(i), 
						remain.substring(0, i) + 
						remain.substring(i + 1));
			}
		}
	}
}