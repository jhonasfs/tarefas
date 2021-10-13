package br.com.jpautil;

/**Classe responsável por iniciar o ambiente de persistência dos dados junto ao banco. 
* @author Jônas
* @version 1.0
*/

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory factory = null;
	
	/**Método estático para criar uma instância do EntityManagerFactory apenas uma vez.
	 * @author Jônas 
	 */
	static {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("tarefas");
		}
	}	
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	public static Object getPrimaryKey(Object entity) {
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}

}
