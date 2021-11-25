package dao;

import java.util.List;

import entity.Reserva;

public interface ReservaDAO {
	
	List<Reserva> pesquisarPorNome(String nome);
    void atualizar(long id, Reserva reserva);
	void adicionar (Reserva reserva);
    void remover(long rg);
	
}