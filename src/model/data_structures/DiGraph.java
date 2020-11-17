package model.data_structures;

import java.util.LinkedList;
import java.util.List;

public class DiGraph <K extends Comparable<K>,V>
{              
	 private LinkedList<Vertex<K,V>> vertices;    
	 private LinkedList<Edge<K,V>> edges;       
	 
	 // Lo cambiaremos luego a un estilo m�s similar a una tabla hash, pero no hay mucho tiempo de momento.
	 public DiGraph() 
	 {
		 vertices = new LinkedList<>();
	     edges = new LinkedList<>();
	 }
	 
	/**
	 * Retorna true si el v�rtice con id suministrado est� en el grafo
	 * @param id
	 * @return
	 */
	public boolean containsVertex(K id)
	{
		for (Vertex<K, V> vertex : vertices) 
		{
			if (vertex.getId().equals(id))
			{
				return true;
			}	
		}
		
		return false;
	}
	
	/**
	 * Devuelve el n�mero de v�rtices en el grafo
	 * @return
	 */
	public int numVertices() 
	{
		return vertices.size();
	}
	
	/**
	 * Devuelve el n�mero de arcos en el grafo
	 * @return
	 */
	public int numEdges()
	{
		return edges.size();
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
		
		for (Edge<K, V> edge : edges) 
		{
			if(edge.getSource().getId().equals(source) && edge.getDest().getId().equals(dest))
			{
				//Cambia el peso
				edge.setWeight(weight);
				//Se sale del m�todo
				return;
			}
//			else if(edge.getDest().getId().equals(dest))
//			{
//				edge.getDest().increaseInd();	
//				break;
//			}
		}
		//Si no existe el arco, lo agrega.
		//Adentro ya se aument� el indegree del nodo destino.
		Edge<K,V> newEdge = new Edge<>(getVertex(source),getVertex(dest), weight);	
		newEdge.getSource().addEdge(newEdge);
		edges.add(newEdge);				    
	}
	
	/**
	 * Retorna el v�rtice a partir de su identificador �nico
	 * @param id
	 * @return
	 */
	public Vertex<K,V> getVertex(K id)
	{

		for (Vertex<K, V> vertex : vertices) 
		{
			if (vertex.getId().equals(id))
			{
				return vertex;
			}	
		}
		
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
		for (Edge<K, V> edge : edges) 
		{
			if(edge.getSource().getId().equals(idS) && edge.getDest().getId().equals(idD))
			{
				return edge;
			}
		}
		
		return null;
	}
	
	/**
	 * Devuelve una lista de arcos adyacentes (salientes) al v�rtice con id
	 * @param id
	 * @return
	 */
	public List<Edge<K,V>> adjacentEdges(K id)
	{
		return getVertex(id).edges();
	}
	
	/**
	 * Devuelve una lista de v�rtices adyacentes (salientes) al v�rtice con id
	 */
	public List<Vertex<K,V>> adjacentVertex(K id)
	{
		return  getVertex(id).vertices();
	}
	
	/**
	 * Devuelve el grado de entrada del v�rtice vertex (n�mero de arcos entrantes)
	 * @param id
	 * @return
	 */
	public int indegree(K id)
	{
		return getVertex(id).indegree();
	}
	
	/**
	 * Devuelve el grado de salida del v�rtice vertex (n�mero de arcos salientes)
	 * @param vertex
	 * @return
	 */
	public int outdegree(K id)
	{
		return getVertex(id).outdegree();
	}
	
	/**
	 * Devuelve una lista con todos los arcos del grafo
	 * @return
	 */
	public List<Edge<K,V>> edges()
	{
		return edges;
	}
	
	/**
	 * Devuelve una lista con los v�rtices del grafo
	 * @return
	 */
	public List<Vertex<K,V>> vertices() 
	{
		return vertices;
	}
	
}
