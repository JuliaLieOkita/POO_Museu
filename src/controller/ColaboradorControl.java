package controller;

import java.time.LocalDate;

import dao.ColaboradorDAO;
import daoImpl.ColaboradorDAOImpl;
import entity.Colaborador;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ColaboradorControl {
	
	public LongProperty id = new SimpleLongProperty(0);
    public StringProperty nomeInstituicao = new SimpleStringProperty("");
    public LongProperty cnpj = new SimpleLongProperty(0);
    public DoubleProperty valorDoado = new SimpleDoubleProperty(0);
    public ObjectProperty dataDoacao = new SimpleObjectProperty(LocalDate.now());
    public StringProperty descricao = new SimpleStringProperty("");
    
    private ObservableList<Colaborador> listaView = FXCollections.observableArrayList();
    private ColaboradorDAO exposicaoDAO = new ColaboradorDAOImpl();
    
    public Colaborador getEntity() {
    	Colaborador exposicao = new Colaborador();
    	exposicao.setId(id.get());
    	exposicao.setNomeInstituicao(nomeInstituicao.get());
    	exposicao.setCnpj(cnpj.get());
    	exposicao.setValorDoado(valorDoado.get());
    	exposicao.setDataDoacao((LocalDate) dataDoacao.get());
    	exposicao.setDescricao(descricao.get());
        return exposicao;
    }
    
    public void setEntity(Colaborador colaborador) {
        id.set(colaborador.getId());
        nomeInstituicao.set(colaborador.getNomeInstituicao());
        cnpj.set(colaborador.getCnpj());
        valorDoado.set(colaborador.getValorDoado());
        dataDoacao.set(colaborador.getDataDoacao());
        descricao.set(colaborador.getDescricao());
    }
    
    public void adicionar() {
    	exposicaoDAO.adicionar(getEntity());
        this.atualizarListaView();
    }

    public void remover() {
    	exposicaoDAO.remover(cnpj.get());
        atualizarListaView();
    }

    public void atualizar() {
    	exposicaoDAO.atualizar(id.get(), getEntity());
        this.pesquisar();
    }

    public void pesquisar() {
        listaView.clear();
        listaView.addAll(exposicaoDAO.pesquisarPorNome(nomeInstituicao.get()));
    }
    
    public void atualizarListaView() {
        listaView.clear();
        listaView.addAll(exposicaoDAO.pesquisarPorNome(""));
    }
    
    public void limparCampos(){
        Colaborador exposicao = getEntity();
        exposicao.setId(0);
    	exposicao.setNomeInstituicao("");
    	exposicao.setCnpj(0);
    	exposicao.setValorDoado(0.0);
    	exposicao.setDataDoacao(null);
    	exposicao.setDescricao("");
        this.atualizarListaView();
    }

    public ObservableList<Colaborador> getListaView() {
        return listaView;
    }
	
}