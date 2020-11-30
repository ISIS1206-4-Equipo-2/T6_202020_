package model.logic;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import model.data_structures.DiGraph;
import model.data_structures.Edge;
import model.data_structures.TablaHashSeparateChaining;
import model.data_structures.Vertex;

public class Modelo {
    private static final String DATOS1 = "data/201801-1-citibike-tripdata.csv";
    private static final String DATOS2 = "data/201801-2-citibike-tripdata.csv";
    private static final String DATOS3 = "data/201801-3-citibike-tripdata.csv";
    private static final String DATOS4 = "data/201801-4-citibike-tripdata.csv";
    private DiGraph<Integer, Estacion> grafo;
    private DiGraph<Integer, Estacion> grafoEdades;
    String ruta;

    public Modelo() {

    }

    public void cargarDatos(int datos) throws Exception {
        grafo = new DiGraph<Integer, Estacion>();
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
                String strLatitud = viajes[5].trim();//Latitud
                if (strLatitud.equals("")) {
                    continue;
                }
                Double latitud = Double.parseDouble(strLatitud);
                String strLongitud = viajes[6].trim();//Longitud
                if (strLongitud.equals("")) {
                    continue;
                }
                Double longitud = Double.parseDouble(strLongitud);
                grafo.insertVertex(iniID, new Estacion(nombre, latitud, longitud));
            }
            int finID = Integer.parseInt(viajes[7].trim());
            if (!grafo.containsVertex(finID)) {
                String nombre = viajes[8].trim();// Nombre
                if (nombre.equals("")) {
                    continue;
                }
                String strLatitud = viajes[9].trim();//Latitud
                if (strLatitud.equals("")) {
                    continue;
                }
                Double latitudEnd = Double.parseDouble(strLatitud);
                String strLongitud = viajes[10].trim();//Longitud
                if (strLongitud.equals("")) {
                    continue;
                }
                Double longitudEnd = Double.parseDouble(strLongitud);
                grafo.insertVertex(finID, new Estacion(nombre,latitudEnd, longitudEnd));
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
    
    //Requerimiento 3 ---> Complejidad de 3n, recorre 3 veces la lista de vertices
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

    /**
     * @param resistencia total 
     */

    public List<Ruta> rutasResistencia(int id, double resistencia){
        Vertex<Integer,Estacion> v = grafo.getVertex(id);
        Dfs dfs = new Dfs(grafo, v);
        dfs.dfsRango(v, resistencia);
        List<Ruta> rutas = dfs.darRutas();
        return rutas;
    }

    /**
     * Verifica los parámetros del método de rutas por resistencia
     */
    public void verificarParam(Integer id)throws Exception{
        if(!grafo.containsVertex(id)) throw new Exception("El id no existe en el grafo");
        if(grafo.getVertex(id).outdegree()==0) throw new Exception("No hay rutas salientes de la estacion");
    }

    /**
     * Encuentra los puntos de inicio y fin mas cercanos (id de las estaciones)
     * a las coordenas de inicio y fin de la ruta
     * Posicion 0: Vertice inicial - Posicion 1: Vertice final 
     */
    public Integer[] darEstacionesCercanas(Double pLat0, Double pLng0, Double pLatf, Double pLngf){
        Vertex<Integer,Estacion> vStart = null;
        Vertex<Integer,Estacion> vFinish = null;
        Double dmin0 = Double.POSITIVE_INFINITY;
        Double dminf = Double.POSITIVE_INFINITY;
        Integer[] resp = new Integer[2];
        for(Vertex<Integer,Estacion> v : grafo.vertices()){
            if(v.getInfo().darDistancia(pLat0, pLng0)<dmin0){
                vStart = v;
                dmin0 = v.getInfo().darDistancia(pLat0, pLng0);
            }
            if(v.getInfo().darDistancia(pLatf, pLngf)<dminf){
                vFinish = v;
                dminf = v.getInfo().darDistancia(pLat0, pLng0);
            }
        }
        if(vStart==null || vFinish==null) return null;
        resp[0]= vStart.getId();
        resp[1]= vFinish.getId();
        return resp;
    }

    /**
     * Crea un grafo con pesos para el rango de edad
     * @param edad: "0" 0-10, "1" 11-20, "2" 21-30, "3" 31-40, "4" 41-50, "5" 51-60, "6" 60+
     */
    public Stack<Integer[]> estacionesEdades(String edades, int anioAct) throws Exception{
        Stack<Integer[]> resp = new Stack<Integer[]>();
        grafo = new DiGraph<Integer, Estacion>();
        int edadMin = -1;
        int edadMax = -1;
        switch (edades) {
            case "0":
                edadMin=anioAct;
                edadMax=anioAct-10;
                break;
            case "1":
                edadMin=anioAct-11;
                edadMax=anioAct-20;
                break;
            case "2":
                edadMin=anioAct-21;
                edadMax=anioAct-30;
                break;
            case "3":
                edadMin=anioAct-31;
                edadMax=anioAct-40;
                break;
            case "4":
                edadMin=anioAct-41;
                edadMax=anioAct-50;
                break;
            case "5":
                edadMin=anioAct-51;
                edadMax=anioAct-60;
                break;
            case "6":
                edadMin=anioAct-61;
                edadMax=anioAct-100; //Claim: Las personas de mas de 100 anios no montan bicis
            default:
                throw new Exception("No es un rango de edad valido");
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

        TablaHashSeparateChaining<Integer,TablaHashSeparateChaining<Integer,Integer>> tabla = new TablaHashSeparateChaining<Integer,TablaHashSeparateChaining<Integer,Integer>>();
        //Revisa restricciones
        while ((viajes = csvViajes.readNext()) != null) {
            if(viajes[12].contains("Subscriber")){//Verifica a los suscriptores
                String strEdad = viajes[13].trim();//Longitud
                if (!strEdad.equals("")) {
                    Integer edad = Integer.parseInt(strEdad);
                    if(edad<=edadMin && edad>=edadMax){//Verifica que esté en el rango de edad
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
                            grafo.addEdge(iniID, finID, 1);
                            if(!tabla.contains(iniID)){
                                tabla.put(iniID, new TablaHashSeparateChaining<Integer,Integer>());
                            }
                            if(!tabla.get(iniID).contains(finID)){
                                tabla.get(iniID).put(finID, 1);
                            }
                        } else {
                            tabla.get(iniID).put(finID, tabla.get(iniID).get(finID)+1);
                            grafo.getEdge(iniID, finID).setWeight(grafo.getEdge(iniID, finID).weight()+1);
                        }
                        Edge<Integer, Estacion> maxArc = grafo.edges()[0];
                        for (Edge<Integer, Estacion> arco : grafo.edges()) {
                            if (arco.weight() > maxArc.weight()){
                                resp = new Stack<Integer[]>();
                                maxArc = arco;
                                Integer[] info = new Integer[3];
                                info[0]=arco.getSource().getId();
                                info[1]=arco.getDest().getId();
                                info[2]=(int) Math.round(arco.weight());
                                if(info[0]!=null && info[1]!=null && info[2]!=null) resp.push(info);
                            }
                            if (arco.weight() == maxArc.weight()){
                                Integer[] info = new Integer[3];
                                info[0]=arco.getSource().getId();
                                info[1]=arco.getDest().getId();
                                info[2]=(int) Math.round(arco.weight());
                                if(info[0]!=null && info[1]!=null && info[2]!=null) resp.push(info);
                            }
                        }
                    }
                }
            }
        }
        if(resp.isEmpty())return null;
        return resp;
    }
}