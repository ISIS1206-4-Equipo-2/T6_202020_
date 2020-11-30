package controller;

import java.util.Scanner;

import model.logic.Estacion;
import model.logic.GoogleMaps;
import model.logic.Modelo;
import view.View;

public class Controller {

	private Modelo modelo;
	private View view;

	public Controller() {
		view = new View();
		modelo = new Modelo();
	}

	public void run() {
		view.clearScreen();
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		while (!fin) {
			view.printMenu();
			String option = "";
			try {
				option = lector.nextLine();
			} catch (Exception e) {
				view.printMessage(e.getMessage());
				fin = true;
			}
			view.clearScreen();
			switch (option) {
				case "1": // Carga de datos
					view.printMessage("Elija uno de los datos (1, 2, 3 o 4): ");
					view.printMessage("----------------------------------");
					try {
						int datos = Integer.parseInt(lector.nextLine());
						if (datos < 1 || datos > 4) {
							throw new Exception("--------- Opcion Invalida ---------");
						}
						view.printMessage("--------- Cargando Datos ---------");
						long t_i = System.currentTimeMillis();
						modelo.cargarDatos(datos);
						long t_f = System.currentTimeMillis();
						long tiempo = t_f - t_i;
						double tiempoS = (double) tiempo / 1000;
						view.printMessage("----------------------------------");
						view.printMessage("TIEMPO TOTAL REQUERIDO: " + tiempoS + " segundos");
					} catch (Exception e) {
						view.printMessage("No se pudieron cargar los datos");
						view.printMessage(e.getMessage());
					}
					break;
				case "2":
					view.printMessage("Introduzca el id a consultar: ");
					view.printMessage("----------------------------------");
					try {
						int id = Integer.parseInt(lector.nextLine());
						view.printMessage(modelo.darGradosPorID(id));
						view.printMessage("----------------------------------");
					} catch (Exception e) {
						view.printMessage("No se pudieron obtener los grados de entrada y salida");
						view.printMessage(e.getMessage());
					}
					break;
				case "4":
					
					modelo.Clusters();
					
					break;
				case "5": //Estaciones cr�ticas
					try
					{
						long t_i = System.currentTimeMillis();
						modelo.estacionesCriticas();
						long t_f = System.currentTimeMillis();
						long tiempo = t_f - t_i;
						double tiempoS = (double) tiempo / 1000;
						view.printMessage("TIEMPO TOTAL REQUERIDO: " + tiempoS + " segundos");
					}
					catch (Exception e) 
					{
						view.printMessage("Hubo un error.");
						view.printMessage(e.getMessage());
					}
				
					break;
				case "7": //  Recomendador de Rutas
					try 
					{
						view.printMessage("Introduzca la edad: ");
						view.printMessage("----------------------------------");
						
						int edad = Integer.parseInt(lector.nextLine());
						Estacion[] masViajes = modelo.estacionConMasViajerosPorEdad(edad);
						
						view.printMessage("Mas por salida: " + masViajes[0].darNombre() + " con " + " " +masViajes[0].cantidadEnRangoEdadS(edad) +" salidas.");
						view.printMessage("Longitud: " +masViajes[0].darLongitud());
						view.printMessage("Latitud: " +masViajes[0].darLatitud());
						view.printMessage("----------------------------------");
						view.printMessage("Mas por entrada: " + masViajes[1].darNombre() + " con " + " " +masViajes[1].cantidadEnRangoEdadE(edad) +" entradas.");
						view.printMessage("Longitud: " +masViajes[1].darLongitud());
						view.printMessage("Latitud: " +masViajes[1].darLatitud());	
					} 
					catch (Exception e) 
					{
						view.printMessage("Hubo un error.");
						view.printMessage(e.getMessage());
					}
					break;
				case "9": //Graficar??
					try 
					{
						view.printMessage("Generando archivo...");
						modelo.maps();
					}
					catch (Exception e) 
					{
						view.printMessage("Hubo un error.");
						view.printMessage(e.getMessage());
					}
					break;
				default:
					view.printMessage("--------- Opcion Invalida ---------");
					break;
			}
		}
		lector.close();
	}

}
