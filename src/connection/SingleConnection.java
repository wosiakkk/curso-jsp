package connection;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.management.RuntimeErrorException;

/**
 * Classe responsável por realizar a conexão com o Banco de Dados.
 * Padrão SingletonConnection, uma instância de conexão apra todos e controla-se somente as sessões abertas
 * @author onurb
 *
 */
public class SingleConnection {

		private static String url ="jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
		private static String password ="admin";
		private static String user ="postgres";
		private static Connection connection = null;
		
		//chamada estática do método conectar, para que a partir do momento que chamar essa classe garante que o método conectar seja chamado
		static {
			conectar();
		}
		
		//construtor
		public SingleConnection() {
			conectar();
		}
		
		//método de conexão
		public static void conectar() {
			try {			
				//verificar se já há uma conexão
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
		
		//método público para retornar uma conexão
		public static Connection getConnection() {
			return connection;
		}
}
