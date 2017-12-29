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

import dati.Event;
import dati.User;
import schedule.ScheduleManager;

import userManager.SecurityAuthenticator;
import userManager.UserManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SecurityAuthenticator.class, UserManager.class, ScheduleManager.class })
public class TestAddEvent {
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Test
	public void testDoPost() throws IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(request.getMethod()).thenReturn("POST");
		String str = "{\"username\": \"testUsername\"," + 
				"\"token\" : \"tok\"," + 
				"\"day\" : \"01-01-2000\"," + 
				"\"origin\" : \"eventStart\"," + 
				"\"eventName\" : \"EventName\"," + 
				"\"eventStart\" : \"3600000\"," + 
				"\"eventDuration\" : \"3600000\"," + 
				"\"eventType\" : \"other\"," + 
				"\"eventPosition\": \"eventPos\"}";
		InputStream is = new ByteArrayInputStream(str.getBytes());
		BufferedReader buff = new BufferedReader(new InputStreamReader(is));
		Mockito.when(request.getReader()).thenReturn(buff);
		PowerMockito.mockStatic(SecurityAuthenticator.class);
		PowerMockito.when(SecurityAuthenticator.getUsername(Matchers.anyString())).thenReturn("testUsername");
		PowerMockito.mockStatic(UserManager.class);
		PowerMockito.when(UserManager.getUserInformation(Matchers.anyString())).thenReturn(new User("testUsername",
				"name", "surname", "mail", "phone", "drivinglicence", "creditcard", 1, new Time(3600000)));
		PowerMockito.mockStatic(ScheduleManager.class);
		PowerMockito.when(ScheduleManager.getIntMax()).thenReturn(200);
		PowerMockito.when(ScheduleManager.addEvent(Matchers.any(User.class), Matchers.anyString(),
				Matchers.any(Event.class), Matchers.anyString())).thenReturn(true);
		PrintWriter pr = new PrintWriter(System.out, false);
		Mockito.when(response.getWriter()).thenReturn(pr);
		new AddEvent().doPost(request, response);
		Mockito.verify(request, Mockito.times(1)).getReader();
		Mockito.verify(response, Mockito.times(1)).getWriter();
	}

}
