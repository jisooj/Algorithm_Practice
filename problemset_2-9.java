/*
2/9 Q1 Count the number of islands in a 2D map
Given a 2-dimensional map where each tile is either land or sea, count the number of islands. 
An island is a contiguous set of tiles that are connected horizontally or vertically. 
The following map has three islands (x'es are land, zeros are sea); 

000000
00xx00
0000x0
00000x


overview of the solution in English
Let's suppose the input is char[][] where each element can be either '0' or 'x' to represent islands and sea
Think the matrix is a graph where each element has edges in four ways : up, down, left, right. Borders will 
have some missing edges depending on where they are located. 
We apply BFS and mark every visited place. 
1. Sweep the matrix from top-left corner and if it sees unvisited 'x' then apply BFS 
2. After each BFS, increment the total island count 
In order to keep track of discovered nodes, let's have a boolean matrix with the same dimension

runtime complexity
Happens if the entire matrix is an island
Since runtime of BFS O(|V| + |E|)
|V| = row*col 
|E| = row * (col - 1) + (row - 1) * col
    = rc - r + rc - c
	= 2rc - r - c
Overall runtime : O(row*col)
(|V| + |E|) = (rc + 2rc - r - c)
            = (3rc - r - c)
            = (rc) => O(row*col)
space complexity
O(row*col)

test cases
empty

0

x00

00x0x
0x0xx
0000x
*/


// Main counting method
public int islandCount(char[][] mat) {
	int row = mat.length;
	int col = mat[0].length;
	// default values => false
	boolean[][] visited = new boolean[row][col];
	int count = 0;
	for (int i = 0; i < row; i++) {
		for (int j = 0; j < col; j++) {
			if (!visited[i][j]) {
				count += BFS(mat, visited, i, j);
			}
		}
	}
	return count;
}

// Helper class 
class Point {
	int row;
	int col;
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}
}

// Helper methods 
// checks if i and j are valid coordinates in the given matix
// Returns true if it's valid. Returns false otherwise
private boolean enqueue(Queue<Point> vertices, char[][] mat, int i, int j) {
	if (isValid(mat, i, j) && mat[i][j] == 'x') {
		vertices.add(new Point(i, j));
		return true;
	}
	false;
}

// checks if i and j are valid coordinates in the given matix
private boolean isValid(char[][] mat, int i, int j) {
	return (i < mat.length && i >= 0) && (j < mat[0].length && j >= 0);
}

// If we enqueue at least one node that's 'x', return 1. Else, return 0
private int BFS(char[][] mat, boolean[][] visited, int i, int j) {
	Queue<Point> vertices = new LinkedList<>();
	boolean foundIsland = false;
	foundIsland |= enqueue(vertices, mat, i, j);
	while (!vertices.isEmpty()) {
		Point curr = vertices.remove();
		visited[curr.row][curr.col] = true; 
		foundIsland |= enqueue(vertices, mat, i + 1, j);
		foundIsland |= enqueue(vertices, mat, i - 1, j);
		foundIsland |= enqueue(vertices, mat, i, j + 1);
		foundIsland |= enqueue(vertices, mat, i, j - 1);
	}
	if (foundIsland) {
		return 1;
	} else {
		return 0;
	}
}

/* 7:10 - 7:40

2/9 Q2 Implement a setlike data structure that supports Insert, Remove, and GetRandomElement efficiently

overview of the solution in English
NOTES:
void insert(Obj); HashSet, HashMap
void remove(Obj); HashSet, HashMap
Obj getRandomElement(); Lists, array handle efficient read with index

Need: 
Some type of indexing for fast random access
Some type of hashing for fast insert and remove 
HashMap<Integer, Obj> => <index, Obj>

Bi-directional map by storing two hash maps : 
Hash maps for <index, obj> and <obj, index>. 
Insert when obj is not found in keySet in <obj, index> map. 
Remove when obj is found in keySet. Get the most recently added element and override the obj we wish to remove. Then remove unnecessary mapping in both maps.

code until 30 min mark --> remove method unfinished

runtime complexity
	insert = O(1) on average
	remove = O(1) on average
	getRandomElement = O(1)
space complexity
	O(n), n: number of elements inserted 
	
test cases
	insert : 3, 1, 2, 1
	getRandomElement()
	getRandomElement()
	remove(2)
	print
*/

