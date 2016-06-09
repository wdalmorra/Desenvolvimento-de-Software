<?php

// A sessão precisa ser iniciada em cada página diferente
if (!isset($_SESSION)) session_start();


// Verifica se não há a variável da sessão que identifica o usuário
if (!isset($_SESSION['UsuarioID']) ) {
    // Destrói a sessão por segurança
    session_destroy();
    // Redireciona o visitante de volta pro login
    header("Location: index.php"); exit;
}

?>

<!-- DOCTYPE -->
<!DOCTYPE html>
<html lang="en">
<head>
  <title>DreFinanças</title>
 <!-- Required meta tags always come first -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css" integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd" crossorigin="anonymous">
    <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="menu.css">


    <script>
        function upload_file() {
            var fileSelect = document.getElementById("input01");
            var files;
            var file;
            var user;
        
            var formData = new FormData();
        
            files = fileSelect.files;
            file = files[0];

            user = '<?php echo $_SESSION['UsuarioID']; ?>';

            formData.append('file', file);
            formData.append('user', user);
            // document.getElementById("file-chooser").innerHTML = file.name;
            
            xmlhttp=new XMLHttpRequest();
                
            xmlhttp.open('POST',"upload.php",true);
            xmlhttp.send(formData);
        }
        
    </script>
</head>
<body>
    <nav class="navbar navbar-light bg-faded" id = "nav_bar">
      <a class="navbar-brand" href="#">DreFinanças</a>
    </nav>
  
    <div class = "col-xs-2" id="wrapper">

        <!-- Sidebar -->
        <div id="sidebar-wrapper">
            <ul class="nav nav-pills sidebar-nav nav-stacked left-menu" id="stacked-menu">
                <li class="sidebar-brand">
                    <a href="#">
                        Menu
                    </a>
                </li>
                <li>
                  <a href="#" data-target="#item1" data-toggle="collapse" data-parent="#stacked-menu">Visualizar Meses<span class="caret arrow"></span></a>
                  <ul class="nav nav-stacked collapse left-submenu" id="item1">
                    <li><a class = "sidebar-submenu" href="#">Janeiro/2016</a></li>
                    <li><a class = "sidebar-submenu" href="#">Fevereiro/2016</a></li>
                  </ul>
                </li>
                <li>
                  <a href="#" data-target="#item2" data-toggle="collapse" data-parent="#stacked-menu">Submeter Arquivos<span class="caret arrow"></span></a>          
                  <ul class="nav nav-pills nav-stacked collapse" id="item2">
                    <li><a class = "sidebar-submenu" href="#">Enviar Arquivo</a></li>
                  </ul>
                </li>
                <li>
                  <a href="#" data-target="#item3" data-toggle="collapse" data-parent="#stacked-menu">Relatórios<span class="caret arrow"></span></a>          
                  <ul class="nav nav-pills nav-stacked collapse" id="item3">
                    <li><a class = "sidebar-submenu" href="#">Histograma</a></li>
                    <li><a class = "sidebar-submenu" href="#">Gráfico de Pizza</a></li>
                  </ul>
                </li>
                <li>
                  <a href="#" data-target="#item4" data-toggle="collapse" data-parent="#stacked-menu">Minha Conta<span class="caret arrow"></span></a>          
                  <ul class="nav nav-pills nav-stacked collapse" id="item4">
                    <li><a class = "sidebar-submenu" href="#">Alterar Senha</a></li>
                    <li><a class = "sidebar-submenu" href="#">Log out</a></li>
                  </ul>
                </li>
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->
    </div>
    <div class="col-xs-10">
        <div class="well">
            <div>
                <h1 id= "titulo">Escolha um arquivo:</h1>
            </div>
            <div class="row" id = "file-chooser">
                <div class="col-xs-3">
                    <div class="form-group">
                        <input type="file" name="example" id="input01">
                    </div>
                </div>
                <div class="col-xs-2">
                    <div class="form-group">
                        <button id="b1" type="button" onClick="upload_file()" class="btn btn-primary">
                            <span class="glyphicon glyphicon-ok"></span> Enviar
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- JavaScript: placed at the end of the document so the pages load faster -->
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/js/bootstrap.min.js" integrity="sha384-vZ2WRJMwsjRMW/8U7i6PWi6AlO1L79snBrmgiDpgIWJ82z8eA5lenwvxbMV1PAh7" crossorigin="anonymous"></script>
</body>
</html>