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

public class LoginServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Something's happening in login");
		String userLogin = request.getParameter("user");
		String pass = request.getParameter("password");

		PcUser tempUser= new UserController().getUserByLogin(userLogin);
		if(tempUser==null){
			System.out.println("User unknown");
			refused(response);
		}else if(!Hash.compare(pass, tempUser.getUser().getPassword())){
			System.out.println("Wrong password");
			refused(response);
		}else{
			System.out.println("Login OK");
			accepted(response, tempUser);
		}
	}

	private void refused(HttpServletResponse response){
		System.out.println("refused");
		response.setContentType("text/html");
		String site = new String("http://"+ServerInfo.SERVER_IP+":8080/popcom/login.jsp");
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
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
		System.out.println("POST request detected");
		processRequest(req, resp);
	}

}
