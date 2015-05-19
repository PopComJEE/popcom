package pages;

import helpers.SessionIdentifierGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.PcSession;
import objects.PcUser;
import controller.SessionController;
import controller.UserController;

public class ChatServlet extends HttpServlet {


	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Something's happening in conversation");

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
			String session_id = request.getParameter("session_id");
			String type = request.getParameter("type");
			if(type.equalsIgnoreCase("getAll")){
				PcUser user= new UserController().getUserByToken(sessionId);
				if(user==null){
					System.out.println("refused");
					PrintWriter out = response.getWriter();
					out.print("{\"status\":\"refused\"}");
					out.flush();
				}else{
					System.out.println("accepted");
					for(PcSession s : user.getSessionList()){
						if(s.getSessionId().equals(session_id)){
							PrintWriter out = response.getWriter();
							out.print("{\"history\":");
							if(s.getHistory()==null){
								out.print("[]");
							}else{
								out.print(s.getHistory());
							}
							out.print(",\"user\":");
							out.print("\""+user.getUser().getLogin()+"\"");
							out.print("}");
							out.flush();
						}
					}

				}
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	protected void processRequestPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Something's happening in conversation");

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
				Enumeration<String> parameterNames = request.getParameterNames();
				String conv_id=new SessionIdentifierGenerator().nextSessionId();
				System.out.println("refused");
				new SessionController().joinSession(user, conv_id);
				while (parameterNames.hasMoreElements()) {
					String paramName = parameterNames.nextElement();
					new SessionController().joinSession(new UserController().getUser(paramName), conv_id);
				}
			}
		}
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequestPost(req, resp);
	}
}
