package br.com.dao;

/**Classe responsável por fazer a persistência dos dados junto ao banco. Obs.: Tem o objetivo de ser genérica.
* @author Jônas
* @version 1.0
*/

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import br.com.jpautil.JPAUtil;

public class DaoGeneric<E> {
	
	/**Método para salvar uma tarefa.
	 * @author Jônas 
	 * @return void - Vazio.
	 */
	public void salvar(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.persist(entidade);

		entityTransaction.commit();
		entityManager.close();

	}

	/**Método para atualizar uma tarefa.
	 * @author Jônas
	 * @return void - Vazio.
	 */
	public E merge(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		E retorno = entityManager.merge(entidade);

		entityTransaction.commit();
		entityManager.close();

		return retorno;

	}	
	
	/**Método para deletar uma tarefa.
	 * @author Jônas 
	 * @return void - Vazio.
	 */
	public void deletePorId(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		Object id = JPAUtil.getPrimaryKey(entidade);
		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id)
				.executeUpdate();

		entityTransaction.commit();
		entityManager.close();

	}
	
	/**Método para busca as tarefas no banco, usando as opçoes de filtro da página de busca.
	 * @author Jônas 
	 * @param entidade Class<E> - classe do objeto que será buscado no banco para saber a tabela da consulta.
	 * @param opcao1 String - filtro da consulta.
	 * @param opcao2 String - filtro da consulta.
	 * @param opcao4 String - filtro da consulta.
	 * @param opcao3 String - filtro da consulta.
	 * @return List - Lista de retorno do banco.
	 */

	public List<E> getListEntityFilter(Class<E> entidade, String opcao1, String opcao2, String opcao3, String opcao4) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		String sqlFilter = "from " + entidade.getName(); //Variavel que recebera a sql para busca.

		if (opcao1 != "" || opcao2 != "" || opcao3 != "" || opcao4 != "") //Se houver opções de filtro, adiciona o where na sql.
			sqlFilter += " where ";

		if (opcao1 != "") { //Adiciona a condição do id (número) na sql. Caso não seja vazia, apenas essa condição será usado, descartando todas as outras.
			sqlFilter += "id = " + opcao1;
			

		} else {
			
			if (opcao2 != "") { //Se houver opção 2 adiciona as condições de busca dos atributos título e descrição.
				sqlFilter += "(titulo LIKE '%" + opcao2 + "%' OR descricao LIKE '%" + opcao2 + "%')";
				if (opcao3 != "" || opcao4 != "")
					sqlFilter += " AND ";

			}

			if (opcao3 != "") {	//Se houver opção 3 adiciona a condições de busca por responsável.
				sqlFilter += "responsavel = '" + opcao3 + "'";
				if (opcao4 != "")
					sqlFilter += " AND ";

			}

			if (opcao4 != "") { //Se houver opção 4 adiciona a condições de busca por situação.
				sqlFilter += "situacao = '" + opcao4 + "'";

			}
		}

		List<E> retorno = entityManager.createQuery(sqlFilter).getResultList();

		entityTransaction.commit();
		entityManager.close();

		return retorno;

	}

}
