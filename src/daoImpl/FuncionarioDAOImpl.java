package daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.FuncionarioDAO;
import entity.Arte;
import entity.Funcionario;
import javafx.beans.property.StringPropertyBase;

public class FuncionarioDAOImpl implements FuncionarioDAO {
	
	private static final String DBURL = "jdbc:mysql://localhost:3306/museudb";
	private static final String DBUSER = "root";
	private static final String DBPASS= "123456";
	
	public FuncionarioDAOImpl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void adicionar(Funcionario funcionario) {
		
		try {
            Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "INSERT INTO funcionario (id, nome, cpf, senha, contato, email, cargo, turno)  " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, 0);
            stmt.setString(2, funcionario.getNome());
            stmt.setLong(3, funcionario.getCpf());
            stmt.setString(4, funcionario.getSenha());
            stmt.setLong(5, funcionario.getContato());
            stmt.setString(6, funcionario.getEmail());
            stmt.setString(7, funcionario.getCargo());
            stmt.setString(8, funcionario.getTurno());
            stmt.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public List<Funcionario> pesquisarPorNome(String nome) {
		
		List<Funcionario> encontrados = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "SELECT * FROM funcionario WHERE nome like '%" + nome + "%'";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {            	
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getLong("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setCpf(rs.getLong("cpf"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setContato(rs.getLong("contato"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setTurno(rs.getString("turno"));
                encontrados.add(funcionario);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encontrados;
		
	}

	@Override
	public void atualizar(long id, Funcionario funcionario) {
		
		try (Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS)) {
            String sql = "UPDATE funcionario SET nome = ?, cpf = ?, senha = ?, contato = ?, email = ?, cargo = ?, "
            		+ "turno = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setLong(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getSenha());
            stmt.setLong(4, funcionario.getContato());
            stmt.setString(5, funcionario.getEmail());
            stmt.setString(6, funcionario.getCargo());
            stmt.setString(7, funcionario.getTurno());
            stmt.setLong(8, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void remover(long cpf) {	
		
		try (Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS)) {
			 String sql = "DELETE FROM funcionario WHERE cpf = ?";
	         PreparedStatement stmt = connection.prepareStatement(sql);
	         stmt.setLong(1, cpf);
	         stmt.executeUpdate();
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
		
	}

	@Override
	public String verificarAcesso(String email, String senha) {
		
        try {
        	
            Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "SELECT email, senha, cargo FROM funcionario WHERE email like '%" + email + "%' AND senha like '%" + senha + "%'";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {   
            	if (rs.getString("cargo").equals("Administrador")) {
            		return "Adm";
            	} else if (rs.getString("cargo") != "Adminstrador") {
            		return "Negado";
            	}
            }
            connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		return "Incorreto";
		
	}
	
}