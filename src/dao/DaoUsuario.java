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
				+ "cep,rua,bairro,cidade,estado,ibge, fotobase64, contenttype, curriculobase64, contenttypecurriculo, fotobase64miniatura, ativo, sexo, perfil) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			insert.setBoolean(15, usuario.isAtivo());
			insert.setString(16, usuario.getSexo());
			insert.setString(17, usuario.getPerfil());
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
	
	public List<BeanCursoJsp> listar (String descricaoConsulta){
		String sql = "select * from usuarios where login <> 'admin' and nome like '%"+descricaoConsulta+"%'";
		return consultarUsuarios(sql);
	}

	public List<BeanCursoJsp> listar() {
		String sql = "select * from usuarios where login <> 'admin' order by id asc";
		return consultarUsuarios(sql);
	}

	//esse método surgiu de uma refatoração pois o mesmo código iria se repetir nos 2 métodos listar, somente com o SQL diferente
	private List<BeanCursoJsp> consultarUsuarios(String sql) {
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
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setSexo(rs.getString("sexo"));
				usuario.setPerfil(rs.getString("perfil"));
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
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setSexo(rs.getString("sexo"));
				usuario.setPerfil(rs.getString("perfil"));
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

		String sql = "select count(1) as qtd from usuarios where login = ?"; // se existir o login retoirna 1 se não 0
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
																							// 1 se não 0
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
		//verificando no atualizar se arquivos de imagem ou pdf foram inseridos para serem atualizados e montar a sql de acordo com string builder
		StringBuilder sql = new StringBuilder();
		sql.append("update usuarios set login = ?, senha = ?, nome = ?, fone = ?, cep = ? ");
		sql.append(", rua = ?, bairro = ?, cidade = ?, estado = ?, ibge = ?, ativo = ?, sexo = ?, perfil = ? ");
		
		if(usuario.isAtualizarImagem()) {
			sql.append(" , fotobase64= ?, contenttype = ? ");
		}
		
		if(usuario.isAtualizarPdf()) {
			sql.append(", curriculobase64 = ?, contenttypecurriculo = ? ");
		}
		
		if(usuario.isAtualizarImagem()) {
			sql.append(", fotobase64miniatura = ? ");
		}
		
		sql.append("  where id = ?");
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql.toString());
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
			stmt.setBoolean(11, usuario.isAtivo());
			stmt.setString(12, usuario.getSexo());
			stmt.setString(13, usuario.getPerfil());
			
			//lógica para montar o pstatement de acordo com o sql montado
			
			if(!usuario.isAtualizarImagem() && !usuario.isAtualizarPdf()) {
				//não tem nem imagem e nem pdf no atualizar
				stmt.setLong(14, usuario.getId());	
			}else if (usuario.isAtualizarImagem() && !usuario.isAtualizarPdf()) {
				//tem imagem mas não tem pdf
				stmt.setString(14, usuario.getFotoBase64());
				stmt.setString(15, usuario.getContentType());
				stmt.setString(16, usuario.getFotoBase64Miniatura());
				stmt.setLong(17, usuario.getId());
			}else if(usuario.isAtualizarPdf() && !usuario.isAtualizarImagem()) {
				//tem pdf mas não tem a imagem
				stmt.setString(14, usuario.getCurriculoBase64());
				stmt.setString(15, usuario.getContentTypeCurriculo());
				stmt.setLong(16, usuario.getId());
			}else if(usuario.isAtualizarImagem() && usuario.isAtualizarPdf()) {
				//tem ambos
				stmt.setString(14, usuario.getFotoBase64());
				stmt.setString(15, usuario.getContentType());
				stmt.setString(16, usuario.getCurriculoBase64());
				stmt.setString(17, usuario.getContentTypeCurriculo());
				stmt.setString(18, usuario.getFotoBase64Miniatura());
				stmt.setLong(19, usuario.getId());
			}
			//executando o update
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
