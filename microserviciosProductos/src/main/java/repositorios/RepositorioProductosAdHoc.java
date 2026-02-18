package repositorios;

import java.time.LocalDate;
import java.util.List;

import dominio.EstadoProducto;
import dominio.Producto;
import utils.ProductoResumen;

public interface RepositorioProductosAdHoc extends RepositorioString<Producto>{

	public List<ProductoResumen> getHistorialVentas(LocalDate fecha) throws RepositorioException;
	
	public List<Producto> getByFiltros(String idCategoria, String textoContenido, EstadoProducto estado, double precioMaximo) throws RepositorioException, EntidadNoEncontrada;
	
}

