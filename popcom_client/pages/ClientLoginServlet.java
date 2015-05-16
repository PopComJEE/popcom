package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import network.GetRequest;

public class ClientLoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String user = req.getParameter("user");
		String pass = req.getParameter("password");

		String query="user="+user+"&password="+pass;




		String response=GetRequest.excuteGet("http://localhost:8080/popcom_server/login", query);
		System.out.println("response : "+response);
		JsonObject jsonResponse = Json.createReader(new StringReader(response))
				.readObject();
		
		
		KeepAlive.setString(user);
		System.out.println("user : "+KeepAlive.getString());
		
		
		if(jsonResponse.getBoolean("accepted")){
			resp.sendRedirect("http://localhost:8080/popcom/session_list?login="+user);
		}else{
			resp.sendRedirect("http://localhost:8080/popcom/");
		}
	}

	private void response(HttpServletResponse resp, String msg)
			throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<t1>" + msg + "</t1>");
		out.println("</body>");
		out.println("</html>");
	}
}