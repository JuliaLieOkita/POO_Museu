package dao;

import java.util.List;

import entity.Arte;

public interface ArteDAO {
	
	void adicionar (Arte arte);
	List<Arte> pesquisarPorNome(String nome);
    void atualizar(long id, Arte arte);
    void remover(String nomeObra);
	
}