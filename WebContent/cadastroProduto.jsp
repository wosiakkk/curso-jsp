<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- uri da bilioteca de format Number do JSTL -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Cadstro de Produto</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

<script type='text/javascript' src="webjars/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript"
	src="resources/javascript/jquery.maskMoney.min.js"></script>

</head>
<body>
	<a href="acessoliberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>
	<div class="content">
		<form action="salvarProduto" method="POST" id="formUser"
			onsubmit="return validarCampos()? true: false;">
			<h1>Cadastro de Produto</h1>
			<center>
				<h3 style="color: orange">${msg}</h3>
			</center>
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
								value="${prod.nome}" maxlength="50"></td>
						</tr>
						<tr>
							<td>Quantidade Unidade</td>
							<td><input type="number" id="quantidade" name="quantidade"
								value="${prod.quantidade}" maxlength="4"></td>
						</tr>
						<tr>
							<td>Valor R$</td>
							<td><input type="text" id="valor" name="valor"
								value="${prod.valorEmTexto}" data-thousands="." data-decimal=","></td>
						</tr>
						<tr>

							<td><input type="submit" value="Salvar"></td>
							<td><input type="submit" value="Cancelar"
								onclick="document.getElementById('formUser').action = 'salvarProduto?acao=reset'"></td>
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
						<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${prod.valor}"></fmt:formatNumber></td>
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
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("nome").value == '') {
				alert('O nome deve ser informado.');
				return false;
			} else if (document.getElementById("quantidade").value == '') {
				alert('A quantidade deve ser informada.');
				return false;
			} else if (document.getElementById("valor").value == '') {
				alert('O valor deve ser informado.');
				return false;
			}

			return true;
		};
	</script>
</body>
<!-- Jquery para máscara de dinheiro, script usado é o jquery maskmoney -->
<script type="text/javascript">
	$(function() {
		$('#valor').maskMoney();
	})

</script>
</html>