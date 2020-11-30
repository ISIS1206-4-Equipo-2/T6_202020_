package model.logic;

import java.util.*;

public class Ruta {

    private Stack<Integer> estaciones;
    private Stack<Double> tiempos;

    // Constructor
    public Ruta() {
        estaciones = new Stack<Integer>();
        tiempos = new Stack<Double>();
    }

    // Agrega una estación a la ruta y el tiempo promedio a ella
    public void agregarEstacion(Integer id, Double tiempo) {
        estaciones.push(id);
        double resp = tiempo / 60;
        resp = Math.round(resp * 100) / 100;
        tiempos.push(resp);
    }

    // Devuelve las estaciones en la ruta
    public Stack<Integer> darEstaciones() {
        return estaciones;
    }

    // Devuelve los tiempos entre estaciones
    public Stack<Double> darTiempos() {
        return tiempos;
    }

    // Devuelve el tiempo total que toma la ruta (segundos)
    public Double darTiempoTotal() {
        double resp = 0.0;
        for (Double tiempo : tiempos) {
            resp = resp + tiempo;
        }
        resp = Math.round(resp * 100) / 100;
        return resp;
    }

    // Revisa que la ruta no esté vacía
    public boolean isEmpty() {
        boolean resp = false;
        if (estaciones.empty() && tiempos.empty())
            resp = true;
        return resp;
    }
}
