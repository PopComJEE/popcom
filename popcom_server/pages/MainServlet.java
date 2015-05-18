package pages;

import helpers.Helper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.PcSession;
import objects.PcUser;
import controller.UserController;
import dao_objects.DbUser;

public class MainServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Something's happening in main");

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
			System.out.println("accepted");
			String type = request.getParameter("type");
			if(type.equalsIgnoreCase("getAll")){
				PcUser user= new UserController().getUserByToken(sessionId);
				

				PrintWriter out = response.getWriter();
				out.print(Helper.toJsonObject(user));
				out.flush();
			}
		}
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
