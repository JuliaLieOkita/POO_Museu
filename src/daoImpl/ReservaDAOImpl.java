package daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ReservaDAO;
import entity.Reserva;

public class ReservaDAOImpl implements ReservaDAO {
	
	private static final String DBURL = "jdbc:mysql://localhost:3306/museudb";
	private static final String DBUSER = "root";
	private static final String DBPASS= "123456";
	
	public ReservaDAOImpl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void adicionar(Reserva reserva) {
		
		try {
            Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "INSERT INTO reserva (id, nome, rg, contato, qtd_pessoas, data, hora_inicio)  " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, 0);
            stmt.setString(2, reserva.getNome());
            stmt.setLong(3, reserva.getRg());
            stmt.setLong(4, reserva.getContato());
            stmt.setLong(5, reserva.getQtdPessoas());
            stmt.setDate(6, java.sql.Date.valueOf(reserva.getData()));
            stmt.setString(7, reserva.getHoraInicio());
            stmt.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public List<Reserva> pesquisarPorNome(String nome) {

		List<Reserva> encontrados = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "SELECT * FROM reserva WHERE nome like '%" + nome + "%'";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

                Reserva reserva = new Reserva();
                reserva.setId(rs.getLong("id"));
                reserva.setNome(rs.getString("nome"));
                reserva.setRg(rs.getLong("rg"));
                reserva.setContato(rs.getLong("contato"));
                reserva.setQtdPessoas(rs.getInt("qtd_pessoas"));
                reserva.setData(rs.getDate("data").toLocalDate());
                reserva.setHoraInicio(rs.getString("hora_inicio"));
                encontrados.add(reserva);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encontrados;
		
	}

	@Override
	public void atualizar(long id, Reserva reserva) {
		
		try (Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS)) {
            String sql = "UPDATE reserva SET nome = ?, rg = ?, contato = ?, qtd_pessoas = ?, data = ?, "
            		+ "hora_inicio = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, reserva.getNome());
            stmt.setLong(2, reserva.getRg());
            stmt.setLong(3, reserva.getContato());
            stmt.setInt(4, reserva.getQtdPessoas());
            stmt.setDate(5, java.sql.Date.valueOf(reserva.getData()));
            stmt.setString(6, reserva.getHoraInicio());
            stmt.setLong(7, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void remover(long rg) {
		
		try (Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS)) {
			 String sql = "DELETE FROM reserva WHERE rg = ?";
	         PreparedStatement stmt = connection.prepareStatement(sql);
	         stmt.setLong(1, rg);
	         stmt.executeUpdate();
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
		
	}
	
}