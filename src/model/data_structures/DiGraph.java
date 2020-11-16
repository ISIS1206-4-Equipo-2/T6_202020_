package model.data_structures;

import java.util.LinkedList;
import java.util.List;

public class DiGraph <K extends Comparable<K>,V>
{
	 private int nV;           
	 private int nE;                 
	 private LinkedList<Vertex<K,V>>[] adj;    
	 private int[] indegree;        
	 
	 
	 public DiGraph(int nV) {
		 
	 }
	 
	/**
	 * Retorna true si el v�rtice con id suministrado est� en el grafo
	 * @param id
	 * @return
	 */
	public boolean containsVertex(K id)
	{
		return false;
	}
	
	/**
	 * Devuelve el n�mero de v�rtices en el grafo
	 * @return
	 */
	public int numVertices() 
	{
		return 0;
	}
	
	/**
	 * Devuelve el n�mero de arcos en el grafo
	 * @return
	 */
	public int numEdges()
	{
		return 0;
	}
	
	/**
	 * A�ade un arco dirigido pesado entre el v�rtice source y dest, con peso weight. Si el
	   arco YA existe se modifica su peso.
	 * @param source
	 * @param dest
	 * @param weight
	 */
	public void addEdge(K source, K dest, double weight)
	{
		
	}
	
	/**
	 * Retorna el v�rtice a partir de su identificador �nico
	 * @param id
	 * @return
	 */
	public Vertex<K,V> getVertex(K id)
	{
		return null;
		
	}

	/**
	 * Retorna el arco entre los v�rtices idS y idD (si existe). Retorna null si no existe.
	 * @param idS
	 * @param idD
	 * @return
	 */
	public Edge<K,V> getEdge(K idS, K idD)
	{
		return null;
	}
	
	/**
	 * Devuelve una lista de arcos adyacentes (salientes) al v�rtice con id
	 * @param id
	 * @return
	 */
	public List<Edge<K,V>> adjacentEdges(K id)
	{
		return null;
	}
	
	/**
	 * Devuelve una lista de v�rtices adyacentes (salientes) al v�rtice con id
	 */
	public List<Vertex<K,V>> adjacentVertex(K id)
	{
		return null;
	}
	
	/**
	 * Devuelve el grado de entrada del v�rtice vertex (n�mero de arcos entrantes)
	 * @param vertex
	 * @return
	 */
	public int indegree(K vertex)
	{
		return 0;
	}
	
	/**
	 * Devuelve el grado de salida del v�rtice vertex (n�mero de arcos salientes)
	 * @param vertex
	 * @return
	 */
	public int outdegree(K vertex)
	{
		return 0;
	}
	
	/**
	 * Devuelve una lista con todos los arcos del grafo
	 * @return
	 */
	public List<Edge<K,V>> edges()
	{
		return null;
	}
	
	/**
	 * Devuelve una lista con los v�rtices del grafo
	 * @return
	 */
	public List<Vertex<K,V>> vertices() 
	{
		return null;
	}
	
}
