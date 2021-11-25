package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Reserva {
	
	private long id = 0;
	private String nome = "";
	private long rg = 0;
	private long contato = 0;
	private int qtdPessoas = 0;
	private LocalDate data = LocalDate.now();
	private String horaInicio = "10:00";
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public long getRg() {
		return rg;
	}
	
	public void setRg(long rg) {
		this.rg = rg;
	}
	
	public long getContato() {
		return contato;
	}
	
	public void setContato(long contato) {
		this.contato = contato;
	}
	
	public int getQtdPessoas() {
		return qtdPessoas;
	}
	
	public void setQtdPessoas(int qtdPessoas) {
		this.qtdPessoas = qtdPessoas;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public String getHoraInicio() {
		return horaInicio;
	}
	
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	
}