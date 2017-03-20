/*
2/9 Q5 Boggle (word search)
Given a 4X4 grid of letters and a dictionary, find all the words from the dictionary that can be formed in the grid. 

The rules for forming a word are: start at any position on the board, move in any of the up to 8 directions to choose another letter, repeat. You cannot re-use a letter in a given position in the same word. 

The dictionary is an object with an "isWord" method. If you want that object to have any other methods, assume it has them. Implement the dictionary as well.

overview of the solution in English
Apply DFS for each cell in the grid. Explore 8 paths without violating rules and stop when we hit borders or run out of paths to take. 
For each chosen letter, check to see if it exists in the dict.
	
	
runtime complexity
O(row*col*O(DFS))
O(DFS)???

space complexity
O(row*col)

test cases
a e e t
w b r h
o s y e
o k h w

{the, they, bee, book, bet, why}

choosing 8 direction is done wrong
*/
import java.util.*;
public class Boggle {
   public static final int GRID_SIZE = 4;
   public static void main(String[] args) {
      Boggle bg = new Boggle();
      char[][] mat = {
         {'a', 'e', 'e', 't'},
         {'w', 'b', 'r', 'h'},
         {'o', 's', 'y', 'e'},
         {'o', 'k', 'h', 'w'},
         };
      Dictionary dict = new Dictionary(new String[] {
         "the", "they", "bee", "book", "bet", "why", "abr"
         });
      System.out.println(bg.boggle(mat, dict));
   	
   }

   public Set<String> boggle(char[][] mat, Dictionary dict) {
		Set<String> result = new HashSet<>();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				Set<Integer> path = new HashSet<>();
				DFS(mat, dict, path, i, j, "", result);
			}
		}
		return result;
	}

	private void DFS(char[][] mat, Dictionary dict, Set<Integer> path, 
			int i, int j, String curr, Set<String> result) {
		String newWord = curr + mat[i][j];

		if (dict.isWord(newWord)) {
         System.out.println(newWord);
			result.add(newWord);
		}
		int currIndex = getIndex(i, j, mat);
		path.add(currIndex);
		
		for (int k = 0; k < 9; k++) {
			// k: 0   1  2  3 4 5  6 7 8
			// i: -1 -1 -1  0 0 0  1 1 1 
			// j: -1  0  1 -1 0 1 -1 0 1
			int nextI = (k / 3) - 1 + i;
			int nextJ = (k % 3) - 1 + j;
			int nextIndex = getIndex(nextI, nextJ, mat);
			if (isValidCoord(nextI, nextJ) && !path.contains(nextIndex)) {
				DFS(mat, dict, path, nextI, nextJ, newWord, result);
            path.remove(nextIndex);
			}
		}
	}
	
	private int getIndex(int i, int j, char[][] mat) {
		return i * mat[0].length + j;
	}
	private boolean isValidCoord(int i, int j) {
		return !(i < 0 || i >= GRID_SIZE || 
				j < 0 || j >= GRID_SIZE);
	}
	
	
}




class Dictionary {
   Set<String> dict;
   public Dictionary(String[] words) {
      dict = new HashSet<>();
      for (String s : words) {
         dict.add(s);
      }
   }
   public boolean isWord(String str) {
      return dict.contains(str);
   }
}
