public class XorTest {
	// x^y^x=y
	public static void main(String[] args) {
		int[] a = {1,6,3,4,5};
		int n = 6;
		int missing = 0;
		for (int i = 1; i <= n; i++) {
			missing ^= i;
		}
		for (int i = 0; i < a.length; i++) {
			missing ^= a[i];
		}
		System.out.println(missing);
	}
}

public class FruitComparator implements Comparator<Fruit> {
	public int compare(Fruit a, Fruit b) {
		// do something here
		return 0;
	}
}