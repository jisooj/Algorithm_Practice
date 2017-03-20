/* Problem:
Find inserted char in two otherwise identical strings

1) Assume you have two identical strings except one of them has an inserted character. How do you find which character was inserted? For example: "acdXf" and "acdf", the inserted character is 'X'. 

	My solution works for both parts 1 and 2. But for this case only space complexity could be O(1) for comparing character by character at the same indices. i.e. compare char in index 0 of both strings, then index 1, index 2, etc. If there's a differing char return the char at that index from the longer string. Since the longer string will be guaranteed to have a char inserted.

2) Now assume one of the strings is shuffled.

	My solution works for both parts 1 and 2. 
*/

/* Overview: 
 put each char of string A in hashset for fast lookup
 for each char ch in string B
	if ch is not in hashset it's the inserted char 
 do it the other way around as well since I don't know which string 
 contains inserted char */
 
/* Runtime: 
O(n), n being the number of characters in a string (since longer string is only 1 character longer)
*/

/* Space: 
O(n), n being the number of characters in a string. 
Always uses space of 2n
*/

/* Test cases: (did all except for test cases at 30 min mark)
1. "ab", "abc"
2. "ab", "cab"
3. "ab", "acb"
4. "abc", "ab"
*/
public char getInsertedChar(String a, String b) {
	// EDIT:
	// found out this is unnecessary since we can assume which string 
	// has inserted char by checking string's length
	char inserted = getInsertedCharHelper(a, b);
	if (inserted == 0) {
		return getInsertedCharHelper(b, a);
	} 
	return inserted;
}

private char getInsertedCharHelper(String s, String saved) {
	Set<Character> inventory = new HashSet<>();
	for (int i = 0; i < saved.length(); i++) {
		inventory.add(saved.charAt(i));
	}
	for (int i = 0; i < s.length(); i++) {
		char ch = s.charAt(i);
		if (!inventory.contains(ch)) {
			return ch;
		}
	}
	// return null 
	return 0;

}

---------------------------------------------------------------

/* Problem: 
Increment a decimal-coded number
Given a number, e.g. 2789, as an array [2,7,8,9], increment it: change it to [2,7,9,0]
*/

/* Overview: 
increment the element in the last index by 1. if it becomes 10, then the last element becomes 0 with a carry over to tenth place (second to last element). Next, increment the digit in tenth place by 1. Repeat this process until index 0 or there's no carry over. Stop when index is -1 (not constructing new array. we're just modifying the array)
*/

/* Runtime: 
O(digits) since there can be at most 'digits' many carry over
*/

/* Space: 
O(1). Takes an array as param and we're just modifying it without additional storage
*/

/* Test cases: 
[9]     -> [0]
[0,9,9] -> [1,0,0]
[9,9]   -> [0,0]
[1,2]   -> [1,3]

*/

public void incrementDecimal(int[] n) {
	// carry over: false = no carry over, true = there's carry over
	boolean co = false; 
	int i = n.length - 1; // current digit index 
	do {
		n[i]++;
		if (n[i] == 10) {
			n[i] = 0;
			co = true;
		} else {
			co = false;
		}
		i--; // move to the next digit
	} while (i >= 0 && co);
}


---------------------------------------------------------------
/* Problem: 
Count triangles in an undirected graph
# A triangle is an ordered triplet of nodes (a,b,c) such that 
# a-b, b-c and c-a are edges of the graph. 
# 
# Input: 
# - first line: header: <number of nodes> <number of edges> 
# - all following lines: edges: <node1> <node2> 
# The nodes are the integers 0 ... num_nodes-1. 
# 
# Example: 
# 5 5 
# 0 1 
# 4 3 
# 1 2 
# 3 1 
# 0 2 
# 
# -> returns 1 because there is exactly one triangle: (0,1,2)
*/

