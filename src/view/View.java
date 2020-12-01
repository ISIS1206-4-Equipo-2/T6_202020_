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
		System.out.println("4. Ruta Circular");
		System.out.println("5. Estaciones criticas");
		System.out.println("6. Ruta por resistencia");		
		System.out.println("7. Estacion con mas viajes por edad + recorrido.");
		System.out.println("8. Ruta interes turistico");
		System.out.println("9. BONO: Imprimir mapa");
		System.out.println("10. BONO: Identificador de Bicicletas");
	}

	public void printMessage(String mensaje) {
		System.out.println(mensaje);
	}

	public void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
