$(function () {

});

function uploadXml() {
	var fileSelect = document.getElementById("input01");
	var files;
	var file;
	var formData = new FormData();

	files = fileSelect.files;
	file = files[0];

	var username = getCookie("username");

	formData.append('email', username);
	formData.append('file', file);

	$.ajax({
		url: "../controllers/upload.php",
		type: "POST",
		contentType: false,
		processData: false,
		data: formData
	}).error(function(data){
		alert("Desculpe, o arquivo nao pode ser enviado.");
	}).done(function(data){
		if(data){
			alert("Upload feito com sucesso!");

		} else {
			alert("Falha no upload.");
		}
	})
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
	document.cookie = cname + "=; Path=" + path + "; expires=Thu, 01 Jan 1970 00:00:01 GMT;";
}

function carregaInfos() {
	var nome = getCookie("nome");
	if (nome != "") {
		var textBemVindo = document.getElementById("textbemvindo");
		textBemVindo.innerHTML = "Bem Vindo, " + nome + "!";

		var dropdown = document.getElementById("dropdown");
		dropdown.innerHTML = "Olá, " + nome + "!";
	}
}

function logout() {
	deletaCookie("email");
	deletaCookie("nome");

	// window.location.href = "../index.html";
}