// Let's say MySet stores integers 
public class MySet {
	private Map<Integer, Integer> indexToObj;
	private Map<Integer, Integer> objToIndex;
	
	public MySet() {
		indexToObj = new HashMap<>();
		objToIndex = new HashMap<>();
	}
	
	public void insert(int obj) {
		if (!objToIndex.containsKey(obj)) {
			int index = objToIndex.size();
			objToIndex.put(obj, index);
			indexToObj.put(index, obj);
		}
		
	}
	
	public void remove(int obj) {
		if (objToIndex.containsKey(obj)) {
			int index = objToIndex.get(obj); // removed element's index
			int lastObj = indexToObj.get(indexToObj.size() - 1);
			indexToObj.put(index, lastObj);
			indexToObj.remove(indexToObj.size() - 1);
			
			objToIndex.put(lastObj, index);
			objToIndex.remove(obj);
		}
	}
	
	public int getRandomElement() {
		Random r = new Random();
		int randIndex = r.nextInt(indexToObj.size()); // range: [0, size)
		return indexToObj.get(randIndex);
	}
	
	public String toString() {
		return objToIndex.keySet().toString();
	}
	
	public static void main(String[] args) {
		MySet set = new MySet();
		set.insert(3);
		set.insert(1);
		set.insert(2);
		set.insert(1);
		System.out.println(set);
		System.out.println(set.getRandomElement());
		System.out.println(set.getRandomElement());
		System.out.println(set.getRandomElement());
		set.remove(2);
		System.out.println(set);
		
	}
}


