package dominio;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import repositorios.Identificable;

/**
 * Clase que representa un usuario de la plataforma.
 * Almacena datos personales y de acceso, así como privilegios de administrador.
 */
@Entity
public class Usuario implements Identificable{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	/** Identificador único del usuario */
	private String id;
	/** Correo electrónico del usuario */
	private String correo;
	/** Nombre del usuario */
	private String nombre;
	/** Apellidos del usuario */
	private String apellidos;
	
	/**
	 * Constructor sin argumentos requerido por JPA.
	 */
	public Usuario() {}
	
	/**
	 * Crea un usuario con los datos principales.
	 * @param nombre Nombre
	 * @param apellidos Apellidos
	 * @param correo Correo electrónico
	 */
	public Usuario(String nombre, String apellidos, String correo) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.correo = correo;
	}

	/**
	 * Devuelve el identificador único del usuario.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * Establece el identificador único del usuario.
	 */
	@Override
	public void setId(String id) {
		this.id = id;
		
	}

	/**
	 * Devuelve el correo electrónico del usuario.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electrónico del usuario.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Devuelve el nombre del usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del usuario.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve los apellidos del usuario.
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Establece los apellidos del usuario.
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	
}