package controller;

import dao.FuncionarioDAO;
import daoImpl.FuncionarioDAOImpl;
import entity.Arte;
import entity.Funcionario;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FuncionarioControl {
	
	public LongProperty id = new SimpleLongProperty(0);
	public StringProperty nome = new SimpleStringProperty("");
	public LongProperty cpf = new SimpleLongProperty(0);
	public StringProperty senha = new SimpleStringProperty("");
	public LongProperty contato = new SimpleLongProperty(0);
	public StringProperty email = new SimpleStringProperty("");
	public StringProperty cargo = new SimpleStringProperty("");
	public StringProperty turno = new SimpleStringProperty("");
    
    private ObservableList<Funcionario> listaView = FXCollections.observableArrayList();
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAOImpl();
    
    public Funcionario getEntity() {
    	Funcionario funcionario = new Funcionario();
    	funcionario.setId(id.get());
    	funcionario.setNome(nome.get());
    	funcionario.setCpf(cpf.get());
    	funcionario.setSenha(senha.get());
    	funcionario.setContato(contato.get());
    	funcionario.setEmail(email.get());
    	funcionario.setCargo(cargo.get());
    	funcionario.setTurno(turno.get());
        return funcionario;
    }
    
    public void setEntity(Funcionario funcionario) {
    	id.set(funcionario.getId());
    	nome.set(funcionario.getNome());
    	cpf.set(funcionario.getCpf());
    	senha.set(funcionario.getSenha());
    	contato.set(funcionario.getContato());
    	email.set(funcionario.getEmail());
    	cargo.set(funcionario.getCargo());
    	turno.set(funcionario.getTurno());
    }
    
    public void adicionar() {
    	funcionarioDAO.adicionar(getEntity());
        this.atualizarListaView();
    }

    public void remover() {
    	funcionarioDAO.remover(cpf.get());
        atualizarListaView();
    }

    public void atualizar() {
    	funcionarioDAO.atualizar(id.get(), getEntity());
        this.pesquisar();
    }

    public void pesquisar() {
        listaView.clear();
        listaView.addAll(funcionarioDAO.pesquisarPorNome(nome.get()));
    }
    
    public String verificarAcesso(String cpf, String senha) {
    	return funcionarioDAO.verificarAcesso(cpf, senha);
    }
    
    public void atualizarListaView() {
        listaView.clear();
        listaView.addAll(funcionarioDAO.pesquisarPorNome(""));
    }
    
    public void limparCampos(){
        Funcionario funcionario = getEntity();
        funcionario.setId(0);
        funcionario.setNome("");
        funcionario.setCpf(0);
        funcionario.setSenha(null);
        funcionario.setContato(0);
        funcionario.setEmail(null);
        funcionario.setCargo(null);
        funcionario.setTurno(null);
        this.atualizarListaView();
    }

    public ObservableList<Funcionario> getListaView() {
        return listaView;
    }
    
    
	
}