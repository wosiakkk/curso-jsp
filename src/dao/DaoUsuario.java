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
		String sql = "insert into usuarios (login,senha,nome,"
				+ "cep,rua,bairro,cidade,estado,ibge, fotobase64, contenttype, curriculobase64, contenttypecurriculo, fotobase64miniatura) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insert;
		try {
			insert = connection.prepareStatement(sql);
			insert.setString(1, usuario.getLogin());
			insert.setString(2, usuario.getSenha());
			insert.setString(3, usuario.getNome());
			insert.setString(4, usuario.getCep());
			insert.setString(5, usuario.getRua());
			insert.setString(6, usuario.getBairro());
			insert.setString(7, usuario.getCidade());
			insert.setString(8, usuario.getEstado());
			insert.setString(9, usuario.getIbge());
			insert.setString(10, usuario.getFotoBase64());
			insert.setString(11, usuario.getContentType());
			insert.setString(12, usuario.getCurriculoBase64());
			insert.setString(13, usuario.getContentTypeCurriculo());
			insert.setString(14, usuario.getFotoBase64Miniatura());
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

	public List<BeanCursoJsp> listar() {
		String sql = "select * from usuarios where login <> 'admin' order by id asc";
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
				usuario.setFotoBase64Miniatura(rs.getString("fotobase64miniatura"));
				usuario.setContentType(rs.getString("contenttype"));
				usuario.setCurriculoBase64(rs.getString("curriculobase64"));
				usuario.setContentTypeCurriculo(rs.getString("contenttypecurriculo"));
				lista.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void delete(Long id) {

		String sql = "delete from usuarios where id= ? and login <> 'admin'";
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

		String sql = "select * from usuarios where id = ? and login <> 'admin'";
		try {
			PreparedStatement buscar = connection.prepareStatement(sql);
			buscar.setLong(1, id);
			ResultSet rs = buscar.executeQuery();
			if (rs.next()) {
				BeanCursoJsp usuario = new BeanCursoJsp();
				usuario.setId(rs.getLong("id"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setNome(rs.getString("nome"));
				usuario.setFone(rs.getString("fone"));
				usuario.setCep(rs.getString("cep"));
				usuario.setRua(rs.getString("rua"));
				usuario.setBairro(rs.getString("bairro"));
				usuario.setCidade(rs.getString("cidade"));
				usuario.setEstado(rs.getString("estado"));
				usuario.setIbge(rs.getString("ibge"));
				usuario.setFotoBase64(rs.getString("fotobase64"));
				usuario.setFotoBase64Miniatura(rs.getString("fotobase64miniatura"));
				usuario.setContentType(rs.getString("contenttype"));
				usuario.setCurriculoBase64(rs.getString("curriculobase64"));
				usuario.setContentTypeCurriculo(rs.getString("contenttypecurriculo"));
				return usuario;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean validarLogin(String login) {

		String sql = "select count(1) as qtd from usuarios where login = ?"; // se existir o login retoirna 1 se n�o 0
		try {
			PreparedStatement buscar = connection.prepareStatement(sql);
			buscar.setString(1, login);
			ResultSet rs = buscar.executeQuery();
			if (rs.next()) {
				int quantidade = rs.getInt("qtd");
				if (quantidade > 0)
					return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean validarLoginUpdate(String login, Long id) {

		String sql = "select count(1) as qtd from usuarios where login = ? and  id <> ?"; // se existir o login retoirna
																							// 1 se n�o 0
		try {
			PreparedStatement buscar = connection.prepareStatement(sql);
			buscar.setString(1, login);
			buscar.setLong(2, id);
			ResultSet rs = buscar.executeQuery();
			if (rs.next()) {
				int quantidade = rs.getInt("qtd");
				if (quantidade > 0)
					return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public void atualizar(BeanCursoJsp usuario) {

		String sql = "update usuarios set login = ?, senha = ?, nome = ?, fone = ?, cep = ?"
				+ ", rua = ?, bairro = ?, cidade = ?, estado = ?, ibge = ? , fotobase64= ?,"
				+ " contenttype = ?, curriculobase64 = ?, contenttypecurriculo = ?, fotobase64miniatura = ?  where id = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			stmt.setString(3, usuario.getNome());
			stmt.setString(4, usuario.getFone());
			stmt.setString(5, usuario.getCep());
			stmt.setString(6, usuario.getRua());
			stmt.setString(7, usuario.getBairro());
			stmt.setString(8, usuario.getCidade());
			stmt.setString(9, usuario.getEstado());
			stmt.setString(10, usuario.getIbge());
			stmt.setString(11, usuario.getFotoBase64());
			stmt.setString(12, usuario.getContentType());
			stmt.setString(13, usuario.getCurriculoBase64());
			stmt.setString(14, usuario.getContentTypeCurriculo());
			stmt.setString(15, usuario.getFotoBase64Miniatura());
			stmt.setLong(16, usuario.getId());

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
