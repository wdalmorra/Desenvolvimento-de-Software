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
    <link rel="stylesheet" href="login.css">

</head>
<body>
	

	<div id="fullscreen_bg" class="fullscreen_bg"/>

	<div class="container">

		
	    <form class="form-signin" action="validacao.php" method="post" >
			<fieldset>
				<h1 class="form-signin-heading text-muted">DreFinanças</h1>
				<input type="text" class="form-control" name="usuario" placeholder="Email address" required="" autofocus="">
				<input type="password" class="form-control" name="senha" placeholder="Password" required="">
				<button class="btn btn-lg btn-primary btn-block" type="submit">
					Sign In
				</button>
		
			</fieldset>	
			<div>
	            <button id="login_lost_btn" type="button"  class="btn btn-link a">Lost Password? </button>
	            <button id="login_register_btn" type="button" onClick="GoRegistre()"  class="btn btn-link a">Register</button>
	        </div>
		</form>
		

	</div>

  <!-- JavaScript: placed at the end of the document so the pages load faster -->
  <!-- jQuery library -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>

  <!-- Latest compiled JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/js/bootstrap.min.js" integrity="sha384-vZ2WRJMwsjRMW/8U7i6PWi6AlO1L79snBrmgiDpgIWJ82z8eA5lenwvxbMV1PAh7" crossorigin="anonymous"></script>

 </body>
</html>
<script type="text/javascript">
function GoRegistre()
{
location.href="register.php"
}
</script>