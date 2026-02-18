package servicios;

import java.util.List;

import dominio.Categoria;
import dominio.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.RepositorioUsuariosJPA;
import repositorios.RepositorioCategoriasAdHocJPA;
import repositorios.RepositorioException;
import utils.CargarCategorias;
import utils.ICargarCategorias;

public class ServicioCategorias implements IServicioCategorias{


	RepositorioCategoriasAdHocJPA repositorioCategorias = FactoriaRepositorios.getRepositorio(Categoria.class);
	RepositorioUsuariosJPA repositorioUsuarios = FactoriaRepositorios.getRepositorio(Usuario.class);
	
	@Override
	public boolean cargarCategorias(String ruta, String idUsuario) {
		
		
		try {		
			ICargarCategorias cargar = new CargarCategorias();
			Categoria raiz = cargar.cargarCategorias(ruta);
			if (raiz == null) return false;
			
			try {
				repositorioCategorias.getById(raiz.getId());
				System.out.println("INFO: La categoría principal '" + raiz.getNombre() + "' (ID: " + raiz.getId() + ") ya existe. Omitiendo carga.");
				return false;
			} catch (EntidadNoEncontrada e) {
			}
			
			repositorioCategorias.add(raiz);
			return true;
			
		} catch (RepositorioException e) { 
			System.err.println("Error de repositorio al cargar categorías: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}


	public boolean modificarCategoria(String idCategoria, String descripcion, String idUsuario) {
		try {
			
			Categoria categoria = repositorioCategorias.getById(idCategoria);
			categoria.setDescripcion(descripcion);
			repositorioCategorias.update(categoria);
			
			return true;
			
		} catch (EntidadNoEncontrada e) {
			System.err.println("Error al modificar: No se encontró la categoría con id " + idCategoria);
			e.printStackTrace();
			return false;
		} catch (RepositorioException e) {
			System.err.println("Error de repositorio al modificar la categoría: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Categoria> recuperarCategoriasRaiz() {
		try {
			return repositorioCategorias.getCategoriasRaiz();
		} catch (RepositorioException e) {
			System.err.println("Error al recuperar categorías raíz: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Categoria> recuperarCategoriasDescendentes(String idCategoria) {
		try {
			Categoria categoriaPadre = repositorioCategorias.getById(idCategoria);
			return repositorioCategorias.getDescendientes(categoriaPadre);
			
		} catch (EntidadNoEncontrada e) {
			System.err.println("Error al buscar descendientes: No se encontró la categoría con id " + idCategoria);
			e.printStackTrace();
			return null;
		} catch (RepositorioException e) {
			System.err.println("Error de repositorio al buscar descendientes: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
