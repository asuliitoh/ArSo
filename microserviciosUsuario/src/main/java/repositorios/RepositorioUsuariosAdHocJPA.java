package repositorios;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import utils.EntityManagerHelper;


public class RepositorioUsuariosAdHocJPA extends RepositorioUsuariosJPA implements RepositorioUsuariosAdHoc  {

	@Override
	public boolean checkEmail(String correo) throws RepositorioException {
		
		try {
			EntityManager em = EntityManagerHelper.getEntityManager();
			String queryString = "SELECT COUNT(u)"
					+ " FROM Usuario u "
					+ " WHERE u.correo = :correo";
			
			TypedQuery<Long> query = em.createQuery(queryString, Long.class);
			query.setParameter("correo", correo);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			Long resultado = query.getSingleResult();
			return resultado.equals(0L);
			
		}catch(RuntimeException e) {
			throw new RepositorioException("Error buscando usuarios por correo y teléfono", e);
		
		}finally {
			EntityManagerHelper.closeEntityManager();
		}
		
	}

}
