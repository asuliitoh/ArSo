package repositorios;

import dominio.Usuario;

public interface RepositorioUsuariosAdHoc extends RepositorioString<Usuario> {

	public boolean checkEmail(String correo) throws RepositorioException;
	
}
