package repositorios;

import dominio.Usuario;

public interface RepositorioUsuariosAdHoc extends RepositorioString<Usuario> {

	public boolean checkEmailAndTelefono(String correo, String telefono) throws RepositorioException;
	
}
