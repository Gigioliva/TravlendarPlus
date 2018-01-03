package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import dati.TypeMeans;
import schedule.ExternalRequestManager;

/**
 * This class represents the servlet that manages the endpoint /GetPath. Allows
 * only POST requests with URL parameters:
 * { 
 * 		"origin": "",
 * 		"destination": "",
 * 		"mode": ""
 * }
 */
@WebServlet(name = "GetPath", urlPatterns = { "/GetPath" })
@MultipartConfig
public class GetPath extends HttpServlet {

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
				String origin = requestJSON.getString("origin");
				String destination = requestJSON.getString("destination");
				String mode = requestJSON.getString("mode");
				String resp= ExternalRequestManager.getPath(origin, destination, Enum.valueOf(TypeMeans.class, mode).getTypeAPI());
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.println(resp);
				out.flush();
				out.close();
			} catch (JSONException e) {
				System.out.print("Error in GetPathServlet: " + data);
			}
		}
	}
}
