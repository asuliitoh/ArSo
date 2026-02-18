package servicios;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import dominio.Categoria;
import dominio.EstadoProducto;
import dominio.LugarDeRecogida;
import dominio.Producto;
import dominio.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.RepositorioProductosAdHoc;
import repositorios.RepositorioUsuariosAdHoc;
import repositorios.RepositorioCategoriasAdHoc;
import repositorios.RepositorioException;
import utils.ProductoResumen;

public class ServicioProductos implements IServicioProductos{

	public RepositorioProductosAdHoc repositorioProductos = FactoriaRepositorios.getRepositorio(Producto.class);
	public RepositorioCategoriasAdHoc repositorioCategorias = FactoriaRepositorios.getRepositorio(Categoria.class);
	public RepositorioUsuariosAdHoc repositorioUsuarios = FactoriaRepositorios.getRepositorio(Usuario.class);
	
	@Override
	public Optional<String> registrarProducto(String titulo, String descripcion, double precio, EstadoProducto estado,
			String idCategoria, String disponibilidadEnvio, String IDVendedor) {
		
		if (titulo == null || titulo.isEmpty() || descripcion == null || descripcion.isEmpty() || estado == null ||
			idCategoria == null || idCategoria.isEmpty() || disponibilidadEnvio == null || disponibilidadEnvio.isEmpty() ||
			IDVendedor == null || IDVendedor.isEmpty()) return Optional.empty();
		
		try {
			// Si la categoría del producto no está registrado en el repositorio u ocurre un error, se lanza una excepción y se lanza null.
			Categoria categoria = repositorioCategorias.getById(idCategoria);
			// Si el vendedor del producto no está registrado en el repositorio u ocurre un error, se lanza una excepción y se lanza null.
			Usuario vendedor = repositorioUsuarios.getById(IDVendedor);
			
			// Se parsea la disponibilidad de envío
			boolean envio = "true".equalsIgnoreCase(disponibilidadEnvio);
			
			Producto producto = new Producto(titulo, descripcion, precio, estado, categoria, vendedor, envio);
	
			// Se persiste Y se devuelve la id del producto.
			String id = repositorioProductos.add(producto);
			return Optional.of(id);
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
			return Optional.empty();
		} catch (RepositorioException e) {		
			e.printStackTrace();
			return Optional.empty();
		}
	
	}

	@Override
	public boolean asignarLugarRecogida(String idProducto,  String descripcion, int longitud, int latitud) {
		
		try {
			
			Producto producto = repositorioProductos.getById(idProducto);
			LugarDeRecogida lugar = new LugarDeRecogida(descripcion, longitud, latitud);
			producto.setLugarDeRecogida(lugar);
			repositorioProductos.update(producto); 
			return true;
			
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
			return false;
		} catch (RepositorioException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	

	@Override
	public boolean modificarProducto(String identificador, double precio, String descripcion) {
		
		if (identificador == null || (precio < 0.0) || descripcion == null) return false;
		
		try {
			Producto producto = repositorioProductos.getById(identificador);
			producto.setPrecio(precio);
			producto.setDescripcion(descripcion);
			repositorioProductos.update(producto);
			return true;
			
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
			return false;
		} catch (RepositorioException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean addVisualizacion(String identificador) {

		if (identificador == null) return false;
		

		try {
			Producto producto = repositorioProductos.getById(identificador);
			producto.setVisualizaciones(producto.getVisualizaciones() + 1);
			repositorioProductos.update(producto);
			return true;
			
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
			return false;
		} catch (RepositorioException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<ProductoResumen> getHistorialVentas(LocalDate fecha) {
		if (fecha == null) return Collections.emptyList();
		else
			try {
				return repositorioProductos.getHistorialVentas(fecha);
			} catch (RepositorioException e) {
				e.printStackTrace();
				return Collections.emptyList();	
			}
	}

	@Override
	public List<Producto> getProductoByFiltros(String idCategoria, String textoContenido, EstadoProducto estado,
			double precioMaximo) {
		
		try {
			return repositorioProductos.getByFiltros(idCategoria, textoContenido, estado, precioMaximo);
		} catch (RepositorioException e) {
			e.printStackTrace();
			return Collections.emptyList();
		} catch (EntidadNoEncontrada e) {
			return Collections.emptyList();
		}
		
	}

	
	
}