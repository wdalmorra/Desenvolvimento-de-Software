$(function () {
	var email = getCookie("email");
	if (email != "") {
		window.location.href = "./views/menu.html";
	}

});

function esqueceuSenha() {
	window.location.href="./views/esqueceuSenha.html";
}

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

function carregaMenu(){
	window.location.href = "./views/menu.html";
}

function registra() {
	window.location.href="./views/registro.html";
}
