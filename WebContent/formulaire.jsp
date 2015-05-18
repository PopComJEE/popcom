<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title> Formulaire d'inscription </title>
	<head>
		
		<style type="text/css">
    <%@include file="style.css" %>
    </style>
 <script type="text/javascript">

function verif_form()
{
 if(document.formulaire.Nom.value == "")  {
   alert("Veuillez entrer votre nom!");
   document.formulaire.Nom.focus();
   return false;
  }
 if(document.formulaire.Prenom.value == "")  {
	   alert("Veuillez entrer votre Prenom!");
	   document.formulaire.Prenom.focus();
	   return false;
	  }
 if(document.formulaire.Date_n.value == "") {
   alert("Veuillez entrer votre Date de naissance!");
   document.formulaire.Date_n.focus();
   return false;
  }
 if(document.formulaire.Mail.value == "") {
   alert("Veuillez entrer votre adresse électronique!");
   document.formulaire.Mail.focus();
   return false;
  }
 if(document.formulaire.Mail.value.indexOf('@') == -1) {
   alert("Ce n'est pas une adresse électronique!");
   document.formulaire.Mail.focus();
   return false;
  }
 if(document.formulaire.Pseudo.value == "")  {
	   alert("Veuillez entrer votre Pseudo!");
	   document.formulaire.Pseudo.focus();
	   return false;
	  }
 if(document.formulaire.Password.value == "")  {
	   alert("Veuillez entrer votre mot de passe!");
	   document.formulaire.Password.focus();
	   return false;
	  }
 if(document.formulaire.Password2.value == "")  {
	   alert("Resaissisez votre mot de passe!");
	   document.formulaire.Password2.focus();
	   return false;
	  }
 
 if(document.formulaire.Password.value != document.formulaire.Password2.value)  {
	   alert("Veuillez saisir le même mot de passe");
	   return false;
	  }
}

</script>


    
	</head>
	
	<body>
	
		<fieldset id="cadreinscrip">
		<div class="form-style">
		<h1>Inscription</h1>

		<form name="formulaire" action="connexion.jsp" method=POST onSubmit="return verif_form()">
		</br>
		<tr>
		<select name="civile"> 
			<option>Madame
			<option>Mademoiselle
			<option>Monsieur 
		</select> 
		</tr>
			
				<tr>
					<p id="p1">Nom : </p>
					<td><input id="bouton2" type=text name="Nom"></td>
				</tr>
				
				<tr>	
					<p id="p1">Prénom : </p>
					<td><input id="bouton2" type=text name="Prenom"></td>
				</tr>	
				<tr>
					<p id="p1"> Date de Naissance </p>
					<td><input id="bouton2" type=date name="Date_n"></td>
				</tr>
				<tr>
					<p id="p1">Mail: </p>
					<td><input id="bouton2" type=text name="Mail"></td>
				</tr>
				<tr>
					<p id="p1">Pseudo : </p>
					<td><input id="bouton2" type=text name="Pseudo"></td>
				</tr>
				<tr>
					<p id="p1">Mot de passe : </p>
					<td><input id="bouton2" type=password name="Password"></td>
				</tr>
				<tr>
					<p id="p1">Ressaisissez mot de passe : </p>
					<td><input id="bouton2" type=password name="Password2"></td>
				</tr>
				
				<tr>
				
					<td><input class="bouton" TYPE="submit" NAME="submit" VALUE="Inscription" Onclick="mail.php"> </td>
				</tr>
		</form>
		</div>
		</fieldset>
		
	</body>
</html>