$(function () {
	var email = getCookie("email");
	if (email == "") {
		window.location.href = "../index.html";
	}
});

function populaTudo() {
	var email = getCookie("email");
	var pais;
	var estado;
	var cidade;
	var nascimento;
	var tipo = "popula";

	if (email != "") {
		$.ajax({
			url: "../controllers/usuario.php",
			type: "POST",
			data: {
				username: email,
				tipo: tipo
			},
			dataType: "json"
		}).error(function(data){
			alert("Desculpe, nao foi possivel carregar as informacoes.");
		}).done(function(data){
			if(data.state == "ok") {
				pais = data.pais;
				estado = data.estado;
				cidade = data.cidade;
				nascimento = data.nasc;

				nasc = nascimento.split("-");
				nascimento = nasc[2] + "/" + nasc[1] + "/" + nasc[0];

				carregaInfos(nascimento);
				populaPaises(pais, estado, cidade);
			} else {
				alert("Erro no banco de dados.");
			}
		})
	}
}

function populaPaises(pais, estado, cidade){
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
			for(var i = 0; i < select.options.length; i++) {
				if(select.options[i].text == pais) {
					select.selectedIndex = i;
					break;
				}
			}
			populaEstados(estado, cidade);
		} else {
			alert("Algo deu errado com os países!");
		}
	})
}

function populaEstados(estado, cidade){
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
			for(var i = 0; i < select.options.length; i++) {
				if(select.options[i].text == estado) {
					select.selectedIndex = i;
					break;
				}
			}
			populaCidades(cidade);
		} else {
			alert("Algo deu errado com os estados!");
		}
	})	
}

function populaCidades(cidade){
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
			for(var i = 0; i < select.options.length; i++) {
				if(select.options[i].text == cidade) {
					select.selectedIndex = i;
					break;
				}
			}
		} else {
			alert("Algo deu errado com as cidades!");
		}
	})
}

function alteraDados() {
	var txNome = document.getElementById("txNome").value;
	var txNasc = document.getElementById("txNasc").value;
	var txPais = document.getElementById("txPais").value;
	var txEstado = document.getElementById("txEstado").value;
	var txCidade = document.getElementById("txCidade").value;
	var tipo = "altera_dados";
	var email = getCookie("email");

	if(email != "") {
		$.ajax({
			url: "../controllers/usuario.php",
			type: "POST",
			dataType: "json",
			data: {
				email: email,
				nome: txNome,
				nascimento: txNasc,
				pais: txPais,
				estado: txEstado,
				cidade: txCidade,
				tipo: tipo
			}
		}).error(function(data){
			alert("Desculpe, nao foi possivel alterar os dados.");
		}).done(function(data){
			if(data.state == "ok"){
				alert("Dados alterados com sucesso!");
				criaCookie(data.nome);
				location.reload();
			} else {
				alert("Algo deu errado!");
			}
		})
	}
}

function alteraSenha() {
	var senhaAtual = document.getElementById("senhaAtual").value;
	var senha = document.getElementById("senha").value;
	var txConfSenha = document.getElementById("txConfSenha").value;
	var tipo = "altera_senha";
	var email = getCookie("email");

	var erro = false;

	if(senhaAtual == "") {
		document.getElementById("senhaAtual").style.borderColor = "#E34234";
		erro = true;
	} else {
		document.getElementById("senhaAtual").style.borderColor = "#CCCCCC";
	}

	if(senha == "") {
		document.getElementById("senha").style.borderColor = "#E34234";
		erro = true;
	} else {
		document.getElementById("senha").style.borderColor = "#CCCCCC";
	}

	if(senha != txConfSenha) {
		document.getElementById("txConfSenha").style.borderColor = "#E34234";
	} else if(email != "" && !erro) {
		$.ajax({
			url: "../controllers/usuario.php",
			type: "POST",
			data: {
				email: email,
				senhaAtual: senhaAtual,
				senhaNova: senha,
				tipo: tipo
			}
		}).error(function(data){
			alert("Desculpe, nao foi possivel alterar a senha.");
		}).done(function(data){
			if(data){
				alert("Senha alterada com sucesso!");
			} else {
				alert("Senha incorreta!");
			}
		})
	}
}

function cancela() {
	window.location.href="./menu.html";
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

function criaCookie(nome){
	var path = "/WebVersion/";
	deletaCookie("nome");
	document.cookie = "nome=" + nome + "; Path=" + path;
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

function carregaInfos(nasc) {
	var nome = getCookie("nome");
	if (nome != "") {
		var dropdown = document.getElementById("dropdown");
		dropdown.innerHTML = "Olá, " + nome + "!";

		var txNome = document.getElementById("txNome");
		txNome.value = nome;

		var txNasc = document.getElementById("txNasc");
		txNasc.value = nasc;
	}
}

function logout() {
	deletaCookie("email");
	deletaCookie("nome");
}