import java.util.*;

public class WordBreak {
	public static void main(String[] args) {
		String s = "hellohihi";
		Set<String> dict = new HashSet<>();
		dict.add("hell");
		dict.add("hello");
		dict.add("hi");
		System.out.println(breakString(dict, s));
		System.out.println(breakStringDP(dict, s));
	}

	private static String buildResult(Stack<String> result) {
		StringBuilder sb = new StringBuilder();
		
		// insert every word into the beginning 
		// since the stack order is reversed when popping out
		while (!result.isEmpty()) {
			String word = result.pop();
			sb.insert(0, word);
			sb.insert(0, " ");
		}
		return sb.toString().trim();
	}


	public static String breakString(Set<String> dict, String str) {
		return breakString(dict, new Stack<String>(), str, 0);
	}

	private static String breakString(Set<String> dict, Stack<String> result, 
			String str, int startIndex) {
		if (startIndex == str.length()) {
			return buildResult(result);
		} else {
			for (int i = startIndex; i < str.length(); i++) {
				String prefix = str.substring(startIndex, i + 1);
				if (dict.contains(prefix)) {
					result.push(prefix);
					String broken = breakString(dict, result, str, startIndex + prefix.length());
					if(broken != null) {
						return broken;
					}
					result.pop();
				}
			}
			return null;
		}
	}
	
	// memoization: if I have a prefix, would its suffix be broken down?
	// If so, store it into a map<prefix,broken suffix>
	public static String breakStringDP(Set<String> dict, String str) {
		Map<String, String> memo = new HashMap<>();
		return breakStringDP(dict, new Stack<String>(), str, 0, memo);
		
	}
	private static String breakStringDP(Set<String> dict, Stack<String> result, 
			String str, int startIndex, Map<String, String> memo) {
		String prefix = str.substring(0, startIndex);
		if (memo.containsKey(prefix)) {
			return memo.get(prefix);
		}
		if (startIndex == str.length()) {
			return buildResult(result);
		} else {
			for (int i = startIndex; i < str.length(); i++) {
				String newPrefix = str.substring(startIndex, i + 1);
				if (dict.contains(newPrefix)) {
					result.push(newPrefix);
					String broken = breakString(dict, result, str, startIndex + newPrefix.length());
					if(broken != null) {
						memo.put(newPrefix, broken);
						return broken;
					}
					result.pop();
				}
			}
			memo.put(prefix, null);
			return null;
		}
	}
	
}