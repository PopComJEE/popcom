package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import network.GetRequest;

public class ClientSessionListServlet extends HttpServlet {

	private static final String URL_SERVER="http://localhost:8080/popcom_server/";
	private static final String URL_CLIENT="http://localhost:8080/popcom/";

	private ArrayList<String[]> mSessionList;

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {

		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String login=req.getParameter("login");
		String query="type=get_session_list&login="+login;

		String response=GetRequest.excuteGet(URL_SERVER+"session", query);
		System.out.println("response : "+response);

		JsonObject jsonResponse = Json.createReader(new StringReader(response)).readObject();
		JsonArray session_list = jsonResponse.getJsonArray("session_list");


		mSessionList=new ArrayList<String[]>();

		for(int i=0;i<session_list.size();i++){
			JsonObject session = session_list.getJsonObject(i);
			JsonArray user_list = session.getJsonArray("user_list");
			String[] list = new String[user_list.size()];
			mSessionList.add(list);

			for(int j=0;j<user_list.size();j++){
				list[j]=user_list.getJsonObject(j).getString("login");
			}
		}

		response(resp, mSessionList);

		System.out.println("user : "+KeepAlive.getString());
	}

	private void response(HttpServletResponse resp, ArrayList<String[]> msg)
			throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<table style=\"width:100%\">");
		for(String[] s : msg){
			out.println("<tr>");
			out.print("<td><a href=\""+URL_CLIENT+"\">Conv "+(msg.indexOf(s)+1)+" avec : ");
			for(int i=0;i<s.length;i++){
				out.print(s[i]);
				if(i<s.length-2)
					out.print(", ");
				else if(i<s.length-1)
					out.print(" et ");
			}
			out.println("</a></td></tr>");
		}
		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
	}
}