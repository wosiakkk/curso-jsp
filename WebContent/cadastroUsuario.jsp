<%@page import="beans.BeanCursoJsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Cadstro de Usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
<!-- Importando jquery da biblioteca de repositório do maven -->
<script type='text/javascript' src="webjars/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<a href="acessoliberado.jsp"><img src="resources/img/home.png"
		alt="Home" title="Voltar ao início" style="width: 50px; height: 50px"></a>
	<a href="index.jsp"><img src="resources/img/logout.png" alt="Sair"
		title="Sair do sistema" style="width: 45px; height: 45px"></a>
	<div class="content">
		<form action="salvarUsuario" method="POST" id="formUser"
			onsubmit="return validarCampos()? true: false;"
			enctype="multipart/form-data">
			<h1>Cadastro de Usuário</h1>
			<center>
				<h3 style="color: orange">${msg}</h3>
			</center>
			<ul class="form-style-1">
				<li>
					<table>
						<tr>
							<td>Código</td>
							<td><input type="text" readonly="readonly" id="id" name="id"
								value="${user.id}"></td>
							<td>CEP</td>
							<td><input type="text" id="cep" name="cep"
								value="${user.cep}" onblur="consultaCep()" maxlength="8"></td>
						</tr>
						<tr>
							<td>Login</td>
							<td><input type="text" id="login" name="login"
								value="${user.login}" maxlength="20"></td>
							<td>Rua</td>
							<td><input type="text" id="rua" name="rua"
								value="${user.rua}" maxlength="50"></td>
						</tr>
						<tr>
							<td>Nome</td>
							<td><input type="text" id="nome" name="nome"
								value="${user.nome}" maxlength="50"></td>
							<td>Bairro</td>
							<td><input type="text" id="bairro" name="bairro"
								value="${user.bairro}" maxlength="50"></td>
						</tr>
						<tr>
							<td>Senha</td>
							<td><input type="password" id="senha" name="senha"
								value="${user.senha}"></td>
							<td>Cidade</td>
							<td><input type="text" id="cidade" name="cidade"
								value="${user.cidade}" maxlength="50"></td>
						</tr>
						<tr>
							<td>Estado</td>
							<td><input type="text" id="estado" name="estado"
								value="${user.estado}"></td>
						</tr>
						<tr>
							<td>IBGE</td>
							<td><input type="text" id="ibge" name="ibge"
								value="${user.ibge}"></td>
							<td>Ativo:</td>
							<td><input type="checkbox" name="ativo" id="ativo"
								<%if (request.getAttribute("user") != null) {
									BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
									if (user.isAtivo()) {
										out.print(" ");
										out.print("checked=\"checked\"");
										out.print(" ");
									}
								}%>></td>
						</tr>
						<tr>
							<td>Foto:</td>
							<td><input type="file" name="foto" id="foto"></td>
							<td>Sexo:</td>
							<td><input type="radio" name="sexo" value="masculino"
								<%if (request.getAttribute("user") != null) {
									BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
									if (user.getSexo().equalsIgnoreCase("masculino")) {
										out.print("");
										out.print("checked=\"checked\"");
										out.print("");
									}
								}%>>Masculino
							 <input type="radio" name="sexo" value="feminino"
								<%if (request.getAttribute("user") != null) {
									BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
									if (user.getSexo().equalsIgnoreCase("feminino")) {
										out.print("");
										out.print("checked=\"checked\"");
										out.print("");
									}
								}%>>
								Feminino</td>
						</tr>
						<tr>
							<td>Currículo:</td>
							<td><input type="file" name="curriculo" id="curriculo"
								value="curriculo"></td>
								<td>Perfil:</td>
								<td>
									<select id="perfil" name="perfil">
										<option value="naoInformado">SELECIONE</option>							
										<option value="administrador"
											<%if (request.getAttribute("user") != null) {
												BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
												if (user.getPerfil().equalsIgnoreCase("administrador")) {
													out.print("");
													out.print("selected=\"selected\"");
													out.print("");
												}
											}%>
										>Administrador</option>							
										<option value="secretario"
											<%if (request.getAttribute("user") != null) {
												BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
												if (user.getPerfil().equalsIgnoreCase("secretario")) {
													out.print("");
													out.print("selected=\"selected\"");
													out.print("");
												}
											}%>
										>Secretário</option>							
										<option value="gerente"
										<%if (request.getAttribute("user") != null) {
												BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
												if (user.getPerfil().equalsIgnoreCase("gerente")) {
													out.print("");
													out.print("selected=\"selected\"");
													out.print("");
												}
											}%>
										>Gerente</option>							
										<option value="funcionario"
										<%if (request.getAttribute("user") != null) {
												BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
												if (user.getPerfil().equalsIgnoreCase("funcionario")) {
													out.print("");
													out.print("selected=\"selected\"");
													out.print("");
												}
											}%>
										>Funcionário</option>							
									</select>
								</td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="Salvar"></td>
							<td><input type="submit" value="Cancelar"
								onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'"></td>
						</tr>
					</table>
				</li>
			</ul>
		</form>
		<form action="servletPesquisa" method="post">
			<ul class="form-style-1">
				<li>
					<table>
						<tr>
							<td>Descrição</td>
							<td><input type="text" id="descricaoconsulta" name="descricaoconsulta"></td>
							<td><input type="submit" value="Pesquisar"></td>
						</tr>
					</table>
				</li>
			</ul>
		</form>
		<div class="container">
			<table class="responsive-table">
				<caption>Lista de usuários</caption>
				<tr>
					<th>ID</th>
					<th>Foto</th>
					<th>Currículo</th>
					<th>Nome</th>
					<th>Excluir</th>
					<th>Editar</th>
					<th>Telefones</th>
				</tr>
				<c:forEach items="${usuarios}" var="user">
					<tr>
						<td><c:out value="${user.id}"></c:out></td>
						<!-- *** Verificando se há foto no BD para exibir do usuário *** -->
						<c:if test="${user.fotoBase64Miniatura.isEmpty() == false}">
							<td><a
								href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}"><img
									src='<c:out value="${user.fotoBase64Miniatura}"></c:out>'
									alt="Imagem User" title="Imagem User" width="32px"
									height="32px"></a></td>
						</c:if>
						<c:if
							test="${user.fotoBase64Miniatura.isEmpty() == true || user.fotoBase64Miniatura.isEmpty() == null}">
							<td><img src="resources/img/userpadrao.png"
								alt="Imagem User" title="Usuário sem foto" width="32px"
								height="32px"></td>
						</c:if>
						<!-- *** -->
						<c:if test="${user.curriculoBase64.isEmpty() == false}">
							<td><a
								href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}"><img
									alt="Currículo" src="resources/img/cv.png"
									title="Currículo do usuário"></a></td>
						</c:if>
						<c:if
							test="${user.curriculoBase64.isEmpty() == true || user.curriculoBase64.isEmpty() == null }">
							<td><img alt="Sem currículo"
								src="resources/img/not-found.png"
								onclick="alert('Não possui currículo')"
								title="Usuário não possui currículo"></td>
						</c:if>
						<td><c:out value="${user.nome}"></c:out></td>
						<td><a href="salvarUsuario?acao=delete&user=${user.id}"><img
								src="resources/img/excluir.png" width="20px" height="20px"
								title="Excluir Usuário"
								onclick="return confirm('Confirmar a exclusão ?');"></a></td>
						<td><a href="salvarUsuario?acao=editar&user=${user.id}">
								<img src="resources/img/editar.png" width="20px" height="20px"
								title="Editar Usuário">
						</a></td>
						<td><a href="salvarTelefones?acao=addFone&user=${user.id}">
								<img src="resources/img/telefone.png" width="20px" height="20px"
								title="Telefones">
						</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("login").value == '') {
				alert('O login deve ser informado.');
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert('O nome deve ser informado.');
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert('A senha deve ser informada.');
				return false;
			}

			return true;
		};

		function consultaCep() {

			var cep = $("#cep").val();

			//Consulta o webservice viacep.com.br/
			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							//Atualiza os campos com os valores da consulta.
							$("#rua").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#estado").val(dados.uf);
							$("#ibge").val(dados.ibge);
						} //end if.
						else {
							//CEP pesquisado não foi encontrado.
							$("#cep").val('');
							$("#rua").val('');
							$("#bairro").val('');
							$("#cidade").val('');
							$("#estado").val('');
							$("#ibge").val('');
							alert("CEP não encontrado.");
						}
					});

		};
	</script>
</body>
</html>