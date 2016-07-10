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

