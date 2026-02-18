package dominio;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.*;


import repositorios.Identificable;

/**
 * Representa una categoría de productos en la plataforma.
 * Permite organizar los productos en grupos temáticos, incluyendo subcategorías.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Categoria implements Identificable{
	/** Identificador único de la categoría */
	@Id
	@XmlAttribute
	private String id;
	/** Nombre de la categoría */
	@XmlElement
	private String nombre;
	/** Descripción de la categoría */
	@Lob
	@XmlAttribute
	private String descripcion;
	/** Ruta asociada a la categoría (puede ser utilizada para navegación o almacenamiento) */
	@Lob
	@XmlAttribute
	private String ruta;
	/** Subcategoría asociada, si existe */
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "categoria_subcategoria",
			joinColumns = @JoinColumn(name = "categoria_id"),
			inverseJoinColumns = @JoinColumn(name = "subcategoria_id")
	)
	@XmlElement(name = "categoria")
	private List<Categoria> subcategorias;
	
	@OneToMany(mappedBy="categoria")
	private List<Producto> productos;	
	
	public Categoria() {}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
		
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<Categoria> subcategorias) {
		this.subcategorias = subcategorias;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
}