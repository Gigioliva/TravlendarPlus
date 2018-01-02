package servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

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

import dati.Schedule;
import schedule.ScheduleManager;

import userManager.SecurityAuthenticator;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SecurityAuthenticator.class, ScheduleManager.class })
public class TestGetSchedule {

	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Test
	public void testDoPost() throws IOException, ServletException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(request.getMethod()).thenReturn("POST");
		// possibile update da fare
		String str = "{\"username\": \"testUsername\"," + 
				"\"token\" : \"tok\"," + 
				"\"day\" : \"01-01-2000\"}";
		InputStream is = new ByteArrayInputStream(str.getBytes());
		BufferedReader buff = new BufferedReader(new InputStreamReader(is));
		Mockito.when(request.getReader()).thenReturn(buff);
		PowerMockito.mockStatic(SecurityAuthenticator.class);
		PowerMockito.when(SecurityAuthenticator.getUsername(Matchers.anyString())).thenReturn("testUsername");
		PowerMockito.mockStatic(ScheduleManager.class);
		Schedule sched;
		sched = new Schedule("testUsername", "01-01-2000");
		PowerMockito.when(ScheduleManager.getSchedule(Matchers.anyString(),Matchers.anyString())).thenReturn(sched);

		StringWriter sw = new StringWriter();
		PrintWriter pr = new PrintWriter(sw, false);
		//PrintWriter pr = new PrintWriter(System.out, false);
		Mockito.when(response.getWriter()).thenReturn(pr);
		new GetSchedule().doPost(request, response);
		Mockito.verify(request, Mockito.times(1)).getReader();
		Mockito.verify(response, Mockito.times(1)).getWriter();
		assert(sw.toString().equals("{\"status\":\"OK\",\"schedule\":{\"username\":\"testUsername\","
				+ "\"day\":\"01-01-2000\",\"singleSchedule\":[]}}\n"));
		
		
	}

}
