package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.BeanTelefone;
import dao.DaoTelefones;
import dao.DaoUsuario;

@WebServlet("/salvarTelefones")
public class Telefones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUsuario daoUsuario = new DaoUsuario();
	private DaoTelefones daoTelefones = new DaoTelefones();

	public Telefones() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao.equalsIgnoreCase("addFone")) {

			String user = request.getParameter("user");

			BeanCursoJsp usuario = daoUsuario.consultar(Long.parseLong(user));

			request.getSession().setAttribute("userEscolhido", usuario);

			RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
			request.setAttribute("telefones", daoTelefones.listar(usuario.getId()));

			view.forward(request, response);
		} else if (acao.equalsIgnoreCase("deleteFone")) {
			String foneId = request.getParameter("foneId");
			daoTelefones.delete(Long.parseLong(foneId));

			BeanCursoJsp userSessao = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");

			RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
			request.setAttribute("telefones", daoTelefones.listar(userSessao.getId()));
			request.setAttribute("msg", "Telefone deletado com sucesso.");
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");
		String numero = request.getParameter("numero");
		String tipo = request.getParameter("tipo");

		String acao = request.getParameter("acao");

		if (acao == null || (acao != null && !acao.equalsIgnoreCase("voltar"))) {

			if (numero == null || (numero != null && numero.isEmpty())) {
				RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("telefones", daoTelefones.listar(beanCursoJsp.getId()));
				request.setAttribute("msg", "Informe o número do telefone");
				view.forward(request, response);

			} else {

				BeanTelefone telefone = new BeanTelefone();
				telefone.setNumero(numero);
				telefone.setTipo(tipo);
				telefone.setUsuario(beanCursoJsp.getId());

				daoTelefones.salvar(telefone);

				request.getSession().setAttribute("userEscolhido", beanCursoJsp);
				request.setAttribute("userEscolhido", beanCursoJsp);

				RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("telefones", daoTelefones.listar(beanCursoJsp.getId()));
				request.setAttribute("msg", "Telefone salvo com sucesso.");
				view.forward(request, response);
			}

		} else {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		}
	}

}
