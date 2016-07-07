$(function () {
	var email = getCookie("email");
	if (email == "") {
		window.location.href = "../index.html";
	}
	loadPieChart();
	loadBarChart();
});

function loadPieChart(){
	//-------------
	//- PIE CHART -
	//-------------
	// Get context with jQuery - using jQuery's .get() method.
	var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
	var pieChart = new Chart(pieChartCanvas);
	var PieData = [
	  {
		value: 700,
		color: "#f56954",
		highlight: "#f56954",
		label: "Chrome"
	  },
	  {
		value: 500,
		color: "#00a65a",
		highlight: "#00a65a",
		label: "IE"
	  },
	  {
		value: 400,
		color: "#f39c12",
		highlight: "#f39c12",
		label: "FireFox"
	  },
	  {
		value: 600,
		color: "#00c0ef",
		highlight: "#00c0ef",
		label: "Safari"
	  },
	  {
		value: 300,
		color: "#3c8dbc",
		highlight: "#3c8dbc",
		label: "Opera"
	  },
	  {
		value: 100,
		color: "#d2d6de",
		highlight: "#d2d6de",
		label: "Navigator"
	  }
	];
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
	// You can switch between pie and douhnut using the method below.
	pieChart.Doughnut(PieData, pieOptions);

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
	document.cookie = cname + "=; Path=" + path + "; expires=Thu, 01 Jan 1970 00:00:01 GMT;";
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

	// window.location.href = "../index.html";
}