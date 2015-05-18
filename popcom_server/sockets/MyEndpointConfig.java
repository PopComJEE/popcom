package sockets;

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
		HttpSession httpSession = null;
		while(httpSession==null){
			httpSession = (HttpSession)request.getHttpSession();
			System.out.println("httpSession null");
		}
		config.getUserProperties().put(HttpSession.class.getName(),httpSession);

	}

}