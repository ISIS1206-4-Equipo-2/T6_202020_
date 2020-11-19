package view;

import model.logic.Modelo;

public class View {
	/**
	 * Metodo constructor
	 */
	public View() {

	}

	public void printMenu() {
		System.out.println("1. Cargar datos");
		System.out.println("2. Consultar grado de entrada y salida");
	}

	public void printMessage(String mensaje) {
		System.out.println(mensaje);
	}

	public void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
