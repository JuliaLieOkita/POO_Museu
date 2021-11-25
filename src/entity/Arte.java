package entity;

import java.time.LocalDate;

public class Arte {
	
	private long id = 0;
	private String nomeObra = "";
	private String nomeArtista = "";
	private LocalDate dataCriacao = LocalDate.now();
	private String descricao = "";
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNomeObra() {
		return nomeObra;
	}
	
	public void setNomeObra(String nomeObra) {
		this.nomeObra = nomeObra;
	}
	
	public String getNomeArtista() {
		return nomeArtista;
	}
	
	public void setNomeArtista(String nomeArtista) {
		this.nomeArtista = nomeArtista;
	}
	
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}