<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<a href="acessoliberado.jsp"><img src="resources/img/home.png"
		alt="Home" title="Voltar ao início" style="width: 50px; height: 50px"></a>
	<a href="index.jsp"><img src="resources/img/logout.png" alt="Sair"
		title="Sair do sistema" style="width: 45px; height: 45px"></a>
<center>
	<h2>Seja bem vindo ao sistema em JSP</h2>
	<a href="salvarUsuario?acao=listarTodos"><img
		src="resources/img/usuario.png" width="80px" height="80px"
		title="Cadastrar Usuário"></a>
		<a href="salvarProduto?acao=listarTodos"><img
		src="resources/img/produtos.png" width="80px" height="80px"
		title="Cadastrar Produto"></a>
</center>
</body>
</html>