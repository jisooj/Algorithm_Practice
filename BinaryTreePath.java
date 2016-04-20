/*Given a binary tree, write a function that prints all of 
the paths from the root node to the leaf nodes. What is the 
functions run time and space requirement.  

runtime : O(n log(n))
space : O(log(n)) in balanced binary tree
		O(n) in linked list-like binary tree

max number of leaf nodes possible: perfect binary tree = 2^(h-1) = n / 2 + 1

Runtime analysis:
		stack size	toString	numLeaves	X
list 	O(n)		O(X)		1 			X = n
perfect	O(log(n))	O(X)		n/2 + 1 	X = log(n)

list's runtime    = O(n)
perfect's runtime = O(n log(n))
*/
import java.util.*;
public class BinaryTreePath {
	class Node {
		int data;
		Node left;
		Node right;

		Node(int data, Node left, Node right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
		Node(int data) {
			this(data, null, null);
		}
		public String toString() {
			return data + "";
		}
	}

	Node root;


	//  1
	// 2 3 
	// 	  1
	public BinaryTreePath() {
		/* this.root = new Node(1,
			new Node(2), 
			new Node(3, null, new Node(1))); */
		this.root = new Node(0,
			new Node(1, new Node(2), new Node(3)), 
			new Node(4));
	}

	//  1
	// 2 3 
	// 	  1
	public void printPaths() {
		if (root != null) {
			Stack<Node> path = new Stack<Node>();
			path.push(root);
			printPaths(path, root);	
		}
	}

	// [1]
	private void printPaths(Stack<Node> path, Node root) {
		if (root != null) {
			if (root.left == null && root.right == null) {
				System.out.println(path);
			} else {
				path.push(root.left);
				printPaths(path, root.left);
				path.pop();

				path.push(root.right);
				printPaths(path, root.right);
				path.pop();
			}
		}
	}
	
	// ---------------------------------------------------------
   // this way, or use heapsort by storing all nodes in array with 1 based indexing
	void sinkZero() {
		root = sinkZero(root);
	}

	Node sinkZero(Node root) {
		if (root != null) {
			root.left = sinkZero(root.left);
			root.right = sinkZero(root.right);
			// process
			if (root.data == 0) {
				root = percolateDown(root);
			}
		}
		return root;
	}

	boolean isLeaf(Node root) {
		return root.left == null && root.right == null;
	}

	Node percolateDown(Node zero) {
		while (!isLeaf(zero)) {
         Node tempRoot = null;
			if (zero.left != null) {
				tempRoot = swap(zero, zero.left, true);
			} else if (zero.right != null) {
				tempRoot = swap(zero, zero.right, false);
			}
		}
		return root;
	}
	// swap the given parent and child
	Node swap(Node parent, Node child, boolean isLeft) {
		Node L = parent.left;
		Node R = parent.right;
		parent.left = child.left;
		parent.right = child.right;
		if (isLeft) {
			child.left = parent;
			child.right = R;
		} else {
			child.right = parent;
			child.left = L;
		}
      return child;
	}
	// ---------------------------------------------------------
	void printPreorder() {
		printPreorder(root);
		System.out.println();
	}
	private void printPreorder(Node root) {
		if (root != null) {
			System.out.print(" " + root.data + " ");
			printPreorder(root.left);
			printPreorder(root.right);
		}
	}
	// ---------------------------------------------------------

	public static void main(String[] args) {
		BinaryTreePath tree = new BinaryTreePath();
		tree.root = tree.percolateDown(tree.root);
		//tree.root = tree.swap(tree.root, tree.root.left, true);
		System.out.println("--------");
		tree.printPreorder();
	}


}