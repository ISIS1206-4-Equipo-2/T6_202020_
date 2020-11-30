package model.logic;

import java.util.*;

import model.data_structures.DiGraph;
import model.data_structures.Vertex;
import model.data_structures.Edge;

public class Dfs {
    private boolean[] marked;
    private int[] edgeTo;
    private List<Vertex<Integer, Estacion>> hojas;
    DiGraph<Integer, Estacion> grafo;
    private Vertex<Integer, Estacion> v0;
    private List<Vertex<Integer, Estacion>> vertices;

    public Dfs(DiGraph<Integer, Estacion> G, Vertex<Integer, Estacion> v) {
        marked = new boolean[G.numVertices()];
        edgeTo = new int[G.numVertices()];
        G.asignarPosiciones();
        grafo = G;
        vertices = grafo.vertices();
        v0 = v;
        hojas = new LinkedList<Vertex<Integer, Estacion>>();
    }

    // Método de dfs
    public void dfs(Vertex<Integer, Estacion> v) {
        int pos = v.darPosicion();
        marked[pos] = true;
        boolean hoja = true;
        for (Vertex<Integer, Estacion> w : grafo.adjacentVertex(v.getId())) {
            if (!marked[w.darPosicion()]) {
                edgeTo[w.darPosicion()] = pos;
                dfs(w);
                hoja = false;
            }
        }
        if (hoja)
            hojas.add(v);
    }

    // Realiza el dfs con restricción de Rango de tiempo
    public void dfsRango(Vertex<Integer, Estacion> v, double rango) {
        int pos = v.darPosicion();
        marked[pos] = true;
        boolean hoja = true;
        for (Vertex<Integer, Estacion> w : grafo.adjacentVertex(v.getId())) {
            double rangoAct = rango;
            if (!marked[w.darPosicion()]) {
                Edge<Integer, Estacion> edge = grafo.getEdge(v.getId(), w.getId());
                if (edge != null) {
                    if (edge.weight() <= rango) {// Dentro de rango->itero
                        edgeTo[w.darPosicion()] = pos;
                        double gastado = grafo.getEdge(v.getId(), w.getId()).weight();
                        rangoAct = rangoAct - gastado;
                        if (rangoAct > 0) {
                            dfsRango(w, rangoAct);
                            hoja = false;
                        }
                    }
                }
            }
        }
        if (hoja)
            hojas.add(v);
    }

    // Crea la ruta al vertice v
    public Ruta darRuta(Vertex<Integer, Estacion> v) {
        Ruta resp = new Ruta();
        for (int x = v.darPosicion(); x != v0.darPosicion(); x = edgeTo[x]) {
            Vertex<Integer, Estacion> vAct = vertices.get(x);
            Vertex<Integer, Estacion> vNext = vertices.get(edgeTo[x]);
            Integer id = vAct.getId();
            Double tiempo = grafo.getEdge(vNext.getId(), vAct.getId()).weight();
            resp.agregarEstacion(id, tiempo);
        }
        if (!resp.isEmpty())
            return resp;
        return null;
    }

    // Devuelve todas las rutas
    public List<Ruta> darRutas() {
        List<Ruta> resp = new LinkedList<Ruta>();
        for (Vertex<Integer, Estacion> v : hojas) {
            Ruta rutaTemp = darRuta(v);
            resp.add(rutaTemp);
        }
        if (!resp.isEmpty())
            return resp;
        return null;
    }
}