package model.data_structures;

import java.util.LinkedList;
import java.util.List;

public class Vertex<K extends Comparable<K>,V>
{
	private K id;
	
	private V value;
	
	private boolean marked;
	
	private LinkedList<Edge<K,V>> adjEdges;
	
	private LinkedList<Vertex<K,V>> adjVertices;
	
	private int indegree;
	
	/**
	 * Crea un v�rtice con identificador �nico y valor (informaci�n asociada), el v�rtice
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
	 * Devuelve el identificador del v�rtice
	 * @return
	 */
	public K getId()
	{
		return id;
	}
	
	/**
	 * Devuelve el valor asociado al v�rtice
	 * @return
	 */
	public V getInfo()
	{
		return value;		
	}
	
	/**
	 * Retorna si el v�rtice est� marcado o no
	 * @return
	 */
	public boolean getMark() 
	{
		return marked;
	}
	
	/**
	 * Agrega un arco adyacente al v�rtice
	 * @param edge
	 */
	public void addEdge( Edge<K,V> edge ) 
	{
		adjEdges.add(edge);
	}
	
	public void addVertex(Vertex<K, V> vertex)
	{
		adjVertices.add(vertex);
	}
	/**
	 * Marca el v�rtice
	 */
	public void mark()
	{
		marked = true;
	}
	
	/**
	 * Desmarca el v�rtice
	 */
	public void unmark()
	{
		marked = false;
	}
	
	/**
	 * Retorna el n�mero de arcos (salientes) del v�rtice
	 * @return
	 */
	public int outdegree() 
	{
		return adjEdges.size();
	}
	
	/**
	 * Retorna el n�mero de arcos (entrantes) del v�rtice
	 * @return
	 */
	public int indegree()
	{
		//Hay que modificar esto. o no)?
		return indegree;
	}
	
	/**
	 * 
	 */
	public void increaseInd()
	{
		indegree++;
	}
	/**
	 * Retorna el arco con el v�rtice vertex (si existe). Retorna null si no existe.
	 * @param vertex
	 * hoalaaa
	 * @return
	 */
	public Edge<K,V> getEdge(K vertex) 
	{
		Edge<K,V> resp = null;
		
		for (Edge<K, V> edge : adjEdges) 
		{
			if(edge.getDest().getId().equals(vertex))
			{
				resp = edge;
				break;
			}
		}
		return resp;
	}
	
	/**
	 * Retorna una lista con sus v�rtices adyacentes (salientes)
	 * @return
	 */
	public List<Vertex<K,V>> vertices() 
	{
		return adjVertices;		
	}
	
	/**
	 * Retorna una lista con sus arcos adyacentes (salientes)
	 * @return
	 */
	public List<Edge<K,V>> edges() 
	{
		return adjEdges;
	}
}
