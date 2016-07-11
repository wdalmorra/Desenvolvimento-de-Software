$(function () {
  var email = getCookie("email");
  if (email == "") {
    window.location.href = "../index.html";
  }
});

function carregaInfos() {
  var nome = getCookie("nome");
  if (nome != "") {
    var dropdown = document.getElementById("dropdown");
    dropdown.innerHTML = "Olá, " + nome + "!";
  }
  populaDropdowns();
}

function aplicarFiltroBar() {
  loadBarChart();
}

function aplicarFiltroPie() {
	loadGlobalPieChart();
	loadUserPieChart();
}

function populaDropdowns () {
  populaPaisesBar();
  populaPaisesPie();
  populaIdadesPie();
  populaIdadesBar();
  populaAnosPie();
  populaAnosBar();
  populaCategoriasBar();
  populaMesesPie();
  populaMesesBar();
}

function populaCategoriasBar(){
  // alert("Algo deu errado com os 2!");

  $.ajax({
      url: "../controllers/carregaCategorias.php",
      type: "POST",
      dataType:"json"
    }).error(function(data){
      alert("Não foi possível carregar as categorias.");
    }).done(function(data){
      if(data.length > 0){
        var select = document.getElementById("txCategoriaBar");

        var el1 = document.createElement("option");
        el1.textContent = "Saldo";
        el1.value = "Saldo";
        select.appendChild(el1);

        var el2 = document.createElement("option");
        el2.textContent = "Total despesas";
        el2.value = "Total despesas";
        select.appendChild(el2);

        var el3 = document.createElement("option");
        el3.textContent = "Total receitas";
        el3.value = "Total receitas";
        select.appendChild(el3);

        for(var i = 0; i < data.length; i++) {
          var opt = data[i]["nome"];
          el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          select.appendChild(el);
        }

      } else {
        alert("Algo deu errado com as categorias!");
      }
    })
}

function populaMesesPie(){
  // alert("Algo deu errado com os 2!");

  $.ajax({
      url: "../controllers/carregaMeses.php",
      type: "POST",
      dataType:"json"
    }).error(function(data){
      alert("Não foi possível carregar os meses.");
    }).done(function(data){
      if(data.length > 0){
        var select = document.getElementById("txMesPie");
        var el = document.createElement("option");
        el.textContent = "Todos";
        el.value = "Todos";
        select.appendChild(el);
        for(var i = 0; i < data.length; i++) {
          var opt = data[i]["mes"];
          el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          select.appendChild(el);
        }

      } else {
        alert("Algo deu errado com os meses!");
      }
    })
} 

function populaMesesBar(){
  // alert("Algo deu errado com os 2!");

  $.ajax({
      url: "../controllers/carregaMeses.php",
      type: "POST",
      dataType:"json"
    }).error(function(data){
      alert("Não foi possível carregar os meses.");
    }).done(function(data){
      if(data.length > 0){
        var select = document.getElementById("txMesInicialBar");
        var select2 = document.getElementById("txMesFinalBar");

        // var el = document.createElement("option");
        // el.textContent = "Todos";
        // el.value = "Todos";
        // select.appendChild(el);
        // var el2 = document.createElement("option");
        // el2.textContent = "Todos";
        // el2.value = "Todos";
        // select2.appendChild(el2);

        for(var i = 0; i < data.length; i++) {
          var opt = data[i]["mes"];
          el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          el2 = document.createElement("option");
          el2.textContent = opt;
          el2.value = opt;
          select.appendChild(el);
          select2.appendChild(el2);
        }

        select.selectedIndex = 0;
        select2.selectedIndex = data.length - 1;

        loadBarChart();

      } else {
        alert("Algo deu errado com os meses!");
      }
    })
}

function populaAnosPie(){
  // alert("Algo deu errado com os 2!");

  $.ajax({
      url: "../controllers/carregaAnos.php",
      type: "POST",
      dataType:"json"
    }).error(function(data){
      alert("Não foi possível carregar os meses.");
    }).done(function(data){
      if(data.length > 0){
        var select = document.getElementById("txAnoPie");
        var el = document.createElement("option");
        el.textContent = "Todos";
        el.value = "Todos";
        select.appendChild(el);
        for(var i = 0; i < data.length; i++) {
          var opt = data[i]["ano"];
          el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          select.appendChild(el);
        }

      } else {
        alert("Algo deu errado com os meses!");
      }
    })
}

