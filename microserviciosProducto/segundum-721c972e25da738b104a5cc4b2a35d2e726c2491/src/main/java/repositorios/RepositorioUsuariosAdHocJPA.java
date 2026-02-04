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
	public boolean checkEmailAndTelefono(String correo, String telefono) throws RepositorioException {
		
		try {
			EntityManager em = EntityManagerHelper.getEntityManager();
			String queryString = "SELECT id"
					+ "FROM Usuario u "
					+ "WHERE u.correo == :correo AND u.telefono == :telefono";
			TypedQuery<String> query = em.createNamedQuery(queryString, String.class);
			
			query.setParameter("correo", correo);
			query.setParameter("telefono", telefono);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			query.getSingleResult();
			//En caso de que getSingleResult no haya encontrado nada o haya encontrado más de un valor, lanza excepciones.
			return true;
		
		}catch(NoResultException | NonUniqueResultException e) {
			return false;
			
		}catch(RuntimeException e) {
			throw new RepositorioException("Error buscando usuarios por correo y teléfono", e);
		
		}finally {
			EntityManagerHelper.closeEntityManager();
		}
		
	}

}
