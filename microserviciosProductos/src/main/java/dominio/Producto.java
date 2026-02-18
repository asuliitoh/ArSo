package dominio;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import repositorios.Identificable;

/**
 * Representa un producto publicado en la plataforma.
 * Contiene información relevante como título, descripción, precio, estado, fecha de publicación, categoría, visualizaciones,
 * disponibilidad de envío, lugar de recogida y vendedor.
 */
@Entity
public class Producto implements Identificable {

	/** Identificador único del producto */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String id;
	/** Título del producto */
	private String titulo;
	/** Descripción detallada del producto */
	@Lob
	private String descripcion;
	/** Precio del producto */
	private double precio;
	/** Estado actual del producto */
	@Enumerated(EnumType.ORDINAL)
	private EstadoProducto estado;
	/** Fecha en la que se publicó el producto */
	private LocalDateTime fechaPublicacion;
	/** Categoría a la que pertenece el producto */
	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;
	/** Número de visualizaciones del producto */
	private int visualizaciones;
	/** Indica si el envío está disponible para el producto */
	private boolean envioDisponible;
	/** Lugar de recogida del producto */
	@Embedded
	private LugarDeRecogida recogida;
	/** Usuario que vende el producto */
	@ManyToOne
	@JoinColumn(name = "vendedor_id")
	private Usuario vendedor;
	
	/**
	 * Constructor sin argumentos, utilizado por JPA.
	 */
	public Producto() {}
	
	
	public Producto (String titulo, String descripcion, double precio, EstadoProducto estado, Categoria categoria, Usuario vendedor, boolean envioDisponible) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.estado = estado;
		this.categoria = categoria;
		this.vendedor = vendedor;
		this.envioDisponible = envioDisponible;
		this.fechaPublicacion = LocalDateTime.now();
		this.visualizaciones = 0;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
		
	}
	
	public void setLugarDeRecogida(LugarDeRecogida recogida) {
		this.recogida = recogida;
	}
	
	public LugarDeRecogida getLugarDeRecogida() {
		return this.recogida;
	}


	public String getTitulo() {
		return this.titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDescripcion() {
		return this.descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public double getPrecio() {
		return this.precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public EstadoProducto getEstado() {
		return this.estado;
	}


	public void setEstado(EstadoProducto estado) {
		this.estado = estado;
	}


	public LocalDateTime getFechaPublicacion() {
		return this.fechaPublicacion;
	}


	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}


	public Categoria getCategoria() {
		return this.categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public int getVisualizaciones() {
		return this.visualizaciones;
	}


	public void setVisualizaciones(int visualizaciones) {
		this.visualizaciones = visualizaciones;
	}


	public boolean isEnvioDisponible() {
		return this.envioDisponible;
	}


	public void setEnvioDisponible(boolean envioDisponible) {
		this.envioDisponible = envioDisponible;
	}


	public Usuario getVendedor() {
		return this.vendedor;
	}


	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}
	
}