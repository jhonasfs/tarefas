package br.com.jonas.tarefas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;

import br.com.dao.DaoGeneric;
import br.com.entidades.Tarefa;

@SessionScoped
@ManagedBean(name = "tarefaBean")

public class TarefaBean {
	private Tarefa tarefa = new Tarefa();
	private DaoGeneric<Tarefa> daoGeneric = new DaoGeneric<Tarefa>();
	private List<Tarefa> tarefas = new ArrayList<Tarefa>();
	
	private String opcao1 = "", opcao2 = "", opcao3 = "", opcao4 = "";
	
	public String salvar() {
		if (tarefa.getId() == null) {
			tarefa.setSituacao("Em andamento");
		}
		daoGeneric.salvar(tarefa);
		novo();
		return "";
	}
	
	public String atualizar() {
		tarefa = daoGeneric.merge(tarefa);		
		//carregarTarefas();		
		return "busca";
	}
	
	public String concluir(Tarefa t) {
		t.setSituacao("Concluída");
		tarefa = t;
		atualizar();
		return "";
	}
	
	public String novo() {
		tarefa = new Tarefa();
		return "";
	}
	
	public String remove(Tarefa t) {
		daoGeneric.deletePorId(t);
		novo();
		carregarTarefas();
		return "";
	}
	
	public String editar(Tarefa t) {		
		tarefa = t;
		return "editar";
	}
	
	
	public void carregarTarefas() {
		tarefas = daoGeneric.getListEntity(Tarefa.class);
	}
	
	public void buscarTarefas() {
		tarefas = daoGeneric.getListEntity(Tarefa.class);
	}
	
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
