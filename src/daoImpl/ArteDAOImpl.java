package daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ArteDAO;
import entity.Arte;

public class ArteDAOImpl implements ArteDAO {
	
	private static final String DBURL = "jdbc:mysql://localhost:3306/museudb";
	private static final String DBUSER = "root";
	private static final String DBPASS= "123456";
	
	public ArteDAOImpl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void adicionar(Arte arte) {
		
		try {
            Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "INSERT INTO arte (id, nome_obra, nome_artista, data_criacao, descricao)  " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, 0);
            stmt.setString(2, arte.getNomeObra());
            stmt.setString(3, arte.getNomeArtista());
            stmt.setDate(4, java.sql.Date.valueOf(arte.getDataCriacao()));
            stmt.setString(5, arte.getDescricao());
            stmt.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public List<Arte> pesquisarPorNome(String nome) {
		
		List<Arte> encontrados = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "SELECT * FROM arte WHERE nome_obra like '%" + nome + "%'";

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

                Arte arte = new Arte();
                arte.setId(rs.getLong("id"));
                arte.setNomeObra(rs.getString("nome_obra"));
                arte.setNomeArtista(rs.getString("nome_artista"));
                arte.setDataCriacao(rs.getDate("data_criacao").toLocalDate());
                arte.setDescricao(rs.getString("descricao"));
                encontrados.add(arte);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encontrados;
		
	}

	@Override
	public void atualizar(long id, Arte arte) {
		
		try (Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS)) {
            String sql = "UPDATE arte SET nome_obra = ?, nome_artista = ?, data_criacao = ?, descricao = ? "
            		+ "WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, arte.getNomeObra());
            stmt.setString(2, arte.getNomeArtista());
            stmt.setDate(3, java.sql.Date.valueOf(arte.getDataCriacao()));
            stmt.setString(4, arte.getDescricao());
            stmt.setLong(5, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void remover(String nomeObra) {
		
		 try (Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS)) {
			 String sql = "DELETE FROM arte WHERE nome_obra = ?";
	         PreparedStatement stmt = connection.prepareStatement(sql);
	         stmt.setString(1, nomeObra);
	         stmt.executeUpdate();
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
		
	}
	
}