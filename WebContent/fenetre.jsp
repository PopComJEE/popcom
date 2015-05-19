<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fenetre conversation</title>
<style type="text/css">
    <%@include file="style.css" %>
    </style>
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
		alert("connectToChatServer");
	}

	function httpGet(theUrl) {
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", theUrl, false);
		xmlHttp.send(null);
		return xmlHttp.responseText;
	}
	
	function addMessage(userLogin, message){
		var user = document.getElementById("response");
		user.innerHTML += "<tr><td>"+userLogin+":</td><td>"+message+"</td></tr>"
	}

	function populatePage(jsonHistory) {
		for(var i=0;i<jsonHistory.length;i++){
			addMessage(jsonHistory[i].user,jsonHistory[i].message);
		}
	}

	function getUrlParameter(sParam) {
		var sPageURL = window.location.search.substring(1);
		var sURLVariables = sPageURL.split('&');
		for (var i = 0; i < sURLVariables.length; i++) {
			var sParameterName = sURLVariables[i].split('=');
			if (sParameterName[0] == sParam) {
				return sParameterName[1];
			}
		}
	}

	var fonc = function() {
		var response = httpGet("http://localhost:8080/popcom/chat?type=getAll&&session_id="
				+ getUrlParameter("session_id"));
		alert(response);
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
	<div class="form-style">
		<table id="response" class="table table-bordered">
		</table>
		<table>
			<FORM>
				<tr>
					<TEXTAREA name="message" rows=5 cols=90%></TEXTAREA>
					<p>
						<input type="submit" style="width: 90px; height: 50px;"
							align="right" value="Envoyer">
					</p>
				</tr>
			</FORM>
		</table>
	</div>
</body>
</html>