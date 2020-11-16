package model.data_structures;

import java.util.List;

public class Vertex<K extends Comparable<K>,V>
{
	private K id;
	
	private V value;
	
	private boolean marked;
	
	/**
	 * Crea un vértice con identificador único y valor (información asociada), el vértice
	   inicia desmarcado
	 * @param id
	 * @param value
	 */
	public Vertex(K id, V value) 
	{
		this.id = id;
		this.value = value;
		marked = false;
	}
	
	/**
	 * Devuelve el identificador del vértice
	 * @return
	 */
	public K getId()
	{
		return id;
	}
	
	/**
	 * Devuelve el valor asociado al vértice
	 * @return
	 */
	public V getInfo()
	{
		return value;		
	}
	
	/**
	 * Retorna si el vértice está marcado o no
	 * @return
	 */
	public boolean getMark() 
	{
		return marked;
	}
	
	/**
	 * Agrega un arco adyacente al vértice
	 * @param edge
	 */
	public void addEdge( Edge<K,V> edge ) 
	{
		
	}
	
	/**
	 * Marca el vértice
	 */
	public void mark()
	{
		marked = true;
	}
	
	/**
	 * Desmarca el vértice
	 */
	public void unmark()
	{
		marked = false;
	}
	
	/**
	 * Retorna el número de arcos (salientes) del vértice
	 * @return
	 */
	public int outdegree() 
	{
		return 0;
	}
	
	/**
	 * Retorna el número de arcos (entrantes) del vértice
	 * @return
	 */
	public int indegree()
	{
		return 0;
	}
	
	/**
	 * Retorna el arco con el vértice vertex (si existe). Retorna null si no existe.
	 * @param vertex
	 * hoalaaa
	 * @return
	 */
	public Edge<K,V> getEdge(K vertex) 
	{
		return null;
	}
	
	/**
	 * Retorna una lista con sus vértices adyacentes (salientes)
	 * @return
	 */
	public List<Vertex<K,V>> vertices() 
	{
		return null;		
	}
	
	/**
	 * Retorna una lista con sus arcos adyacentes (salientes)
	 * @return
	 */
	public List<Edge<K,V>> edges() 
	{
		return null;
	}
}
