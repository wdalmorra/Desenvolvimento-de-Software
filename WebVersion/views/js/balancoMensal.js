$(function () {
	var email = getCookie("email");
	if (email == "") {
		window.location.href = "../index.html";
	}
	loadPieChart();
	loadBarChart();
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

	populaMeses();
	populaAnos();
}

function populaMeses(){
  // alert("Algo deu errado com os 2!");

  $.ajax({
      url: "../controllers/carregaMeses.php",
      type: "POST",
      dataType:"json"
    }).error(function(data){
      alert("Não foi possível carregar os meses.");
    }).done(function(data){
      if(data.length > 0){
        var select = document.getElementById("txMes");
        for(var i = 0; i < data.length; i++) {
          var opt = data[i]["mes"];
          var el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          select.appendChild(el);
        }

      } else {
        alert("Algo deu errado com os meses!");
      }
    })
}
function populaAnos(){
  // alert("Algo deu errado com os 2!");

  $.ajax({
      url: "../controllers/carregaAnos.php",
      type: "POST",
      dataType:"json"
    }).error(function(data){
      alert("Não foi possível carregar os anos.");
    }).done(function(data){
      if(data.length > 0){
        var select = document.getElementById("txAno");
        for(var i = 0; i < data.length; i++) {
          var opt = data[i]["ano"];
          var el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          select.appendChild(el);
        }

      } else {
        alert("Algo deu errado com os anos!");
      }
    })
}

function carregaReceitaDespesa() {
	carregaReceita();
	carregaDespesas();
}

function carregaReceita() {
	var usuario = getCookie("email");
	var txMes = "01";
	var txAno = "2016";		
				
	$.ajax({
		url: "../controllers/carregaReceitas.php",
		type: "POST",
		data: {
			email: usuario,
			mes: txMes ,  
			ano: txAno
		}
		}).error(function(data){
			 alert("Não foi possível carregar as receitas.");
		}).done(function(data){
			if(data.length > 0){
			alert(data[0]["valor"]);
		 	  
				var select = document.getElementById("receita");
				for(var i = 0; i < data.length; i++) {
				  var opt = data[i]["valor"];
				  var el = document.createElement("table");
				  el.textContent = opt;
				  el.value = opt;
				  select.appendChild(el);
				}
			} else {
				alert("Algo deu errado com as receita!");
			}
		})
}

function carregaDespesas(){
	var usuario = getCookie("email");
	var txMes = "01";
	var txAno = "2016";	
	$.ajax({
		url: "../controllers/carregaDespesas.php",
		type: "POST",
		data: {
			email: usuario,
			mes: txMes ,  
			ano: txAno
		}
		}).error(function(data){
			 alert("Não foi possível carregar as despesas.");
		}).done(function(data){
			if(data.length > 0){
				alert("deu certo!");
		 	        var select = document.getElementById("despesas");
				for(var i = 0; i < data.length; i++) {
				  var opt = data[i]["valor"];
				  var el = document.createElement("table");
				  el.textContent = opt;
				  el.value = opt;
				  select.appendChild(el);
				}
			} else {
				alert("Algo deu errado com as despesas!");
			}
		})
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
			}
		})
	}
} 


function logout() {
	deletaCookie("email");
	deletaCookie("nome");

	// window.location.href = "../index.html";
}
