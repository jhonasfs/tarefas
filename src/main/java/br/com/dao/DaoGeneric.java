package br.com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.jpautil.JPAUtil;

public class DaoGeneric<E> {

	public void salvar(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.persist(entidade);

		entityTransaction.commit();
		entityManager.close();

	}

	public E merge(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		E retorno = entityManager.merge(entidade);

		entityTransaction.commit();
		entityManager.close();

		return retorno;

	}

	public void delete(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.remove(entidade);

		entityTransaction.commit();
		entityManager.close();

	}

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

	public List<E> getListEntity(Class<E> entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		List<E> retorno = entityManager.createQuery("from " + entidade.getName()).getResultList();

		entityTransaction.commit();
		entityManager.close();

		return retorno;

	}

	public List<E> getListEntityFilter(Class<E> entidade, String opcao1, String opcao2, String opcao3, String opcao4) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		String sqlFilter = "from " + entidade.getName();

		if (opcao1 != "" || opcao2 != "" || opcao3 != "" || opcao4 != "")
			sqlFilter += " where ";

		if (opcao1 != "") {
			sqlFilter += "id = " + opcao1;
			

		} else {
			
			if (opcao2 != "") {
				sqlFilter += "(titulo LIKE '%" + opcao2 + "%' OR descricao LIKE '%" + opcao2 + "%')";
				if (opcao3 != "" || opcao4 != "")
					sqlFilter += " AND ";

			}

			if (opcao3 != "") {
				sqlFilter += "responsavel = '" + opcao3 + "'";
				if (opcao4 != "")
					sqlFilter += " AND ";

			}

			if (opcao4 != "") {
				sqlFilter += "situacao = '" + opcao4 + "'";

			}
		}

		System.out.println(sqlFilter);

		List<E> retorno = entityManager.createQuery(sqlFilter).getResultList();

		entityTransaction.commit();
		entityManager.close();

		return retorno;

	}

}
