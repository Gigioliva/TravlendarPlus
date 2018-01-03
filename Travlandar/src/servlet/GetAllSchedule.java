package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import dati.Event;
import dati.Journey;
import dati.Schedule;
import schedule.ScheduleManager;
import userManager.SecurityAuthenticator;

/**
 * This class represents the servlet that manages the endpoint /GetAllSchedule. Allows
 * only POST requests with URL parameters:
 * * { 
 * 		"username": "",
 * 		"token": ""
 * }
 */
@WebServlet(name = "GetAllSchedule", urlPatterns = { "/GetAllSchedule" })
@MultipartConfig
public class GetAllSchedule extends HttpServlet {

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
				String resp;
				if (username != null && username.equals(requestJSON.getString("username"))) {
					ArrayList<Schedule> schedules = ScheduleManager.getSchedules(username);
					resp = getResponse("OK", schedules);
				} else {
					resp = getResponse("KO", null);
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
				System.out.print("Error in GetScheduleServlet: " + data);
			}
		}
	}

	private static String getResponse(String status, ArrayList<Schedule> schedules) {
		if (schedules != null) {
			JsonArrayBuilder array = Json.createArrayBuilder();
			for (Schedule el : schedules) {
				array.add(getSingleSchedule(el));
			}
			return Json.createObjectBuilder().add("status", status).add("schedule", array.build().toString()).build()
					.toString();
		} else {
			return Json.createObjectBuilder().add("status", status).add("schedule", "Schedule non trovato").build()
					.toString();
		}

	}

	private static String getSingleSchedule(Schedule schedule) {
		JsonArrayBuilder array = Json.createArrayBuilder();
		for (Journey el : schedule.getSchedule()) {
			Event ev = el.getEvent();
			array.add(Json.createObjectBuilder().add("ID", ev.getID()).add("name", ev.getName()).add("start", ev.getStart().toString())
					.add("duration", ev.getDuration().toString()).build().toString());
		}
		return Json.createObjectBuilder().add("day", schedule.getDay()).add("events", array.build().toString()).build()
				.toString();
	}
}