function populaAnosBar(){
  // alert("Algo deu errado com os 2!");

  $.ajax({
      url: "../controllers/carregaAnos.php",
      type: "POST",
      dataType:"json"
    }).error(function(data){
      alert("Não foi possível carregar os anos.");
    }).done(function(data){
      if(data.length > 0){
        var select = document.getElementById("txAnoInicialBar");
        var select2 = document.getElementById("txAnoFinalBar");

        // var el = document.createElement("option");
        // el.textContent = "Todos";
        // el.value = "Todos";
        // select.appendChild(el);
        // var el2 = document.createElement("option");
        // el2.textContent = "Todos";
        // el2.value = "Todos";
        // select2.appendChild(el2);

        for(var i = 0; i < data.length; i++) {
          var opt = data[i]["ano"];
          el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          el2 = document.createElement("option");
          el2.textContent = opt;
          el2.value = opt;
          select.appendChild(el);
          select2.appendChild(el2);
        }

        select.selectedIndex = data.length - 1;
        select2.selectedIndex = 0;

      } else {
        alert("Algo deu errado com os anos!");
      }
    })
}

function populaIdadesPie(){
  // alert("Algo deu errado com os 2!");

  $.ajax({
      url: "../controllers/carregaIdades.php",
      type: "POST",
      dataType:"json"
    }).error(function(data){
      alert("Não foi possível carregar as idades.");
    }).done(function(data){
      if(data.length > 0){
        var select = document.getElementById("txIdadeInicialPie");
        var select2 = document.getElementById("txIdadeFinalPie");
        // var el = document.createElement("option");
        // el.textContent = "Todos";
        // el.value = "Todos";
        // select.appendChild(el);
        // var el2 = document.createElement("option");
        // el2.textContent = "Todos";
        // el2.value = "Todos";
        // select2.appendChild(el2);
        for(var i = 0; i < data.length; i++) {
          var opt = data[i]["idade"];
          el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          el2 = document.createElement("option");
          el2.textContent = opt;
          el2.value = opt;
          select.appendChild(el);
          select2.appendChild(el2);
        }
        select.selectedIndex = 0;
        select2.selectedIndex = data.length - 1;

      } else {
        alert("Algo deu errado com as idades!");
      }
    })
}

function populaIdadesBar(){
  // alert("Algo deu errado com os 2!");

  $.ajax({
      url: "../controllers/carregaIdades.php",
      type: "POST",
      dataType:"json"
    }).error(function(data){
      alert("Não foi possível carregar as idades.");
    }).done(function(data){
      if(data.length > 0){
        var select = document.getElementById("txIdadeInicialBar");
        var select2 = document.getElementById("txIdadeFinalBar");

        // var el = document.createElement("option");
        // el.textContent = "Todos";
        // el.value = "Todos";
        // select.appendChild(el);
        // var el2 = document.createElement("option");
        // el2.textContent = "Todos";
        // el2.value = "Todos";
        // select2.appendChild(el2);

        for(var i = 0; i < data.length; i++) {
          var opt = data[i]["idade"];
          el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          el2 = document.createElement("option");
          el2.textContent = opt;
          el2.value = opt;
          select.appendChild(el);
          select2.appendChild(el2);
        }

        select.selectedIndex = 0;
        select2.selectedIndex = data.length - 1;

      } else {
        alert("Algo deu errado com as idades!");
      }
    })
}


function populaPaisesPie(){
  // alert("Algo deu errado com os 2!");

  $.ajax({
      url: "../controllers/carregaPaises.php",
      type: "POST",
      dataType:"json"
    }).error(function(data){
      alert("Não foi possível carregar os países.");
    }).done(function(data){
      if(data.length > 0){
        var select = document.getElementById("txPaisPie");
        var el = document.createElement("option");
        el.textContent = "Todos";
        el.value = "Todos";
        select.appendChild(el);
        for(var i = 0; i < data.length; i++) {
          var opt = data[i]["nome"];
          el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          select.appendChild(el);
        }

      } else {
        alert("Algo deu errado com os países!");
      }
    })
}

function populaEstadosPie(){
  var ddPaises = document.getElementById("txPaisPie");
  var selected = ddPaises.options[ddPaises.selectedIndex].value;
  var ddCidade = document.getElementById("txCidadePie");

  if (selected != "Todos") {
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
          var select = document.getElementById("txEstadoPie");
          select.options.length = 0;
          ddCidade.options.length = 0;

          var el = document.createElement("option");
          el.textContent = "Todos";
          el.value = "Todos";
          select.appendChild(el);
          for(var i = 0; i < data.length; i++) {
            // alert(data[i]["estado"]);
            var opt = data[i]["estado"];
            el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            select.appendChild(el);
          }
        } else {
          alert("Algo deu errado com os estados!");
        }
      })  
  } else {
    var select = document.getElementById("txEstadoPie");
    select.length = 0;
    ddCidade.length = 0;
  }

}

