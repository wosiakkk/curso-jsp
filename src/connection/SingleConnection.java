package connection;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.management.RuntimeErrorException;

/**
 * Classe respons�vel por realizar a conex�o com o Banco de Dados.
 * Padr�o SingletonConnection, uma inst�ncia de conex�o apra todos e controla-se somente as sess�es abertas
 * @author onurb
 *
 */
public class SingleConnection {

		private static String url ="jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
		private static String password ="admin";
		private static String user ="postgres";
		private static Connection connection = null;
		
		//chamada est�tica do m�todo conectar, para que a partir do momento que chamar essa classe garante que o m�todo conectar seja chamado
		static {
			conectar();
		}
		
		//construtor
		public SingleConnection() {
			conectar();
		}
		
		//m�todo de conex�o
		public static void conectar() {
			try {			
				//verificar se j� h� uma conex�o
				if(connection == null){
					Class.forName("org.postgresql.Driver");
					connection= DriverManager.getConnection(url,user,password);
					//para definir quando realizar um commit no BD
					connection.setAutoCommit(false);
				}
			} catch (Exception e) {
				throw new RuntimeException("Erro ao conectar no Banco de Dados");
			}
		}
		
		//m�todo p�blico para retornar uma conex�o
		public static Connection getConnection() {
			return connection;
		}
}
