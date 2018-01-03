package servlet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.json.JSONObject;

import userManager.SecurityAuthenticator;
import userManager.UserManager;

/**
 * This class represents the servlet that manages the endpoint /download. Allows
 * only POST requests made with the appropriate form for file input
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = { "/upload" })
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String token = request.getParameter("token");

		System.out.println("Prova upload: " + username + " " + "token");

		if (SecurityAuthenticator.getUsername(token).equals(username)) {
			response.setContentType("text/html;charset=UTF-8");
			final Part filePart = request.getPart("file");
			InputStream filecontent = null;
			filecontent = filePart.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			try {
				String urlImg = getImgurContent(out);
				PrintWriter outClient = response.getWriter();
				if (UserManager.setFieldUser(username, "img", urlImg)) {
					outClient.println(getResponse("OK"));
				} else {
					outClient.println(getResponse("KO"));
				}
				outClient.flush();
				outClient.close();
			} catch (Exception e) {
				System.out.println("Error in Upload Imgur");
				e.printStackTrace();
			}
		}
	}

	private static String getImgurContent(ByteArrayOutputStream byteArray) throws Exception {
		URL url;
		url = new URL("https://api.imgur.com/3/upload");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		byte[] byteImage = byteArray.toByteArray();
		// String dataImage = Base64.encode(byteImage);
		String dataImage = Base64.getEncoder().encodeToString(byteImage);
		String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(dataImage, "UTF-8");

		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Client-ID " + "42a15f5e9659fb5");
		// conn.setRequestProperty("Content-Type",
		// "application/x-www-form-urlencoded");

		conn.connect();
		StringBuilder stb = new StringBuilder();
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			stb.append(line).append("\n");
		}
		wr.close();
		rd.close();
		JSONObject requestJSON;
		requestJSON = new JSONObject(stb.toString());
		String urlImage = requestJSON.getJSONObject("data").getString("link");
		return urlImage;
	}

	private static String getResponse(String status) {
		return Json.createObjectBuilder().add("status", status).build().toString();
	}
}