package model.data_structures;

import java.util.LinkedList;

public class Pila <V>{

    LinkedList<V> pila;

    public Pila(){
        pila= new LinkedList<V>();
    }
    public void push(V v){
        pila.add(v);
    }
    public V pop(){
        if(pila.isEmpty()){
            return null;
        }
        return pila.removeLast();
    }
    public int lenght(){
        return pila.size();
    }
	public boolean empty() {
		return pila.isEmpty();
	}
}
