import java.util.*;

public class GroupAnagram {
	public static void main(String[] args) {
		String[] test = {"eat", "tea", "tan", "ate", "nat", "bat"};
		System.out.println(groupAnagram(test));
	}
	
	public static List<Set<String>> groupAnagram(String[] words) {
		HashMap<String, Set<String>> group = new HashMap<String, Set<String>>();
		for (String s : words) {
			char[] ca = s.toCharArray();
			Arrays.sort(ca);
			String key = new String(ca);
			if (!group.containsKey(key)) {
				group.put(key, new HashSet<String>());
			} 
			group.get(key).add(s);
		}
		List<Set<String>> result = new ArrayList<Set<String>>();
		for (String key : group.keySet()) {
			result.add(group.get(key));
		}
		return result;
	}
}