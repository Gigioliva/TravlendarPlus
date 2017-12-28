package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "FileUploadServlet", urlPatterns = { "/upload" })
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		final Part filePart = request.getPart("file");
		final String fileName = getFileName(filePart);
		final String path_server = "./"; //Cambiare percorso in base all'ID

		OutputStream out = null;
		InputStream filecontent = null;
		final PrintWriter writer = response.getWriter();
		//request.getParameter("username");
		
		try {
			File temp = new File(path_server + fileName);
			if (!temp.exists()) {
				temp.createNewFile();
			}
			out = new FileOutputStream(temp);
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			writer.println("Caricato il file: " + fileName + " in " + path_server);
		} catch (FileNotFoundException fne) {
			writer.println("Devi inserire un file");

		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}