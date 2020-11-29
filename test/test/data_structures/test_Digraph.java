package test.data_structures;

import model.data_structures.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class test_Digraph {
    DiGraph<Integer,String> digrafo = new DiGraph<>();
    public void setUp(){
        //Construye el grafo dirigido 
        //inserta vértices
        digrafo.insertVertex(1, "a");
        digrafo.insertVertex(2, "b");
        digrafo.insertVertex(3, "c");
        digrafo.insertVertex(4, "d");
        //inserta aristas
        //Todos los pesos de arco 1
        digrafo.addEdge(1, 2, 1);
        digrafo.addEdge(2, 3, 1);
        digrafo.addEdge(1, 3, 1);        
        digrafo.addEdge(3, 1, 1);
        digrafo.addEdge(3, 4, 1);
    }

    /**
     * Los métodos de addEdge e insertVertex se verifican 
     * indirectamente al verificar los demas metodos
    **/
    @Test
    public void testNumVertEdge(){
        setUp();
        assertEquals(4, digrafo.numVertices());
        assertEquals(5, digrafo.numEdges());
    }

    @Test
    public void testGetVertex(){
        setUp();
        int i = 2;
        Integer resp = Integer.valueOf(i);
        Vertex<Integer,String> actual = digrafo.getVertex(2);
        Vertex<Integer,String> v3 = digrafo.getVertex(3);
        Vertex<Integer,String> v1 = digrafo.getVertex(1);
        //Informacion del vertice        
        assertEquals("b", actual.getInfo());
        assertEquals(resp, actual.getId());
        assertEquals(1, digrafo.indegree(2)); //Test para indegree(K id)
        assertEquals(1, digrafo.outdegree(2)); //Test para outdegree(K id)
        //Verifica las relaciones
        assertEquals(actual.vertices(), digrafo.adjacentVertex(2)); //Test para vertices adyacentes
        assertEquals(actual.edges(), digrafo.adjacentEdges(2)); //Test para edges adyacentes
        assertTrue(actual.vertices().contains(v3));  //Debería estar
        assertFalse(actual.vertices().contains(v1)); //Solo deberian estar los enlaces de salida
        assertTrue(v1.vertices().contains(actual));  //Debería ser adyacente a v1
    }

    @Test
    public void testContainsVert(){
        setUp();
        assertTrue(digrafo.containsVertex(1));
        assertFalse(digrafo.containsVertex(5));
    }

    @Test
    public void testVertices(){
        setUp();
        List<Vertex<Integer,String>> vertices = digrafo.vertices();
        Vertex<Integer,String> v1 = digrafo.getVertex(1);
        Vertex<Integer,String> v2 = digrafo.getVertex(2);
        Vertex<Integer,String> v3 = digrafo.getVertex(3);
        Vertex<Integer,String> v4 = digrafo.getVertex(4);
        Vertex<Integer,String> vfake = new Vertex<>(5,"e");
        //Verifica que los vertices esten
        assertTrue(vertices.contains(v1)); 
        assertTrue(vertices.contains(v2)); 
        assertTrue(vertices.contains(v3)); 
        assertTrue(vertices.contains(v4));
        assertFalse(vertices.contains(vfake));  //No deberia estar
    }

    
    @Test
    public void testEdges(){
        setUp();
        Edge<Integer,String>[] edges = digrafo.edges(); //Lista de edges
        for(Vertex<Integer,String> vertice : digrafo.vertices()){ //Todos los vertices
            for(Edge<Integer,String> edge : vertice.edges()){ //Todos los edges de cada vertice
                assertTrue(ArrayUtils.contains(edges, edge));
            }
        }
    }
    

    @Test
    public void testGetEdge(){
        setUp();
        Vertex<Integer,String> v1 = digrafo.getVertex(1);
        assertTrue(v1.edges().contains(digrafo.getEdge(1, 2))); //Deberia encontrar el edge
        assertNull(digrafo.getEdge(2, 1)); //No es la direccion correcta
        assertNull(digrafo.getEdge(2, 4)); //No hay un enlace
    }
}