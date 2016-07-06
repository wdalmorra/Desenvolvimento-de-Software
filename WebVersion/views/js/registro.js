$(function () {
});

function populaPaises(){
	// alert("Algo deu errado com os 2!");

	$.ajax({
			url: "../controllers/carregaPaises.php",
			type: "POST",
			dataType:"json"
		}).error(function(data){
			alert("Não foi possível carregar os países.");
		}).done(function(data){
			if(data.length > 0){
				var select = document.getElementById("txPais");
				for(var i = 0; i < data.length; i++) {
					var opt = data[i]["nome"];
					var el = document.createElement("option");
					el.textContent = opt;
					el.value = opt;
					select.appendChild(el);
				}

			} else {
				alert("Algo deu errado com os países!");
			}
		})
}

function populaEstados(){
	var ddPaises = document.getElementById("txPais");
	var selected = ddPaises.options[ddPaises.selectedIndex].value;
	var ddCidade = document.getElementById("txCidade");

	$.ajax({
			url: "../controllers/carregaEstados.php",
			type: "POST",
			dataType:"json",
			data: {
				pais: selected
			}
		}).error(function(data){
			alert("Não foi possível carregar os estados.");
		}).done(function(data){
			if(data.length > 0){
				var select = document.getElementById("txEstado");
				select.options.length = 0;
				ddCidade.options.length = 0;
				for(var i = 0; i < data.length; i++) {
					// alert(data[i]["estado"]);
					var opt = data[i]["estado"];
					var el = document.createElement("option");
					el.textContent = opt;
					el.value = opt;
					select.appendChild(el);
				}
			} else {
				alert("Algo deu errado com os estados!");
			}
		})	
}

function populaCidades(){
	var ddPaises = document.getElementById("txPais");
	var sPais = ddPaises.options[ddPaises.selectedIndex].value;
	var ddEstados = document.getElementById("txEstado");
	var sEstado = ddEstados.options[ddEstados.selectedIndex].value;

	$.ajax({
			url: "../controllers/carregaCidades.php",
			type: "POST",
			dataType:"json",
			data: {
				pais: sPais,
				estado: sEstado
			}
		}).error(function(data){
			alert("Não foi possível carregar as cidades.");
		}).done(function(data){
			// alert(data);
			if(data.length > 0){
				var select = document.getElementById("txCidade");
				select.options.length = 0;
				for(var i = 0; i < data.length; i++) {
					var opt = data[i]["nome"];
					var el = document.createElement("option");
					el.textContent = opt;
					el.value = opt;
					select.appendChild(el);
				}
			} else {
				alert("Algo deu errado com as cidades!");
			}
		})
}
function registra() {
	var usuario = document.getElementById("usuario").value;
	var txNome = document.getElementById("txNome").value;
	var txNasc = document.getElementById("txNasc").value;
	var txCidade = document.getElementById("txCidade").value;
	var txEstado = document.getElementById("txEstado").value;
	var txPais = document.getElementById("txPais").value;
	var senha = document.getElementById("senha").value;
	var txConfSenha = document.getElementById("txConfSenha").value;

	var erro = false;

	if(usuario == "") {
		document.getElementById("usuario").style.borderColor = "#E34234";
		erro = true;
	} else {
		document.getElementById("usuario").style.borderColor = "#CCCCCC";
	}

	if(txNome == "") {
		document.getElementById("txNome").style.borderColor = "#E34234";
		erro = true;
	} else {
		document.getElementById("txNome").style.borderColor = "#CCCCCC";
	}

	if(txNasc == "") {
		document.getElementById("txNasc").style.borderColor = "#E34234";
		erro = true;
	} else {
		document.getElementById("txNasc").style.borderColor = "#CCCCCC";
	}

	if(txCidade == "") {
		document.getElementById("txCidade").style.borderColor = "#E34234";
		erro = true;
	} else {
		document.getElementById("txCidade").style.borderColor = "#CCCCCC";
	}

	if(txEstado == "") {
		document.getElementById("txEstado").style.borderColor = "#E34234";
		erro = true;
	} else {
		document.getElementById("txEstado").style.borderColor = "#CCCCCC";
	}

	if(txPais == "") {
		document.getElementById("txPais").style.borderColor = "#E34234";
		erro = true;
	} else {
		document.getElementById("txPais").style.borderColor = "#CCCCCC";
	}

	if(senha == "") {
		document.getElementById("senha").style.borderColor = "#E34234";
		erro = true;
	} else {
		document.getElementById("senha").style.borderColor = "#CCCCCC";
	}

	if(senha != txConfSenha) {
		document.getElementById("txConfSenha").style.borderColor = "#E34234";
	} else if(!erro) {
		$.ajax({
			url: "../controllers/registro.php",
			type: "POST",
			data: {
				email: usuario,
				nome: txNome,
				nascimento: txNasc,
				pais: txPais,
				estado: txEstado,
				cidade: txCidade,
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
