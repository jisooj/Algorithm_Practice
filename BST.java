import java.util.*;

public class BST {
	public static void main(String[] args) {
		System.out.println(new BST().isBST());
	}
	private Node root;
	
	public BST() {
		root = new Node(5);
		Node node1 = new Node(2);
		Node node2 = new Node(10);
		Node node3 = new Node(1);
		Node node4 = new Node(4);
		
		root.left = node1;
		root.right = node2;
		node1.left = node3;
		node1.right = node4;
	}
	
	// Another approach for mergeBST:
	// runtime: O((|T1| + |T2|)^2)
	// space: O(1)
	// Overview: traverse T1 and add each node to T2
	
	// returns a root node to merged BST
	// runtime: O(|T1| + |T2|)
	// space: O(|T1| + |T2|)
	public Node mergeBST(Node root1, Node root2) {
		/*
		in-order traveral in BST will result sorted order
		1. traverse T1 in-order and store nodes in list1
		2. repeat step 1 for T2
		3. merge list1 and list2 to form list3
		4. build balanced BST from list3
		*/
		List<Node> list1 = new ArrayList<>();
		List<Node> list2 = new ArrayList<>();
		inOrder(root1, list1);
		inOrder(root2, list2);
		List<Node> list3 = mergeLists(list1, list2);
		// making sure leaf nodes won't have children
		clearChildren(list3); 
		return buildBalncedBST(list3, 0, list3.size() - 1);
	}
	
	private Node buildBalncedBST(List<Node> sortedList, int low, int high) {
		if (low <= high) {
			int mid = (low + high) / 2;
			Node root = sortedList.get(mid);
			root.left = buildBalncedBST(sortedList, low, mid - 1);
			root.right = buildBalncedBST(sortedList, mid + 1, high);
			return root;
		}
		return null;
	}
	// assuming T1 and T2 will have distinct values
	// if not, we can store elem in LinkedHashSet and then put them back into List
	private List<Node> mergeLists(List<Node> list1, List<Node> list2) {
		List<Node> result = new ArrayList<>();
		int i = 0;
		int j = 0; 
		while (i < list1.size() && j < list2.size()) {
			Node n1 = list1.get(i);
			Node n2 = list2.get(j);
			if (n1.val < n2.val) {
				result.add(n1);
				i++;
			} else {
				result.add(n2);
				j++;
			}
		}
		while (i < list1.size()) {
			result.add(list1.get(i));
		}
		while (j < list2.size()) {
			result.add(list2.get(j));
		}
		return result;
	}
	private void clearChildren(List<Node> list) {
		for (Node n : list) {
			n.left = null;
			n.right = null;
		}
	}
	private void inOrder(Node root, List<Node> list) {
		if (root != null) {
			inOrder(root.left, list);
			list.add(root);
			inOrder(root.right, list);
		}
	}
	
	// returns true if the given binary tree is BST
	public boolean isBST() {
		return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	private boolean isBST(Node root, int min, int max) {
		if (root == null) {
			return true;
		}
		boolean isValid = (root.val >= min && root.val <= max);
		if (!isValid) {
			return false;
		}
		return isBST(root.left, min, root.val - 1) &&
				isBST(root.right, root.val + 1, max);
	}
	
	class Node {
		int val;
		Node left;
		Node right;
		
		public Node(int val) {
			this(val, null, null);
		}
		
		public Node(int val, Node left, Node right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
}