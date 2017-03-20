import java.util.*;
// implement Queue with a single stack 
public class QueueWithStack<E> {
	private Stack<E> st;
	
	public QueueWithStack() {
		st = new Stack<E>();
	}
	
	public void enqueue(E elem) {
		st.push(elem);
	}
	
	public E dequeue() {
		if (st.isEmpty()) {
			return null;
		} else {
			E e = st.pop();
			E bottom = dequeue();
			if (bottom == null) {
				return e;
			} else {
				st.push(e);
				return bottom;
			}
		}
	}
	
	public String toString() {
		return st.toString();
	}
	
	public boolean isEmpty() {
		return st.isEmpty();
	}
	
	public static void main(String[] args) {
		int[] arr = {1,2,3,4,5,6};
		QueueWithStack<Integer> q = new QueueWithStack();
		for (int i : arr) {
			q.enqueue(i);
		}
		System.out.println(q);
		while (!q.isEmpty()) {
			System.out.print(q.dequeue() + " ");
		}
		System.out.println();
	}
}