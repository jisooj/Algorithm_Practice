import java.util.*;
// 389112 
public class LockPatterns {
	public static void main(String[] args) {
		LockPatterns lp = new LockPatterns();
		System.out.println(lp.countLockPatterns());
	}

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
		
		public String toString() {
			return index+"";
		}
	}	
	
}
