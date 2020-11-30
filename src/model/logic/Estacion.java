package model.logic;

public class Estacion {

    private String nombre;
    private Double latitud;
    private Double longitud;

    public Estacion(String pNombre){
        nombre=pNombre;
    }
    
    public Estacion(String pNombre, Double lat, Double lng){
        nombre=pNombre;
        latitud = lat;
        longitud = lng;
    }

	public String darNombre() {
		return nombre;
	}
    
    /**Retorna la distancia de la estacion a un punto dado
     * utiliza la formula de Haversine
    **/
    public Double darDistancia(Double pLat, Double pLng){
        if(longitud==null || latitud==null) return null;
        double radioTierra = 6371;//en kilómetros 
        double dLat = Math.toRadians(this.latitud - pLat);  
        double dLng = Math.toRadians(this.longitud - pLng);  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(pLat)) * Math.cos(Math.toRadians(this.latitud));  
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
        double distancia = radioTierra * va2;  
        distancia = (double) Math.round(distancia * 1000) / 1000;//Aproxima los km
        return distancia; 
    }
}
