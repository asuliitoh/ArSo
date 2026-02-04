package servicios;

import java.time.LocalDate;
import java.util.Optional;
import dominio.Usuario;

public interface IServicioUsuarios {

	public Optional<String> registrarUsuario(String nombre, String apellido, String correo, String clave, LocalDate fecha, String telefono);
	
	public boolean modificarUsuario(String idUsuario, String nombre, String apellido,String correo,String clave, LocalDate fecha, String telefono);
	
	public Usuario recuperarUsuario(String idUsuario);
}
