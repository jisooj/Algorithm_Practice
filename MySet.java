import java.util.*;

// Let's say MySet stores integers 
public class MySet {
	private Map<Integer, Integer> indexToObj;
	private Map<Integer, Integer> objToIndex;
	
	public MySet() {
		indexToObj = new HashMap<>();
		objToIndex = new HashMap<>();
	}
	
	public void insert(int obj) {
		if (!objToIndex.containsKey(obj)) {
			int index = objToIndex.size();
			objToIndex.put(obj, index);
			indexToObj.put(index, obj);
		}
		
	}
	
	public void remove(int obj) {
		if (objToIndex.containsKey(obj)) {
			int index = objToIndex.get(obj); // removed element's index
			int lastObj = indexToObj.get(indexToObj.size() - 1);
			indexToObj.put(index, lastObj);
			indexToObj.remove(indexToObj.size() - 1);
			
			objToIndex.put(lastObj, index);
			objToIndex.remove(obj);
		}
	}
	
	public int getRandomElement() {
		Random r = new Random();
		int randIndex = r.nextInt(indexToObj.size()); // range: [0, size)
		return indexToObj.get(randIndex);
	}
	
	public String toString() {
		return objToIndex.keySet().toString();
	}
	
	public static void main(String[] args) {
		MySet set = new MySet();
		set.insert(3);
		set.insert(1);
		set.insert(2);
		set.insert(1);
		System.out.println(set);
		System.out.println(set.getRandomElement());
		System.out.println(set.getRandomElement());
		System.out.println(set.getRandomElement());
		set.remove(2);
		System.out.println(set);
		
	}
}