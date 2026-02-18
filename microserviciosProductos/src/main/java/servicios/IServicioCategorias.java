package servicios;

import java.util.List;

import dominio.Categoria;

public interface IServicioCategorias {

	public boolean cargarCategorias(String ruta, String idUsuario);
	
	public boolean modificarCategoria(String idCategoria, String descripcion ,String idUsuario);
	
	public List<Categoria> recuperarCategoriasRaiz();
	
	public List<Categoria> recuperarCategoriasDescendentes(String idCategoria);
	
}
