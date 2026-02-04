package repositorios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import dominio.Categoria;
import utils.EntityManagerHelper;

public class RepositorioCategoriasAdHocJPA extends RepositorioCategoriasJPA implements RepositorioCategoriasAdHoc {

	@Override
	public List<Categoria> getCategoriasRaiz() throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {

			String jpql = "SELECT c FROM Categoria c WHERE c NOT IN (SELECT sub FROM Categoria cat JOIN cat.subcategorias sub)";
			
			TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			return query.getResultList();
			
		} catch (RuntimeException e) {
			throw new RepositorioException("Error al recuperar las categorías raíz", e);
		} finally {
			if (em.isOpen()) {
                EntityManagerHelper.closeEntityManager();
            }
		}
	}

	@Override
	public List<Categoria> getDescendientes(Categoria categoriaPadre) throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			String rutaLike = categoriaPadre.getRuta() + "%";
			String jpql = "SELECT c FROM Categoria c WHERE c.ruta LIKE :rutaLike AND c.id <> :idPadre";
			
			TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);
			query.setParameter("rutaLike", rutaLike);
			query.setParameter("idPadre", categoriaPadre.getId());
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			
			return query.getResultList();
			
		} catch (RuntimeException e) {
			throw new RepositorioException("Error al recuperar los descendientes de la categoría " + categoriaPadre.getId(), e);
		} finally {
			if (em.isOpen()) {
                EntityManagerHelper.closeEntityManager();
            }
		}
	}
}
