package model.data_structures;

public class TablaHashSeparateChaining<K extends Comparable<K>, V> implements TablaSimbolos<K, V> {

	/**
	 * Constante con la capacidad inicial
	 */
	private static final int CAPACIDAD_INICIAL = 3;

	/**
	 * Cantidad de elementos.
	 */
	private int n;

	/**
	 * Cantidad de espacios.
	 */
	private int m;

	/**
	 * Cantidad de rehashes
	 */
	private int cantidadRehashes;
	
	/**
	 * Arreglo de nodos
	 */
	private BusquedaSecuencial<K, V>[] st;

	public TablaHashSeparateChaining() {
		this(CAPACIDAD_INICIAL);
	}

	@SuppressWarnings("all")
	public TablaHashSeparateChaining(int m) {
		this.m = m;

		st = (BusquedaSecuencial<K, V>[]) new BusquedaSecuencial[m];

		for (int i = 0; i < m; i++)

			st[i] = new BusquedaSecuencial();
	}

	/**
	 * La funcion hash para las llaves
	 */
	public int hash(K k) {
		int hashcode = (k.hashCode() & 0x7fffffff) % m;
		return hashcode;
	}

	private void rehash(int cantidad) {
		TablaHashSeparateChaining<K, V> temp = new TablaHashSeparateChaining<K, V>(cantidad);

		for (int i = 0; i < m; i++) {
			for (K key : st[i].keys()) {
				temp.put(key, st[i].get(key));
			}
		}

		this.m = temp.m;
		this.n = temp.n;
		this.st = temp.st;
		this.cantidadRehashes++;
	}

	/**
	 * Agrega una dupla (K, V) a la tabla. Si la llave K existe, se reemplaza su
	 * valor V asociado. V no puede ser null. En este caso una llave K solo tiene
	 * asociado un valor V.
	 * 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("La llave del parametro es nula.");
		}

		// Verifica que el factor de carga siempre sea menor o igual 5, de lo contrario,
		// hace un rehash
		if (n >= 5 * m) {
			rehash(2 * m);
		}

		int i = hash(key);

		if (!st[i].contains(key)) {
			n++;
		}

		st[i].put(key, value);
	}

	/**
	 * Obtener el valor V asociado a la llave K. Se obtiene null solo si la llave K
	 * no existe. Se usa el comparador sobre las llaves para saber si existe.
	 * 
	 * @param key
	 * @return El valor que guarda la clave
	 */
	public V get(K key) {
		if (key == null) {
			throw new IllegalArgumentException("La llave del par�metro es nula.");
		}

		int i = hash(key);
		V resp = (V) st[i].get(key);
		return resp;
	}

	/**
	 * Borrar la dupla asociada a la llave K. Se obtiene el valor V asociado a la
	 * llave K. Se obtiene null solo si la llave K no existe.
	 * 
	 * @param key
	 * @return El valor eliminado
	 */
	public V remove(K key) {
		V resp = null;

		if (key == null) {
			throw new IllegalArgumentException("La llave del par�metro es nula.");
		}

		int i = hash(key);

		if (st[i].contains(key)) {
			n--;
			resp = st[i].get(key);
			st[i].delete(key);
		}

		// Divide la tabla si el factor de carga es menor a 2
		if (m > CAPACIDAD_INICIAL && n <= 2 * m) {
			rehash(m / 2);
		}

		return resp;
	}

	/**
	 * Retorna true en el caso que la llave K se encuentre almacenada en la Tabla, o
	 * false en el caso contrario.
	 */
	public boolean contains(K key) {
		if (key == null) {
			throw new IllegalArgumentException("La llave del par�metro es nula.");
		}

		return get(key) != null;
	}

	/**
	 * Retorna true si la Tabla NO tiene datos, o false en caso contrario.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Retorna el n�mero de duplas en la Tabla de S�mbolos
	 * 
	 * @return El n�mero de duplas.
	 */
	public int size() {
		return n;
	}

	/**
	 * Retorna el tamano de la Tabla de Simbolos
	 * 
	 * @return Cantidad de espacios
	 */
	public int capacity() {
		return m;
	}

	/**
	 * Retorna todas las llaves almacenadas en la Tabla.
	 */
	@SuppressWarnings("all")
	public K[] keySet() {
		K[] resp = (K[]) new Comparable[n];

		Iterable<K> keys = keys();

		int i = 0;
		for (K key : keys) {
			resp[i] = key;
			i++;
		}

		return resp;
	}

	/**
	 * Retorna todos los valores almacenados en la Tabla.
	 */
	@SuppressWarnings("all")
	public V[] valueSet() {
		V[] resp = (V[]) new Object[n];

		Iterable<K> keys = keys();

		int i = 0;
		for (K key : keys) {
			resp[i] = get(key);
			i++;
		}

		return resp;
	}

	/**
	 * Returns all keys in this symbol table as an {@code Iterable}. To iterate over
	 * all of the keys in the symbol table named {@code st}, use the foreach
	 * notation: {@code for (Key key : st.keys())}.
	 * 
	 * Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.
	 * 
	 * @return all keys in this symbol table
	 */
	public Iterable<K> keys() {
		Cola<K> cola = new Cola<K>();
		for (int i = 0; i < m; i++) {
			for (K key : st[i].keys())
				cola.enqueue(key);
		}
		return cola;
	}
	
	public int darCantidadRehashes()
	{
		return cantidadRehashes;
	}
}
