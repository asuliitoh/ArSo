package utils;

import java.time.LocalDateTime;

public class ProductoResumen {

	private String id;
	private String titulo;
	private double precio;
	private LocalDateTime fechaPublicacion;
	private String nombreCategoria;
	private int visualizaciones;

	/**
	 * Constructor requerido por JPQL para la consulta de historial.
	 * Los campos deben coincidir con los del SELECT NEW.
	 */
	public ProductoResumen(String id, String titulo, double precio, LocalDateTime fechaPublicacion, String nombreCategoria, int visualizaciones) {
		this.id = id;
		this.titulo = titulo;
		this.precio = precio;
		this.fechaPublicacion = fechaPublicacion;
		this.nombreCategoria = nombreCategoria;
		this.visualizaciones = visualizaciones;
	}

	// --- Getters (necesarios para que funcionen las pruebas) ---
	
	public String getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public double getPrecio() {
		return precio;
	}

	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public int getVisualizaciones() {
		return visualizaciones;
	}
	
	@Override
	public String toString() {
		return "ProductoResumen{" +
				"id='" + id + '\'' +
				", titulo='" + titulo + '\'' +
				", precio=" + precio +
				", fechaPublicacion=" + fechaPublicacion +
				", nombreCategoria='" + nombreCategoria + '\'' +
				", visualizaciones=" + visualizaciones +
				'}';
	}
}