package repositorios;

import dominio.Usuario;

public class RepositorioUsuariosJPA extends RepositorioJPA<Usuario>{

	@Override
	public Class<Usuario> getClase() {
		return Usuario.class;
	}

}
