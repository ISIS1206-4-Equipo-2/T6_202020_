package model.logic;

import java.util.LinkedList;

import model.data_structures.Edge;

public class Estacion {

    private String nombre;
    private int[] edadesCantidad;
    
    public Estacion(String pNombre)
    {
    	edadesCantidad = new int[7];
    	for (int i : edadesCantidad) {
			i = 0;
		}
        nombre=pNombre;
    }
    
	public String darNombre() {
		return nombre;
	}
	
	public void aumentarRangoEdad(int edad)
	{
		if(edad >= 0 && edad <= 10)
		{
			
			edadesCantidad[0] = edadesCantidad[0]+1;
		}
		else if(edad >= 11 && edad <= 20)
		{
			edadesCantidad[1] = edadesCantidad[1]+1;
		}
		else if(edad >= 21 && edad <= 30)
		{	
			edadesCantidad[2] = edadesCantidad[2]+1;
		}
		else if(edad >= 31 && edad <= 40)
		{
			edadesCantidad[3] = edadesCantidad[3]+1;
		}
		else if(edad >= 41 && edad <= 50)
		{
			edadesCantidad[4] = edadesCantidad[4]+1;
		}
		else if(edad >= 51 && edad <= 60)
		{
			edadesCantidad[5] = edadesCantidad[5]+1;
		}
		else if(edad >= 61)
		{
			edadesCantidad[6] = edadesCantidad[6]+1;
		}
	}
	
	public int cantidadEnRangoEdad(int edad)
	{
		if(edad >= 0 && edad <= 10)
		{
			return edadesCantidad[0];
		}
		else if(edad >= 11 && edad <= 20)
		{
			return edadesCantidad[1];
		}
		else if(edad >= 21 && edad <= 30)
		{
			return edadesCantidad[2];
		}
		else if(edad >= 31 && edad <= 40)
		{
			return edadesCantidad[3];
		}
		else if(edad >= 41 && edad <= 50)
		{
			return edadesCantidad[4];
		}
		else if(edad >= 51 && edad <= 60)
		{
			return edadesCantidad[5];
		}
		else if(edad >= 61)
		{
			return edadesCantidad[6];
		}
		
		return 0;
	}

}
