package pages;

import helpers.Hash;
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

import objects.PcUser;
import constants.ServerInfo;
import controller.UserController;
import dao_objects.DbUser;

public class RegisterServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Something's happening in register");

		String login = request.getParameter("user");
		String password = request.getParameter("password");
		String birthday = request.getParameter("birthday");
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");

		PcUser user=new PcUser(new DbUser(login, Hash.encode(password), birthday, email, lastName, firstName));
		boolean b = new UserController().addUser(user);
		
		accepted(response, user);
	}
	
	private void accepted(HttpServletResponse response, PcUser user){
		System.out.println("accepted");
		response.setContentType("text/html");
		String site = new String("http://"+ServerInfo.SERVER_IP+":8080/popcom/main.jsp");
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
		String token = new SessionIdentifierGenerator().nextSessionId();
		Cookie cookie = new Cookie ("session_id", token);
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
		user.getUser().setToken(token);
		new UserController().updateUser(user);
	}



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}
	
}
