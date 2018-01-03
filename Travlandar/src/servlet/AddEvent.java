package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import dati.Event;
import dati.EventType;
import dati.User;
import schedule.ScheduleManager;
import userManager.SecurityAuthenticator;
import userManager.UserManager;

/**
 * This class represents the servlet that manages the endpoint /AddEvent. Allows
 * only POST requests with URL parameters:
 * * { 
 * 		"username": "",
 * 		"token": "",
 * 		"day": "",
 * 		"origin" : "",
 * 		"eventName" : "",
 * 		"eventStart" : "",
 * 		"eventDuration" : "",
 * 		"eventType" : "",
 * 		"eventPosition" : ""
 * }
 */
@WebServlet(name = "AddEvent", urlPatterns = { "/AddEvent" })
@MultipartConfig
public class AddEvent extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int FUSO = 3600000;

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
				String resp;
				if (username != null && username.equals(requestJSON.getString("username"))) {
					User user = UserManager.getUserInformation(username);
					String day = requestJSON.getString("day");
					String origin = requestJSON.getString("origin");
					Event event = new Event(ScheduleManager.getIntMax(), requestJSON.getString("eventName"),
							new Time(requestJSON.getInt("eventStart") - FUSO), new Time(requestJSON.getInt("eventDuration") - FUSO),
							Enum.valueOf(EventType.class, requestJSON.getString("eventType").toUpperCase()),
							requestJSON.getString("eventPosition"));
					boolean flag = ScheduleManager.addEvent(user, day, event, origin);
					if(flag) {
						resp = getResponse("OK", "Evento inserito");
					}else {
						resp = getResponse("KO", "Evento non inserito");
					}
				}else {
					resp = getResponse("KO", "Token errato");
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
				System.out.print("Error in GetWeatherServlet: " + data);
			}
		}
	}
	
	private static String getResponse(String status, String details) {
		return Json.createObjectBuilder().add("status", status).add("details", details).build().toString();
	}
}
