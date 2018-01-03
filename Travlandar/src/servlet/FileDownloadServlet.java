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
import userManager.SecurityAuthenticator;
import userManager.UserManager;

/**
 * This class represents the servlet that manages the endpoint /download. Allows
 * only POST requests with URL parameters:
 * * { 
 * 		"username": "",
 * 		"token": ""
 * }
 */
@WebServlet(name = "FileDownloadServlet", urlPatterns = { "/download" })
@MultipartConfig
public class FileDownloadServlet extends HttpServlet {

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
				String token = requestJSON.getString("token");
				String username = SecurityAuthenticator.getUsername(token);
				String resp;
				if (username != null && username.equals(requestJSON.getString("username"))) {
					String urlImg = UserManager.getImage(username);
					if(!urlImg.equals("")) {
						resp = getResponse("OK", urlImg);
					}else {
						resp = getResponse("KO", "Url non trovata");
					}
				} else {
					resp = getResponse("KO", "Token non valido");
				}
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.println(resp);
				out.flush();
				out.close();
			} catch (JSONException e) {
				System.out.print("Error in FileDownloadSevlet: " + data);
			}
		}
	}
	
	private static String getResponse(String status, String url) {
		return Json.createObjectBuilder().add("status", status).add("url", url).build().toString();
	}

}