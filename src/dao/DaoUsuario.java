package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCursoJsp;
import connection.SingleConnection;

public class DaoUsuario {

	private Connection connection;

	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanCursoJsp usuario) {
		String sql = "insert into usuarios (login,senha,nome) values (?,?,?)";
		PreparedStatement insert;
		try {
			insert = connection.prepareStatement(sql);
			insert.setString(1, usuario.getLogin());
			insert.setString(2, usuario.getSenha());
			insert.setString(3, usuario.getNome());
			insert.execute();
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

	public List<BeanCursoJsp> listar() {
		String sql = "select * from usuarios";
		List<BeanCursoJsp> lista = new ArrayList<BeanCursoJsp>();
		try {
			PreparedStatement buscar = connection.prepareStatement(sql);
			ResultSet rs = buscar.executeQuery();		
			while (rs.next()) {
				BeanCursoJsp usuario = new BeanCursoJsp();
				usuario.setId(rs.getLong("id"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setNome(rs.getString("nome"));
				lista.add(usuario);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public void delete(Long id) {
		
		String sql = "delete from usuarios where id= ?";
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
	
	public BeanCursoJsp consultar(Long id) {
		
		String sql = "select * from usuarios where id = ?";
		try {
			PreparedStatement buscar = connection.prepareStatement(sql);
			buscar.setLong(1, id);
			ResultSet rs = buscar.executeQuery();
			if(rs.next()) {
				BeanCursoJsp usuario = new BeanCursoJsp();
				usuario.setId(rs.getLong("id"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setNome(rs.getString("nome"));
				return usuario;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return null;
	}

	public void atualizar(BeanCursoJsp usuario) {
		
		String sql = "update usuarios set login = ?, senha = ?, nome = ? where id = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			stmt.setString(3, usuario.getNome());
			stmt.setLong(4, usuario.getId());
			stmt.executeUpdate();
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
