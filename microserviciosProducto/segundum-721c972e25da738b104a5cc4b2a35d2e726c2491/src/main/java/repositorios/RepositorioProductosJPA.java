package repositorios;

import dominio.Producto;

public class RepositorioProductosJPA extends RepositorioJPA<Producto> {

	@Override
	public Class<Producto> getClase() {
		return Producto.class;
	}

	
}
