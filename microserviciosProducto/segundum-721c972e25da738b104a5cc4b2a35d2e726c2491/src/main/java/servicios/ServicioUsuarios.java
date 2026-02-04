package servicios;

import java.time.LocalDate;
import java.util.Optional;

import dominio.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.RepositorioException;
import repositorios.RepositorioUsuariosAdHoc;

public class ServicioUsuarios implements IServicioUsuarios {

	public RepositorioUsuariosAdHoc repositorio = FactoriaRepositorios.getRepositorio(Usuario.class);
	
	@Override
	public Optional<String> registrarUsuario(String nombre, String apellido, String correo, String clave, LocalDate fecha, String telefono) {
		
		if ((nombre == null || nombre.isEmpty()) || (apellido == null || apellido.isEmpty())
			    || (correo == null || correo.isEmpty()) || (clave == null || clave.isEmpty()) ||
			    (fecha == null)) return Optional.empty();
		
		
		try {
			if (!repositorio.checkEmailAndTelefono(correo, telefono)) return Optional.empty();
			Usuario nuevoUsuario = new Usuario(nombre, apellido, correo);
			String id = this.repositorio.add(nuevoUsuario);
			nuevoUsuario.setId(id);
			return Optional.of(id);
		} catch (RepositorioException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	
	@Override
	public boolean modificarUsuario(String idUsuario, String nombre, String apellido,
			String correo, String clave, LocalDate fecha, String telefono) {
		
		Usuario usuario;
		try {
			usuario = repositorio.getById(idUsuario);
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
			return false;
		} catch (RepositorioException e) {
			e.printStackTrace();
			return false;
		}
		
		if (nombre != null && !nombre.isEmpty()) usuario.setNombre(nombre);
		if (apellido != null && !apellido.isEmpty()) usuario.setApellidos(apellido);
		if (correo != null && !correo.isEmpty()) usuario.setCorreo(correo);
		
		try {
			this.repositorio.update(usuario);
			return true;
		} catch (RepositorioException e) {
			e.printStackTrace();
			return false;
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public Usuario recuperarUsuario(String idUsuario) {
		try {
			return this.repositorio.getById(idUsuario);
			
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
			return null;
		} catch (RepositorioException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}