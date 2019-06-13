package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCursoJsp;
import beans.BeanProduto;
import connection.SingleConnection;

public class DaoProduto {
	private Connection connection;

	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanProduto produto) {
		String sql = "insert into produtos (nome,quantidade,valor) values (?,?,?)";
		PreparedStatement insert;
		try {
			insert = connection.prepareStatement(sql);
			insert.setString(1, produto.getNome());
			insert.setFloat(2, produto.getQuantidade());
			insert.setFloat(3, produto.getValor());
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

	public List<BeanProduto> listar() {
		String sql = "select * from produtos";
		List<BeanProduto> lista = new ArrayList<BeanProduto>();
		try {
			PreparedStatement buscar = connection.prepareStatement(sql);
			ResultSet rs = buscar.executeQuery();
			while (rs.next()) {
				BeanProduto produto = new BeanProduto();
				produto.setId(rs.getLong("id"));
				produto.setNome(rs.getString("nome"));
				produto.setQuantidade(rs.getFloat("quantidade"));
				produto.setValor(rs.getFloat("valor"));
				lista.add(produto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void delete(Long id) {

		String sql = "delete from produtos where id= ?";
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

	public BeanProduto consultar(Long id) {

		String sql = "select * from produtos where id = ?";
		try {
			PreparedStatement buscar = connection.prepareStatement(sql);
			buscar.setLong(1, id);
			ResultSet rs = buscar.executeQuery();
			if (rs.next()) {
				BeanProduto produto = new BeanProduto();
				produto.setId(rs.getLong("id"));
				produto.setNome(rs.getString("nome"));
				produto.setQuantidade(rs.getFloat("quantidade"));
				produto.setValor(rs.getFloat("valor"));
				return produto;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void atualizar(BeanProduto produto) {

		String sql = "update produtos set nome = ?, quantidade = ?, valor = ? where id = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setFloat(2, produto.getQuantidade());
			stmt.setFloat(3, produto.getValor());
			stmt.setLong(4, produto.getId());
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

	public boolean validarNomeProduto(String nome) {

		String sql = "select count(1) as qtd from produtos where nome = ?"; // se existir o login retoirna 1 se não 0
		try {
			PreparedStatement buscar = connection.prepareStatement(sql);
			buscar.setString(1, nome);
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

	public boolean validarNomeProdutoUpdate(String nome, Long id) {

		String sql = "select count(1) as qtd from produtos where nome = ? and  id <> ?"; // se existir o login retoirna
																							// 1 se não 0
		try {
			PreparedStatement buscar = connection.prepareStatement(sql);
			buscar.setString(1, nome);
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

}