function populaCidadesPie(){
  var ddPaises = document.getElementById("txPaisPie");
  var sPais = ddPaises.options[ddPaises.selectedIndex].value;
  var ddEstados = document.getElementById("txEstadoPie");
  var sEstado = ddEstados.options[ddEstados.selectedIndex].value;

  if (sEstado != "Todos") {
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
          var select = document.getElementById("txCidadePie");
          select.options.length = 0;
          var el = document.createElement("option");
          el.textContent = "Todos";
          el.value = "Todos";
          select.appendChild(el);
          for(var i = 0; i < data.length; i++) {
            var opt = data[i]["nome"];
            el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            select.appendChild(el);
          }
        } else {
          alert("Algo deu errado com as cidades!");
        }
      })
  } else {
    var select = document.getElementById("txCidadePie");
    select.length = 0;
  }
}

function populaPaisesBar(){
  // alert("Algo deu errado com os 2!");

  $.ajax({
      url: "../controllers/carregaPaises.php",
      type: "POST",
      dataType:"json"
    }).error(function(data){
      alert("Não foi possível carregar os países.");
    }).done(function(data){
      if(data.length > 0){
        var select = document.getElementById("txPaisBar");
        var el = document.createElement("option");
        el.textContent = "Todos";
        el.value = "Todos";
        select.appendChild(el);
        for(var i = 0; i < data.length; i++) {
          var opt = data[i]["nome"];
          el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          select.appendChild(el);
        }

      } else {
        alert("Algo deu errado com os países!");
      }
    })
}

function populaEstadosBar(){
  var ddPaises = document.getElementById("txPaisBar");
  var selected = ddPaises.options[ddPaises.selectedIndex].value;
  var ddCidade = document.getElementById("txCidadeBar");

  if (selected != "Todos") {
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
          var select = document.getElementById("txEstadoBar");
          select.options.length = 0;
          ddCidade.options.length = 0;
          var el = document.createElement("option");
          el.textContent = "Todos";
          el.value = "Todos";
          select.appendChild(el);
          for(var i = 0; i < data.length; i++) {
            // alert(data[i]["estado"]);
            var opt = data[i]["estado"];
            el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            select.appendChild(el);
          }
        } else {
          alert("Algo deu errado com os estados!");
        }
      })
  } else {
    var select = document.getElementById("txEstadoBar");
    select.length = 0;
    ddCidade.length = 0;
  }
}

function populaCidadesBar(){
  var ddPaises = document.getElementById("txPaisBar");
  var sPais = ddPaises.options[ddPaises.selectedIndex].value;
  var ddEstados = document.getElementById("txEstadoBar");
  var sEstado = ddEstados.options[ddEstados.selectedIndex].value;

  if (sEstado != "Todos") {
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
          var select = document.getElementById("txCidadeBar");
          select.options.length = 0;
          var el = document.createElement("option");
          el.textContent = "Todos";
          el.value = "Todos";
          select.appendChild(el);
          for(var i = 0; i < data.length; i++) {
            var opt = data[i]["nome"];
            el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            select.appendChild(el);
          }
        } else {
          alert("Algo deu errado com as cidades!");
        }
      })
  } else {
    var select = document.getElementById("txCidadeBar");
    select.length = 0;
  }
}

