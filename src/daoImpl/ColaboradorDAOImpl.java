package daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ColaboradorDAO;
import entity.Arte;
import entity.Colaborador;

public class ColaboradorDAOImpl implements ColaboradorDAO {
	
	private static final String DBURL = "jdbc:mysql://localhost:3306/museudb";
	private static final String DBUSER = "root";
	private static final String DBPASS= "123456";
	
	public ColaboradorDAOImpl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void adicionar(Colaborador exposicao) {
		
		try {
            Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "INSERT INTO colaborador (id, nome_instituicao, cnpj, valor_doado, data_doacao, "
            		+ "descricao) VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, 0);
            stmt.setString(2, exposicao.getNomeInstituicao());
            stmt.setLong(3, exposicao.getCnpj());
            stmt.setDouble(4, exposicao.getValorDoado());
            stmt.setDate(5, java.sql.Date.valueOf(exposicao.getDataDoacao()));
            stmt.setString(6, exposicao.getDescricao());
            stmt.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public List<Colaborador> pesquisarPorNome(String nome) {
		
		List<Colaborador> encontrados = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "SELECT * FROM colaborador WHERE nome_instituicao like '%" + nome + "%'";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

            	Colaborador colaborador = new Colaborador();
            	colaborador.setId(rs.getLong("id"));
            	colaborador.setNomeInstituicao(rs.getString("nome_instituicao"));
            	colaborador.setCnpj(rs.getLong("cnpj"));
            	colaborador.setValorDoado(rs.getDouble("valor_doado"));
            	colaborador.setDataDoacao(rs.getDate("data_doacao").toLocalDate());
            	colaborador.setDescricao(rs.getString("descricao"));
                encontrados.add(colaborador);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encontrados;
		
	}

	@Override
	public void atualizar(long id, Colaborador exposicao) {
		
		try (Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS)) {
            String sql = "UPDATE colaborador SET nome_instituicao = ?, cnpj = ?, valor_doado = ?, data_doacao = ?,"
            		+ " descricao = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, exposicao.getNomeInstituicao());
            stmt.setLong(2, exposicao.getCnpj());
            stmt.setDouble(3, exposicao.getValorDoado());
            stmt.setDate(4, java.sql.Date.valueOf(exposicao.getDataDoacao()));
            stmt.setString(5, exposicao.getDescricao());
            stmt.setLong(6, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void remover(long cnpj) {
		
		try (Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS)) {
			 String sql = "DELETE FROM colaborador WHERE cnpj = ?";
	         PreparedStatement stmt = connection.prepareStatement(sql);
	         stmt.setLong(1, cnpj);
	         stmt.executeUpdate();
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
		
	}
	
}