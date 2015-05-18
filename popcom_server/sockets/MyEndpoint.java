/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package sockets;

import helpers.Helper;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import objects.PcSession;
import controller.UserController;

@ServerEndpoint(value="/websocket")
public class MyEndpoint {


    @OnOpen
    public void open(Session session, EndpointConfig config) {
		System.out.println("onOpen chat");
		System.out.println("ws session id : "+session.getId());
		System.out.println("ws config user_id : "+config.getUserProperties().get("ID"));
    }
//	@OnOpen
//	public void open(final Session session) {
//		System.out.println("session openend");
//		System.out.println(session.getBasicRemote().toString());
//	}

//	@OnMessage
//	public String echoText(String name) {
//		return name+" Ellington";
//	}

//	@OnMessage
//	public void echoBinary(byte[] data, Session session) throws IOException {
//		System.out.println("echoBinary: " + data);
//		StringBuilder builder = new StringBuilder();
//		for (byte b : data) {
//			builder.append(b);
//		}
//		System.out.println(builder);
//		session.getBasicRemote().sendBinary(ByteBuffer.wrap(data));
//	}

	@OnMessage
	public void onMessage(final Session session, final String chatMessage) {
		System.out.println("onMessage chat");
		System.out.println(chatMessage);
		System.out.println("onMessage ws session id : "+session.getId());
		try {
			session.getBasicRemote().sendText(interpreteMessage(chatMessage));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
//		try {
//			for (Session s : session.getOpenSessions()) {
//				if (s.isOpen()) {
//					s.getBasicRemote().sendText(chatMessage);
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private String interpreteMessage(String message) throws IOException{
		JsonObject obj = Json.createReader(new StringReader(message))
				.readObject();
		String type = obj.getString("type");
		switch (type) {
		case "getUserSessionList":
//			HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
//			String user_id = httpSession.getAttribute("ID").toString();
			ArrayList<PcSession> sessionList = new UserController().getUser("1").getSessionList();
			JsonArrayBuilder jBuilder = Json.createArrayBuilder();
			for(PcSession s : sessionList){
				jBuilder.add(Helper.toJsonObject(s));
			}
			JsonArray json = jBuilder.build();

			System.out.println(json.toString());
			return json.toString();
		default:
			break;
		}
		return null;
	}
	
}
