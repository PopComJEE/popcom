//package pages;
//
//import helpers.Helper;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.json.Json;
//import javax.json.JsonArrayBuilder;
//import javax.json.JsonObject;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import controller.PopComSession;
//import controller.PopComUser;
//import controller.SessionController;
//import controller.UserController;
//
//public class ServerSessionServlet extends HttpServlet {
//
//	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		System.out.println("Something's happening in session");
//		response.setContentType("application/json;charset=UTF-8");
//
//		JsonObject json;
//		String type = request.getParameter("type");
//		String login = request.getParameter("login");
//		JsonObject jsonResponse = null;
//		if(type.equalsIgnoreCase("get_session_list")){
//			JsonArrayBuilder builder=Json.createArrayBuilder();
//			for(PopComSession session:SessionController.getUserSessions(UserController.getUserByLogin(login))){
//				builder.add(Helper.toJsonObject(session));
//			}
//			jsonResponse=Json.createObjectBuilder()
//					.add("session_list", builder.build())
//					.build();
//		
//		}
//
//		PrintWriter out = response.getWriter();
//		out.print(jsonResponse);
//		out.flush();
//
//
//	}
//
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		processRequest(req, resp);
//	}
//
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		processRequest(req, resp);
//	}
//
//
//
//
//}
