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
		System.out.println("3. Cantidad de clusters");
		System.out.println("5. Estaciones criticas");
		System.out.println("6. Ruta por resistencia");
	}

	public void printMessage(String mensaje) {
		System.out.println(mensaje);
	}

	public void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
