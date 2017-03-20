// Jisoo Jung 
// EXTRA CREDIT OPTIONS IMPLEMENTED - A3E1 and A3E2
//
// For extra credit options A3E1 and A3E2, implement the following methods of class CustomHashtable:
// clear, containsKey, keySet, get, isEmpty, put, putAll, size
// Use an initial capacity of 11 in your hashtable.
// Automatically rehash to double the capacity when using linear probing and a put operation would make the load factor exceed 0.8.
// If you are implementing option A3E2,
// automatically rehash to a capacity of PRIMEDOUBLE(N) when using quadratic probing and a put operation would make the load factor exceed 0.5.
// Here N is the current capacity, and we define PRIMEDOUBLE(N) to be the smallest prime that is greater than 2N.

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomHashtable<K,V> implements Map {
   boolean linearProbing = true;
   boolean quadraticProbing = false;
	
   private static final int DEFAULT_CAPACITY = 11;
   private static final double LOAD_FACTOR = 0.5;
   private Node[] elements;
   private int size;
   
   // clear, containsKey, keySet, get, isEmpty, put, putAll, size
   // TODO
   @SuppressWarnings("unchecked")
   public CustomHashtable() {
      elements = (Node[]) new Object[DEFAULT_CAPACITY];
   }
	
   // TODO clear
   @Override
   public void clear() {
      for (int i = 0; i < elements.length; i++)
         elements[i] = null;
      size = 0;
   }

   // TODO containskey
   @Override
   @SuppressWarnings("unchecked")
   public boolean containsKey(Object key) {
      return findNode((K) key) != null;
   }

   @Override
   public boolean containsValue(Object arg0) {
	  // TODO Auto-generated method stub
      return false;
   }

   @Override
   public Set entrySet() {
      return null;
   }

   // TODO get
   @Override
   @SuppressWarnings("unchecked")
   public V get(Object k) {
	  K key = (K) k;
      Node targetNode = findNode(key);
      if (targetNode != null) {
         return targetNode.value;
      }
      return null;
   }

   //TODO isEmpty
   @Override
   public boolean isEmpty() {
      return size == 0;
   }

   // TODO keyset
   @Override
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

   private Node findNode(K key) {
      Node current = elements[hash(key)];
      while (current != null) {
         if (current.key.equals(key))
            return current;
         current = current.next;
      }
      return null;
   }
   
   // TODO put
   @Override
   @SuppressWarnings("unchecked")
   public V put(Object k, Object v) {
      if (v == null)
         throw new NullPointerException();
      K key = (K) k;
      V value = (V) v;
      if (!containsKey(key)) {
         int h = hash(key);      
         Node node = new Node(key, value);
         node.next = elements[h];
         elements[h] = node;
         size++;
      } else {// below line replaces old key-value pair
         findNode(key).value = value; 
      }
      if (loadFactor() >= LOAD_FACTOR)
         rehash();
      return value;
   }
	
   // Returns size to capacity ratio.
   private double loadFactor() {
      return (double) size / elements.length;
   }	
   
   // TODO put all 
   @Override
   @SuppressWarnings("unchecked")
   public void putAll(Map map) {
	   for (K key : (Set<K>) map.keySet()) {
		   put(key, map.get(key));
	   }
   }

   
   @Override
   public Object remove(Object key) {
	   return null;
   }

   //TODO size
   @Override
   public int size() {
      return size;
   }

   @Override
   public Collection values() {
      return null;
   } 
	
   @SuppressWarnings("unchecked")
   private void rehash() {
      Node[] newer = (Node[]) new Object[2 * elements.length];
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
	
   public void setCollisionHandlingMethod(int methodNumber) {
      if (methodNumber==0) { linearProbing = true;  quadraticProbing = false; }
      if (methodNumber==1) { linearProbing = false; quadraticProbing = true; }
   }
	
   private int hash(K key) {
      return Math.abs(key.hashCode()) % elements.length;
   }

   private class Node {
      public K key;
      public V value;
      public Node next; 
   	
   	
      public Node(K key, V value) {
         this.key = key;
         this.value = value;
      }
   }
}
