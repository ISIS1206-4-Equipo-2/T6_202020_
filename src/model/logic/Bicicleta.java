package model.logic;

import java.util.Stack;

public class Bicicleta 
{
	private String id;
	private Stack<Estacion> estaciones;
	private double tiempo_de_uso;
	private double tiempo_estacionada;
	
	public Bicicleta(String id)
	{
		this.id = id;
		estaciones = new Stack<>();
		tiempo_de_uso = 0;
		tiempo_estacionada = 0;
	}
	
	public double darTiempoUso()
	{
		return tiempo_de_uso;
	}
	
	public double darTiempoEstacionada()
	{
		return tiempo_estacionada;
	}
	
	public void aumentarUso(double tiempo)
	{
		tiempo_de_uso += tiempo;
	}
	
	public void aumentarEstacionada(double tiempo)
	{
		tiempo_estacionada += tiempo;
	}
	
	public void agregarEstacion(Estacion estacion)
	{
		estaciones.push(estacion);
	}
	
	public Iterable<Estacion> darIterableEstaciones()
	{
		return estaciones;
	}
	
}