/* Overview: 
Process input as List<HashSet<Node>> so we have a structure like this
0 -> {v1,v2}
1 -> {v3, ... } ...
(can avoid using Map<Node, HashSet<Node>> since nodes are ints from 0 to N-1. List's index can replace the keys)

Need some way of avoiding duplicate triangles
-> have HashSet<Triplets>. every time triangle is found, check to see if it exists in this set of triplets. If not, increment the triangle count by 1.

Way to find triangle :
for each node n1 in the List
	for each neighbor n2 in n1
		for each neighbor n3 in n2
			if n3 != n1 && 
				n3 is in n1's neighbor && 
				(n1,n2,n3) is not in List<triplet>
				count++
return count
*/

/* Runtime: 
O(|V|+|E|)
*/

/* Space: 
O(|V|+|E|)

*/

/* Test cases: 

*/
import java.util.*;

public class Tri {
	public static void main(String[] args) {
		String in = "5 5\n" + 
					 "0 1\n" +
					 "4 3\n" + 
					 "1 2\n" +
					 "3 1\n" +
					 "0 2";
		System.out.println(countTriangle(in));
	}
	private static void insertNode(List<Set<Integer>> graph, int n1, int n2) {
		if (graph.get(n1) == null){
			graph.set(n1, new HashSet<Integer>());
		}
		graph.get(n1).add(n2);
	}
	
	public static int countTriangle(String input) {
		// ----------- Input processing -----------
		Scanner in = new Scanner(input);
		int numNodes = in.nextInt();
		int numEdges = in.nextInt();
		// intialize graph with nulls
		List<Set<Integer>> graph = new ArrayList<Set<Integer>>();
		for (int i = 0; i < numEdges; i++) {
			graph.add(null);
		}
		for (int i = 0; i < numEdges; i++) {
			int node1 = in.nextInt();
			int node2 = in.nextInt();
			insertNode(graph, node1, node2);
			insertNode(graph, node2, node1);
		}
		System.out.println(graph);
		// ----------------------------------
		Set<Set<Integer>> triplets = new HashSet<Set<Integer>>();
		
		// ----------- Find triangles -----------
		int count = 0;
		for (int n1 = 0; n1 < graph.size(); n1++) {
			for (int n2 : graph.get(n1)) {
				for (int n3 : graph.get(n2)) {
					Set<Integer> triplet = new HashSet<>();
					triplet.add(n1);
					triplet.add(n2);
					triplet.add(n3);
					
					if (n3 != n1 && graph.get(n1).contains(n3) && !triplets.contains(triplet)) {
						triplets.add(triplet);
						count++;
					}
				}
			}
		}
		System.out.println(triplets);
		return count;
	}
	
}

/* ---------------------------------------------------------

			Intersect two sorted lists


overview of the solution in English
soln1: store one list in hash set and iterate the other list to check if it exists in the set. If it does, put that element into resulting list
soln2: use the fact that the lists are sorted. Have two ptrs i and j to indicate current index. 

	Move a ptr where element is smaller. 
	if two elements are equal 
		Then store it into resulting list
	
	Repeat until one list runs out of elements 
	
runtime complexity
	soln1: O(n+m)
	soln2: O(n+m)
	
space complexity
	soln1: O(min(n, m)) 
	soln2: O(1)
	
test cases
	[], [1,2]
	[1,2,3], [2,2,3,5]
	[1,3,3,3,3], [3,3,3]
*/

public List<Integer> intersection(int[] list1, int[] list2) {
	int i = 0;
	int j = 0;
	List<Integer> result = new ArrayList<>();
	
	while(i < list1.size() && j < list2.size()) {
		if (list1[i] == list2[j]) {
			if (result.isEmpty() || result.get(result.size() - 1) != list1[i]) {
				result.add(list1[i]);
			}
			// increment i or j
			i++;
		} else if (list1[i] < list2[j]) {
			i++;
		} else {
			j++;
		}
	}
	return result;
}



