package view;

import model.logic.Modelo;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("1. Cargar datos.");
			System.out.println("2. Buscar tiempos de viaje por fecha, clase de vehículo e infracción (Tabla de Hash Linear Probing)");
			System.out.println("3. Pruebas de desempeño - Tabla de Hash Linear Probing");
			System.out.println("4. Buscar tiempos de viaje por fecha, clase de vehículo e infracción (Tabla de Hash Separate Chaining)");
			System.out.println("5. Pruebas de desempeño - Tabla de Hash Separate Chaining");
			System.out.println("6. Exit");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
		}

		public void printMessage(String mensaje) 
		{
			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			// TODO implementar
		}
}
