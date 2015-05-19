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

	
	function addMessage(userLogin, message){
		var user = document.getElementById("response");
		user.innerHTML += "<tr><td>"+userLogin+":</td><td>"+decodeURI(message)+"</td></tr>"
	}
	
	function onMessageReceived(evt) {
		var json = JSON.parse(evt.data)
		addMessage(json.user,json.message);
	}

	function sendMessage(user, message) {
		var msg = '{"user":"'+user+'","message":"'+message+'","session_id":"'+getUrlParameter("session_id")+'"}';
		wsocket.send(msg);
		$('#message').val('');
	}

	function connectToChatserver() {
		wsocket = new WebSocket(serviceLocation);
		wsocket.onmessage = onMessageReceived;
		alert("connectToChatServer");
	}

	function httpGet(theUrl) {
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", theUrl, false);
		xmlHttp.send(null);
		return xmlHttp.responseText;
	}

	function populatePage(jsonHistory) {
		for(var i=0;i<jsonHistory.history.length;i++){
			addMessage(jsonHistory.history[i].user,jsonHistory.history[i].message);
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
		alert("parse ok");
		if (myObject.status == "refused") {
			window.location.replace("http://localhost:8080/popcom/login.jsp");
		} else {
			populatePage(myObject);
			alert("populate done");
			connectToChatserver();
			$('#chat_window').submit(function(evt) {
				evt.preventDefault();
				var text=encodeURI($('#message').val());
				sendMessage(myObject.user,text);
			});
		}
	};
	$(document).ready(fonc);
</script>
</head>
<body>
	<div class="form-style">
		<table id="response">
		</table>
		<table>
			<FORM id="chat_window">
				<tr>
					<TEXTAREA id="message" name="message" rows=3 cols=90%></TEXTAREA>
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