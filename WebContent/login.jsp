<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Connection </title>
<style type="text/css">
    <%@include file="style2.css" %>
    </style>
    
</head>
<body>

<div class="logo" id="logo"> </div>
	<div id="login">

		<h2><span class="fontawesome-lock"></span>POP'COM</h2>

		<form action="login" method="POST">

			<fieldset>

				<p><label for="pseudo">Pseudo</label></p>
				<p><input name="user" type="pseudo" id="pseudo" value="pseudo" onBlur="if(this.value=='')this.value='pseudo'" onFocus="if(this.value=='pseudo')this.value=''"></p>

				<p><label for="password">Mot de passe</label></p>
				<p><input name="password" type="password" id="password" value="Mot de passe" onBlur="if(this.value=='')this.value='Mot de passe'" onFocus="if(this.value=='Mot de passe')this.value=''"></p> 

				<p><input type="submit" value="Connexion"></p>
				
 <a href="formulaire.jsp">Nouvelle Inscription ?</a>
			</fieldset>

		</form>

	</div>


</body>
</html>