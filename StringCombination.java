import java.util.*;
// string subset or combination - from empty string to full string
public class StringCombination {
	public static void main(String[] args) {
		String test = "1234";
		printSubsets(test);
	}
	// runtime: O(n*2^n), n: number of characters in the given string
	public static void printSubsets(String s) {
		int totalCombo = (int) Math.pow(2, s.length());
		for (int i = 0; i < totalCombo; i++) {
			StringBuilder sb = new StringBuilder();
			int temp = i;
			for (int j = 0; j < s.length(); j++) {
				if (temp % 2 == 1) {
					sb.append(s.charAt(j));
				}
				temp = temp / 2;
			}
			System.out.println(sb.toString());
		}
		System.out.println("Number of subsets = " + totalCombo);
	}
}