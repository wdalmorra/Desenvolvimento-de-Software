$(function () {
	var email = getCookie("email");
	if (email != "") {
		window.location.href = "./menu.html";
	}
});


function envia() {
	var usuario = document.getElementById("usuario").value;
	var erro = false;

	if(usuario == "") {
		document.getElementById("usuario").style.borderColor = "#E34234";
		erro = true;
	}  else if(!erro) {
		$.ajax({
			url: "../controllers/enviaEmail.php",
			type: "POST",
			data: {
				email: usuario
			}
		}).error(function(data){
			alert("Desculpe, nao foi possivel enviar o email.");
		}).done(function(data){
			if(data){
				alert("Email enviado com sucesso!");
				  window.location.href = "../index.html";
			} else {
				alert("Algo deu errado!");
			}
		})
	}
}

function cancela() {
	window.location.href="../index.html";
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
