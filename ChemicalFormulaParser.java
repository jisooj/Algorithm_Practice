import java.util.*;
// Edge cases:
// Element can be multi charactered : Ne, He 
// Number can be multi charactered: C4H10
public class ChemicalFormulaParser {
	public static void main(String[] args) {
		String[] tests = {
			"H",
			"H3",
			"C(N)3",    // C=1,N=3
			"CH(CO3)2", // C=3,H=1,O=6
			"(C(NH4))3", // C=3,N=3,H=12
         "CH(S2O3)AB((C)2)1" //{A=1, B=1, C=3, S=2, H=1, O=3}
		};
		ChemicalFormulaParser parser = new ChemicalFormulaParser();
		for (String s: tests) {
			System.out.println(parser.parse(s.toUpperCase()));
		}
	}
   
	int globalI = 0;
   public Map<Character, Integer> parse(String s) {
      globalI = 0;
      return parseHelper(s);
   }
	// ch : letter, number, opening paren, closing paren
	public Map<Character, Integer> parseHelper(String s) {
		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			globalI++;
			if (isLetter(ch)) {
				// increment letter count by 1
				if (!map.containsKey(ch)) {
					map.put(ch, 0);
				}
				if (i + 1 >= s.length() ||
						(i + 1 < s.length() && !isNumber(s.charAt(i + 1)))) {
					// if (i is the last index || next char is not a number)
					map.put(ch, map.get(ch) + 1);
				}
			} else if (ch == ')') {
				// check if next char is number 
				if (i + 1 < s.length() && isNumber(s.charAt(i + 1))) {
					int mult = s.charAt(i + 1) - '0';
					for (char letter : map.keySet()) {
						map.put(letter, map.get(letter) * mult);
					}
					globalI+=2;
					return map;
				}
				// dont know how much substring we have proocessed
			} else if (ch == '(') {
				Map<Character, Integer> parsedParen = parseHelper(s.substring(i+1));
				for (char letter : parsedParen.keySet()) {
					if (!map.containsKey(letter)) {
						map.put(letter, parsedParen.get(letter));
					} else {
						map.put(letter, map.get(letter) + parsedParen.get(letter));
					}
				}
				i = globalI;
			} else if (isNumber(ch)) {
				if (i > 0) {
					char prev = s.charAt(i - 1);
					map.put(prev, map.get(prev) + (ch - '0'));
				}
			}
		}
		return map;
	}
	private boolean isNumber(char ch) {
		return (ch >= '0' && ch <= '9');
	}
	private boolean isLetter(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
	}
}