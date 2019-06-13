<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Cadstro de Produto</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>
	<div class="content">
		<form action="salvarProduto" method="POST" id="formUser">
			<h1>Cadastro de Produto</h1>
			<center><h3 style="color: orange">${msg}</h3></center>
			<ul class="form-style-1">
				<li>
					<table>
						<tr>
							<td>Código</td>
							<td><input type="text" readonly="readonly" id="id" name="id"
								value="${prod.id}" class="field-long"></td>
						</tr>
						<tr>
							<td>Nome</td>
							<td><input type="text" id="nome" name="nome"
								value="${prod.nome}" class="field-long"></td>
						</tr>
						<tr>
							<td>Quantidade</td>
							<td><input type="text" id="quantidade" name="quantidade"
								value="${prod.quantidade}" class="field-long"></td>
						</tr>
						<tr>
							<td>Valor R$</td>
							<td><input type="text" id="valor" name="valor"
								value="${prod.valor}" class="field-long"></td>
						</tr>
						<tr>
							
							<td><input type="submit" value="Salvar"></td>
							<td><input type="submit" value="Cancelar" onclick="document.getElementById('formUser').action = 'salvarProduto?acao=reset'"></td>
						</tr>
					</table>
				</li>
			</ul>
		</form>
		<div class="container">
			<table class="responsive-table">
				<caption>Lista de produtos</caption>
				<tr>
					<th>ID</th>
					<th>Nome</th>
					<th>Quantidade</th>
					<th>Valor R$</th>
					<th>Excluir</th>
					<th>Editar</th>
				</tr>
				<c:forEach items="${produtos}" var="prod">
					<tr>
						<td><c:out value="${prod.id}"></c:out></td>
						<td><c:out value="${prod.nome}"></c:out></td>
						<td><c:out value="${prod.quantidade}"></c:out></td>
						<td><c:out value="${prod.valor}"></c:out></td>
						<td><a href="salvarProduto?acao=delete&prod=${prod.id}"><img
								src="resources/img/excluir.png" width="20px" height="20px"
								title="Excluir Produto"></a></td>
						<td><a href="salvarProduto?acao=editar&prod=${prod.id}">
								<img src="resources/img/editar.png" width="20px" height="20px"
								title="Editar Produto">
						</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>