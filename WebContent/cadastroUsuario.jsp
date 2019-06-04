<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Cadstro de Usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>
	<div class="content">
		<form action="salvarUsuario" method="POST" id="formUser">
			<h1>Cadastro de Usuário</h1>
			<center><h3 style="color: orange">${msg}</h3></center>
			<ul class="form-style-1">
				<li>
					<table>
						<tr>
							<td>Código</td>
							<td><input type="text" readonly="readonly" id="id" name="id"
								value="${user.id}" class="field-long"></td>
						</tr>
						<tr>
							<td>Login</td>
							<td><input type="text" id="login" name="login"
								value="${user.login}" class="field-long"></td>
						</tr>
						<tr>
							<td>Nome</td>
							<td><input type="text" id="nome" name="nome"
								value="${user.nome}" class="field-long"></td>
						</tr>
						<tr>
							<td>Senha</td>
							<td><input type="password" id="senha" name="senha"
								value="${user.senha}" class="field-long"></td>
						</tr>
						<tr>
							<td>Fone</td>
							<td><input type="text" id="fone" name="fone"
								value="${user.fone}" class="field-long"></td>
						</tr>
						<tr>
							
							<td><input type="submit" value="Salvar"></td>
							<td><input type="submit" value="Cancelar" onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'"></td>
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
					<th>Login</th>
					<th>Nome</th>
					<th>Fone</th>
					<th>Excluir</th>
					<th>Editar</th>
				</tr>
				<c:forEach items="${usuarios}" var="user">
					<tr>
						<td><c:out value="${user.id}"></c:out></td>
						<td><c:out value="${user.login}"></c:out></td>
						<td><c:out value="${user.nome}"></c:out></td>
						<td><c:out value="${user.fone}"></c:out></td>
						<td><a href="salvarUsuario?acao=delete&user=${user.id}"><img
								src="resources/img/excluir.png" width="20px" height="20px"
								title="Excluir Usuário"></a></td>
						<td><a href="salvarUsuario?acao=editar&user=${user.id}">
								<img src="resources/img/editar.png" width="20px" height="20px"
								title="Editar Usuário">
						</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>