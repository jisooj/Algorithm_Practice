import java.util.*;

public class FindRoute {
	public static void main(String[] args) {
		List<Pass> passes = new ArrayList<>();
		passes.add(new Pass("A", "B"));
		passes.add(new Pass("B", "C"));
		passes.add(new Pass("B", "E"));
		passes.add(new Pass("C", "D"));
		passes.add(new Pass("D", "C"));
		passes.add(new Pass("D", "E"))'
		// case1: A->B->C->D
		System.out.println(findRoute(passes, "A", "D");
		// case2: no path
		System.out.println(findRoute(passes, "E", "D");
		
	}
	
	public List<Pass> findRoute(List<Pass> passes,
			String from, String to) {

	}
	public class Node {
		
	}
	public class Pass {
		String from;
		String to;
		public Pass(String from, String to) {
			this.from = from;
			this.to = to;
		}
	}
}