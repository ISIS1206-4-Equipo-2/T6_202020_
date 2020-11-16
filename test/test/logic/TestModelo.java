package test.logic;

import static org.junit.Assert.*;


import model.logic.Comparendo;
import model.logic.Modelo;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;

public class TestModelo {
	
	private Modelo conexion;
	
	@Before
	public void setUp() 
	{
		conexion = new Modelo();
		conexion.leerGeoJson(Controller.JUEGUEMOS,1);
	}

	@Test
	public void testModelo() 
	{
		assertTrue(conexion.darHashLineal()!=null);
	}

	@Test
	public void testDarTamano() 
	{
		assertEquals(20, conexion.darHashLineal().darDatos());
	}
	
}
