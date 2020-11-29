package model.logic;

import java.util.LinkedList;

import model.data_structures.Edge;

public class Estacion {

    private String nombre;
    private int[] edadesCantidadSalida;
    private int[] edadesCantidadEntrada;
    
    private Double latitud;
    private Double longitud;
    
    public Estacion(String pNombre, Double latitud, Double longitud)
    {
    	edadesCantidadSalida = new int[7];
    	for (int i : edadesCantidadSalida) {
			i = 0;
		}
    	edadesCantidadEntrada = new int[7];
    	for (int i : edadesCantidadEntrada) {
			i = 0;
		}
    	
    	this.latitud = latitud;
    	this.longitud = longitud;
        nombre=pNombre;
    }
    
	public String darNombre() {
		return nombre;
	}
	
	public double darLatitud()
	{
		return latitud;
	}
	
	public double darLongitud()
	{
		return longitud;
	}
	public void aumentarRangoEdadS(int edad)
	{
		if(edad >= 0 && edad <= 10)
		{
			
			edadesCantidadSalida[0] = edadesCantidadSalida[0]+1;
		}
		else if(edad >= 11 && edad <= 20)
		{
			edadesCantidadSalida[1] = edadesCantidadSalida[1]+1;
		}
		else if(edad >= 21 && edad <= 30)
		{	
			edadesCantidadSalida[2] = edadesCantidadSalida[2]+1;
		}
		else if(edad >= 31 && edad <= 40)
		{
			edadesCantidadSalida[3] = edadesCantidadSalida[3]+1;
		}
		else if(edad >= 41 && edad <= 50)
		{
			edadesCantidadSalida[4] = edadesCantidadSalida[4]+1;
		}
		else if(edad >= 51 && edad <= 60)
		{
			edadesCantidadSalida[5] = edadesCantidadSalida[5]+1;
		}
		else if(edad >= 61)
		{
			edadesCantidadSalida[6] = edadesCantidadSalida[6]+1;
		}
	}
	
	public int cantidadEnRangoEdadS(int edad)
	{
		if(edad >= 0 && edad <= 10)
		{
			return edadesCantidadSalida[0];
		}
		else if(edad >= 11 && edad <= 20)
		{
			return edadesCantidadSalida[1];
		}
		else if(edad >= 21 && edad <= 30)
		{
			return edadesCantidadSalida[2];
		}
		else if(edad >= 31 && edad <= 40)
		{
			return edadesCantidadSalida[3];
		}
		else if(edad >= 41 && edad <= 50)
		{
			return edadesCantidadSalida[4];
		}
		else if(edad >= 51 && edad <= 60)
		{
			return edadesCantidadSalida[5];
		}
		else if(edad >= 61)
		{
			return edadesCantidadSalida[6];
		}
		
		return 0;
	}
	
	public void aumentarRangoEdadE(int edad)
	{
		if(edad >= 0 && edad <= 10)
		{
			
			edadesCantidadEntrada[0] = edadesCantidadEntrada[0]+1;
		}
		else if(edad >= 11 && edad <= 20)
		{
			edadesCantidadEntrada[1] = edadesCantidadEntrada[1]+1;
		}
		else if(edad >= 21 && edad <= 30)
		{	
			edadesCantidadEntrada[2] = edadesCantidadEntrada[2]+1;
		}
		else if(edad >= 31 && edad <= 40)
		{
			edadesCantidadEntrada[3] = edadesCantidadEntrada[3]+1;
		}
		else if(edad >= 41 && edad <= 50)
		{
			edadesCantidadEntrada[4] = edadesCantidadEntrada[4]+1;
		}
		else if(edad >= 51 && edad <= 60)
		{
			edadesCantidadEntrada[5] = edadesCantidadEntrada[5]+1;
		}
		else if(edad >= 61)
		{
			edadesCantidadEntrada[6] = edadesCantidadEntrada[6]+1;
		}
	}
	
	public int cantidadEnRangoEdadE(int edad)
	{
		if(edad >= 0 && edad <= 10)
		{
			return edadesCantidadEntrada[0];
		}
		else if(edad >= 11 && edad <= 20)
		{
			return edadesCantidadEntrada[1];
		}
		else if(edad >= 21 && edad <= 30)
		{
			return edadesCantidadEntrada[2];
		}
		else if(edad >= 31 && edad <= 40)
		{
			return edadesCantidadEntrada[3];
		}
		else if(edad >= 41 && edad <= 50)
		{
			return edadesCantidadEntrada[4];
		}
		else if(edad >= 51 && edad <= 60)
		{
			return edadesCantidadEntrada[5];
		}
		else if(edad >= 61)
		{
			return edadesCantidadEntrada[6];
		}
		
		return 0;
	}

}
