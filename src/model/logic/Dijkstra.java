package model.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import model.data_structures.DiGraph;
import model.data_structures.Edge;
import model.data_structures.IndexMinPQ;
import model.data_structures.Pila;
import model.data_structures.Vertex;

public class Dijkstra<K extends Comparable<K>, V>
{
	private Edge<K, V>[] edgeTo;
	private double[] distTo;
	private IndexMinPQ<Double> pq;
	private int pos;
	private Vertex<K, K>[] vertices;

	public Dijkstra(DiGraph G, Vertex<K, K> s)
	{
		edgeTo = new Edge[G.numVertices()];
		distTo = new double[G.numVertices()];
		vertices  = G.verticesArray();

		pq = new IndexMinPQ<Double>(G.numVertices());

		for(int i = 0; i < distTo.length; i++)
		{
			distTo[i] = Double.POSITIVE_INFINITY;

			if(vertices[i].getId() == s.getId())
			{
				pos = i;
			}
		}

		distTo[pos] = 0;

		pq = new IndexMinPQ<Double>(G.numVertices());
		pq.insert(pos, distTo[pos]);
		while (!pq.isEmpty()) 
		{

			int v = pq.delMin();

			List<Edge<K, V>> adjEdge = G.adjacentEdges(vertices[v].getId());

			for (Edge<K, V> e : adjEdge)
			{
				relax(e);
			}

		}
	}

	private void relax(Edge<K, V> e) 
	{
		Vertex v1 = e.getSource();
		Vertex w1 = e.getDest();
		
		int v = giveIndexByVertex(v1);
		int w = giveIndexByVertex(w1);
		
		if (distTo[w] > distTo[v] + e.weight()) 
		{
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
			//System.out.println(distTo[w]);
			//System.out.println(edgeTo[w].weight());
			
			if (pq.contains(w)) 
				pq.decreaseKey(w, distTo[w]);
			
			else                
				pq.insert(w, distTo[w]);
		}
	}

	public int giveIndexByVertex(Vertex v)
	{
		for(int i = 0; i < vertices.length; i++)
		{
			if(vertices[i].getId() == v.getId())
			{
				return i;
			}
		}
		
		return Integer.MAX_VALUE;
	}

	public double distTo(Vertex v) 
	{
        validateVertex(v);
        int index = giveIndexByVertex(v);
        return distTo[index];
    }
	
	private void validateVertex(Vertex v) 
	{
        int index = giveIndexByVertex(v);
        if (index > vertices.length)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (vertices.length-1));
    }
	
	
	public boolean hasPathTo(Vertex v) 
	{
        validateVertex(v);
        int index = giveIndexByVertex(v);
        return distTo[index] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return a shortest path from the source vertex {@code s} to vertex {@code v}
     *         as an iterable of edges, and {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Stack<Edge<K, V>> pathTo(Vertex v) 
    {
        validateVertex(v);
        
        if (!hasPathTo(v)) return null;
        
        int index = giveIndexByVertex(v);
        
        Stack<Edge<K, V>> path = new Stack<Edge<K, V>>();
        
        for (Edge e = edgeTo[index]; e != null; e = edgeTo[giveIndexByVertex(e.getSource())]) 
        {
            path.push((Edge<K, V>) e);
        }
        return path;
    }
	
}
