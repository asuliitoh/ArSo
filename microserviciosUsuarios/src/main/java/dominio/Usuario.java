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
	/** Clave de acceso del usuario */
	private String clave;
	/** Fecha de nacimiento del usuario */
	private LocalDate fechaNacimiento;
	/** Teléfono de contacto del usuario */
	private String telefono;
	/** Indica si el usuario tiene privilegios de administrador */
	private boolean esAdministrador;
	
	/**
	 * Constructor sin argumentos requerido por JPA.
	 */
	public Usuario() {}
	
	/**
	 * Crea un usuario con los datos principales.
	 * @param nombre Nombre
	 * @param apellidos Apellidos
	 * @param correo Correo electrónico
	 * @param clave Clave de acceso
	 * @param fecha Fecha de nacimiento
	 * @param admin Si es administrador
	 */
	public Usuario(String nombre, String apellidos, String correo, String clave, LocalDate fecha, boolean admin) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.correo = correo;
		this.clave = clave;
		this.fechaNacimiento = fecha;
		this.esAdministrador = admin;
	}

	/**
	 * Crea un usuario con todos los datos.
	 * @param nombre Nombre
	 * @param apellidos Apellidos
	 * @param correo Correo electrónico
	 * @param clave Clave de acceso
	 * @param fecha Fecha de nacimiento
	 * @param admin Si es administrador
	 * @param telefono Teléfono de contacto
	 */
	public Usuario(String nombre, String apellidos, String correo, String clave, LocalDate fecha, boolean admin, String telefono) {
		this(nombre,apellidos,correo,clave,fecha,admin);
		this.telefono = telefono;
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

	/**
	 * Devuelve la clave de acceso del usuario.
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * Establece la clave de acceso del usuario.
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * Devuelve la fecha de nacimiento del usuario.
	 */
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Establece la fecha de nacimiento del usuario.
	 */
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Devuelve el teléfono de contacto del usuario.
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Establece el teléfono de contacto del usuario.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public boolean esAdministrador() {
		return this.esAdministrador;
	}
	
	public void setEsAdministrador(boolean admin) {
		this.esAdministrador = admin;
	}
	
}