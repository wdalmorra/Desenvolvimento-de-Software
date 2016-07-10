$(function () {
	var email = getCookie("email");
	if (email == "") {
		window.location.href = "../index.html";
	}
	loadPieChart();
	loadBarChart();
});

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
	
var ddMes = document.getElementById("txMes");
	var txMes = ddMes.options[ddMes.selectedIndex].value;	

	var ddAno = document.getElementById("txAno");
	var txAno = ddAno.options[ddAno.selectedIndex].value;	
				
	$.ajax({
		url: "../controllers/carregaReceitas.php",
		type: "POST",
		data: {
			email: usuario,
			mes: txMes ,  
			ano: txAno
		},
		dataType: "json"
		}).error(function(data){
			 alert("Não foi possível carregar as receitas.");
		}).done(function(data){
			if(data.length > 0){
				$("#receitaTabela").html("");
				var tab_body = document.getElementById("receitaTabela");
 				for(var i = 0; i < data.length; i++) {

					var categoria = data[i]["categoria"];
					var valor = data[i]["valor"];

					var tr = document.createElement("tr");
					var td_categoria = document.createElement("td");
					var td_valor = document.createElement("td");

					td_categoria.colSpan = "1";
					td_valor.colSpan = "2";


					td_categoria.innerHTML = categoria;
					td_categoria.id = "categoria_" + i;

					td_valor.innerHTML = valor;
					td_categoria.id = "valor_" + i;
					td_valor.style.color = "blue"; 
					td_categoria.style.color = "blue"; 

					tr.appendChild(td_categoria);
					tr.appendChild(td_valor);

					tab_body.appendChild(tr);

				}
			} else {
				$("#receitaTabela").html("");
				var tab_body = document.getElementById("receitaTabela");
				var tr = document.createElement("tr");
				var td_categoria = document.createElement("td");
				td_categoria.colSpan = "3";
				td_categoria.innerHTML = "Não obteve movimentação de receita neste mês";
				td_categoria.style.color = "green"; 
				tr.appendChild(td_categoria);
				tab_body.appendChild(tr);
			}
		})
}

function carregaDespesas(){
	var usuario = getCookie("email");

	var ddMes = document.getElementById("txMes");
	var txMes = ddMes.options[ddMes.selectedIndex].value;	

	var ddAno = document.getElementById("txAno");
	var txAno = ddAno.options[ddAno.selectedIndex].value;	

	$.ajax({
		url: "../controllers/carregaDespesas.php",
		type: "POST",
		data: {
			email: usuario,
			mes: txMes ,  
			ano: txAno
		},
		dataType: "json"
		}).error(function(data){
			 alert("Não foi possível carregar as despesas.");
		}).done(function(data){
			if(data.length > 0){
				$("#despesaTabela").html("");
				var tab_body = document.getElementById("despesaTabela");

 				for(var i = 0; i < data.length; i++) {

					var categoria = data[i]["categoria"];
					var valor = data[i]["valor"];

					var tr = document.createElement("tr");
					var td_categoria = document.createElement("td");
					var td_valor = document.createElement("td");

					td_categoria.colSpan = "1";
					td_valor.colSpan = "2";

					td_categoria.innerHTML = categoria;
					td_categoria.id = "categoria_" + i;

					td_valor.innerHTML = valor;
					td_categoria.id = "valor_" + i;
					td_valor.style.color = "red"; 
					td_categoria.style.color = "red"; 

					tr.appendChild(td_categoria);
					tr.appendChild(td_valor);

					tab_body.appendChild(tr);

				}
			} else {
				$("#despesaTabela").html("");
				var tab_body = document.getElementById("despesaTabela");

				var tr = document.createElement("tr");
				var td_categoria = document.createElement("td");
				td_categoria.colSpan = "3";
				td_categoria.innerHTML = "Não obteve movimentação de despesa neste mês";
				td_categoria.style.color = "green"; 
				tr.appendChild(td_categoria);
				tab_body.appendChild(tr);
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
