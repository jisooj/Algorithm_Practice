import java.util.*;

public class MyLinkedList {
	private Node front; 
	private int size; 

	public MyLinkedList(int... data) {
		for (int i = data.length - 1; i >= 0; i--) {
			front = new Node(data[i], front, null);
			size++;
		}
	}

	/*Given a linked list, where each node's value can
	itself be a linked list (a recursive linked list), 
	write a function that flattens it.  */
	public void flatten() {
		flatten(front);
	}

	private void flatten(Node list) {
		
	}

	public void addBranch(int index, int... branchData) {
		if (index >= size) {
			throw new IllegalArgumentException();
		}
		Node current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		for (int i = branchData.length - 1; i >= 0; i--) {
			current.branch = new Node(branchData[i], null, current.branch);
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node current = front;
		if (current != null) {
			Node currBranch = current.branch;
			sb.append(current.data);
			while (currBranch != null) {
				sb.append("->");
				sb.append(current.branch.data);
				currBranch = currBranch.branch;
			}
		}

		current = front.next;
		while (current != null) {
			Node currBranch = current.branch;
			sb.append("\n|\n");
			sb.append(current.data);
			while (currBranch != null) {
				sb.append("->");
				sb.append(currBranch.data);
				currBranch = currBranch.branch;
			}
			current = current.next;
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		MyLinkedList list = new MyLinkedList(1,2,3,4,5);
		list.addBranch(1, 
			1,2,3,4,5);
		System.out.println(list);
	}


	class Node {
		int data;
		Node next;
		Node branch;

		Node(int data, Node next, Node branch) {
			this.data = data;
			this.next = next;
			this.branch = branch;
		}
		Node(int data) {
			this(data, null, null);
		}
	}
}
