<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<style type="text/css">
   		 <%@include file="style3.css" %>
  		</style>
		<title>Gestion des amis</title>

<script src="resource/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	function httpGet(theUrl) {
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", theUrl, false);
		xmlHttp.send(null);
		return xmlHttp.responseText;
	}

	var origSearchResult;
	
	function populatePage(jsonUser) {
		origSearchResult=document.getElementById("search_result");
		var user = document.getElementById("header");
		user.innerHTML += "<p>" + jsonUser.user_data.first_name
				+ jsonUser.user_data.last_name + "</p>" + "<p>"
				+ jsonUser.user_data.email + "</p>";

		var friends = document.getElementById("friends");
		var friendArray = jsonUser.friend_list;
		var out = "";
		var i;
		for (i = 0; i < friendArray.length; i++) {
			out += '<p><input type="checkbox" name="'+friendArray[i].user_data.id+'" value="'+friendArray[i].user_data.id+'">'
					+ '<font size="6">'
					+ friendArray[i].user_data.login
					+ '</font><p>';
		}
		friends.innerHTML += out;


	}
	
	function populateSearch(json){
		var friends = document.getElementById("search_result");
		var friendArray = json;
		 var out = "";
		    var i;
		    for(i = 0; i < friendArray.length; i++) {
		        out += '<p><input type="checkbox" name="'+friendArray[i].id+'" value="'+friendArray[i].id+'">'
		        + '<font size="6">' + friendArray[i].login + '</font></p>';

		    }
		    friends.innerHTML = origSearchResult.innerHTML;
		    friends.innerHTML += out;

	}
	
	function httpReq(){
		
		var response=httpGet("http://localhost:8080/popcom/friend?type=search&&query="+document.formulaire.query.value);
		populateSearch(JSON.parse(response));
	}

	
	var myObject;

	var fonc = function() {
		var response = httpGet("http://localhost:8080/popcom/friend?type=getAll");
		myObject = JSON.parse(response);
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
		<div id="header"> 
			<img id="img_pro" src="popcorn.png"  /> 
			</br><center><font size="15">POPCOM</font></center>
			<a href="main.jsp"> <font size=3> Retour &nbsp; </font></a>
		</div>
		
		<div id="contact_found"><center><font size="6">
			<form name="formulaire" action="javascript:httpReq()">
				<label for="champ1">Rechercher un ami :</label> </br>
				<input type="search" placeholder="pseudo" name="query">
				<input type="submit" value="Chercher">
			</form>  </font></center>
			<form id="search_result" name="search_result" action="friend" method="GET">
			<center><p><input type="submit" value="Ajouter"></p></center>
			</form>
		</div>
		
	<div id="liste_amis"><center><font size="6">Liste des amis</font>
	<form id="friends" action="friend" method="POST"><p><input type="submit" value="Supprimer"></p></form></center></div>
</div>
	</body>
</html>