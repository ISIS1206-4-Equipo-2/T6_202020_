package model.logic;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import model.data_structures.DiGraph;
import model.data_structures.Edge;
import model.data_structures.Pila;
import model.data_structures.TablaHashSeparateChaining;
import model.data_structures.Vertex;

public class Modelo {
    private static final String DATOS1 = "data/201801-1-citibike-tripdata.csv";
    private static final String DATOS2 = "data/201801-2-citibike-tripdata.csv";
    private static final String DATOS3 = "data/201801-3-citibike-tripdata.csv";
    private static final String DATOS4 = "data/201801-4-citibike-tripdata.csv";
    private DiGraph<Integer, Estacion> grafo;

    public Modelo() {

    }

    public void cargarDatos(int datos) throws Exception {
        grafo = new DiGraph<Integer, Estacion>();
        String ruta;
        switch (datos) {
            case 1:
                ruta = DATOS1;
                break;
            case 2:
                ruta = DATOS2;
                break;
            case 3:
                ruta = DATOS3;
                break;
            case 4:
                ruta = DATOS4;
                break;
            default:
                throw new Exception("No existe ese conjunto de datos, introduzca un numero entre 1 y 4.");
        }
        final Reader readerViajes = Files.newBufferedReader(Paths.get(ruta));
        // Crea el separador con ","
        final CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
        // Crea los respectivos lectores
        final CSVReader csvViajes = new CSVReaderBuilder(readerViajes).withCSVParser(parser).build();

        /*
         * Carga
         */

        // Crea iterators sobre la lista de viajes.
        String[] viajes = csvViajes.readNext();
        int datosCargados = 0;


        TablaHashSeparateChaining<Integer,TablaHashSeparateChaining<Integer,Integer>> tabla = new TablaHashSeparateChaining<Integer,TablaHashSeparateChaining<Integer,Integer>>();

        while ((viajes = csvViajes.readNext()) != null) {
            int iniID = Integer.parseInt(viajes[3].trim());
            if (!grafo.containsVertex(iniID)) {
                String nombre = viajes[4].trim();// Nombre
                if (nombre.equals("")) {
                    continue;
                }
                grafo.insertVertex(iniID, new Estacion(nombre));
            }
            int finID = Integer.parseInt(viajes[7].trim());
            if (!grafo.containsVertex(finID)) {
                String nombre = viajes[8].trim();// Nombre
                if (nombre.equals("")) {
                    continue;
                }
                grafo.insertVertex(finID, new Estacion(nombre));
            }
            if (grafo.getEdge(iniID, finID) == null) {
                grafo.addEdge(iniID, finID, Integer.parseInt(viajes[0]));
                if(!tabla.contains(iniID)){
                    tabla.put(iniID, new TablaHashSeparateChaining<Integer,Integer>());
                }
                if(!tabla.get(iniID).contains(finID)){
                    tabla.get(iniID).put(finID, 1);
                }
            } else {
                tabla.get(iniID).put(finID, tabla.get(iniID).get(finID)+1);
                int value = tabla.get(iniID).get(finID);
                grafo.getEdge(iniID, finID).setWeight((grafo.getEdge(iniID, finID).weight() * (value - 1) + Integer.parseInt(viajes[0])) / value);
            }
            datosCargados++;
        }
        Edge<Integer, Estacion> minArc = grafo.edges()[0];
        Edge<Integer, Estacion> maxArc = grafo.edges()[0];
        for (Edge<Integer, Estacion> arco : grafo.edges()) {
            if (arco.weight() < minArc.weight()) {
                minArc = arco;
            }
            if (arco.weight() > maxArc.weight()) {
                maxArc = arco;
            }
        }

        System.out.println("Datos cargados: " + datosCargados);
        System.out.println("Estaciones cargadas: " + grafo.numVertices());
        System.out.println("Rutas cargadas: " + grafo.numEdges());
        System.out.println("Ruta mas rapida: " + minArc.getSource().getInfo().darNombre() + " - "
                + minArc.getDest().getInfo().darNombre());
        System.out.println("Tiempo promedio: " + minArc.weight());
        System.out.println("Ruta mas lenta: " + maxArc.getSource().getInfo().darNombre() + " - "
                + maxArc.getDest().getInfo().darNombre());
        System.out.println("Tiempo promedio: " + maxArc.weight());
    }

    public String darGradosPorID(int pID) {
        return "Grado de entrada: " + grafo.indegree(pID) + "\nGrado de salida: " + grafo.outdegree(pID);
    }
    
