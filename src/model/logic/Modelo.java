package model.logic;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import org.apache.commons.lang3.tuple.ImmutablePair;

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
    private TablaHashSeparateChaining<String, Bicicleta> bicicletas;
	
    public Modelo() {

    }

    public void cargarDatos(int datos) throws Exception {
        grafo = new DiGraph<Integer, Estacion>();
        bicicletas = new TablaHashSeparateChaining<String, Bicicleta>();
        
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

        TablaHashSeparateChaining<Integer, TablaHashSeparateChaining<Integer, Integer>> tabla = new TablaHashSeparateChaining<Integer, TablaHashSeparateChaining<Integer, Integer>>();

        while ((viajes = csvViajes.readNext()) != null) {
            int iniID = Integer.parseInt(viajes[3].trim());
            int edadt = (Integer.parseInt(viajes[13].trim()));
            int edad = 2018-edadt;
            int tripDuration = (Integer.parseInt(viajes[0].trim()));
            String idBici = viajes[11].trim();
            
            String nombre1 = viajes[4].trim();
            
            String nombre2 = viajes[8].trim();
            
            if (!grafo.containsVertex(iniID)) {
                if (nombre1.equals("")) {
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
                grafo.insertVertex(iniID, new Estacion(nombre1, latitud, longitud));
            }
            int finID = Integer.parseInt(viajes[7].trim());
            if (!grafo.containsVertex(finID)) {
                if (nombre2.equals("")) {
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
                grafo.insertVertex(finID, new Estacion(nombre2,latitudEnd, longitudEnd));
            }
            if (grafo.getEdge(iniID, finID) == null) {
                grafo.addEdge(iniID, finID, Integer.parseInt(viajes[0]));
                if (!tabla.contains(iniID)) {
                    tabla.put(iniID, new TablaHashSeparateChaining<Integer, Integer>());
                }
                if (!tabla.get(iniID).contains(finID)) {
                    tabla.get(iniID).put(finID, 1);
                }
            } else {
                tabla.get(iniID).put(finID, tabla.get(iniID).get(finID) + 1);
                int value = tabla.get(iniID).get(finID);
                grafo.getEdge(iniID, finID).setWeight(
                        (grafo.getEdge(iniID, finID).weight() * (value - 1) + Integer.parseInt(viajes[0])) / value);
            }
            
            if(grafo.containsVertex(finID) && grafo.containsVertex(iniID))
            {
            	//System.out.println(edad);
            	grafo.getVertex(iniID).getInfo().aumentarRangoEdadS(edad);
            	grafo.getVertex(iniID).getInfo().aumentarViajesSalida();
            	grafo.getVertex(finID).getInfo().aumentarRangoEdadE(edad);
            	grafo.getVertex(finID).getInfo().aumentarViajesLlegada();
            	
            	if(bicicletas.get(idBici) == null)
            	{
            		Bicicleta nuevaBici = new Bicicleta(idBici);
            		nuevaBici.aumentarUso(tripDuration);
                	nuevaBici.agregarEstacion(grafo.getVertex(iniID).getInfo());
                	nuevaBici.agregarEstacion(grafo.getVertex(finID).getInfo()); 
                	bicicletas.put(idBici, nuevaBici);
                	System.out.println("BICI NUEVA");
                	System.out.println(bicicletas.get(idBici).darTiempoUso());
            	}
            	else
            	{
            		bicicletas.get(idBici).aumentarUso(tripDuration);
            		bicicletas.get(idBici).agregarEstacion(grafo.getVertex(iniID).getInfo());
            		bicicletas.get(idBici).agregarEstacion(grafo.getVertex(finID).getInfo()); 	
            	}
            	
            	//System.out.println(grafo.getVertex(iniID).getInfo().cantidadEnRangoEdad(edad));
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

    public LinkedList<LinkedList<Integer>> Clusters() {
        Pila<Vertex<Integer, Estacion>> pil = grafo.DFS();
        DiGraph<Integer, Estacion> invert = grafo.invertir();
        for (Vertex<Integer, Estacion> vert : grafo.vertices()) {
            vert.unmark();
        }
        LinkedList<LinkedList<Integer>> resp = new LinkedList<LinkedList<Integer>>();
        while (!pil.empty()) {
            Pila<Vertex<Integer, Estacion>> group = new Pila<Vertex<Integer, Estacion>>();
            Vertex<Integer, Estacion> actual = pil.pop();
            if (!invert.getVertex(actual.getId()).getMark()) {
                invert.DFSRecurr(invert.getVertex(actual.getId()), group);
            } else {
                continue;
            }
            LinkedList<Integer> g = new LinkedList<Integer>();
            while (!group.empty()) {
                g.add(group.pop().getId());
            }
            resp.add(g);
        }
        return resp;
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
			if(vertex.getInfo().darViajesLlegada() > condM1)
			{
				vCondM1 = vertex.getInfo().darNombre();
				condM1 = vertex.getInfo().darViajesLlegada();
			}	
			
			if(vertex.getInfo().darViajesSalida() > condS1)
			{
				vCondS1 = vertex.getInfo().darNombre();
				condS1 = vertex.getInfo().darViajesSalida();
			}	
			
			if(vertex.getInfo().darViajesLlegada() + vertex.getInfo().darViajesSalida() < condU1)
			{
				vCondU1 = vertex.getInfo().darNombre();
				condU1 = vertex.getInfo().darViajesLlegada() + vertex.getInfo().darViajesSalida();
			}
		}
    	
    	for (Vertex<Integer, Estacion> vertex : lista) 
    	{
			if(vertex.getInfo().darViajesLlegada() > condM2 && !vertex.getInfo().darNombre().equals(vCondM1))
			{
				vCondM2 = vertex.getInfo().darNombre();
				condM2 = vertex.getInfo().darViajesLlegada();
			}	
			
			if(vertex.getInfo().darViajesSalida() > condS2 && !vertex.getInfo().darNombre().equals(vCondS1))
			{
				vCondS2 = vertex.getInfo().darNombre();
				condS2 = vertex.getInfo().darViajesSalida();
			}	
			
			if(vertex.getInfo().darViajesLlegada() + vertex.getInfo().darViajesSalida() < condU2 && !vertex.getInfo().darNombre().equals(vCondU1))
			{
				vCondU2 = vertex.getInfo().darNombre();
				condU2 = vertex.getInfo().darViajesLlegada() + vertex.getInfo().darViajesSalida();
			}
		}
    	
    		
    	for (Vertex<Integer, Estacion> vertex : lista) 
    	{
			if(vertex.getInfo().darViajesLlegada() > condM3 && !vertex.getInfo().darNombre().equals(vCondM1) && !vertex.getInfo().darNombre().equals(vCondM2))
			{
				vCondM3 = vertex.getInfo().darNombre();
				condM3 = vertex.getInfo().darViajesLlegada();
			}	
			
			if(vertex.getInfo().darViajesSalida() > condS3 && !vertex.getInfo().darNombre().equals(vCondS1) && !vertex.getInfo().darNombre().equals(vCondS2))
			{
				vCondS3 = vertex.getInfo().darNombre();
				condS3 = vertex.getInfo().darViajesSalida();
			}	
			
			if(vertex.getInfo().darViajesLlegada() + vertex.getInfo().darViajesSalida() <  condU3 && !vertex.getInfo().darNombre().equals(vCondU1) &&  !vertex.getInfo().darNombre().equals(vCondU2))
			{
				vCondU3 = vertex.getInfo().darNombre();
				condU3 = vertex.getInfo().darViajesLlegada() + vertex.getInfo().darViajesSalida();
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
     * Retorna una tupla con dos estaciones, la primera es la que m�s salidas por la edad tiene, 
     * la segunda es la que mas entradas por edad tiene.
     * @param edad
     * @return [0] salidas, [1] entradas
     */
	@SuppressWarnings("all")
    public Vertex<Integer, Estacion>[] estacionConMasViajerosPorEdad(int edad)
    {
    	List<Vertex<Integer, Estacion>>  lista = grafo.vertices();
    	
    	Vertex[] resp = {null, null};
    	
    	int mayorS = 0;
    	Vertex<Integer, Estacion> biggerS = null;
    	
    	int mayorE = 0;
    	Vertex<Integer, Estacion> biggerE = null;
    	
    
    	for (Vertex<Integer, Estacion> vertex : lista) 
    	{
			int actualS = vertex.getInfo().cantidadEnRangoEdadS(edad);
			int actualE = vertex.getInfo().cantidadEnRangoEdadE(edad);
			
			if(actualS > mayorS)
			{
				mayorS = actualS;
				biggerS = vertex;
			}
			
			if(actualE > mayorE)
			{
				mayorE = actualE;
				biggerE = vertex;
			}
		}
    	
    	resp[0] = biggerS;
    	resp[1] = biggerE;
    	
    	return resp;
    }
    
    
/**
 * M�todo escrito por Santiago Bobadilla
 * Modificado por el Grupo 2
 */
    public void maps()
	{

		List<Vertex<Integer, Estacion>> vertex = grafo.vertices();
		Edge<Integer,Estacion>[]  arcos = grafo.edges();

		// Color de los vertices
		String colVerti = darColorAleatorio();

		// Color de los arcos
		String colArco = darColorAleatorio();

		// Vamos a generar el archivo de los vertices.

		try 
		{
			FileWriter file = new FileWriter("mapa/vertices.js");

			file.write("citymap = { \n");

			for (int i = 0; i< vertex.size();i++)
			{
				Vertex<Integer,Estacion> aux = vertex.get(i);

				if(aux != null)
				{
					Estacion info = (Estacion) aux.getInfo();

					file.write("\t" + aux.getId() + ": {\n");
					file.write("\t center: {lat: " + info.darLatitud() + ", lng: " + info.darLongitud() + "},  \n");
					file.write("\t population: " + 150 + ",   \n");
					file.write("\t color: '" + colVerti + "', \n");
					file.write("\t titulo: " + "'Hola'" + "\n");

					if(i == grafo.numVertices()-1) file.write("\t } \n");
					else 			       file.write("\t }, \n");
				}
			}

			file.write("}");

			file.close();

		}
		catch (Exception e){
			e.printStackTrace();
		}


		try 
		{
			FileWriter file = new FileWriter("mapa/arcos.js");

			file.write("cityroad = { \n");

			//Edge actual = arcos[0];
			int i = 0;
			
			while (i < arcos.length)
			{
				Edge<Integer,Estacion> actual = (Edge<Integer,Estacion>) arcos[i];
				//Arco aux= (Arco) actual.data;

				Vertex<Integer,Estacion> inicio = actual.getDest();
				Estacion infoInicio = (Estacion) inicio.getInfo();

				Vertex<Integer,Estacion> fin = actual.getSource();
				Estacion infoFin = (Estacion) fin.getInfo();

				if(inicio != null & fin != null)
				{
					file.write("\t" + inicio.getId() + fin.getId() + ": {\n");
					file.write("\t inicio: {lat: " + infoInicio.darLatitud() + ", lng: " + infoInicio.darLongitud() + "},  \n");
					file.write("\t fin: {lat: " + infoFin.darLatitud() + ", lng: " + infoFin.darLongitud() + "},  \n");
					file.write("\t color: '" + colArco + "', \n");

					if (i+1  == arcos.length)
					{
						file.write("\t  }  \n");
					}
					else
					{
						file.write("\t  }  ,\n");
					}
				}

				i++;
			}

			file.write("}");

			file.close();

		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		// Abre el mapa
		abrirArchivo();
	}


	private String darColorAleatorio() 
	{
		Color color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
		String stringColor = Integer.toHexString(color.getRGB());
		return stringColor;
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
	private void abrirArchivo()
	{
		String osName = System.getProperty("os.name");
		File file = new File("mapa/grafo.html");
		try {

			if (osName.startsWith("Windows")) 			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
			else if (osName.startsWith("Mac OS X")) 	Runtime.getRuntime().exec("open " + file.getAbsolutePath());
			else 										System.out.println("Please open a browser and go to " + file.getAbsolutePath());

		} 
		catch (IOException e) {
			System.out.println("Failed to start a browser to open the url " + file.getAbsolutePath());
			e.printStackTrace();
		}
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

	public ImmutablePair<LinkedList<LinkedList<Integer>>,LinkedList<Integer>> circularRoute(int iD) {
		Vertex<Integer,Estacion>vert=grafo.getVertex(iD);
		LinkedList<LinkedList<Integer>> rCiclo= new LinkedList<LinkedList<Integer>>();
		LinkedList<Vertex<Integer,Estacion>> recor=new LinkedList<Vertex<Integer,Estacion>>();
		LinkedList<Integer>rTiempo=new LinkedList<Integer>();
		int tiempo=0;
		return circRouteUtil(vert,tiempo,recor, rTiempo,rCiclo);
	}

	private ImmutablePair<LinkedList<LinkedList<Integer>>,LinkedList<Integer>> circRouteUtil(Vertex<Integer,Estacion> vert,int tiempo,LinkedList<Vertex<Integer,Estacion>> recor,LinkedList<Integer>rTiempo, LinkedList<LinkedList<Integer>> rCiclo) {
		LinkedList<Vertex<Integer,Estacion>> newrecor= new LinkedList<Vertex<Integer,Estacion>>();
		for(Vertex<Integer,Estacion> ver:recor){
			newrecor.add(ver);
		}
		newrecor.add(vert);

		for(Edge<Integer,Estacion> edge:vert.edges()){
			if(edge.getSource().equals(vert)&& !edge.getSource().equals(edge.getDest())){
				int newtiempo=tiempo+1200;
				newtiempo+=edge.weight();
				if(!recor.contains(edge.getDest())){
					circRouteUtil(edge.getDest(), newtiempo, newrecor, rTiempo, rCiclo);
				}else if(edge.getDest().equals(recor.getFirst())){
					LinkedList<Integer>r=new LinkedList<Integer>();
					for(Vertex<Integer,Estacion> v:newrecor){
						r.add(v.getId());
					}
					rCiclo.add(r);
					rTiempo.add(tiempo);
				}
			}
		}
		return new ImmutablePair<LinkedList<LinkedList<Integer>>,LinkedList<Integer>>( rCiclo, rTiempo);
	}


    
    
    public void losCaminosDeLaVida(Vertex<Integer, Estacion> from, Vertex<Integer, Estacion> to)
    {
    	Dijkstra<Integer, Estacion> djk = new Dijkstra<Integer, Estacion>(grafo, from);
    	
    	//System.out.println("Peso: "+ djk.distTo(to));
    	
    	Stack<Edge<Integer, Estacion>> hola = djk.pathTo(to);
    	
    	
    	if(hola != null && djk.distTo(to) != Double.POSITIVE_INFINITY && !(djk.distTo(to) == 0))
    	{
    		System.out.println("Cantidad de recorridos: " + hola.size());
    		for (Edge<Integer, Estacion> edge : hola) 
        	{
    			System.out.println("\nDESDE " + edge.getSource().getInfo().darNombre() + " HASTA " + edge.getDest().getInfo().darNombre() );
    		}
    	}
    	else if(hola != null && djk.distTo(to) == 0)
    	{
    		System.out.println("Cantidad de recorridos: " + hola.size());
    	}
    		
    	else
    		System.out.println("Es infinito");    
    }
    
    public Vertex<Integer,Estacion> darVerticePorID(Integer id)
    {
    	return grafo.getVertex(id);
    }
    
    public Bicicleta darBicicleta(String id)
    {
    	return bicicletas.get(id);
    }
	
}