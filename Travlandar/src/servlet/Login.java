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

import dati.User;
import userManager.UserManager;

@WebServlet(name = "Login", urlPatterns = { "/Login" })
@MultipartConfig
public class Login extends HttpServlet {

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
						System.out.print("Error in LoginServlet");
					}
				}
			}
			String data = dati.toString();
			System.out.println(data);
			JSONObject requestJSON;
			try {
				requestJSON = new JSONObject(data);
				String username = requestJSON.getString("username");
				String password = requestJSON.getString("password");
				String token = UserManager.login(username, password);
				String resp;
				if (token != null) {
					User user = UserManager.getUserInformation(username);
					resp = getResponse("OK", token, user.getName(), user.getSurname());
				} else {
					resp = getResponse("KO", "dati errati", "", "");
				}
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				resp = resp.replace("\\", "");
				out.println(resp);
				out.flush();
				out.close();
			} catch (JSONException e) {
				System.out.print("Error in LoginServlet: " + data);
			}
		}
	}

	private static String getResponse(String status, String token, String name, String surname) {
		return Json.createObjectBuilder().add("status", status).add("token", token).add("name", name)
				.add("surname", surname).build().toString();
	}

}
