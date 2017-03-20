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