package entity;

import java.time.LocalDate;

public class Colaborador {
	
	private long id = 0;
	private String nomeInstituicao = "";
	private long cnpj = 0;
	private Double valorDoado = 0.0;
	private LocalDate dataDoacao = LocalDate.now();
	private String descricao = "";
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNomeInstituicao() {
		return nomeInstituicao;
	}
	
	public void setNomeInstituicao(String nomeInstituicao) {
		this.nomeInstituicao = nomeInstituicao;
	}
	
	public long getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(long cnpj) {
		this.cnpj = cnpj;
	}
	
	public Double getValorDoado() {
		return valorDoado;
	}
	
	public void setValorDoado(Double valorDoado) {
		this.valorDoado = valorDoado;
	}
	
	public LocalDate getDataDoacao() {
		return dataDoacao;
	}
	
	public void setDataDoacao(LocalDate dataDoacao) {
		this.dataDoacao = dataDoacao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}