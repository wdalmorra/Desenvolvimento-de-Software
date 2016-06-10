$(function () {


});

function registra() {
	var usuario = document.getElementById("usuario").value;
	var txNome = document.getElementById("txNome").value;
	var txCidade = document.getElementById("txCidade").value;
	var txEstado = document.getElementById("txEstado").value;
	var txPais = document.getElementById("txPais").value;
	var senha = document.getElementById("senha").value;
	var txConfSenha = document.getElementById("txConfSenha").value;

	if(senha != txConfSenha) {
		document.getElementById("txConfSenha").style.borderColor = "#E34234";
	} else {
		$.ajax({
			url: "../controllers/registro.php",
			type: "POST",
			data: {
				email: usuario,
				nome: txNome,
				senha: senha
			}
		}).error(function(data){
			alert("Desculpe, nao foi possivel fazer o registro.");
		}).done(function(data){
			if(data){
				alert("Registro efetuado com sucesso!");
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