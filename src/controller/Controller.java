package controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

import org.apache.commons.lang3.tuple.ImmutablePair;

import model.data_structures.Vertex;
import model.logic.Bicicleta;
import model.logic.Estacion;
import model.logic.Modelo;
import view.View;
import java.util.List;
import model.logic.Ruta;

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
				case "3": // Cantidad de clusters
					try {
						view.printMessage("Introduzca el primer id: ");
						int id1 = Integer.parseInt(lector.nextLine());
						view.printMessage("----------------------------------");
						view.printMessage("Introduzca el segundo id: ");
						int id2 = Integer.parseInt(lector.nextLine());
						view.printMessage("----------------------------------");
						LinkedList<LinkedList<Integer>> clusters = modelo.Clusters();
						view.printMessage(
								"Existen " + clusters.size() + " componentes fuertemente conexos en el grafo.");
						boolean flag = false;
						for (LinkedList<Integer> list : clusters) {
							if (list.contains(id1) && list.contains(id2)) {
								flag = true;
								break;
							}
						}
						if (flag) {
							view.printMessage("Los IDs " + id1 + " y " + id2 + " se encuentran en el mismo cluster.");
						} else {
							view.printMessage(
									"Los IDs " + id1 + " y " + id2 + " no se encuentran en el mismo cluster.");
						}
						view.printMessage("----------------------------------");
					} catch (Exception e) {
						view.printMessage("No se puddo ejecutar el metodo");
						view.printMessage(e.getMessage());
					}
					break;
				case "4":
					view.printMessage("Introduzca el id de inicio: ");
					int idm = Integer.parseInt(lector.nextLine());
					view.printMessage("----------------------------------");
					view.printMessage("Introduzca el tiempo dispoible: ");
					int tm = Integer.parseInt(lector.nextLine())*60;
					view.printMessage("----------------------------------");
					view.printMessage("Posibles rutas: ");
					view.printMessage("----------------------------------");
					ImmutablePair<LinkedList<LinkedList<Integer>>, LinkedList<Integer>> pair = modelo.circularRoute(idm);
					Iterator<LinkedList<Integer>> lista = pair.left.iterator();
					Iterator<Integer> tiempom = pair.right.iterator();
					while (lista.hasNext()) {
						int ti=tiempom.next();
						LinkedList<Integer> lis=lista.next();
						if(ti<=tm){
							view.printMessage("Minutos: "+ti/60);
							view.printMessage(lis.toString());
							view.printMessage("----------------------------------");
						}
					}
					break;
				case "5": // Estaciones cr�ticas
					try {
						long t_i = System.currentTimeMillis();
						modelo.estacionesCriticas();
						long t_f = System.currentTimeMillis();
						long tiempo = t_f - t_i;
						double tiempoS = (double) tiempo / 1000;
						view.printMessage("TIEMPO TOTAL REQUERIDO: " + tiempoS + " segundos");
					} catch (Exception e) {
						view.printMessage("Hubo un error.");
						view.printMessage(e.getMessage());
					}

					break;
				case "7": // Recomendador de Rutas
					try {
						view.printMessage("Introduzca la edad: ");
						view.printMessage("----------------------------------");

						int edad = Integer.parseInt(lector.nextLine());
						Vertex<Integer, Estacion>[] masViajes = modelo.estacionConMasViajerosPorEdad(edad);
							
						view.printMessage("Mas por salida: " + masViajes[0].getInfo().darNombre() + " con " + " "
								+ masViajes[0].getInfo().cantidadEnRangoEdadS(edad) + " salidas.");
						view.printMessage("Longitud: " + masViajes[0].getInfo().darLongitud());
						view.printMessage("Latitud: " + masViajes[0].getInfo().darLatitud());
						view.printMessage("ID: " + masViajes[0].getId());
						view.printMessage("----------------------------------");
						view.printMessage("Mas por entrada: " + masViajes[1].getInfo().darNombre() + " con " + " "
								+ masViajes[1].getInfo().cantidadEnRangoEdadE(edad) + " entradas.");
						view.printMessage("Longitud: " + masViajes[1].getInfo().darLongitud());
						view.printMessage("Latitud: " + masViajes[1].getInfo().darLatitud());
						view.printMessage("ID: " + masViajes[1].getId());
						modelo.losCaminosDeLaVida(masViajes[0], masViajes[1]);
					} catch (Exception e) {
						view.printMessage("Hubo un error.");
						view.printMessage(e.getMessage());
					}
					break;

				case "6": // Ruta por resistencia (Luisa)
					view.printMessage("Introduzca el id a consultar: ");
					view.printMessage("----------------------------------");
					try {
						int id = Integer.parseInt(lector.nextLine());
						view.printMessage("Introduzca el tiempo maximo de resistencia (en minutos):");
						view.printMessage("----------------------------------");
						int resistencia = Integer.parseInt(lector.nextLine());
						Double resDouble = Double.valueOf(resistencia * 60); // Paso a segundos
						modelo.verificarParam(id);
						long t_i = System.currentTimeMillis();
						List<Ruta> rutas = modelo.rutasResistencia(id, resDouble);
						long t_f = System.currentTimeMillis();
						long tiempo = t_f - t_i;
						double tiempoS = (double) tiempo / 1000;
						if (!rutas.isEmpty() && rutas != null) {
							view.printMessage("----------------------------------");
							view.printMessage("Se encontraron " + rutas.size() + " rutas");
							view.printMessage("Tiempo requerido: " + tiempoS + " segundos");
							view.printMessage("Introduzca la cantidad de rutas a consultar (0 para verlas todas):");
							view.printMessage("----------------------------------");
							int cant = Integer.parseInt(lector.nextLine());
							int cont = 0;
							if (cant != 0) {
								for (Ruta ruta : rutas) {
									if (cont < cant) {
										cont = cont + 1;
										Stack<Integer> estaciones = ruta.darEstaciones();
										Stack<Double> tiempos = ruta.darTiempos();
										String strEstaciones = Integer.toString(id);
										String strTiempos = "";
										for (Integer est : estaciones)
											strEstaciones = strEstaciones + " - " + est;
										for (Double t : tiempos)
											strTiempos = strTiempos + " - " + t;
										strTiempos = strTiempos.substring(2);// Elimina primer espacio
										view.printMessage("Ruta " + cont + " : " + strEstaciones);
										view.printMessage("Tiempos: " + strTiempos);
										view.printMessage("--------------------------------------");
									}
								}
							} else {
								for (Ruta ruta : rutas) {
									cont = cont + 1;
									Stack<Integer> estaciones = ruta.darEstaciones();
									Stack<Double> tiempos = ruta.darTiempos();
									String strEstaciones = Integer.toString(id);
									String strTiempos = "";
									for (Integer est : estaciones)
										strEstaciones = strEstaciones + " - " + est;
									for (Double t : tiempos)
										strTiempos = strTiempos + " - " + t;
									strTiempos = strTiempos.substring(2);// Elimina primer espacio
									view.printMessage("Ruta " + cont + " : " + strEstaciones);
									view.printMessage("Tiempos: " + strTiempos);
									view.printMessage("--------------------------------------");
								}
							}
						}
					} catch (Exception e) {
						view.printMessage("------------ ERROR ---------------");
						view.printMessage(e.getMessage());
					}
					break;
				case "8":// Ruta interes turistico
					view.printMessage("----------------- NOTA -----------------");
					view.printMessage("Coordenadas de refencia de la ciudad de Nueva Yors");
					view.printMessage("Latitud: 40.71 grados y Longitud: -73.93 grados");
					view.printMessage("----------------------------------------");
					try {
						view.printMessage("Ingrese las coordenadas iniciales en formato lat long (ej: \"-12.5 15\"):");
						String strGeo0 = lector.nextLine().trim();
						String[] latLong = strGeo0.split(" ");
						Double latitud0 = Double.parseDouble(latLong[0]);
						Double longitud0 = Double.parseDouble(latLong[1]);
						if (latitud0 < -90 || latitud0 > 90)
							throw new Exception(); // Rango de latitudes
						if (longitud0 < -180 || longitud0 > 180)
							throw new Exception(); // Rango de longitudes
						view.printMessage("Ingrese las coordenadas finales en formato lat-long (ej: \"-25 25.5\"):");
						String strGeof = lector.nextLine().trim();
						String[] latLongf = strGeof.split(" ");
						Double latitudf = Double.parseDouble(latLongf[0]);
						Double longitudf = Double.parseDouble(latLongf[1]);
						if (latitudf < -90 || latitudf > 90)
							throw new Exception(); // Rango de latitudes
						if (longitudf < -180 || longitudf > 180)
							throw new Exception(); // Rango de longitudes
						long t_i = System.currentTimeMillis();
						Integer[] ids = modelo.darEstacionesCercanas(latitud0, longitud0, latitud0, longitudf);
						long t_f = System.currentTimeMillis();
						long tiempo = t_f - t_i;
						double tiempoS = (double) tiempo / 1000;
						view.printMessage("\n---------------- RESUMEN  ------------------");
						view.printMessage("Tiempo total: " + tiempoS + " segundos");
						view.printMessage("Estaciones mas cercanas:");
						view.printMessage("Inicio: "+ modelo.darVerticePorID(ids[0]).getInfo().darNombre() + " con la ID " + ids[0]);
						view.printMessage("Final: "+ modelo.darVerticePorID(ids[1]).getInfo().darNombre() + " con la ID " + ids[1]);
						
						view.printMessage("El camino mas corto entre ambas estaciones es: ");
						modelo.losCaminosDeLaVida(modelo.darVerticePorID(ids[0]), modelo.darVerticePorID(ids[1]));
						
						view.printMessage("---------------------------------------------");
					} catch (Exception e) {
						view.printMessage("--------- Formato de geoposicionamiento invalido ---------");
						view.printMessage(e.getMessage());
						fin = true;
						e.printStackTrace();
					}
					break;
				case "9": //Publicidad por edades
					view.printMessage("Seleciones el rango de edad a consultar (ej. 1):");
					view.printMessage("(0) 0-10   (1) 11-20  (2) 21-30  (3) 31-40");
					view.printMessage("(4) 41-50  (5) 51-60  (6) 61+");
					try{
						String edad = lector.nextLine().trim();
						long t_i = System.currentTimeMillis();
						List<Integer[]> ids = modelo.estacionesEdades(edad, 2020);
						long t_f = System.currentTimeMillis();
						long tiempo = t_f - t_i;
						double tiempoS = (double)tiempo/1000;
						if(ids==null) System.out.println("No hay rutas en este rango");
						view.printMessage("\n---------------- RESUMEN  ------------------");
						view.printMessage("Tiempo de búsqueda: " + tiempoS + " segundos");
						view.printMessage("Las estaciones con mas flujo de turistas son:");
						int cont = 1;
						for(Integer[] est : ids){
							view.printMessage("--------------Par " + cont + "------------");
							view.printMessage("Inicio: " + est[0]);
							view.printMessage("Final : " + est[1]);
							view.printMessage("Total de viajes: " + est[2]);
							cont++;
						}
					}
					catch(Exception e){
						view.printMessage("--------- Error al hallar edades ---------");
						view.printMessage(e.getMessage());
						fin = true;
					}
				break;
				default:
					view.printMessage("--------- Opcion Invalida ---------");
					break;
					case "10":
					view.printMessage("Ingrese la ID de la bicicleta");
					String id = lector.nextLine().trim();
					
					Bicicleta bici = modelo.darBicicleta(id);
					
					if(bici != null)
					{
						view.printMessage("Tiempo de viajes en minutos: " + bici.darTiempoUso()/60);
						view.printMessage("Tiempo estacionada: " + bici.darTiempoEstacionada()/60);
						view.printMessage("Estaciones visitadas:");
						view.printMessage("---------------------------------------------");
						for (Estacion estacion : bici.darIterableEstaciones()) 
						{
							view.printMessage(estacion.darNombre());
						}
					}
					else
					{
						view.printMessage("No existe la bicicleta");
					}
					break;
				case "11": // Graficar?? SII
					try {
						view.printMessage("Generando archivo...");
						modelo.maps();
						view.printMessage("Archivo generado.");
					} catch (Exception e) {
						view.printMessage("Hubo un error.");
						view.printMessage(e.getMessage());
					}
					break;
			}
		}
		lector.close();
	}

}
