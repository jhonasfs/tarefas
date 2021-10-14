package br.com.jonas.tarefas;

/**Classe para gerenciar os dados que trafegam entre a interface e o DAO. 
* Responsável por inserir, excluir, alterar e buscas dados (tarefas).
* @author Jônas
* @version 1.0
*/

import java.util.ArrayList;
import java.util.List;

//import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;

import br.com.dao.DaoGeneric;
import br.com.entidades.Tarefa;

@SessionScoped
@ManagedBean(name = "tarefaBean")

public class TarefaBean {
	private Tarefa tarefa = new Tarefa();	//Objeto tarefa para ser utilizado pelo bean. 
	private DaoGeneric<Tarefa> daoGeneric = new DaoGeneric<Tarefa>(); //DAO responsável por enviar/buscar os dados no branco.
	private List<Tarefa> tarefas = new ArrayList<Tarefa>();	//Lista de taredas carregadas do banco.
	
	/*Variáveis com as opções de filtro da tela de busca.
	 * opcao1 = número; opcao2 = título/descrição; opcao3 = reponsável; opcao4 = situação.*/	 
	private String opcao1 = "", opcao2 = "", opcao3 = "", opcao4 = ""; 
	
	
	/**Método para salvar uma tarefa.
	 * @author Jônas 
	 * @return String - Vazio, pois não precisa de redirecionamento.
	 */
	public String salvar() {
		if (tarefa.getId() == null) { //Caso o id (número) seja nulo, é uma nova tarefa sendo cadastrada, então a situação é Em andamento.
			tarefa.setSituacao("Em andamento");
		}
		daoGeneric.salvar(tarefa);
		novo(); //Cria nova tarefa vazia para limpar o formulário.
		return "";
	}
	
	/**Método para atualizar uma tarefa.
	 * @author Jônas 
	 * @return String - retorna a página de busca após atualizar a tarefa.
	 */
	public String atualizar() {
		tarefa = daoGeneric.merge(tarefa);
		buscarTarefasFiltro();
		return "busca";
	}	
	
	/**Método para atualizar situação de uma tarefa para concluído.
	 * @author Jônas 
	 * @return String - Vazio, pois não precisa de redirecionamento.
	 */
	public String concluir(Tarefa t) {
		t.setSituacao("Concluída");
		tarefa = t;
		atualizar();
		buscarTarefasFiltro();
		return "";
	}	
	
	/**Método para criar uma tarefa vazia para limpar o formulário.
	 * @author Jônas
	 * @return String - Vazio, pois não precisa de redirecionamento.
	 */
	public String novo() {
		tarefa = new Tarefa();
		return "";
	}
	
	/**Método para remover uma tarefa.
	 * @author Jônas
	 * @param t Tarefa - Tarefa que será removida.
	 * @return String - Vazio, pois não precisa de redirecionamento.
	 */
	public String remove(Tarefa t) {
		daoGeneric.deletePorId(t);
		novo();
		buscarTarefasFiltro();
		return "";
	}
	
	
	/**Método para carregar uma tarefa da lista para o objeto Tareja utilizado pelo bean, assim os forms da página de carregar a ficaram carregados.
	 * @author Jônas
	 * @param t Tarefa - Tarefa que será editada.
	 * @return String - retorna a página editar onde está o formulário de edição.
	 */
	public String editar(Tarefa t) {		
		tarefa = t;
		return "editar";
	}
	
	
	
	/**Método para carregar a lista de tarefas com utilização dos filtros.
	 * @author Jônas
	 * @return void.
	 */
	public void buscarTarefasFiltro() {		
		
		tarefas = daoGeneric.getListEntityFilter(Tarefa.class, opcao1, opcao2, opcao3, opcao4);
	}
	
	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {		
		this.tarefa = tarefa;
	}

	public DaoGeneric<Tarefa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Tarefa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	
	public List<Tarefa> getTarefas(){
		return tarefas;
	}

	public String getOpcao1() {
		return opcao1;
	}

	public void setOpcao1(String opcao1) {
		this.opcao1 = opcao1;
	}

	public String getOpcao2() {
		return opcao2;
	}

	public void setOpcao2(String opcao2) {
		this.opcao2 = opcao2;
	}

	public String getOpcao3() {
		return opcao3;
	}

	public void setOpcao3(String opcao3) {
		this.opcao3 = opcao3;
	}

	public String getOpcao4() {
		return opcao4;
	}

	public void setOpcao4(String opcao4) {
		this.opcao4 = opcao4;
	}
	
	
	
	

}
