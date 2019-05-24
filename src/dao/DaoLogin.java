package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;

public class DaoLogin {

	private Connection connection;
	
	public DaoLogin() {
		connection = SingleConnection.getConnection();
	}
	
	public boolean vaidarLogin(String login, String senha) throws Exception{
		
		String sql = "SELECT * FROM usuarios where login = ? and senha = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, login);
		stmt.setString(2, senha);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			return true;//VALIDOU USUÁRIO
		}else {
			return false;//NÃO VALIDOU USUÁRIO
		}
		
	}

}
