package repositorios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import dominio.Categoria;
import dominio.EstadoProducto;
import dominio.Producto;
import utils.EntityManagerHelper;
import utils.ProductoResumen;

public class RepositorioProductosAdHocJPA extends RepositorioProductosJPA implements RepositorioProductosAdHoc {

	@Override
	public List<ProductoResumen> getHistorialVentas(LocalDate fecha) throws RepositorioException {
		
		try {
			// REQUISITO: Filtrar por mes y año.
			LocalDateTime inicioDelMes = fecha.withDayOfMonth(1).atStartOfDay();
			LocalDateTime finDelMes = fecha.withDayOfMonth(fecha.lengthOfMonth()).atTime(23, 59, 59);

			EntityManager em = EntityManagerHelper.getEntityManager();
			
			// REQUISITO: Resumen con id, título, precio, fecha, nombre categoría, visualizaciones, ordenado por visitas DESC.
			String queryString = "SELECT NEW utils.ProductoResumen(p.id, p.titulo, p.precio, p.fechaPublicacion, p.categoria.nombre, p.visualizaciones) "
					+ "FROM Producto p "
					+ "WHERE p.fechaPublicacion BETWEEN :inicio AND :fin " 
					+ "ORDER BY p.visualizaciones DESC";
			
			TypedQuery<ProductoResumen> query = em.createQuery(queryString, ProductoResumen.class);
			
			query.setParameter("inicio", inicioDelMes);
			query.setParameter("fin", finDelMes);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			
			return query.getResultList();
			
		}catch(RuntimeException e) {
			throw new RepositorioException("Error buscando historialVentas", e);
		}finally {
			EntityManagerHelper.closeEntityManager();
		}
		
	}
	
	@Override
	public List<Producto> getByFiltros(String idCategoria, String textoContenido, EstadoProducto estado,
			double precioMaximo) throws RepositorioException, EntidadNoEncontrada {

		try {
			EntityManager em = EntityManagerHelper.getEntityManager();
			
			StringBuilder queryString = new StringBuilder("SELECT p FROM Producto p WHERE 1=1");
			
			String rutaCategoria = null;
			
			// REQUISITO: Filtro de Categoría y descendientes (usando ruta)
			if (idCategoria != null && !idCategoria.isEmpty()) {
				try {
					Categoria cat = em.find(Categoria.class, idCategoria);
					if (cat == null) throw new EntidadNoEncontrada("La categoria pasada como filtro (" + idCategoria + ") no existe");
					rutaCategoria = cat.getRuta();
					queryString.append(" AND p.categoria.ruta LIKE :rutaCategoria"); 
				} catch (RuntimeException e) {
					throw new RepositorioException("Error al buscar la categoría para el filtro", e);
				}
			}
				
			// REQUISITO: Texto en descripción (opcional)
			if (textoContenido != null && !textoContenido.isEmpty()) { 
				queryString.append(" AND p.descripcion LIKE CONCAT('%', :texto, '%')");
			}
			
			// REQUISITO: Estado "igual o mejor" (opcional)
			if (estado != null) {
				queryString.append(" AND p.estado <= :estado"); 
			}
			
			// REQUISITO: Precio máximo (opcional)
			if (precioMaximo > 0) {
				queryString.append(" AND p.precio <= :precioMaximo");
			}
			
			TypedQuery<Producto> query = em.createQuery(queryString.toString(), Producto.class);
			
			// Asignar parámetros
			if (rutaCategoria != null) {
				query.setParameter("rutaCategoria", rutaCategoria + "%");
			}
			if (textoContenido != null && !textoContenido.isEmpty()) {
				query.setParameter("texto", textoContenido);
			}
			if (estado != null) {
				query.setParameter("estado", estado);
			}
			if (precioMaximo > 0) {
				query.setParameter("precioMaximo", precioMaximo);
			}
			
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			
			return query.getResultList();
			
		}catch(RuntimeException e){
			throw new RepositorioException("Error buscando by filtros", e);
		}finally {
			EntityManagerHelper.closeEntityManager();
		}
	}

}