    public void Clusters(){
        Pila<Vertex<Integer,Estacion>> pil=grafo.DFS();
        DiGraph<Integer, Estacion> invert=grafo.invertir();
        for(Vertex<Integer,Estacion>vert:invert.vertices()){
			vert.unmark();
		}
        while(!pil.empty()){
            Pila<Vertex<Integer,Estacion>> group = new Pila<Vertex<Integer,Estacion>>();
            Vertex<Integer,Estacion> actual=pil.pop();
            if(!actual.getMark()){
                invert.DFSRecurr(actual, group);
            }
            while(!group.empty()){
                System.out.print(group.pop()+",");
            }
            System.out.println(group.pop());
        }
    }
    
    //Requerimiento 3 ---> Complejidad de 3n, recorre 3 veces la lista de v�rtices
    public void estacionesCriticas()
    {
    	//3 estaciones Top de llegada
    	List<Vertex<Integer, Estacion>>  lista = grafo.vertices();
    	int condM1 = 0; //Condicional para el m�ximo
    	int condM2 = 0; 
    	int condM3 = 0;
    	
    	int condS1 = 0; //Condicional para el m�nimo
    	int condS2 = 0;
    	int condS3 = 0;
    	
    	int condU1 = Integer.MAX_VALUE; 
    	int condU2 = Integer.MAX_VALUE;
    	int condU3 = Integer.MAX_VALUE;
    	
    	String vCondM1 = ""; 
    	String vCondM2 = "";
    	String vCondM3 = "";
    	
    	String vCondS1 = "";
    	String vCondS2 = "";
    	String vCondS3 = "";
    	
    	String vCondU1 = "";
    	String vCondU2 = "";
    	String vCondU3 = "";
    	
    	
    	for (Vertex<Integer, Estacion> vertex : lista) 
    	{
			if(vertex.indegree() > condM1)
			{
				vCondM1 = vertex.getInfo().darNombre();
				condM1 = vertex.indegree();
			}	
			
			if(vertex.outdegree() > condS1)
			{
				vCondS1 = vertex.getInfo().darNombre();
				condS1 = vertex.outdegree();
			}	
			
			if(vertex.indegree() + vertex.outdegree() < condS1)
			{
				vCondU1 = vertex.getInfo().darNombre();
				condU1 = vertex.indegree() + vertex.outdegree();
			}
		}
    	
    	for (Vertex<Integer, Estacion> vertex : lista) 
    	{
			if(vertex.indegree() > condM2 && !vertex.getInfo().darNombre().equals(vCondM1))
			{
				vCondM2 = vertex.getInfo().darNombre();
				condM2 = vertex.indegree();
			}	
			
			if(vertex.outdegree() > condS2 && !vertex.getInfo().darNombre().equals(vCondS1))
			{
				vCondS2 = vertex.getInfo().darNombre();
				condS2 = vertex.outdegree();
			}	
			
			if(vertex.indegree() + vertex.outdegree() < condU2 && !vertex.getInfo().darNombre().equals(vCondU1))
			{
				vCondU2 = vertex.getInfo().darNombre();
				condU2 = vertex.indegree() + vertex.outdegree();
			}
		}
    	
    		
    	for (Vertex<Integer, Estacion> vertex : lista) 
    	{
			if(vertex.indegree() > condM3 && !vertex.getInfo().darNombre().equals(vCondM1) && !vertex.getInfo().darNombre().equals(vCondM2))
			{
				vCondM3 = vertex.getInfo().darNombre();
				condM3 = vertex.indegree();
			}	
			
			if(vertex.outdegree() > condS3 && !vertex.getInfo().darNombre().equals(vCondS1) && !vertex.getInfo().darNombre().equals(vCondS2))
			{
				vCondS3 = vertex.getInfo().darNombre();
				condS3 = vertex.outdegree();
			}	
			
			if(vertex.indegree() + vertex.outdegree() <  condS3 && !vertex.getInfo().darNombre().equals(vCondU1) &&  !vertex.getInfo().darNombre().equals(vCondU2))
			{
				vCondU3 = vertex.getInfo().darNombre();
				condU3 = vertex.indegree() + vertex.outdegree();
			}
		}
    	
    	System.out.println("------------------------------------------");
    	System.out.println("Estaciones con m�s llegadas Top: ");
    	System.out.println("\n1: " + vCondM1 + " con " + condM1);
    	System.out.println("\n2: " + vCondM2 + " con " + condM2);
    	System.out.println("\n3: " + vCondM3 + " con " + condM3);
    	System.out.println("------------------------------------------");
    	System.out.println("Estaciones con m�s salidas Top: ");
    	System.out.println("\n1: " + vCondS1 + " con " + condS1);
    	System.out.println("\n2: " + vCondS2 + " con " + condS2);
    	System.out.println("\n3: " + vCondS3 + " con " + condS3);
    	System.out.println("------------------------------------------");
    	System.out.println("Estaciones menos utilizadas: ");
    	System.out.println("\n1: " + vCondU1 + " con " + condU1);
    	System.out.println("\n2: " + vCondU2 + " con " + condU2);
    	System.out.println("\n3: " + vCondU3 + " con " + condU3);
    	
    }
}