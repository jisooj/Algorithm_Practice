import java.util.*;

public class DivideLinkedList {
	public static void main(String[] args) {
		int[] arr = {3,2,1,0,10,-3};
		Node list = null;
		for (int i = 0; i < arr.length; i++) {
			list = new Node(arr[i], list);
		}
		
		Node afterList = divideList(list);
		Node curr = afterList;
		while (curr != null) {
			System.out.print(curr.val + " ");
			curr = curr.next;
		}
		System.out.println();
	}
	
	public static Node divideList(Node list) {
		Node curr = list;
		int avg = 0;
		int size = 0;
		while (curr != null) {
			avg += curr.val;
			size++;
			curr = curr.next;
		}
		avg = avg / size;
		
		curr = list;
		Node front = null;
		Node end = null;
		while (curr != null) {
			list = list.next;
			// front case 
			if (front == null) {
				front = curr;
				end = curr; 
				curr.next = null;
			} else if (curr.val < avg) { // middle case 
				curr.next = front;
				front = curr;
			} else {
				end.next = curr;
				end = end.next;
				curr.next = null;
			}
			curr = list;
		}
		return front;
	}


	static class Node {
		int val;
		Node next;
		
		public Node(int val) {
			this(val, null);
		}
		
		public Node(int val, Node next) {
			this.val = val;
			this.next = next;
		}
	}	
}
