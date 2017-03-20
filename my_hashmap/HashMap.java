// Jisoo Jung

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

// HashMap is a collection of elements that associates key element to
// value element. It uses hash code to allow the clients for quick 
// insert, remove, and search operations. Unlike TreeMap, HashMap
// is not sorted, but it has its advantages on the fast operations.
public class HashMap<K, V> implements Map<K, V> {
   private static final int DEFAULT_CAPACITY = 10;
   private static final double MAX_LOAD = 0.75;
   private Node[] elements;
   private int size;
	
   // Constructs a new, empty HashMap object.
   @SuppressWarnings("unchecked")
	public HashMap() {
		elements = (Node[]) new HashMap.Node[DEFAULT_CAPACITY];
	}
	
   // Removes all the association of key/value pairs from this object.
	public void clear() {
      for (int i = 0; i < elements.length; i++)
         elements[i] = null;
      size = 0;
	}
	
   // Returns true if the given key is associated with a value in this map.
   // Throws NullPointerException if the given key is null.
	public boolean containsKey(K key) {
      return findNode(key) != null;
	}
   
   // Returns a value associated with the given key.
   // Throws NullPointerException if the given key is null.   
	public V get(K key) {
      Node keyNode = findNode(key);
      if (keyNode != null)
         return keyNode.value;
      return null;
	}
   
   // Returns a node that stores the given key and its value associated with.
   // Throws NullPointerException if the given key is null.   
   private Node findNode(K key) {
      Node current = elements[hash(key)];
      while (current != null) {
         if (current.key.equals(key))
            return current;
         current = current.next;
      }
      return null;
   }

   // Returns true if the map has no key/value pair. Returns false otherwise.
	public boolean isEmpty() {
      return size == 0;
	}

   // Returns a set containing all the keys that have been put into this map.
	public Set<K> keySet() {
      Set<K> keySet = new HashSet<K>();
      for (Node node : elements) {
         while (node != null) {
            keySet.add(node.key);
            node = node.next;
         }
      }
      return keySet;
	}

   // Associates the given key to the given value. Size increases by one.
   // If the load factor (size to capacity ratio) reaches or exceeds 
   // MAX_LOAD, its capacity is increased. 
   // Throws NullPointerException if either or both key and value are null.
	public void put(K key, V value) {
      if (value == null)
         throw new NullPointerException();
      if (!containsKey(key)) {
         int h = hash(key);      
         Node node = new Node(key, value);
         node.next = elements[h];
         elements[h] = node;
         size++;
      } else // below line replaces old key-value pair
         findNode(key).value = value; 
      
      if (loadFactor() >= MAX_LOAD)
         rehash();
	}
	
   // Returns size to capacity ratio.
   private double loadFactor() {
      return (double) size / elements.length;
   }

   // Doubles the current capacity. This method is called when the load 
   // factor reaches or exceeds MAX_LOAD.
   @SuppressWarnings("unchecked")
   private void rehash() {
      Node[] newer = (Node[]) new HashMap.Node[2 * elements.length];
      Node[] old = elements;
      elements = newer;
      size = 0;
      for (Node node : old) {
         while (node != null) {
            put(node.key, node.value);
            node = node.next;
         }
      }
   }
   
   // Removes key/value pair for the given key. Size decreases by one.
	public void remove(K key) {
      int h = hash(key);   
      if (elements[h] != null) {
         if (elements[h].key.equals(key)) {
            elements[h] = elements[h].next;
            size--;
         } else {
            Node current = elements[h];
            while (current.next != null && !current.next.key.equals(key))
               current = current.next;
            // [current.next == null || current.next.key.equals(key)] at this point
            if (current.next != null) { 
               current.next = current.next.next;
               size--;
            }
         }
      }
	}
   
   // Returns the number of key/value pairs in this map.
	public int size() {
      return size;
	}

   // Returns a string representation of this map and has following format:
   // {key=value, key=value}
	public String toString() {
      String result = "";
      boolean first = true;
      for (Node node : elements) {
         while (node != null) {
            if (first) {
               result += node.key + "=" + node.value;
               first = false;
            } else
               result += ", " + node.key + "=" + node.value;
            node = node.next;
         }
      }
      return "{" + result + "}";
	}
   
   // Returns an index that the given key should be stored inside the 
   // elements. The index is produced based on this hash function and 
   // the key's hash code.
   // Throws NullPointerException if the given key is null.
	private int hash(K key) {
		return Math.abs(key.hashCode()) % elements.length;
	}
   
   // Node object stores key, value, and a reference to the next node.
	private class Node {
      public K key;
      public V value;
      public Node next;
      
      // Constructs a new Node object and stores the given key and value.
      // A reference to the next node is initially set to null.
      public Node(K key, V value) {
         this.key = key;
         this.value = value;
      }
   }
}