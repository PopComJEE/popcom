package pages;

import helpers.Hash;
import helpers.JsonHelper;
import helpers.SessionIdentifierGenerator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.PcSession;
import objects.PcUser;
import constants.ServerInfo;
import controller.UserController;
import dao_objects.DbUser;

public class EditServlet extends HttpServlet {

	protected void processRequestGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Something's happening in edit");

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
			PcUser user= new UserController().getUserByToken(sessionId);
			if(user==null){
				System.out.println("refused");
				PrintWriter out = response.getWriter();
				out.print("{\"status\":\"refused\"}");
				out.flush();
			}else{
				System.out.println("accepted");
				PrintWriter out = response.getWriter();
				out.print(JsonHelper.toJsonObject(user.getUser()));
				out.flush();
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
		System.out.println("Something's happening in edit");

		String login = request.getParameter("user");
		String password = request.getParameter("password");
		String birthday = request.getParameter("birthday");
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");

		PcUser user=new PcUser(new DbUser(login, Hash.encode(password), birthday, email, lastName, firstName));

		Cookie[] cookies = request.getCookies();
		for(int i=0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("session_id")) {
				String sessionId = cookies[i].getValue();
				user.getUser().setToken(sessionId);
			}
		}
		
		
		boolean b = new UserController().updateUser(user);
		
		accepted(response, user);
	}
	
	private void accepted(HttpServletResponse response, PcUser user){
		System.out.println("accepted");
		response.setContentType("text/html");
		String site = new String("http://"+ServerInfo.SERVER_IP+":8080/popcom/main.jsp");
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
	}

}
