package servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Time;

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

import dati.User;

import userManager.SecurityAuthenticator;
import userManager.UserManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SecurityAuthenticator.class, UserManager.class })
public class TestUserInformation {

	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Test
	public void testDoPost() throws IOException, ServletException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(request.getMethod()).thenReturn("POST");
		String str = "{\"username\": \"testUsername\",\n" + 
				"\"token\": \"tok\"}";
		InputStream is = new ByteArrayInputStream(str.getBytes());
		BufferedReader buff = new BufferedReader(new InputStreamReader(is));
		Mockito.when(request.getReader()).thenReturn(buff);
		PowerMockito.mockStatic(SecurityAuthenticator.class);
		PowerMockito.when(SecurityAuthenticator.getUsername(Matchers.anyString())).thenReturn("testUsername");
		PowerMockito.mockStatic(UserManager.class);
		PowerMockito.when(UserManager.getUserInformation(Matchers.anyString())).thenReturn(new User("testUsername",
				"name", "surname", "mail", "phone", "drivinglicence", "creditcard", 1, new Time(3600000)));
		PrintWriter pr = new PrintWriter(System.out, false);
		Mockito.when(response.getWriter()).thenReturn(pr);
		new UserInformation().doPost(request, response);
		Mockito.verify(request, Mockito.times(1)).getReader();
		Mockito.verify(response, Mockito.times(1)).getWriter();
		
	}

}

