import java.util.*;

public class RomanNumeral {
	public static void main(String[] args) {
		String[] tests = {
			"I",
			"II",
			"IV",
			"V",
			"VI",
			"IX",
			"XIV",
			"MCMIV",
			"CD",
			"CM",
			"MDCCC"
		};
		for (String s : tests) {
			System.out.println(romanNumeralToInt(s));
		}
	}
	
	
	public static int romanNumeralToInt(String s) {
		Map<String, Integer> romanToInt = new HashMap<>();
		romanToInt.put("I", 1);
		romanToInt.put("V", 5);
		romanToInt.put("X", 10);
		romanToInt.put("L", 50);
		romanToInt.put("C", 100);
		romanToInt.put("D", 500);
		romanToInt.put("M", 1000);
		
		int sum = 0;
		for (int i = 0; i < s.length() - 1; i++) {
			int curr = romanToInt.get(s.charAt(i) + "");
			int next = romanToInt.get(s.charAt(i + 1) + "");
			if (curr >= next) {
				sum += curr;
			} else {
				sum -= curr;
			}
		}
		sum += romanToInt.get(s.charAt(s.length() - 1) + "");
		return sum;
	}
}













