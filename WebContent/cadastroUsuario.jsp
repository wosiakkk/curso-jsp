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
	<a href="acessoliberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>

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
							value="${user.id}" class="field-long"></td>
						<td>CEP</td>
						<td><input type="text" id="cep" name="cep"
							value="${user.cep}" class="field-long" onblur="consultaCep()"></td>
					</tr>
					<tr>
						<td>Login</td>
						<td><input type="text" id="login" name="login"
							value="${user.login}" class="field-long"></td>
						<td>Rua</td>
						<td><input type="text" id="rua" name="rua"
							value="${user.rua}" class="field-long"></td>
					</tr>
					<tr>
						<td>Nome</td>
						<td><input type="text" id="nome" name="nome"
							value="${user.nome}" class="field-long"></td>
						<td>Bairro</td>
						<td><input type="text" id="bairro" name="bairro"
							value="${user.bairro}" class="field-long"></td>
					</tr>
					<tr>
						<td>Senha</td>
						<td><input type="password" id="senha" name="senha"
							value="${user.senha}" class="field-long"></td>
						<td>Cidade</td>
						<td><input type="text" id="cidade" name="cidade"
							value="${user.cidade}" class="field-long"></td>
					</tr>
					<tr>
						<td>Fone</td>
						<td><input type="text" id="fone" name="fone"
							value="${user.fone}" class="field-long"></td>
						<td>Estado</td>
						<td><input type="text" id="estado" name="estado"
							value="${user.estado}" class="field-long"></td>
					</tr>
					<tr>
						<td>IBGE</td>
						<td><input type="text" id="ibge" name="ibge"
							value="${user.ibge}" class="field-long"></td>
					</tr>
					<tr>
						<td>Foto:</td>
						<td><input type="file" name="foto" id="foto"
							class="field-long"><input type="text" readonly="readonly"
							value="${user.fotoBase64}" name="fotoTemp" hidden> <input
							type="text" readonly="readonly" value="${user.contentType}"
							name="contentTypeTemp" hidden></td>
					</tr>
					<tr>
						<td>Currículo:</td>
						<td><input type="file" name="curriculo" id="curriculo"
							value="curriculo" class="field-long"><input type="text"
							readonly="readonly" value="${user.curriculoBase64}"
							name="curriculoTemp" hidden> <input type="text"
							readonly="readonly" value="${user.contentTypeCurriculo}"
							name="contentTypeCurriculoTemp" hidden></td>
					</tr>
					<tr>
						<td><input type="submit" value="Salvar"></td>
						<td><input type="submit" value="Cancelar"
							onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'"></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

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
				<c:if test="${user.fotoBase64.isEmpty() == false}">
					<td><a
						href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}"><img
							src='<c:out value="${user.tempFotoUser}"></c:out>'
							alt="Imagem User" title="Imagem User" width="32px" height="32px"></a></td>
				</c:if>
				<c:if test="${user.fotoBase64.isEmpty() == true || user.fotoBase64.isEmpty() == null}">
					<td><img src="resources/img/userpadrao.png" alt="Imagem User" width="32px" height="32px"></td>
				</c:if>
				<!-- *** -->
				<c:if test="${user.curriculoBase64.isEmpty() == false}">
				<td><a
					href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}"><img alt="Currículo" src="resources/img/cv.png"></a></td>
				</c:if>
				<c:if test="${user.curriculoBase64.isEmpty() == true || user.curriculoBase64.isEmpty() == null }">
					<td><img alt="Sem currículo" src="resources/img/not-found.png" onclick="alert('Não possui currículo')"></td>
				</c:if>
				<td><c:out value="${user.nome}"></c:out></td>
				<td><a href="salvarUsuario?acao=delete&user=${user.id}"><img
						src="resources/img/excluir.png" width="20px" height="20px"
						title="Excluir Usuário"></a></td>
				<td><a href="salvarUsuario?acao=editar&user=${user.id}"> <img
						src="resources/img/editar.png" width="20px" height="20px"
						title="Editar Usuário">
				</a></td>
				<td><a href="salvarTelefones?acao=addFone&user=${user.id}">
						<img src="resources/img/telefone.png" width="20px" height="20px"
						title="Telefones">
				</a></td>
			</tr>
		</c:forEach>
	</table>
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
			} else if (document.getElementById("fone").value == '') {
				alert('O telefone deve ser informado.');
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