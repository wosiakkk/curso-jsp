package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanProduto;
import beans.BeanTelefone;
import connection.SingleConnection;

public class DaoTelefones {
	private Connection connection;

	public DaoTelefones() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanTelefone telefone) {
		String sql = "insert into telefone (numero,tipo,usuario) values (?,?,?)";
		PreparedStatement insert;
		try {
			insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
			insert.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<BeanTelefone> listar(Long id) {
		String sql = "select * from telefone where usuario = ?";
		List<BeanTelefone> lista = new ArrayList<BeanTelefone>();
		try {
			PreparedStatement buscar = connection.prepareStatement(sql);
			buscar.setLong(1, id);
			ResultSet rs = buscar.executeQuery();
			while (rs.next()) {
				BeanTelefone telefone = new BeanTelefone();
				telefone.setId(rs.getLong("id"));
				telefone.setNumero(rs.getString("numero"));
				telefone.setTipo(rs.getString("tipo"));
				telefone.setUsuario(rs.getLong("usuario"));
				
				lista.add(telefone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void delete(Long id) {

		String sql = "delete from telefone where id= ?";
		try {
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.setLong(1, id);
			delete.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
