package controller;

import java.time.LocalDate;

import dao.ArteDAO;
import daoImpl.ArteDAOImpl;
import entity.Arte;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ArteControl {
	
	public LongProperty id = new SimpleLongProperty(0);
	public StringProperty nomeObra = new SimpleStringProperty("");
	public StringProperty nomeArtista = new SimpleStringProperty("");
	public ObjectProperty dataCriacao = new SimpleObjectProperty(LocalDate.now());
	public StringProperty descricao = new SimpleStringProperty("");
    
    private ObservableList<Arte> listaView = FXCollections.observableArrayList();
    private ArteDAO arteDAO = new ArteDAOImpl();
    
    public Arte getEntity() {
    	Arte arte = new Arte();
    	arte.setId(id.get());
    	arte.setNomeObra(nomeObra.get());
    	arte.setNomeArtista(nomeArtista.get());
    	arte.setDataCriacao((LocalDate)dataCriacao.get());
    	arte.setDescricao(descricao.get());
        return arte;
    }
    
    public void setEntity(Arte arte) {
        id.set(arte.getId());
        nomeObra.set(arte.getNomeObra());
        nomeArtista.set(arte.getNomeArtista());
        dataCriacao.set(arte.getDataCriacao());
        descricao.set(arte.getDescricao());
    }
    
    public void adicionar() {
    	arteDAO.adicionar(getEntity());
        this.atualizarListaView();
    }

    public void remover() {
    	arteDAO.remover(nomeObra.get());
        atualizarListaView();
    }

    public void atualizar() {
    	arteDAO.atualizar(id.get(), getEntity());
        this.pesquisar();
    }

    public void pesquisar() {
        listaView.clear();
        listaView.addAll(arteDAO.pesquisarPorNome(nomeObra.get()));
    }
    
    public void atualizarListaView() {
        listaView.clear();
        listaView.addAll(arteDAO.pesquisarPorNome(""));
    }
    
    public void limparCampos(){
        Arte arte = getEntity();
        arte.setId(0);
    	arte.setNomeObra("");
    	arte.setNomeArtista("");
    	arte.setDataCriacao(null);
    	arte.setDescricao("");
        this.atualizarListaView();
    }

    public ObservableList<Arte> getListaView() {
        return listaView;
    }
	
}