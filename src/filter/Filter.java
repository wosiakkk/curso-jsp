package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import connection.SingleConnection;

/**
 * O filter serve para interceptar todas as requisições realizadas no sistema
 * Ele é a interface que fica entre as requisições do front e o lado do servidor
 * @author onurb
 *
 */

@WebFilter(urlPatterns = {"/*"})
public class Filter implements javax.servlet.Filter {

	private static Connection connection;	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
			connection.commit();
		}catch(Exception exception) {
			exception.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		connection = SingleConnection.getConnection();
	}
}
