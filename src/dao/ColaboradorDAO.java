package dao;

import java.util.List;

import entity.Colaborador;

public interface ColaboradorDAO {
	
	void adicionar (Colaborador exposicao);
	List<Colaborador> pesquisarPorNome(String nome);
    void atualizar(long id, Colaborador exposicao);
    void remover(long cnpj);
	
}