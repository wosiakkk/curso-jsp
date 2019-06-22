<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Cadastro de Telefones</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

</head>
<body>
	<a href="acessoliberado.jsp"><img src="resources/img/home.png"
		alt="Home" title="Voltar ao início" style="width: 50px; height: 50px"></a>
	<a href="index.jsp"><img src="resources/img/logout.png" alt="Sair"
		title="Sair do sistema" style="width: 45px; height: 45px"></a>
	<div class="content">
		<form action="salvarTelefones" method="POST" id="formUser"
			onsubmit="return validarCampos()? true: false;">
			<h1>Cadastro de Telefones</h1>
			<center>
				<h3 style="color: orange">${msg}</h3>
			</center>
			<ul class="form-style-1">
				<li>
					<table>
						<tr>
							<td>User</td>
							<td><input type="text" readonly="readonly" id="id" name="id"
								value="${userEscolhido.id}" ></td>
							<td><input type="text" readonly="readonly" id="nome"
								name="nome" value="${userEscolhido.nome}" ></td>

						</tr>
						<tr>
							<td>Número</td>
							<td><input type="text" id="numero" name="numero"
								></td>
							<td><select id="tipo" name="tipo" >
									<option>Casa</option>
									<option>Contato</option>
									<option>Celular</option>
							</select></td>

						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="Salvar" ></td>
							<td><input type="submit" value="Voltar"
								onclick="document.getElementById('formUser').action = 'salvarTelefones?acao=voltar'"
								></td>
						</tr>
					</table>
				</li>
			</ul>
		</form>
		<div class="container">
			<table class="responsive-table">
				<caption>Lista de Telefones</caption>
				<tr>
					<th>ID</th>
					<th>Número</th>
					<th>Tipo</th>
					<th>Excluir</th>
				</tr>
				<c:forEach items="${telefones}" var="fone">
					<tr>
						<td><c:out value="${fone.id}"></c:out></td>
						<td><c:out value="${fone.numero}"></c:out></td>
						<td><c:out value="${fone.tipo}"></c:out></td>

						<td><a
							href="salvarTelefones?acao=deleteFone&foneId=${fone.id}" onclick="return confirm('Confirmar exclusão?');"><img
								src="resources/img/excluir.png" width="20px" height="20px"
								title="Excluir Telefone"/></a></td>

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
			}

			return true;
		};
	</script>
</body>
</html>