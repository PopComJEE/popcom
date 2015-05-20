package pages;

import helpers.JsonHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.PcUser;
import constants.ServerInfo;
import controller.FriendController;
import controller.SessionController;
import controller.UserController;

public class FriendServlet extends HttpServlet {

	protected void processRequestGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Something's happening in friend");

		response.setContentType("application/json;charset=UTF-8");
		Cookie[] cookies = request.getCookies();
		String sessionId = null;
		boolean foundSession=false;
		for(int i=0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("session_id")) {
				sessionId = cookies[i].getValue();
				foundSession=true;
			}
		}

		if(!foundSession){
			System.out.println("refused");
			PrintWriter out = response.getWriter();
			out.print("{\"status\":\"refused\"}");
			out.flush();
		}else{
			String type = request.getParameter("type");
			PcUser user= new UserController().getUserByToken(sessionId);
			if(type!=null && type.equalsIgnoreCase("getAll")){
				if(user==null){
					System.out.println("refused");
					PrintWriter out = response.getWriter();
					out.print("{\"status\":\"refused\"}");
					out.flush();
				}else{
					System.out.println("accepted");
					PrintWriter out = response.getWriter();
					out.print(JsonHelper.toJsonObject(user));
					out.flush();
				}
			}else if(type!=null && type.equalsIgnoreCase("search")){
				String query=request.getParameter("query");
				ArrayList<PcUser> result = new UserController().searchUser(query);
				ArrayList<String> al=new ArrayList<String>();
				for(int i=0;i<result.size();i++){
					boolean b=true;
					for(PcUser u:user.getFriendList()){
						if(result.get(i).getUser().getId().equals(user.getUser().getId())){
							b=false;
							break;
						}else if(result.get(i).getUser().getId().equals(u.getUser().getId())){
							b=false;
							break;
						}
					}
					if(b)
						al.add(JsonHelper.toJsonObject(result.get(i).getUser()).toString());
				}

				PrintWriter out = response.getWriter();
				out.print("[");
				for(int i=0;i<al.size()-1;i++){
					out.print(al.get(i)+",");
				}
				out.print(al.get(al.size()-1));
				out.print("]");
				out.flush();
			}else{
				System.out.println("FRIEND ADD");
				Enumeration<String> parameterNames = request.getParameterNames();
				while (parameterNames.hasMoreElements()) {
					String paramName = parameterNames.nextElement();
					new FriendController().addFriend(user, paramName);
					new FriendController().addFriend(paramName, user.getUser().getId());
				}
				accepted(response);
			}
		}
	}



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequestGet(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doPost");
		processRequestPost(req, resp);
	}

	protected void processRequestPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Something's happening in friend");
		System.out.println("FRIEND REMOVE");

		Cookie[] cookies = request.getCookies();
		String sessionId = null;
		boolean foundSession=false;
		for(int i=0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("session_id")) {
				sessionId = cookies[i].getValue();
				foundSession=true;
			}
		}
		PcUser user;
		if(!foundSession){
			System.out.println("refused");
			PrintWriter out = response.getWriter();
			out.print("{\"status\":\"refused\"}");
			out.flush();
		}else{
			String type = request.getParameter("type");
			user = new UserController().getUserByToken(sessionId);
			if(user==null){
				System.out.println("refused");
				PrintWriter out = response.getWriter();
				out.print("{\"status\":\"refused\"}");
				out.flush();
			}else{
				Enumeration<String> parameterNames = request.getParameterNames();
				while (parameterNames.hasMoreElements()) {
					String paramName = parameterNames.nextElement();
					new FriendController().removeFriend(user, paramName);
					new FriendController().removeFriend(paramName, user.getUser().getId());
				}
				accepted(response);
			}
		}
	}

	private void accepted(HttpServletResponse response){
		System.out.println("accepted");
		response.setContentType("text/html");
		String site = new String("http://"+ServerInfo.SERVER_IP+":8080/popcom/gestion_amis.jsp");
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
	}

}
