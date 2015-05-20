<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
    <%@include file="style.css" %>
    </style>
<title> Profil </title>
<script src="resource/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	var wsocket;
	var serviceLocation = "ws://localhost:8080/popcom/websocket";
	var $chatWindow;

	function onMessageReceived(evt) {
		for ( var i in JSON.parse(evt.data)) {
			addSession(i.id_session);
		}
	}

	function addSession(session) {
		var $messageLine = $('<tr><td>' + session + '</td></tr>');
		$chatWindow.append($messageLine);
	}

	function sendMessage() {
		var msg = '{"type":"getUserSessionList"}';
		wsocket.send(msg);
	}

	function connectToChatserver() {
		wsocket = new WebSocket(serviceLocation);
		wsocket.onmessage = onMessageReceived;
		wsocket.onopent = sendMessage();
	}

	function httpGet(theUrl) {
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", theUrl, false);
		xmlHttp.send(null);
		return xmlHttp.responseText;
	}
	
	function populatePage(jsonUser) {
		var user = document.getElementById("header");
		user.innerHTML += 
			"<p>" + jsonUser.user_data.first_name + jsonUser.user_data.last_name + "</p>"
		  + "<p>" + jsonUser.user_data.email + "</p>";
		  
		
		var friends = document.getElementById("zone_contact_active");
		var friendArray = jsonUser.friend_list;
		 var out = "";
		    var i;
		    for(i = 0; i < friendArray.length; i++) {
		        out += '<p><input type="checkbox" name="'+friendArray[i].user_data.id+'" value="'+friendArray[i].user_data.id+'">'
		        + '<font size="6">' + friendArray[i].user_data.login + '</font></p>';
		    }
		    friends.innerHTML += out;
		    
		    
		    var conversations = document.getElementById("zone_convers");
			var sessionArray = jsonUser.session_list;
		    var out = "";
		    var i;
		    for(i = 0; i < sessionArray.length; i++) {
		        out += '<a href="fenetre.jsp?session_id='+sessionArray[i].id_session+'"><p><font size="6">Conversation ' + sessionArray[i].id_session + '</font></p>';
		        var userList=sessionArray[i].user_list
		        for(j=0;j<userList.length;j++) {
		        	out += '<p><font size="6">' + sessionArray[i].user_list[j].login + '</font></p>';
		        }
		       	out += '<p><font size="3">' +sessionArray[i].last_message.user +' : '+decodeURI(sessionArray[i].last_message.message) + '</font></p></a><br>';
		    }
		    conversations.innerHTML += out;
		    
	}
	
	var myObject;

	var fonc = function() {
		var response = httpGet("http://localhost:8080/popcom/main?type=getAll");
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
		<center><font size="15">
			Profil 
			<a href="logout">Déconnection<img id="modification" src="exit.png">
			</a> <br>
			<a href="modif.jsp">Modifier compte<img id="modification" src="modif.png">
			</a>
		</font></center>
		<a href="gestion_amis.jsp"><font size=3>Gestion des amis &nbsp; &nbsp;</font></a>
		
	</div>
	
<div id="zone_contact"><center><font size="6">Contacts</font></center><br>
<form id="zone_contact_active" action="chat" method="POST"><p><input type="submit" value="Chatter"></p></form></div>
<div id="zone_convers"><center><font size="6">Conversations</font></center></div>


</body>
</html>

