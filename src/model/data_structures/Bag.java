package model.data_structures;

import java.util.Iterator;

public class Bag<K extends Comparable<K>,V> implements Iterable<V>
{
	private Vertex<K,V> first;
	private int n;
	
	public Bag()
	{
		first = null;
		n = 0;
	}
	
	public boolean isEmpty() 
	{
        return first == null;
    }
	
	public int size()
	{
        return n;
    }
	
	public void add(K id, V value) 
	{
		Vertex<K,V> oldfirst = first;
        first = new Vertex<K,V>(id, value);
        first.addVertex(oldfirst);
        n++;
    }

	@Override
	public Iterator<V> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	public Iterator<V> iterator()  
//	{
//        return new LinkedIterator(first);  
//    }
//
//    private class LinkedIterator implements Iterator<V> 
//    {
//        private Vertex<K,V> current;
//
//        public LinkedIterator(Vertex<K,V>(id, value) first) 
//        {
//            current = first;
//        }
//
//        public boolean hasNext()  { return current != null;                     }
//        public void remove()      { throw new UnsupportedOperationException();  }
//
//        public Item next() {
//            if (!hasNext()) throw new NoSuchElementException();
//            Item item = current.item;
//            current = current.next; 
//            return item;
//        }
//    }
	
}
