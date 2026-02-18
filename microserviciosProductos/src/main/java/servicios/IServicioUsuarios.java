package servicios;

import java.time.LocalDate;
import java.util.Optional;
import dominio.Usuario;

public interface IServicioUsuarios {

	public Optional<String> registrarUsuario(String nombre, String apellido, String correo);
	
	public boolean modificarUsuario(String idUsuario, String nombre, String apellido,String correo);
	
	public Usuario recuperarUsuario(String idUsuario);
}
