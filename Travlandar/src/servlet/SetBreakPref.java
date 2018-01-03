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

import dati.Break;
import userManager.SecurityAuthenticator;
import userManager.UserManager;

/**
 * This class represents the servlet that manages the endpoint /SetBreakPref. Allows
 * only POST requests with URL parameters:
 * * { 
 * 		"username": "",
 * 		"token": "",
 * 		"flag": "",
 * 		"name" : "",
 * 		"start" : "",
 * 		"end" : "",
 * 		"duration" : ""
 * }
 */
@WebServlet(name = "SetBreakPref", urlPatterns = { "/SetBreakPref" })
@MultipartConfig
public class SetBreakPref extends HttpServlet {

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
				boolean flagBreak = requestJSON.getBoolean("flag");
				Break breakPref;
				if (flagBreak) {
					breakPref = new Break(requestJSON.getString("name"), new Time(requestJSON.getInt("start") - FUSO),
							new Time(requestJSON.getInt("end") - FUSO),
							new Time(requestJSON.getInt("duration") - FUSO));
				} else {
					breakPref = new Break(requestJSON.getString("name"), new Time(0), new Time(0), new Time(0));
				}
				String resp;
				if (username != null && username.equals(requestJSON.getString("username"))) {
					boolean ris = UserManager.setBreakPref(username, flagBreak, breakPref);
					if (ris) {
						resp = getResponse("OK");
					} else {
						resp = getResponse("KO");
					}
				} else {
					resp = getResponse("KO");
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
				System.out.print("Error in SetBreakPrefServlet: " + data);
			}
		}
	}

	private static String getResponse(String status) {
		return Json.createObjectBuilder().add("status", status).build().toString();
	}
}