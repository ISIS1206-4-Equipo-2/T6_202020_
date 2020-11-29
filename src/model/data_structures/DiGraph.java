package model.data_structures;

import java.util.LinkedList;
import java.util.List;

public class DiGraph<K extends Comparable<K>, V> {
	private TablaHashSeparateChaining<K, Vertex<K, V>> vertices;
	private TablaHashSeparateChaining<String,Edge<K, V>> edges;

	// Lo cambiaremos luego para usar una tabla hash, pero no hay mucho tiempo de
	// momento.
	public DiGraph() {
		vertices = new TablaHashSeparateChaining<K,Vertex<K, V>>();
		edges = new TablaHashSeparateChaining<String,Edge<K, V>>();
	}

	/**
	 * Retorna true si el vertice con id suministrado esta en el grafo
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsVertex(K id) 
	{
		return vertices.contains(id);	
	}

	/**
	 * Devuelve el numero de vertices en el grafo
	 * 
	 * @return
	 */
	public int numVertices() {
		return vertices.size();
	}

	/**
	 * Devuelve el numero de arcos en el grafo
	 * 
	 * @return
	 */
	public int numEdges() {
		return edges.size();
	}

	/**
	 * Agrega un nuevo vertice a la lista de vertices
	 * 
	 * @param el id del vertice y su valor
	 */
	public void insertVertex(K id, V value) 
	{
		Vertex<K, V> vertice = new Vertex<>(id, value);
		vertices.put(id, vertice);
	}

	/**
	 * A�ade un arco dirigido pesado entre el vertice source y dest, con peso
	 * weight. Si el arco YA existe se modifica su peso.
	 * 
	 * @param source
	 * @param dest
	 * @param weight
	 */
	public void addEdge(K source, K dest, double weight) {
		Vertex<K, V> vSource = getVertex(source);
		Vertex<K, V> vDest = getVertex(dest);

		if (vSource == null || vDest == null) {
			throw new IllegalArgumentException("Uno de los vertices no existe.");
		}

		if(edges.contains(source.toString()+","+dest.toString())){
			edges.get(source.toString()+","+dest.toString()).setWeight(weight);
			return;
		}

		// Si no existe el arco, lo agrega como uno nuevo.
		Edge<K, V> newEdge = new Edge<>(vSource, vDest, weight);
		// Aumenda el indegree del nodo destino
		vSource.addEdge(newEdge);
		vDest.addEdge(newEdge);
		edges.put(source.toString()+","+dest.toString(), newEdge);
	}

	/**
	 * Retorna el vertice a partir de su identificador unico
	 * 
	 * @param id
	 * @return
	 */
	public Vertex<K, V> getVertex(K id) 
	{
		return vertices.get(id);
	}

	/**
	 * Retorna el arco entre los v�rtices idS y idD (si existe). Retorna null si no
	 * existe.
	 * 
	 * @param idS
	 * @param idD
	 * @return
	 */
	public Edge<K, V> getEdge(K idS, K idD) {
		if(edges.contains(idS.toString()+","+idD.toString())){
			return edges.get(idS.toString()+","+idD.toString());
		}

		return null;
	}

	/**
	 * Devuelve una lista de arcos adyacentes (salientes) al v�rtice con id
	 * 
	 * @param id
	 * @return
	 */
	public List<Edge<K, V>> adjacentEdges(K id) {
		return getVertex(id).edges();
	}

	/**
	 * Devuelve una lista de v�rtices adyacentes (salientes) al v�rtice con id
	 */
	public List<Vertex<K, V>> adjacentVertex(K id) {
		return getVertex(id).vertices();
	}

	/**
	 * Devuelve el grado de entrada del v�rtice vertex (n�mero de arcos entrantes)
	 * 
	 * @param id
	 * @return
	 */
	public int indegree(K id) {
		return getVertex(id).indegree();
	}

	/**
	 * Devuelve el grado de salida del v�rtice vertex (n�mero de arcos salientes)
	 * 
	 * @param vertex
	 * @return
	 */
	public int outdegree(K id) {
		return getVertex(id).outdegree();
	}

	/**
	 * Devuelve una lista con todos los arcos del grafo
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Edge<K, V>[] edges() {
		Object[]raw= edges.valueSet();
		Edge<K, V>[] answer = new Edge[raw.length];
		for(int i=0;i<raw.length;i++){
			answer[i]=(Edge<K, V>) raw[i];
		}
		return answer;
	}

	/**
	 * Devuelve una lista con los v�rtices del grafo
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Vertex<K, V>> vertices() 
	{
		Object array[] = vertices.valueSet();
		//Vertex<K, V>  array2[] = (Vertex<K, V>[]) new Object[array.length];
		
		LinkedList<Vertex<K, V>> list = new LinkedList<>();
		
		for (Object vertex : array) 
		{
			list.addLast((Vertex<K, V>) vertex);
		}
				
		return list;
	}
	
	
	

}
