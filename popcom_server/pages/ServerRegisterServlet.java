package pages;

import helpers.Hash;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.PcUser;
import controller.UserController;
import dao_objects.DbUser;

public class ServerRegisterServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Something's happening in register");

		String login = request.getParameter("user");
		String password = request.getParameter("password");
		String birthday = request.getParameter("birthday");
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");

		
		boolean b = new UserController().addUser(new PcUser(new DbUser(login, Hash.encode(password), birthday, email, lastName, firstName)));
		
//		PcUser tempUser= new UserController().getUserByLogin(userLogin);
		if(b){
			System.out.println("register OK");
		}else {
			System.out.println("register not OK");
		}

//		PrintWriter out = response.getWriter();
//		out.print(json);
//		out.flush();
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
