package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.BeanProduto;
import dao.DaoProduto;

@WebServlet("/salvarProduto")
public class Produto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoProduto daoProduto = new DaoProduto();

	public Produto() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//minha a��o � difenrente de nula se sim pega a a��o, se n�o define ela como listarTodos
		String acao = request.getParameter("acao") != null? request.getParameter("acao") : "listarTodos" ;
		String prod = request.getParameter("prod");

		if (acao.equalsIgnoreCase("delete")) {
			daoProduto.delete(Long.parseLong(prod));
			RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
			request.setAttribute("produtos", daoProduto.listar());
			view.forward(request, response);
		} else if (acao.equalsIgnoreCase("editar")) {
			BeanProduto produto = daoProduto.consultar(Long.parseLong(prod));
			RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
			request.setAttribute("prod", produto);
			view.forward(request, response);
		} else if (acao.equalsIgnoreCase("listarTodos")) {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
			request.setAttribute("produtos", daoProduto.listar());
			view.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
			request.setAttribute("produtos", daoProduto.listar());
			view.forward(request, response);
		} else {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");

			BeanProduto produto = new BeanProduto();
			produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);// o id tem valor? se sim faz o setId se n�o faz o
																		// setID atribuindo o valor null
			produto.setNome(nome);
		
			if (!quantidade.isEmpty())
				produto.setQuantidade(Float.parseFloat(quantidade));
				
			if(!valor.isEmpty()) {
				
				String valorSemPonto = valor.replaceAll("\\.", "");
			
				produto.setValor(Float.parseFloat(valorSemPonto.replaceAll("\\,", ".")));
			
			}
			if (nome == null || nome.isEmpty()) {
				request.setAttribute("msg", "O nome do produto deve ser informado");
				request.setAttribute("prod", produto);
			} else if (quantidade == null || quantidade.isEmpty()) { 
				request.setAttribute("msg", "A quantidade do produto deve ser informada");
				request.setAttribute("prod", produto);
			} if(valor == null || valor.isEmpty()) {
				request.setAttribute("msg", "O valor do produto deve ser informado");
				request.setAttribute("prod", produto);
			} else if (id == null || id.isEmpty() && !daoProduto.validarNomeProduto(nome)) {
				request.setAttribute("msg", "Este produto j� foi cadastrado com esse nome");
				request.setAttribute("prod", produto);
			}else if (id == null || id.isEmpty() && daoProduto.validarNomeProduto(nome)) {
				daoProduto.salvar(produto);
			} else if (id != null && !id.isEmpty()) {
				if (!daoProduto.validarNomeProdutoUpdate(nome, Long.parseLong(id))) {
					request.setAttribute("msg", "O nome do produto j� est� cadastrado");
					request.setAttribute("prod", produto);
				} else {
					daoProduto.atualizar(produto);
				}
			}

			RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
			request.setAttribute("produtos", daoProduto.listar());
			view.forward(request, response);
		}

	}

}
