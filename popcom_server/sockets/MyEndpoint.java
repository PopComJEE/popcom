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

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import objects.PcSession;
import objects.PcUser;
import controller.SessionController;
import controller.UserController;
import dao.Dao_Message;
import dao_objects.DbMessage;
import dao_objects.DbUser;

@ServerEndpoint(value="/websocket", configurator = MyEndpointConfig.class)
public class MyEndpoint {

	private String mSessionId;

	@OnOpen
	public void open(Session session, EndpointConfig config) {
		mSessionId=config.getUserProperties().get("websocket_token").toString();
		System.out.println("session opened");
		System.out.println(session.getBasicRemote().toString());

		System.out.println(mSessionId);
	}

	@OnMessage
	public void onMessage(final Session session, final String chatMessage) {
		System.out.println("onMessage chat");
		System.out.println(chatMessage);
		String convId=actionsOnMessage(chatMessage);
		PcSession conversation=new SessionController().getSession(convId);
		ArrayList<DbUser> userList=conversation.getUserList();
		String[] tokenList = new String[userList.size()];
		for(int i=0;i<tokenList.length;i++){
			tokenList[i]=userList.get(i).getMessage();
		}
		try {
			for (Session s : session.getOpenSessions()) {
				String token=s.getUserProperties().get("websocket_token").toString();
				for(int i=0;i<tokenList.length;i++){
					if(tokenList[i].equals(token)){
						if (s.isOpen()) {
							s.getBasicRemote().sendText(chatMessage);
						}
					}
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String actionsOnMessage(String message){
		JsonObject obj = Json.createReader(new StringReader(message)).readObject();
		String userLogin = obj.getString("user");
		PcUser user = new UserController().getUserByLogin(userLogin);
		user.getUser().setMessage(mSessionId);
		new UserController().updateUser(user);
		try {
			DbMessage history;
			history = (DbMessage) new Dao_Message().get(obj.getString("session_id"));
			JsonArray array;
			JsonArrayBuilder builder=Json.createArrayBuilder();
			boolean b=false;
			if(history!=null){
				b=true;
				array=history.getHistory();
				for(int i=0;i<array.size();i++){
					builder.add(array.get(i));
				}
			}
			builder.add(obj);
			array=builder.build();
			history=new DbMessage();
			history.setHistory(array);
			history.setLastMessage(obj);
			history.setSessionId(obj.getString("session_id"));
			if(b)
			new Dao_Message().update(history);
			else
				new Dao_Message().add(history);	

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(mSessionId);

		return obj.getString("session_id");
	}

}
