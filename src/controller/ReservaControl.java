package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import dao.ReservaDAO;
import daoImpl.ReservaDAOImpl;
import entity.Reserva;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReservaControl {
	
	public LongProperty id = new SimpleLongProperty(0);
	public StringProperty nome = new SimpleStringProperty("");
	public LongProperty rg = new SimpleLongProperty(0);
	public LongProperty contato = new SimpleLongProperty(0);
	public IntegerProperty qtdPessoas = new SimpleIntegerProperty(0);
	public ObjectProperty data = new SimpleObjectProperty(LocalDate.now());
	public StringProperty horaInicio = new SimpleStringProperty("10:00");
    
    private ObservableList<Reserva> listaView = FXCollections.observableArrayList();
    private ReservaDAO reservaDAO = new ReservaDAOImpl();
    
    public Reserva getEntity() {
    	Reserva reserva = new Reserva();
    	reserva.setId(id.get());
    	reserva.setNome(nome.get());
    	reserva.setRg(rg.get());
    	reserva.setContato(contato.get());
    	reserva.setData((LocalDate)data.get());
    	reserva.setHoraInicio(horaInicio.get());
    	reserva.setQtdPessoas(qtdPessoas.get());
        return reserva;
    }
    
    public void setEntity(Reserva reserva) {
        id.set(reserva.getId());
        nome.set(reserva.getNome());
        rg.set(reserva.getRg());
        contato.set(reserva.getContato());
        data.set(reserva.getData());
        horaInicio.set(reserva.getHoraInicio());
        qtdPessoas.set(reserva.getQtdPessoas());
    }
    
    public void adicionar() {
    	reservaDAO.adicionar(getEntity());
        this.atualizarListaView();
    }

    public void remover() {
    	reservaDAO.remover(rg.get());
        atualizarListaView();
    }

    public void atualizar() {
    	reservaDAO.atualizar(id.get(), getEntity());
        this.pesquisar();
    }

    public void pesquisar() {
        listaView.clear();
        listaView.addAll(reservaDAO.pesquisarPorNome(nome.get()));
    }
    
    public void atualizarListaView() {
        listaView.clear();
        listaView.addAll(reservaDAO.pesquisarPorNome(""));
    }
    
    public void limparCampos(){
    	Reserva reserva = new Reserva();
    	reserva.setId(0);
    	reserva.setNome("");
    	reserva.setRg(0);
    	reserva.setContato(0);
    	reserva.setData(null);
    	reserva.setHoraInicio(null);
    	reserva.setQtdPessoas(0);
        this.atualizarListaView();
    }

    public ObservableList<Reserva> getListaView() {
        return listaView;
    }
	
}