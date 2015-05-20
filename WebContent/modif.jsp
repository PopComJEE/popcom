<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Modification</title>
	<head>
		
		<style type="text/css">
    <%@include file="style.css" %>
    </style>
<script src="resource/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	function verif_form() {
		if (document.formulaire.email.value == "") {
			alert("Veuillez entrer votre adresse électronique!");
			document.formulaire.email.focus();
			return false;
		}
		if (document.formulaire.email.value.indexOf('@') == -1) {
			alert("Ce n'est pas une adresse électronique!");
			document.formulaire.email.focus();
			return false;
		}
		if (document.formulaire.last_name.value == "") {
			alert("Veuillez entrer votre nom!");
			document.formulaire.last_name.focus();
			return false;
		}
		if (document.formulaire.first_name.value == "") {
			alert("Veuillez entrer votre Prenom!");
			document.formulaire.first_name.focus();
			return false;
		}
		if (document.formulaire.birthday.value == "") {
			alert("Veuillez entrer votre Date de naissance!");
			document.formulaire.birthday.focus();
			return false;
		}
		if (document.formulaire.user.value == "") {
			alert("Veuillez entrer votre Pseudo!");
			document.formulaire.user.focus();
			return false;
		}
		if (document.formulaire.password.value == "") {
			alert("Veuillez entrer votre mot de passe!");
			document.formulaire.password.focus();
			return false;
		}
		if (document.formulaire.password2.value == "") {
			alert("Resaissisez votre mot de passe!");
			document.formulaire.password2.focus();
			return false;
		}

		if (document.formulaire.password.value != document.formulaire.password2.value) {
			alert("Veuillez saisir le même mot de passe");
			return false;
		}
		
		document.getElementById('user').disabled = false;
	}
	
	function populatePage(json){
		document.formulaire.user.value = json.login;
		document.formulaire.email.value = json.email;
		document.formulaire.last_name.value = json.last_name;
		document.formulaire.first_name.value = json.first_name;
		document.formulaire.birthday.value = json.birthday;
	}

	function httpGet(theUrl) {
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", theUrl, false);
		xmlHttp.send(null);
		return xmlHttp.responseText;
	}
	
	var fonc = function() {
		var response = httpGet("http://localhost:8080/popcom/edit");
		var myObject = JSON.parse(response);
		if (myObject.status == "refused") {
			window.location.replace("http://localhost:8080/popcom/login.jsp");
		} else {
			populatePage(myObject);
		}
	};
	$(document).ready(fonc);
</script>



</head>
	
	<body>
	
		<fieldset id="cadreinscrip">
		<div class="form-style">
		<h1>Modification</h1>

		<form name="formulaire" action="edit" method=POST onSubmit="return verif_form()">
				
				<tr>
					<p id="p1">Pseudo : </p>
					<td><input id="user" type=text name="user" disabled="disabled"></td>
				</tr>
				<tr>
					<p id="p1">Mail: </p>
					<td><input id="bouton2" type=text name="email"></td>
				</tr>
				<tr>
					<p id="p1">Nom : </p>
					<td><input id="bouton2" type=text name="last_name"></td>
				</tr>
				
				<tr>	
					<p id="p1">Prénom : </p>
					<td><input id="bouton2" type=text name="first_name"></td>
				</tr>	
				<tr>
					<p id="p1"> Date de Naissance </p>
					<td><input id="bouton2" type=date name="birthday"></td>
				</tr>
				<tr>
					<p id="p1">Mot de passe : </p>
					<td><input id="bouton2" type=password name="password"></td>
				</tr>
				<tr>
					<p id="p1">Ressaisissez mot de passe : </p>
					<td><input id="bouton2" type=password name="password2"></td>
				</tr>
				
				<tr>
				
					<td><input class="bouton" TYPE="submit" NAME="submit" VALUE="Modifier" > </td>
				</tr>
		</form>
		</div>
		</fieldset>
		
	</body>
</html>