function pieChartCallback(data, pieChart,pieChartNome) {

	var PieData = [];
	var colors = ["#5DA5DA", "#FAA43A", "#60BD68", "#F17CB0", "#DECF3F", "#B2912F", "#F15854", "#B276B2"];

	if(data.length > 0) {
		t="#"+pieChartNome;
		$(t).html("");
		for ($i = 0; $i < data.length; $i++) {
			PieData.push({
				value: (data[$i].valor) / 100,
				color: colors[$i % 8],
				highlight: colors[$i % 8],
				label: (data[$i].categoria)
			});
		}

		var pieOptions = {
			//Boolean - Whether we should show a stroke on each segment
			segmentShowStroke: true,
			//String - The colour of each segment stroke
			segmentStrokeColor: "#fff",
			//Number - The width of each segment stroke
			segmentStrokeWidth: 2,
			//Number - The percentage of the chart that we cut out of the middle
			percentageInnerCutout: 0, // This is 0 for Pie charts
			//Number - Amount of animation steps
			animationSteps: 100,
			//String - Animation easing effect
			animationEasing: "easeOutBounce",
			//Boolean - Whether we animate the rotation of the Doughnut
			animateRotate: true,
			//Boolean - Whether we animate scaling the Doughnut from the centre
			animateScale: true,
			//Boolean - whether to make the chart responsive to window resizing
			responsive: true,
			// Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
			maintainAspectRatio: true,
			//String - A legend template
			legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"
		};

		//Create pie or douhnut chart
		pieChart.Doughnut(PieData, pieOptions);

		} else {
			t="#"+pieChartNome;
			$(t).html("");
			var tab_body = document.getElementById(pieChartNome);
			var tr = document.createElement("tr");
			var td_categoria = document.createElement("td");
			td_categoria.colSpan = "5";
			td_categoria.innerHTML = "Não é possoíel gerar gráfico pois não hà movimentação";
			td_categoria.style.color = "green"; 
			tr.appendChild(td_categoria);
			tab_body.appendChild(tr);
		}
}

function loadGlobalPieChart(){

	// Destroi o grafico anterior, se houver
	if (typeof loadGlobalPieChart.pieChart != 'undefined') {
		$('#pieChart').replaceWith('<canvas id="pieChart"></canvas>');
	}

    // Get context with jQuery - using jQuery's .get() method.
	var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
	loadGlobalPieChart.pieChart = new Chart(pieChartCanvas);

	// Despesa ou receita?
	var isReceita = "0";
	if ($(".tipo:checked").val() == "receita") {
		isReceita = "1";
	}

	// Qual pais?
	var dPais = document.getElementById("txPaisPie");
	var txPais = dPais.options[dPais.selectedIndex].value;

	// Qual estado?
	var txEstado = "Todos";
	if (txPais != "Todos") {
		var dEstado = document.getElementById("txEstadoPie");
		txEstado = dEstado.options[dEstado.selectedIndex].value;
	}

	// Qual cidade?
	var txCidade = "Todos";
	if (txPais != "Todos" && txEstado != "Todos") {
		var dCidade = document.getElementById("txCidadePie");
		txCidade = dCidade.options[dCidade.selectedIndex].value;
	}

	// Qual a época?
	var dMes = document.getElementById("txMesPie");
	var txMes = dMes.options[dMes.selectedIndex].value;

	var dAno = document.getElementById("txAnoPie");
	var txAno = dAno.options[dAno.selectedIndex].value;

  // Qual faixa etária
  var dIdadeInicial = document.getElementById("txIdadeInicialPie");
  var txIdadeInicial = dIdadeInicial.options[dIdadeInicial.selectedIndex].value;

  var dIdadeFinal = document.getElementById("txIdadeFinalPie");
  var txIdadeFinal = dIdadeFinal.options[dIdadeFinal.selectedIndex].value;

  if (txIdadeInicial == "100+") {txIdadeInicial = 100};
  if (txIdadeFinal == "100+") {txIdadeFinal = 200};

  var today = new Date();
  var mm = today.getMonth()+1;
  var yyyy = today.getFullYear();
  var dd = today.getDate();
  
  if (dd < 10) { dd = "0" + dd};
  if (mm < 10) { mm = "0" + mm};

  var yyyyInicial = yyyy - txIdadeInicial;
  var yyyyFinal = yyyy - txIdadeFinal;

  var dateInicial = yyyyInicial + "-" + mm + "-" + dd;
  var dateFinal = yyyyFinal + "-" + mm + "-" + dd;

  // alert(dateInicial);
  // alert(dateFinal);



	// Para qual usuario?
	var email = getCookie("email");

	// Faz a solicitacao dos dados
	$.ajax({
		url: "../controllers/graficos.php",
		type: "POST",
		dataType:"json",
		data: {
			isReceita: isReceita,
      tipo: "pie",
			user: "",
			owner: email,
			pais: txPais,
			estado: txEstado,
			cidade: txCidade,
			mes: txMes,
			ano: txAno,
      idadeMin: dateInicial,
      idadeMax: dateFinal
		},
	}).error(function(data){
		alert("Não foi possível carregar o gráfico de pizza (global).");
	}).done(function(data) {
		pieChartCallback(data, loadGlobalPieChart.pieChart,"pie1")
	})
}

