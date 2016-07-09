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

function populaUsuarios() {
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
		if(data.length > 0){
			var tab_body = document.getElementById("tabela-body");
			for(var i = 0; i < data.length; i++) {
				var email = data[i]["email"];
				var email_logado = getCookie("email");

				if(email != email_logado) {
					var admin = data[i]["admin"];
					var status = data[i]["status"];
					var id_admin = "admin_" + i;
					var id_rem = "rem_" + i;
					var tr = document.createElement("tr");
					var td_email = document.createElement("td");
					var td_admin = document.createElement("td");
					var td_rem = document.createElement("td");
					var a_admin = document.createElement("a");
					var a_rem = document.createElement("a");
					var i_admin = document.createElement("i");
					var i_rem = document.createElement("i");

					td_email.colSpan = "3";
					td_admin.colSpan = "1";
					td_rem.colSpan = "1";

					td_email.innerHTML = email;
					td_email.id = "email_" + i;

					a_admin.setAttribute("href", "#");
					i_admin.setAttribute("id", id_admin);
					a_admin.onclick = function(x, y) { return function() { toggleAdmin(x, y); }; }(td_email.id, admin);
					if(admin) {
						i_admin.setAttribute("class", "fa fa-square fa-lg");
						a_admin.setAttribute("title", "Remover admin");
					} else {
						i_admin.setAttribute("class", "fa fa-square-o fa-lg");
						a_admin.setAttribute("title", "Ativar admin");
					}
					i_admin.style.color = "black";
					i_admin.setAttribute("aria-hidden", "true");

					a_rem.setAttribute("href", "#");
					i_rem.setAttribute("id", id_rem);
					a_rem.onclick = function(x, y) { return function() { toggleUser(x, y); }; }(td_email.id, status);
					if(status == "ativo") {
						i_rem.setAttribute("class", "fa fa-times fa-lg");
						i_rem.style.color = "red";
						a_rem.setAttribute("title", "Remover usuário");
					} else {
						i_rem.setAttribute("class", "fa fa-check fa-lg");
						i_rem.style.color = "green";
						a_rem.setAttribute("title", "Reativar usuário");
					}
					i_rem.setAttribute("aria-hidden", "true");

					a_admin.appendChild(i_admin);
					td_admin.appendChild(a_admin);

					a_rem.appendChild(i_rem);
					td_rem.appendChild(a_rem);

					tr.appendChild(td_email);
					tr.appendChild(td_admin);
					tr.appendChild(td_rem);

					tab_body.appendChild(tr);
				}
			}

		} else {
			alert("Não há usuários cadastrados!");
		}
	})
}

function toggleAdmin(id, admin) {
	var email = document.getElementById(id).innerHTML;
	var tipo;
	if(admin) {
		tipo = "del_admin";
	} else {
		tipo = "add_admin";
	}

	alert(tipo);

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
			location.reload();
		}
	})
}

function toggleUser(id, status) {
	var email = document.getElementById(id).innerHTML;
	var tipo;
	if(status == "ativo") {
		tipo = "del_user";
	} else {
		tipo = "add_user";
	}

	alert(tipo);

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
			location.reload();
		}
	})
}