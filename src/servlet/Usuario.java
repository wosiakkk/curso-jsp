package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

/**
 * Servlet implementation class Usuario
 */
@WebServlet("/salvarUsuario")
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DaoUsuario daoUsuario = new DaoUsuario();  
    
    public Usuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String acao = request.getParameter("acao");
		String user = request.getParameter("user");
		
		if(acao.equalsIgnoreCase("delete")) {
			daoUsuario.delete(Long.parseLong(user));
			RequestDispatcher view  = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		}else if(acao.equalsIgnoreCase("editar")) {
			BeanCursoJsp beanCursoJsp = daoUsuario.consultar(Long.parseLong(user));
			RequestDispatcher view  = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("user", beanCursoJsp);
			view.forward(request, response);
		}else if(acao.equalsIgnoreCase("listarTodos")) {
			RequestDispatcher view  = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String login = request.getParameter("login");
		String senha  = request.getParameter("senha");
		String nome  = request.getParameter("nome");
		
		BeanCursoJsp usuario  = new BeanCursoJsp();
		usuario.setId(!id.isEmpty()? Long.parseLong(id) : 0);// o id tem valor? se sim faz o setId se não faz o setID atribuindo o valor 0
		usuario.setLogin(login);
		usuario.setSenha(senha);
		usuario.setNome(nome);
		
		if(id == null || id.isEmpty()) {
			daoUsuario.salvar(usuario);
		}else {
			daoUsuario.atualizar(usuario);
		}
		
		RequestDispatcher view  = request.getRequestDispatcher("/cadastroUsuario.jsp");
		request.setAttribute("usuarios", daoUsuario.listar());
		view.forward(request, response);
		
	}

}
