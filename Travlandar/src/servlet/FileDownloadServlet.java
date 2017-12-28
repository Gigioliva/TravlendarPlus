package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FileDownloadServlet", urlPatterns = { "/download" })
@MultipartConfig
public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(request.getMethod().equals("GET")) {
			String name = request.getParameter("username");
			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream("./" + name + ".jpg");
			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > 0){
			    out.write(buffer, 0, length);
			}
			in.close();
			out.flush();
		}
	}

}