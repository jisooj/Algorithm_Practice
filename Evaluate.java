import java.util.*;
/*
4. Given a string with plus, minus, parenthesis, numbers, evaluate the expression: http://stackoverflow.com/questions/3422673/evaluating-a-math-expression-given-in-string-form
*/
// 10:44
public class Evaluate {
	public static void main(String[] args) {
		String[] tests = {
			"1 + 5 - 2 + 3 - 12",
			"-4 + 9",
			"3"
			//"3 + (-2 - 2 + 5)",
			//"3 - (6 + 9)",
			//"5 + ((5 - 3) - (-4))",
			//"(22 - 12) - 2"
		};
		for (String s : tests) {
			System.out.println(s + " = " + evaluate(s));
		}
	}
	// +, -, (), numbers
	public static int evaluate(String exp) {   // NOTE: will not work if a term has more than 1 digit 
		int i = 0;
		int net = 0;
		boolean isPlus = true;
		while (i < exp.length()) {
			char ch = exp.charAt(i);
			if (ch != ' ') {
				if (isNumber(ch)) {
					if (isPlus) {
						net += (ch - '0');
					} else {
						net -= (ch - '0');
					}
				} else if (ch == '-') {
					isPlus = false;
				} else if (ch == '+') {
					isPlus = true;
				} 
			}
			i++;
		}
		return net;
	}
	
	public static boolean isNumber(char ch) {
		return (ch >= '0' && ch <= '9');
	}
}