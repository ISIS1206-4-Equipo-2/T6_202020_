package model.data_structures;

import java.util.LinkedList;
import java.util.List;

public class Vertex<K extends Comparable<K>, V> {
	private K id;
	private V value;
	private boolean marked;
	private LinkedList<Edge<K, V>> edges;
	private int indegree;
	private int outdegree;
	private int posicion;

	/**
	 * Crea un vertice con identificador unico y valor (informacion asociada)
	 * default vertice desmarcado
	 * 
	 * @param id
	 * @param value
	 */
	public Vertex(K id, V value) {
		this.id = id;
		this.value = value;
		marked = false;
		edges = new LinkedList<>();
		posicion = 0;
	}

	/**
	 * Devuelve el identificador del vertice
	 * 
	 * @return
	 */
	public K getId() {
		return id;
	}

	/**
	 * Devuelve el valor asociado al vertice
	 * 
	 * @return
	 */
	public V getInfo() {
		return value;
	}

	/**
	 * Retorna si el vertice esta marcado o no
	 * 
	 * @return
	 */
	public boolean getMark() {
		return marked;
	}

	/**
	 * Agrega un arco adyacente al vertice Si el vertice de entrada es el vertice
	 * actual indegree ++ Si el vertice de salida es el vertice actual outdegree ++
	 * Si no es ninguno no agrega la arista
	 * 
	 * @param edge
	 */
	public void addEdge(Edge<K, V> edge) {
		// Verifica si el vertice actual es el source o dest del edge
		if (edge.getSource().getId().equals(this.id) && edge.getSource().getInfo().equals(this.value)) {
			if (!edges.contains(edge)) {
				edges.add(edge);
				outdegree++;
			}
		} else if (edge.getDest().getId().equals(this.id) && edge.getDest().getInfo().equals(this.value)) {
			if (!edges.contains(edge)) {
				indegree++;
			}
		} else {
			System.out.println("ERROR: No se agrego el edge");
			System.out.println("El vertice actual no coincide con el source ni el dest");
		}
	}

	/**
	 * Marca el vertice
	 */
	public void mark() {
		marked = true;
	}

	/**
	 * Desmarca el vertice
	 */
	public void unmark() {
		marked = false;
	}

	/**
	 * Retorna el nemero de arcos (salientes) del v�rtice
	 * 
	 * @return
	 */
	public int outdegree() {
		return outdegree;
	}

	/**
	 * Retorna el n�mero de arcos (entrantes) del v�rtice
	 * 
	 * @return
	 */
	public int indegree() {
		return indegree;
	}

	/**
	 * Retorna el arco con el vertice vertex (si existe). Retorna null si no existe.
	 * 
	 * @param vertex hoalaaa
	 * @return
	 */
	public Edge<K, V> getEdge(K vertex) {
		Edge<K, V> resp = null;

		for (Edge<K, V> edge : edges) {
			if (edge.getDest().getId().equals(vertex)) {
				resp = edge;
				break;
			}
		}
		return resp;
	}

	/**
	 * Retorna una lista con sus vertices adyacentes (salientes)
	 * 
	 * @return
	 */
	public List<Vertex<K, V>> vertices() {
		LinkedList<Vertex<K, V>> vertices = new LinkedList<>();
		for (Edge<K, V> edge : edges) {
			vertices.addLast(edge.getDest());
		}
		return vertices;
	}

	/**
	 * Retorna una lista con sus arcos adyacentes (salientes)
	 * 
	 * @return
	 */
	public List<Edge<K, V>> edges() {
		return edges;
	}
	
	/**
	 * Cambia la posición de un vértice
	 */
	public void cambiarPosicion(int pos){
		posicion = pos;
	}

	/**
	 * Retorna la poscion
	 */
	public int darPosicion(){
		return posicion;
	}
}