/*
How do you print out a binary tree level-by-level, i.e., all the level 1 nodes, then the level 2 nodes (bonus if they're left-to-right)?


overview of the solution in English
soln1: List<List<Node>> where each index represents a single level. Each element is a list of nodes at corresponding level. Pre-order traversal to incrementally store levels. 

soln2: BFS

runtime complexity
soln1: O(n)
soln2: O(n)

space complexity
soln1: O(n) <- always store 'n' nodes 
soln2: O(n) <- at worst case, it will use n/2 when tree is perfect binary tree

test cases
1. null
2. single node
3. 
       1
      /  \
     2    3
	     /
        4
4. 
       1
      /  \
     2    3
    / \  / \
   4  5 6   7

*/

// ------ Store nodes at each level -----
public void printBinaryTree(Node root) {
	List<List<Node>> levels = new ArrayList<List<Node>>();
	// store nodes 
	printBinaryTree(levels, root, 0);
	
	// Print nodes 
	for (List<Node> level : levels) {
		for (Node node : level) {
			System.out.print(node + " ");
		}
		System.out.println();
	}
}

private void printBinaryTree(List<List<Node>> levels, Node root,
		int level) {
			
	if (root != null) {
		printBinaryTree(levels, root.left, level + 1);
		if (levels.size() == level) {
			levels.add(new ArrayList<Node>());
		}
		levels.get(level).add(root);
		printBinaryTree(levels, root.right, level + 1);
	}
	
}

// ------ BFS ------------
public void printBinaryTree(Node root) {
	Queue<Node> q = new LinkedList<>();
	q.add(root);
	int currRemain = 1; // remaining nodes in curr level
	int nextRemain = 0; // num nodes to process in next level
	
	while (!q.isEmpty()) {
		Node curr = q.remove();
		currRemain--;
		System.out.println(curr + " ");
		
		if (curr.left != null) {
			q.add(curr.left);
			nextRemain++;
		}
		if (curr.right != null) {
			q.add(curr.right);
			nextRemain++;
		}
		// done with this level, print next level
		if (currRemain == 0) {
			System.out.println();
			currRemain = nextRemain;
			nextRemain = 0;
		}
	}
}




/*
Given a dictionary (a list of words in lexicographic order) of all words in an unknown/invented language, find the alphabet (an ordered list of characters) of that language. 


e.g. Example dictionary: 
Art
Rat
Cat
Car

Alphabet: [a, t, r, c]

overview of the solution in English
	
	1. orders can be figured out when words have the same prefix. so convert dictionary word to prefix tree that has a dummy node as a root. this dummy node simplifies coding later
	2. traverse the prefix tree and for each node with at least 2 children, extract lexicographic ordering and store it to directed graph, which I will represent as HashMap<character, Edge>.
	Edge class contains two List<Character> for both 'outgoing' and 'incoming' nodes. 
	3. with the graph from step 2, run topological sort, which will determine the completeness and validity of the dictionary

runtime complexity
	
   n: total number of char in the dictionary
   N: number of nodes in prefix tree
   V: number of nodes in graph or number of unique char
   O(n) + O(N) + O(V^2) --> O(n^2)
   Worst case it's O(n^2) when dictionary contains a single lettered word
   and all are unique letters. e.g. dict with 'a' thru 'z'.

space complexity
   O(n), n: total number of char in the dictionary

test cases
	[Ara,Art,Rat,Cat,Car]  -> [a,t,r,c]
	[aa,ab,BB,Bb]          -> [a,B,b]
	[Art,Rat,Cat,Car]      -> incomplete ordering
	[a,b,cc,ca]            -> invalid ordering
	
	Nothing was mentioned about case sensitivity. Since the problem is dealing with an unknown/invented language I'll assume that I don't know which case comes before the other one. 
*/

class TreeNode {
	char lettter;
	Map<Character, TreeNode> children;
	public TreeNode(char letter) {
		this.letter = letter;
		// LinkedHashMap for fast lookup and ordering
		this.children = new LinkedHashMap<>();
	}
}

class Edge {
	// avoid duplicate edges when building graph
	Set<Character> outgoing;
	Set<Character> incoming;
	public Edge() {
		outgoing = new ArrayList<>();
		incoming = new ArrayList<>();
	}
}

