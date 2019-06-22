package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

/**
 * Servlet implementation class Usuario
 */
@WebServlet("/salvarUsuario")
@MultipartConfig // necessária a notação para receber dados de texto e dado de upload
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUsuario daoUsuario = new DaoUsuario();

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String user = request.getParameter("user");

		if (acao != null && acao.equalsIgnoreCase("delete") && user != null) {
			daoUsuario.delete(Long.parseLong(user));
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("editar")) {
			BeanCursoJsp beanCursoJsp = daoUsuario.consultar(Long.parseLong(user));
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("user", beanCursoJsp);
			view.forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("listarTodos")) {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("download")) {
			BeanCursoJsp beanCursoJsp = daoUsuario.consultar(Long.parseLong(user));
			if (beanCursoJsp != null) {
				// clicando na imagem sem redirecionar para outra página e realizar o download
				// direto da imagem
				// no content type realizei um replace para tirar somente a ext do arquivo que
				// preciso para contatenar

				String contentType = "";
				byte[] fileBytes = null;
				String tipo = request.getParameter("tipo");

				if (tipo.equalsIgnoreCase("imagem")) {
					contentType = beanCursoJsp.getContentType();
					// convertendo a base64 salva no banco da imagem para byte
					fileBytes = new Base64().decodeBase64(beanCursoJsp.getFotoBase64());
				} else if (tipo.equalsIgnoreCase("curriculo")) {
					contentType = beanCursoJsp.getContentTypeCurriculo();
					// convertendo a base64 salva no banco do curriculo para byte
					fileBytes = new Base64().decodeBase64(beanCursoJsp.getCurriculoBase64());
				}

				response.setHeader("Content-Disposition", "attachment;filename=arquivo." + contentType.split("\\/")[1]);

				// colocar os bytes em objeto de fluxo de entrada para processar
				InputStream input = new ByteArrayInputStream(fileBytes);

				/* Início da resposta para o navegador */
				int read = 0;
				byte[] bytes = new byte[1024];
				OutputStream os = response.getOutputStream();

				while ((read = input.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}

				os.flush();
				os.close();

			}
		} else {
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

					/*
					 * List<FileItem> fileItem = new ServletFileUpload( new
					 * org.apache.commons.fileupload.disk.DiskFileItemFactory()).parseRequest(
					 * request); for (FileItem fileItem2 : fileItem) { // procura o campo da foto na
					 * requisição if (fileItem2.getFieldName().equals("foto")) { // base 64 lib tom
					 * cat String fotoBase64 = new Base64().encodeBase64String(fileItem2.get());
					 * String contentType = fileItem2.getContentType();
					 * usuario.setFotoBase64(fotoBase64); usuario.setContentType(contentType); } }
					 */

					Part imagemFoto = request.getPart("foto");

					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {
						
						byte[] bytesImagem = converteStreamParaByte(imagemFoto.getInputStream());
						String fotoBase64 = new Base64()
								.encodeBase64String(bytesImagem);

						usuario.setFotoBase64(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());
						
						
						/* início miniatura imagem */
						
						//decodificar a imagem que vai virar miniatura
						byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
						
						// Transforma a imagem decodificada em um buffered image
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
						
						//pegar o tipo da imagem
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();/* o tipo da imagem é 
						igual a 0? se sim atribui o valor padrão INT_ARGB, se n pega o tipo da imagem*/
						
						//Cria a imagem em miniatura
						BufferedImage redimensionarImagem = new BufferedImage(100, 100, type);
						Graphics2D grafico = redimensionarImagem.createGraphics();
						//desenhando a imagem novamente
						grafico.drawImage(bufferedImage, 0, 0, 100,100, null);	
						grafico.dispose();
						//escrevendo imagem novamente
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(redimensionarImagem, "png", baos);
						
						//preparando a imagem para ser mostrada direta na tela, infomando o cabeçalho data:image/png;base64,
						String miniaturaBase64 = "data:image/png;base64,"+ DatatypeConverter.printBase64Binary(baos.toByteArray());
						
						usuario.setFotoBase64Miniatura(miniaturaBase64);
						/* fim miniatura imagem */
						
						
					} else {
						usuario.setFotoBase64(request.getParameter("fotoTemp"));
						usuario.setContentType(request.getParameter("contentTypeTemp"));
					}

					// Processamento PDF
					Part curriculoPdf = request.getPart("curriculo");

					if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {
						String curriculoBase64 = new Base64()
								.encodeBase64String(converteStreamParaByte(curriculoPdf.getInputStream()));

						usuario.setCurriculoBase64(curriculoBase64);
						usuario.setContentTypeCurriculo(curriculoPdf.getContentType());
					} else {
						usuario.setCurriculoBase64(request.getParameter("curriculoTemp"));
						usuario.setContentTypeCurriculo(request.getParameter("contentTypeCurriculoTemp"));
					}

				}
			}

			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/* fim upoload foto */

			usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);// o id tem valor?
			// se sim faz o setId se não faz o
			// setID atribuindo o valor null
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
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
		int reads = imagem.read();
		while (reads != -1) {
			baops.write(reads);
			reads = imagem.read();
		}
		return baops.toByteArray();
	}

}
