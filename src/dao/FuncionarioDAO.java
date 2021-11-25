package dao;

import java.util.List;

import entity.Funcionario;

public interface FuncionarioDAO {
	
	void adicionar (Funcionario funcionario);
	List<Funcionario> pesquisarPorNome(String nome);
    void atualizar(long id, Funcionario funcionario);
    void remover(long cpf);
    String verificarAcesso(String cpf, String senha);
	
}