// n: total number of char in the dictionary
// N: number of nodes in prefix tree
// V: number of nodes in graph or number of unique char
// O(n) + O(N) + O(V^2) --> O(n^2)
// Worst case it's O(n^2) when dictionary contains a single lettered word
// and all are unique letters. e.g. dict with 'a' thru 'z'.
public List<Character> getLexiOrder(List<String> dict) {
	// 1. dict to prefix tree and make char set
	TreeNode root = makePrefixTree(dict);
	
	// 2. prefix tree to graph 
	Map<Character, Edge> lexiGraph = new HashMap<>();
	buildGraph(lexiGraph, root);
	
	// 3. run topo sort with the graph
	return topologicalSort(lexiGraph);
}

// runtime: O(total char in dict)
// space: O(total char in dict)
private TreeNode makePrefixTree(List<String> dict) {
	TreeNode root = new TreeNode(0); // dummy node
	TreeNode curr = root;
	for (String word : dict) {
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			if (!curr.children.containsKey(ch)) {
				curr.children.put(ch, new TreeNode(ch));
			}
			// advance curr to next letter in the prefix
			curr = curr.children.get(ch);
		}
	}
	return root;
}


// Tree traversal to visit all nodes once. When a node has multiple children 
// link orders from it. 
// runtime: O(N), N: number of nodes in prefix tree
// space: O(N + E), E: number of relations we can find in the given dict
private void buildGraph(Map<Character, Edge> lexiGraph, TreeNode root) {
	if (root != null) {
		// checking if words have the same prefix
		if (root.children.size() > 1) {
			linkNodes(lexiGraph, root);
		} else if (!lexiGraph.containsKey(root.letter)) {
			lexiGraph.put(root.letter, new Edge());
		}
		buildGraph(lexiGraph, root.left);
		buildGraph(lexiGraph, root.right);
	}
}

// two pointers curr and prev will be linked together in the graph 
// First iteration will just set curr to the first element
// runtime and space : same as buildGraph()
private void linkNodes(Map<Character, Edge> lexiGraph, TreeNode root) {
	// iterate root's children
	Iterator<Character> iter = root.children.keySet().iterator();
	char curr = 0;
	char prev = 0;
	boolean isFirst = true;
	while (iter.hasNext()) {
		if (isFirst) {
			isFirst = false;
			curr = iter.next();
		} else {
			prev = curr;
			curr = iter.next();
			// NOTE: duplicate edges might be added here. 
			// This is why the edges are stored as a set 
			if (!lexiGraph.containsKey(prev)) {
				lexiGraph.put(prev, new Edge());
			}
			prev.outgoing.add(curr);
			if (!lexiGraph.containsKey(curr)) {
				lexiGraph.put(curr, new Edge());
			}
			curr.incoming.add(prev);
		}	
	}
}

// runtime: O(V^2), V: number of vertices/nodes in the graph == num unique char
// space: O(V)
private List<Character> topologicalSort(Map<Character, Edge> graph) {
	List<Character> order = new ArrayList<>();
	while (!graph.isEmpty()) {  // O(V)
		// baseNode is a node without any incoming edges
		char baseNode = 0;
		char numBaseNode = 0;
		for (char node : graph.keySet()) { // O(V)
			List<Character> incomingEdges = graph.get(node).incoming;
			if (incomingEdges.isEmpty()) {
				numBaseNode++;
				if (numBaseNode > 1) {
					throw new IllegalArgumentException("Incomplete ordering: Need more info");
				}
				baseNode = node;
			}
		}
		// At any point if the baseNode isn't found, cycle exists
		// (NOTE: graph also needs to be !empty, 
		// but this condition is satisfied inside while loop)
		if (numBaseNode == 0) {
			throw new IllegalArgumentException("Invalid ordering: cycle detected");
		}
		
		// Add baseNode to resulting order
		order.add(baseNode);
		
		// Remove links with basedNode and its outgoing nodes
		// This loop iterates no more than |Edges| total
		for (char out : graph.get(baseNode).outgoing) { 
			graph.get(out).incoming.remove(out);
		}
		// Remove baseNode from graph
		graph.remove(baseNode);
	}
	return order;
}