/*
Q3 Break up a string into dictionary words
For example, "OBAMASAYSHEPLANSTOVETOTHEBILL" to "obama says he plans to veto the bill". You’re given a dictionary in whichever form you like.

overview of the solution in English
Recursively break up the words by taking out substring from the starting index i.
i changes in every recursive call. Extract the substring from the index i up until 
the substring exists in the dictionary. Then i increments the length of the chosen substring, which will be stored in a stack. If recursive call doesn't find the answer, backtrack by popping out a word from the stack. Then i decrements. 
Repeat until i reaches the end of the initial string. 


code until 30 min mark

complete solution if took more than 30 mins

runtime complexity
n: number of characters in the given string. 
O(2^n)

space complexity
O(n), n: number of characters in the given string. 

test cases
s = hellohi, dict = {hell,hello,hi}
s = hellohi, dict = {hell,hello}

*/
private String buildResult(Stack<String> result) {
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


public String breakString(HashSet<String> dict, String str) {
	return breakString(dict, new Stack<String>(), str, 0);
}

private String breakString(HashSet<String> dict, Stack<String> result, 
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


/*
2/9 Q4 Count the number of android lock patterns
There are 9 points in a 3x3 grid. A valid lock pattern connects between 4 and 9 of the points. Each point can be visited at most once. However notice there is one other important and less obvious rule to how points can be connected. Any two points A and B can be directly connected only if there is no other point C directly between them (in a straight line in the plane), unless C was already visited. This is because the pattern would not be AB in this case, but rather ACB.

Example

A C B 
X X X 
X X X 

Going from A to B will capture C in the pattern but only if C was not already visited.

overview of the solution in English
3x3 grid is represented as a graph where each node has edges to all the other nodes. 
Node A is reachable from node B if there's no node in between nodes A and B. If there's a 
node in between, but it's already been visited, then node A is reachable. 

Apply DFS from starting node until there's no more nodes to discover and let's keep track of path. 
For each chosen node, if the path length is at least 4 increment the lock count by 1.
Repeat these steps from node 1 to 9

runtime complexity
DFS 9 times total
for each DFS 
	8 path in first recursive call
		7 path in second recursive call 
			...
				1 path
9 * 8 * 7 * ... * 1
Therefore overall runtime is O(n!) where n: number of points in a grid 

space complexity
O(n) as we keep track of current path and marks all the nodes 

test cases
not really much test cases since grid is fixed to 3x3

*/
public static final int NUM_NODES = 9;

public int countLockPatterns() {
	// create nodes 
	List<Node> nodes = new ArrayList<>();
	for (int i = 0; i < NUM_NODES; i++) {
		nodes.add(new Node(i));
	}
	
	int count = 0;
	for (int i = 0; i < NUM_NODES; i++) {
		boolean[] visited = new boolean[NUM_NODES]; // reset to false
		Stack<Node> path = new Stack<>();
		
		path.push(nodes.get(i));
		visited[i] = true;
		
		count += DFS(nodes, visited, path, nodes.get(i));
	}
	return count;
}

// need to reset visited
private int DFS(List<Node> nodes, boolean[] visited, Stack<Node> path, Node current) {
	if (path.size() == NUM_NODES) {
		return 1;
	} else {
		int count = 0;
		if (path.size() >= 4) {
			count++;
		}
		for (int i = 0; i < NUM_NODES; i++) {
			Node next = nodes.get(i);
			if (current.isReachable(next, visited)) {
				visited[next.index] = true;
				path.push(next);
				count += DFS(nodes, visited, path, next);
				path.pop();
				visited[next.index] = false;
			}
		}
		return count;
	}
}


class Node {
	int x;
	int y;
	int index; // 0 - 8
	
	public Node(int index) {
		this.index = index;
		x = index % 3;
		y = index / 3;
	}
	/*
	0 1 2
	3 4 5 
	6 7 8
	*/
	public boolean isReachable(Node other, boolean[] visited) {
		int dx = Math.abs(other.x - x);
		int dy = Math.abs(other.y - y);
		boolean isNodeBtw = ((dx == 2) && (dy == 0)) || 
						((dx == 0) && (dy == 2)) || 
						((dx == 2) && (dy == 2));
		Node lowerIndexNode = null;
		if (this.index < other.index) {
			lowerIndexNode = this;
		} else {
			lowerIndexNode = other;
		}
		int indexDiff = Math.abs(other.index - this.index) / 2;
		int middleNodeIndex = lowerIndexNode.index + indexDiff;
		
		// needs to check if there's node in between first. 
		// then check if other node has been discovered
		if (isNodeBtw) {
			return visited[middleNodeIndex] && !visited[other.index];
		} else {
			return !visited[other.index];
		}
	}
}	

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
char[][] mat = {
	{'a', 'e', 'e', 't'},
	{'w', 'b', 'r', 'h'},
	{'o', 's', 'y', 'e'},
	{'o', 'k', 'h', 'w'},
	};
Dictionary dict = new Dictionary(new String[] {
	"the", "they", "bee", "book", "bet", "why", "abr"
});
choosing 8 direction is done wrong
*/
public static final int GRID_SIZE = 4;

public Set<String> boggle(char[][] mat, Dictionary dict) {
	Set<String> ans = new HashSet<>();
	// for each cell, apply DFS
	for (int i = 0; i < mat.length; i++) {
		for (int j = 0; j < mat[0].length; j++) {
			Set<Integer> visited = new HashSet<>();
			String firstLetter = mat[i][j] + "";
			if (dict.isWord(firstLetter)) {
				ans.add(firstLetter);
			}
			int index = i * GRID_SIZE + j;
			visited.add(index);
			DFS(ans, mat, dict, visited, i, j, firstLetter);
		}
	}
	return ans;
}


// stop when there's no more path to take 
// stop when index is out of bounds: not [0,r*c)
// get surrounding indices from current index 
private void DFS(Set<String> ans, char[][] mat, 
		Dictionary dict, Set<Integer> visited, 
		int r, int c, String current) {
	for (int i = 0; i < 9; i++) {
		int nextIndex = getNext(r, c, i);
		if (isValid(nextIndex, mat) && 
			!visited.contains(nextIndex)) {
				
			int nextRow = nextIndex / mat[0].length;
			int nextCol = nextIndex % mat[0].length;
			String newWord = current + mat[nextRow][nextCol];
			if (dict.isWord(newWord)) {
				ans.add(newWord);
			}
			visited.add(nextIndex);
			DFS(ans, mat, dict, visited, nextRow, nextCol, newWord);
			visited.remove(nextIndex);
		}
	}
}
/*
0  1  2  3 
4  5  6  7
8  9  10 11
12 13 14 15
*/
private int getNext(int r, int c, int i) {
	// i:  0  1  2  3 4 5  6 7 8
	// r: -1 -1 -1  0 0 0  1 1 1
	// c: -1  0  1 -1 0 1 -1 0 1
	int row = r + (i / 3) - 1;
	int col = c + (i % 3) - 1;
	if (row < 0 || col < 0) {
		return -1;
	} 
	return row * GRID_SIZE + col;
}
private boolean isValid(int index, char[][] mat) {
	return (index >= 0) && (index < mat.length * mat[0].length);
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
