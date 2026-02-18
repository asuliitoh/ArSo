package repositorios;

import dominio.Categoria;

public class RepositorioCategoriasJPA extends RepositorioJPA<Categoria> {

	@Override
	public Class<Categoria> getClase() {
		return Categoria.class;
	}

}
