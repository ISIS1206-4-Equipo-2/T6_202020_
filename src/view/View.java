package view;

public class View {
	/**
	 * Metodo constructor
	 */
	public View() {

	}

	public void printMenu() {
		System.out.println("\n1. Cargar datos");
		System.out.println("2. Consultar grado de entrada y salida");
		System.out.println("5. Estaciones críticas");
		System.out.println("7. Estacion con mas viajes por edad FALTA LA RUTA MAS CORTA CON DIJSKTRA");
	}

	public void printMessage(String mensaje) {
		System.out.println(mensaje);
	}

	public void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
