$(function () {
	var email = getCookie("email");
	if (email == "") {
		window.location.href = "../index.html";
	}
});

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}

function deletaCookie(cname) {
	var path = "/WebVersion/";
	var isChrome = !!window.chrome && !!window.chrome.webstore;

	if(isChrome) {
		document.cookie = cname + "=; Path=" + path + "; Expires=" + new Date(0).toGMTString();
	} else {
		document.cookie = cname + "=; Path=" + path + "; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
	}
}

function carregaInfos() {
	var nome = getCookie("nome");
	if (nome != "") {
		var dropdown = document.getElementById("dropdown");
		dropdown.innerHTML = "Olá, " + nome + "!";
	}
}

function logout() {
	deletaCookie("email");
	deletaCookie("nome");
}

function verificaAdmin() {
	var tipo = "admin";

	var email = getCookie("email");
	if(email != "") {
		$.ajax({
			url: "../controllers/gerencia.php",
			type: "POST",
			data: {
				tipo: tipo,
				email: email
			}
		}).error(function(data){
			alert("Desculpe, ocorreu um erro na solicitacao.");
		}).done(function(data){
			if(data){
				document.getElementById("gerencia").style.visibility='visible';
			} else {
				window.location.href = "../index.html";
			}
		})
	}
}

function popularUsuarios() {
	var tipo = "tabela";

	$.ajax({
		url: "../controllers/gerencia.php",
		type: "POST",
		data: {
			tipo: tipo
		},
		dataType: "json"
	}).error(function(data){
		alert("Desculpe, ocorreu um erro na solicitacao.");
	}).done(function(data){
		alert(data.state);
	})
}