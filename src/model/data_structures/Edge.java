package model.data_structures;

public class Edge<K extends Comparable<K>, V>
{
	private Vertex<K,V> source;
	
	private Vertex<K,V> dest;
	
	private double weight;
	/**
	 * Crea el arco desde el v�rtice source al v�rtice dest con peso weight
	 * @param source
	 * @param dest
	 * @param weight
	 */
	public Edge(Vertex<K,V> source, Vertex<K,V>dest, double weight)
	{
		this.source = source;
		this.dest = dest;
		dest.increaseInd();
		this.weight = weight;
	}
	
	/**
	 * Devuelve el v�rtice origen
	 * @return
	 */
	public Vertex<K,V> getSource()
	{
		return source;
	}

	/**
	 * Devuelve el v�rtice destino
	 * @return
	 */
	public Vertex<K,V> getDest() 
	{
		return dest;
	}
	
	/**
	 * Devuelve el peso del arco
	 * @return
	 */
	public double weight()
	{
		return weight;
	}
	
	/**
	 * Modifica el peso del arco
	 * @param weight
	 */
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
}
