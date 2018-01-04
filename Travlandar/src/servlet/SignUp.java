package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import userManager.UserManager;

/**
 * This class represents the servlet that manages the endpoint /SignUp. Allows
 * only POST requests with URL parameters:
 * { 
 * 		"username": "",
 * 		"password": "",
 * 		"name": "",
 * 		"surname" : "",
 * 		"email" : "",
 * 		"phone" : "",
 * 		"drivingLicense" : "",
 * 		"creditCard" : ""
 * }
 */

@WebServlet(name = "SignUp", urlPatterns = { "/SignUp" })
@MultipartConfig
public class SignUp extends HttpServlet {

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
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("username", requestJSON.getString("username"));
				param.put("password", requestJSON.getString("password"));
				param.put("name", requestJSON.getString("name"));
				param.put("surname", requestJSON.getString("surname"));
				param.put("email", requestJSON.getString("email"));
				param.put("phone", requestJSON.getString("phone"));
				param.put("drivingLicense", requestJSON.getString("drivingLicense"));
				param.put("creditCard", requestJSON.getString("creditCard"));
				param.put("maxWalk", "10000");
				param.put("maxHourMeans", "23:00:00");
				param.put("img", "noSet");
				boolean flag = UserManager.signUp(param);
				String resp;
				if (flag) {
					resp = getResponse("OK");
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
				Mail.inviaMail(requestJSON.getString("email"));
			} catch (JSONException e) {
				System.out.print("Error in SignUpServlet: " + data);
			}
		}
	}

	private static String getResponse(String status) {
		return Json.createObjectBuilder().add("status", status).build().toString();
	}
}