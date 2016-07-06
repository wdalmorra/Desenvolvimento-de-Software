$(function () {
	var email = getCookie("email");
	if (email != "") {
		window.location.href = "./views/menu.html";
	}

});

function login() {
	var email = document.getElementById("email").value;
	var senha = document.getElementById("senha").value;

	$.ajax({
		url: "controllers/login.php",
		type: "POST",
		data: {
			username: email,
			password: senha
		},
		dataType: "json"
	}).error(function(data){
		alert("Desculpe, nao foi possivel fazer login.");
	}).done(function(data){
		if(data.state == "ok"){
			criaCookie(email, data.nome);
			carregaMenu();
		} else {
			alert("Usuario ou senha incorretos!");
		}
	})
}

function criaCookie(email, nome){
	var path = "/WebVersion/";
	deletaCookie("email");
	deletaCookie("nome");
	document.cookie = "email=" + email + "; Path=" + path;
	document.cookie = "nome=" + nome + "; Path=" + path;
}

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

function carregaMenu(){
	window.location.href = "./views/menu.html";
}

function registra() {
	window.location.href="./views/registro.html";
}