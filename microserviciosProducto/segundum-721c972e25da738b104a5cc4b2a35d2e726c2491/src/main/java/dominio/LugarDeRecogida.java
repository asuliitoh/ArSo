package dominio;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * Representa un lugar de recogida para un producto.
 * Incluye una descripción y coordenadas geográficas.
 */
@Embeddable
public class LugarDeRecogida {

	/** Descripción del lugar de recogida */
	@Lob
	private String recogida;
	/** Longitud geográfica */
	private double longitud;
	/** Latitud geográfica */
	private double latitud;
	
	
	/**
	 * Constructor por defecto, para JPA.
	 */
	public LugarDeRecogida() {};
	
	
	public LugarDeRecogida(String recogida, int longitud, int latitud) {
		this.recogida = recogida;
		this.longitud = longitud;
		this.latitud = latitud;
	}
	
	public String getRecogida() {
		return recogida;
	}




	public void setRecogida(String recogida) {
		this.recogida = recogida;
	}




	public double getLongitud() {
		return longitud;
	}




	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}




	public double getLatitud() {
		return latitud;
	}




	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}



}