package servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.naming.PartialResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

/**
 * Servlet implementation class Usuario
 */
@WebServlet("/salvarUsuario")
@MultipartConfig //necessária a notação para receber dados de texto e dado de upload
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUsuario daoUsuario = new DaoUsuario();
	
	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String acao = request.getParameter("acao");
		String user = request.getParameter("user");

		if (acao.equalsIgnoreCase("delete")) {
			daoUsuario.delete(Long.parseLong(user));
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		} else if (acao.equalsIgnoreCase("editar")) {
			BeanCursoJsp beanCursoJsp = daoUsuario.consultar(Long.parseLong(user));
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("user", beanCursoJsp);
			view.forward(request, response);
		} else if (acao.equalsIgnoreCase("listarTodos")) {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		} else {

			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String fone = request.getParameter("fone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String ibge = request.getParameter("ibge");


			BeanCursoJsp usuario = new BeanCursoJsp();
			
			/* Início de upload de foto */
			try {
				// validar se o formulário é de upload
				if (ServletFileUpload.isMultipartContent(request)) {

					/*List<FileItem> fileItem = new ServletFileUpload(
							new org.apache.commons.fileupload.disk.DiskFileItemFactory()).parseRequest(request);
					for (FileItem fileItem2 : fileItem) {
						// procura o campo da foto na requisição
						if (fileItem2.getFieldName().equals("foto")) {
							// base 64 lib tom cat
							String fotoBase64 = new Base64().encodeBase64String(fileItem2.get());
							String contentType = fileItem2.getContentType();
							usuario.setFotoBase64(fotoBase64);
							usuario.setContentType(contentType);
						}
					}*/
					
					Part imagemFoto = request.getPart("foto");
					String fotoBase64 = new Base64().encodeBase64String(converteStreamParaByte(imagemFoto.getInputStream()));
					
					usuario.setFotoBase64(fotoBase64);
					usuario.setContentType(imagemFoto.getContentType());
					
					
				}	
			}

			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/* fim upoload foto */

			//usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);// o id tem valor? se sim faz o setId se não faz o
																		// setID atribuindo o valor null
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setFone(fone);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setEstado(estado);
			usuario.setIbge(ibge);

			if (login == null || login.isEmpty()) {
				request.setAttribute("msg", "O login deve ser informado");
				request.setAttribute("user", usuario);

			} else if (senha == null || senha.isEmpty()) {
				request.setAttribute("msg", "A senha deve ser informada");
				request.setAttribute("user", usuario);

			} else if (nome == null || nome.isEmpty()) {
				request.setAttribute("msg", "O nome deve ser informado");
				request.setAttribute("user", usuario);

			} else if (fone == null || fone.isEmpty()) {
				request.setAttribute("msg", "O telefone deve ser informado");
				request.setAttribute("user", usuario);

			} else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) {
				request.setAttribute("msg", "Usuário já existe com o mesmo login");
				request.setAttribute("user", usuario);
			} else if (id == null || id.isEmpty() && daoUsuario.validarLogin(login)) {
				daoUsuario.salvar(usuario);
				request.setAttribute("msg", "Usuário cadastrado com sucesso");
			} else if (id != null && !id.isEmpty()) {
				if (!daoUsuario.validarLoginUpdate(login, Long.parseLong(id))) {
					request.setAttribute("msg", "O login já existe para outro usuário cadastrado");
					request.setAttribute("user", usuario);
				} else {
					daoUsuario.atualizar(usuario);
					request.setAttribute("msg", "Usuário atualizado com sucesso");
				}
			}

			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		}
	}
//método para converter dados da imagem em Stream para um array de Bytes para poder salvar como texto no BD
	private static byte[] converteStreamParaByte(InputStream imagem) throws IOException {
		ByteArrayOutputStream baops = new ByteArrayOutputStream();
		int reads =  imagem.read();
		while(reads !=-1) {
			baops.write(reads);
			reads = imagem.read();
		}
		return baops.toByteArray();
	}
	
}
