package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import schedule.ScheduleManager;
import userManager.SecurityAuthenticator;

/**
 * This class represents the servlet that manages the endpoint /DeleteSchedule. Allows
 * only POST requests with URL parameters:
 * { 
 * 		"username": "",
 * 		"token": "",
 * 		"day": ""
 * }
 */
@WebServlet(name = "DeleteSchedule", urlPatterns = { "/DeleteSchedule" })
@MultipartConfig
public class DeleteSchedule extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getMethod().equals("POST")) {
			StringBuffer dati = new StringBuffer();
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = request.getReader();
				char[] charBuffer = new char[128];
				int bytesRead;
				while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
					dati.append(charBuffer, 0, bytesRead);
				}

			} catch (IOException ex) {
				throw ex;
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException ex) {
						throw ex;
					}
				}
			}
			String data = dati.toString();
			System.out.println(data);
			JSONObject requestJSON;
			try {
				requestJSON = new JSONObject(data);
				String username = SecurityAuthenticator.getUsername(requestJSON.getString("token"));
				String day = requestJSON.getString("day");
				String resp;
				if (username != null && username.equals(requestJSON.getString("username"))) {
					boolean flag = ScheduleManager.deleteSchedule(username, day);
					if (flag) {
						resp = getResponse("OK", "Schedule eliminato");
					} else {
						resp = getResponse("KO", "Schedule non trovato");
					}
				} else {
					resp = getResponse("KO", "Token non valido");
				}
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				resp = resp.replace("\\", "");
				resp = resp.replace("\"{\"", "{\"");
				resp = resp.replace("\"}\"", "\"}");
				resp = resp.replace("\"[", "[");
				resp = resp.replace("]\"", "]");
				out.println(resp);
				out.flush();
				out.close();
			} catch (JSONException e) {
				System.out.print("Error in DeleteScheduleServlet: " + data);
			}
		}
	}
	
	private static String getResponse(String status, String details) {
		return Json.createObjectBuilder().add("status", status).add("details", details).build().toString();
	}
}
