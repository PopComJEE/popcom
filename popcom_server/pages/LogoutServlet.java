package pages;

import helpers.Hash;
import helpers.SessionIdentifierGenerator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.PcUser;
import constants.ServerInfo;
import controller.UserController;

public class LogoutServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Something's happening in logout");

			logout(request,response);
	}

	private void logout(HttpServletRequest request, HttpServletResponse response){
		
		Cookie[] cookies = request.getCookies();
		for(int i=0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("session_id")) {
				//suppress cookie from database
				PcUser user= new UserController().getUserByToken(cookie.getValue());
				user.getUser().setToken("");
				new UserController().updateUser(user);
				break;
			}
		}
		
		response.setContentType("text/html");
		String site = new String("http://"+ServerInfo.SERVER_IP+":8080/popcom/login.jsp");
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
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