function loadUserPieChart(){

	// Destroi o grafico anterior, se houver
	if (typeof loadUserPieChart.pieChart != 'undefined') {
		$('#pieChart2').replaceWith('<canvas id="pieChart2"></canvas>');
	}

    // Get context with jQuery - using jQuery's .get() method.
	var pieChartCanvas = $("#pieChart2").get(0).getContext("2d");
	loadUserPieChart.pieChart = new Chart(pieChartCanvas);

	// Despesa ou receita?
	var isReceita = "0";
	if ($(".tipo:checked").val() == "receita") {
		isReceita = "1";
	}

	// Qual a época?
	var dMes = document.getElementById("txMesPie");
	var txMes = dMes.options[dMes.selectedIndex].value;

	var dAno = document.getElementById("txAnoPie");
	var txAno = dAno.options[dAno.selectedIndex].value;

  // // Qual faixa etária
  // var dIdadeInicial = document.getElementById("txIdadeInicialPie");
  // var txIdadeInicial = dIdadeInicial.options[dIdadeInicial.selectedIndex].value;

  // var dIdadeFinal = document.getElementById("txIdadeFinalPie");
  // var txIdadeFinal = dIdadeFinal.options[dIdadeFinal.selectedIndex].value;

  // if (txIdadeInicial == "100+") {txIdadeInicial = 100};
  // if (txIdadeFinal == "100+") {txIdadeFinal = 200};

  // var today = new Date();
  // var mm = today.getMonth()+1;
  // var yyyy = today.getFullYear();
  // var dd = today.getDate();
  
  // if (dd < 10) { dd = "0" + dd};
  // if (mm < 10) { mm = "0" + mm};

  // var yyyyInicial = yyyy - txIdadeInicial;
  // var yyyyFinal = yyyy - txIdadeFinal;

  // var dateInicial = yyyyInicial + "-" + mm + "-" + dd;
  // var dateFinal = yyyyFinal + "-" + mm + "-" + dd;

	// Para qual usuario?
	var email = getCookie("email");

	// Faz a solicitacao dos dados
	$.ajax({
		url: "../controllers/graficos.php",
		type: "POST",
		dataType:"json",
		data: {
			isReceita: isReceita,
      tipo: "pie",
			user: email,
			owner: email,
			pais: "Todos",
			estado: "Todos",
			cidade: "Todos",
			mes: txMes,
			ano: txAno,
      idadeMin: "2200-12-31",
      idadeMax: "1800-01-01"
		},
	}).error(function(data){
		alert("Não foi possível carregar o gráfico de pizza (user).");
	}).done(function(data) {
		pieChartCallback(data, loadUserPieChart.pieChart,"pie2")
	})
}

$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
  var target = $(e.target).attr("href") // activated tab
  if (target == "#piechart") {
    loadUserPieChart();
    loadGlobalPieChart();
  } else {
    loadBarChart();
  }
});

function barChartCallback(data, barChart) {
  var labels = [];
  var dadosGlobais = [];
  var dadosUser = [];
  if(data.length > 0) {
    for(var i = 0; i < data.length; i++) {
      labels.push(data[i].mes + "/" + data[i].ano);
      dadosGlobais.push((data[i].valor) / 100.0);
      dadosUser.push((data[i].userValor) / 100.0);
    }
    var areaChartData = {
        labels: labels,
        datasets: [
          {
            label: "Dados globais",
            fillColor: "rgba(210, 214, 222, 1)",
            strokeColor: "rgba(210, 214, 222, 1)",
            pointColor: "rgba(210, 214, 222, 1)",
            pointStrokeColor: "#c1c7d1",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: dadosGlobais
          },
          {
            label: "Dados do usuário",
            // fillColor: "rgba(60,141,188,0.9)",
            // strokeColor: "rgba(60,141,188,0.8)",
            // pointColor: "#3b8bba",
            // pointStrokeColor: "rgba(60,141,188,1)",
            // pointHighlightFill: "#fff",
            // pointHighlightStroke: "rgba(60,141,188,1)",
            data: dadosUser 
          }
        ]
      };

      var barChartData = areaChartData;
      barChartData.datasets[1].fillColor = "#00a65a";
      barChartData.datasets[1].strokeColor = "#00a65a";
      barChartData.datasets[1].pointColor = "#00a65a";
      var barChartOptions = {
        //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
        scaleBeginAtZero: true,
        //Boolean - Whether grid lines are shown across the chart
        scaleShowGridLines: true,
        //String - Colour of the grid lines
        scaleGridLineColor: "rgba(0,0,0,.05)",
        //Number - Width of the grid lines
        scaleGridLineWidth: 1,
        //Boolean - Whether to show horizontal lines (except X axis)
        scaleShowHorizontalLines: true,
        //Boolean - Whether to show vertical lines (except Y axis)
        scaleShowVerticalLines: true,
        //Boolean - If there is a stroke on each bar
        barShowStroke: true,
        //Number - Pixel width of the bar stroke
        barStrokeWidth: 2,
        //Number - Spacing between each of the X value sets
        barValueSpacing: 5,
        //Number - Spacing between data sets within X values
        barDatasetSpacing: 1,
        //String - A legend template
        legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].fillColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
        //Boolean - whether to make the chart responsive
        responsive: true,
        maintainAspectRatio: true
      };

      barChart.Bar(barChartData, barChartOptions);
    } else {
      alert("Nenhum Registro Encontrado.");
    }
}


