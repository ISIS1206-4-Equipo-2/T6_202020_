package model.logic;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import model.data_structures.DiGraph;
import model.data_structures.Edge;
import model.data_structures.TablaHashSeparateChaining;

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
        return "Grado de entrada: " + grafo.outdegree(pID) + "\nGrado de salida: " + grafo.indegree(pID);
    }
}