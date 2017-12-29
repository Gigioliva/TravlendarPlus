package servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dati.Break;
import userManager.SecurityAuthenticator;
import userManager.UserManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SecurityAuthenticator.class, UserManager.class })
public class TestSetBreakPref {
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Test
	public void testDoPost() throws IOException, ServletException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(request.getMethod()).thenReturn("POST");
		String str = "{\"username\" : \"testUsername\"," + 
				"\"token\" : \"tok\"," + 
				"\"flag\": \"true\"," + 
				"\"name\": \"uno dei break\"," + 
				"\"start\": \"36000000\"," + 
				"\"end\": \"72000000\"," + 
				"\"duration\": \"3600000\"}";
		InputStream is = new ByteArrayInputStream(str.getBytes());
		BufferedReader buff = new BufferedReader(new InputStreamReader(is));
		Mockito.when(request.getReader()).thenReturn(buff);
		PowerMockito.mockStatic(SecurityAuthenticator.class);
		PowerMockito.when(SecurityAuthenticator.getUsername(Matchers.anyString())).thenReturn("testUsername");
		PowerMockito.mockStatic(UserManager.class);
		PowerMockito.when(UserManager.setBreakPref(Matchers.anyString(), Matchers.anyBoolean(), Matchers.any(Break.class))).thenReturn(true);
		PrintWriter pr = new PrintWriter(System.out, false);
		Mockito.when(response.getWriter()).thenReturn(pr);
		new SetBreakPref().doPost(request, response);
		Mockito.verify(request, Mockito.times(1)).getReader();
		Mockito.verify(response, Mockito.times(1)).getWriter();
	
	}

}