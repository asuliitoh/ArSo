package servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import dominio.EstadoProducto;
import dominio.Producto;
import utils.ProductoResumen;

public interface IServicioProductos {

	public Optional<String> registrarProducto(String titulo, String descripcion, double precio, EstadoProducto estado, String idCategoria, String disponibilidadEnvio, String IDVendedor);
	
	public boolean asignarLugarRecogida(String idProducto, String descripcion, int longitud, int latitud);
	
	public boolean modificarProducto(String identificador, double precio, String descripcion);
	
	public boolean addVisualizacion(String identificador);
	
	public List<ProductoResumen> getHistorialVentas(LocalDate fecha);
	
	public List<Producto> getProductoByFiltros(String idCategoria, String textoContenido, EstadoProducto estado, double precioMaximo);
}
