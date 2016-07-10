$(function () {
  var email = getCookie("email");
  if (email == "") {
    window.location.href = "../index.html";
  }
	loadBarChart();
  populaDropdowns();
});

function carregaInfos() {
  var nome = getCookie("nome");
  if (nome != "") {
    var dropdown = document.getElementById("dropdown");
    dropdown.innerHTML = "Olá, " + nome + "!";
  }
}

function aplicarFiltroBar() {

}

function aplicarFiltroPie() {

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

        var el = document.createElement("option");
        el.textContent = "Todos";
        el.value = "Todos";
        select.appendChild(el);
        var el2 = document.createElement("option");
        el2.textContent = "Todos";
        el2.value = "Todos";
        select2.appendChild(el2);

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
        var el = document.createElement("option");
        el.textContent = "Todos";
        el.value = "Todos";
        select.appendChild(el);
        var el2 = document.createElement("option");
        el2.textContent = "Todos";
        el2.value = "Todos";
        select2.appendChild(el2);
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
        var el = document.createElement("option");
        el.textContent = "Todos";
        el.value = "Todos";
        select.appendChild(el);
        var el2 = document.createElement("option");
        el2.textContent = "Todos";
        el2.value = "Todos";
        select2.appendChild(el2);
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
        var el = document.createElement("option");
        el.textContent = "Todos";
        el.value = "Todos";
        select.appendChild(el);
        var el2 = document.createElement("option");
        el2.textContent = "Todos";
        el2.value = "Todos";
        select2.appendChild(el2);
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

$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
  var target = $(e.target).attr("href") // activated tab
  if (target == "#piechart") {
    loadPieChart();
  } else {
    loadBarChart();
  }
});


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