function loadBarChart(){

//   // if (typeof loadBarChart.barChart != 'undefined') {
//   //   $('#barChart').replaceWith('<canvas id="barChart"></canvas>');
//   // }

  var barChartCanvas = $("#barChart").get(0).getContext("2d");
  var barChart = new Chart(barChartCanvas);

  // Qual a época?
  var dMesIni = document.getElementById("txMesInicialBar");
  var txMesIni = dMesIni.options[dMesIni.selectedIndex].value;

  var dAnoIni = document.getElementById("txAnoInicialBar");
  var txAnoIni = dAnoIni.options[dAnoIni.selectedIndex].value;

  var dMesFim = document.getElementById("txMesFinalBar");
  var txMesFim = dMesFim.options[dMesFim.selectedIndex].value;

  var dAnoFim = document.getElementById("txAnoFinalBar");
  var txAnoFim = dAnoFim.options[dAnoFim.selectedIndex].value;

  // Qual categoria?
  var dCategoria = document.getElementById("txCategoriaBar");
  var txCategoria = dCategoria.options[dCategoria.selectedIndex].value;

  // Qual faixa etaria?
  var dIdadeIni = document.getElementById("txIdadeInicialBar");
  var txIdadeIni = dIdadeIni.options[dIdadeIni.selectedIndex].value;

  var dIdadeFim = document.getElementById("txIdadeFinalBar");
  var txIdadeFim = dIdadeFim.options[dIdadeFim.selectedIndex].value;

  // Qual o local?
  var dPais = document.getElementById("txPaisBar");
  var txPais = dPais.options[dPais.selectedIndex].value;

  var txEstado = "Todos";
  if (txPais != "Todos") {
    var dEstado = document.getElementById("txEstadoBar");
    txEstado = dEstado.options[dEstado.selectedIndex].value;
  }

  // Qual cidade?
  var txCidade = "Todos";
  if (txPais != "Todos" && txEstado != "Todos") {
    var dCidade = document.getElementById("txCidadeBar");
    txCidade = dCidade.options[dCidade.selectedIndex].value;
  }

  // Para qual usuario?
  var email = getCookie("email");

  var today = new Date();
  var mm = today.getMonth()+1;
  var yyyy = today.getFullYear();
  var dd = today.getDate();
  
  if (dd < 10) { dd = "0" + dd};
  if (mm < 10) { mm = "0" + mm};

  var yyyyInicial = yyyy - txIdadeIni;
  var yyyyFinal = yyyy - txIdadeFim;

  var dateInicial = yyyyInicial + "-" + mm + "-" + dd;
  var dateFinal = yyyyFinal + "-" + mm + "-" + dd;

  // Faz a solicitacao dos dados
  $.ajax({
    url: "../controllers/graficos.php",
    type: "POST",
    dataType:"json",
    data: {
      tipo: "bar",
      user: email,
      owner: email,
      mesIni: txMesIni,
      anoIni: txAnoIni,
      mesFim: txMesFim,
      anoFim: txAnoFim,
      categoria: txCategoria,
      idadeIni: dateInicial,
      idadeFim: dateFinal,
      pais: txPais,
      estado: txEstado,
      cidade: txCidade
    }
  }).error(function(data){
    alert("Não foi possível carregar o gráfico de barras.");
  }).done(function(data) {
    document.getElementById("barChartLabel").innerHTML = "Dados para " + txCategoria;
    barChartCallback(data, barChart);
  })
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
      }
    })
  }
}
