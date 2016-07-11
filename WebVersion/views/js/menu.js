$(function () {
	var email = getCookie("email");
	if (email == "") {
		window.location.href = "../index.html";
	}
	loadPieChart();
	loadBarChart();
});

function pieChartCallback(data, pieChart) {

	var PieData = [];
	var colors = ["#5DA5DA", "#FAA43A", "#60BD68", "#F17CB0", "#DECF3F", "#B2912F", "#F15854", "#B276B2"];

	if(data.length > 0) {
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
			// alert("Erro na geração do gráfico de pizza.");
		}
}

function loadPieChart(){
	//-------------
	//- PIE CHART -
	//-------------
	// Get context with jQuery - using jQuery's .get() method.
	var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
	var pieChart = new Chart(pieChartCanvas);

	// Para qual usuario?
	var email = getCookie("email");

	var tipo = ["despesa", "receita"];

	var cat = tipo[Math.floor(Math.random() * 2)];

	$.ajax({
      url: "../controllers/carregaPieChartDashboard.php",
      type: "POST",
      dataType:"json",
      data: {
			user: email,
			tipo: cat
		},
    }).error(function(data){
      // alert("Não foi possível carregar as categorias.");
    }).done(function(data){

    	// var pieChartName = document.getElementById("pieChartName");

    	// var date = data[0].split("-");

    	// var meses = ["Janeiro","Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];

    	// pieChartName.value = "Balanço de " + meses[date[1]] + " de " + date[0];

      	// pieChartCallback(data.splice(0,1), pieChart);
      	pieChartCallback(data, pieChart);
    });

    $.ajax({
      url: "../controllers/carregaUltimoMes.php",
      type: "POST",
      dataType:"json",
      data: {
			user: email,
		},
    }).error(function(data){
      // alert("Não foi possível carregar as categorias.");
    }).done(function(data){

    	var pieChartName = document.getElementById("pieChartName");


    	var date = data[0].mes.split("-");
    	// alert(date[1]);

    	var meses = ["Janeiro","Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];
		rd = "Receitas";
    	if (cat == "despesa") {
    		rd = "Despesas";
    	};
    	pieChartName.innerHTML =  rd + " de " + meses[parseInt(date[1]) - 1] + " de " + date[0];

      	// pieChartCallback(data.splice(0,1), pieChart);
      	// pieChartCallback(data, pieChart);
    });

}

function loadBarChart(){
	//-------------
	//- BAR CHART -
	//-------------

	var areaChartData = {
	  labels: ["January", "February", "March", "April", "May", "June", "July"],
	  datasets: [
		{
		  label: "Electronics",
		  fillColor: "rgba(210, 214, 222, 1)",
		  strokeColor: "rgba(210, 214, 222, 1)",
		  pointColor: "rgba(210, 214, 222, 1)",
		  pointStrokeColor: "#c1c7d1",
		  pointHighlightFill: "#fff",
		  pointHighlightStroke: "rgba(220,220,220,1)",
		  data: [65, 59, 80, 81, 56, 55, 40]
		},
		{
		  label: "Digital Goods",
		  fillColor: "rgba(60,141,188,0.9)",
		  strokeColor: "rgba(60,141,188,0.8)",
		  pointColor: "#3b8bba",
		  pointStrokeColor: "rgba(60,141,188,1)",
		  pointHighlightFill: "#fff",
		  pointHighlightStroke: "rgba(60,141,188,1)",
		  data: [28, 48, 40, 19, 86, 27, 90]
		}
	  ]
	};


	var barChartCanvas = $("#barChart").get(0).getContext("2d");
	var barChart = new Chart(barChartCanvas);
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
}

function uploadXml() {
	var fileSelect = document.getElementById("input01");
	var files;
	var file;
	var formData = new FormData();

	files = fileSelect.files;
	file = files[0];

	var username = getCookie("email");

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

function carregaInfos() {
	var nome = getCookie("nome");
	if (nome != "") {
		var textBemVindo = document.getElementById("textbemvindo");
		textBemVindo.innerHTML = "Bem vindo, " + nome + "!";

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
			}
		})
	}
}
