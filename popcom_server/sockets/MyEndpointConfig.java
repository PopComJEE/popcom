package sockets;

import helpers.SessionIdentifierGenerator;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class MyEndpointConfig extends ServerEndpointConfig.Configurator {

	@Override
	public void modifyHandshake(ServerEndpointConfig config, 
			HandshakeRequest request, 
			HandshakeResponse response)
	{
		config.getUserProperties().put("websocket_token",new SessionIdentifierGenerator().nextSessionId());
	}

}