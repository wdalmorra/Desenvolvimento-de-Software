var masterPath = "/"
//var masterPath = "/WebVersion/"

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
	var isChrome = !!window.chrome && !!window.chrome.webstore;

	if(isChrome) {
		document.cookie = cname + "=; Path=" + masterPath + "; Expires=" + new Date(0).toGMTString();
	} else {
		document.cookie = cname + "=; Path=" + masterPath + "; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
	}
}

function criaCookie(email, nome){
	var path = "/WebVersion/";
	deletaCookie("email");
	deletaCookie("nome");
	document.cookie = "email=" + email + "; Path=" + masterPath;
	document.cookie = "nome=" + nome + "; Path=" + masterPath;
}
