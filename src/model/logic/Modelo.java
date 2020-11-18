package model.logic;

import java.io.Reader;

import model.data_structures.DiGraph;

public class Modelo 
{
    private static final String DATOS1 = "data/201801-3-citibike-tripdata.csv";
	private static final String DATOS2 = "data/201801-3-citibike-tripdata.csv";
	private static final String DATOS3 = "data/201801-3-citibike-tripdata.csv";
	private static final String DATOS4 = "data/201801-3-citibike-tripdata.csv";
    private DiGraph<Integer,Estacion> grafo;
    
	public Modelo(){

    }

    public void cargarDatos(int datos){
        String ruta;
        switch(datos){
            case 1:
                ruta=DATOS1;
                break;
            case 2:
                ruta=DATOS1;
                break;
            case 3:
                ruta=DATOS1;
                break;
            case 4:
                ruta=DATOS1;
                break;
            default:
                throw new Exception("No existe ese conjunto de datos, introduzca un numero entre 1 y 4.")
                
            final Reader readerAccidentes = Files.newBufferedReader(Paths.get(ruta));
			// Crea el separador con ","
			final CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
			// Crea los respectivos lectores
			final CSVReader csvAccidentes = new CSVReaderBuilder(readerAccidentes).withCSVParser(parser).build();

        }
    }
}






