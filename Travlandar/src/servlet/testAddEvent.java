package servlet;

import java.io.IOException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dati.Event;
import dati.User;
import schedule.ScheduleManager;

import org.json.JSONObject;

import userManager.SecurityAuthenticator;
import userManager.UserManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SecurityAuthenticator.class, UserManager.class, ScheduleManager.class })
public class testAddEvent {

	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private JSONObject requestJSON;
	
	
	
	@Test
	public void testDoPost() throws IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(request.getMethod()).thenReturn("POST");
		PowerMockito.mockStatic(SecurityAuthenticator.class);
		PowerMockito.when(SecurityAuthenticator.getUsername(Matchers.anyString())).thenReturn("testUsername");
		JSONObject requestJSON = Mockito.mock(JSONObject.class);
		Mockito.when(requestJSON.getString("username")).thenReturn("testUsername");
		PowerMockito.mockStatic(UserManager.class);
		PowerMockito.when(UserManager.getUserInformation(Matchers.anyString())).thenReturn(new User("testUsername",
				"name", "surname", "mail", "phone", "drivinglicence", "creditcard", 1, new Time(3600000)));
		Mockito.when(requestJSON.getString("day")).thenReturn("testDay");
		Mockito.when(requestJSON.getString("origin")).thenReturn("testOrigin");
		PowerMockito.mockStatic(ScheduleManager.class);
		PowerMockito.when(ScheduleManager.getIntMax()).thenReturn(200);
		Mockito.when(requestJSON.getString("eventName")).thenReturn("testEventName");
		Mockito.when(requestJSON.getInt("eventStart")).thenReturn(7200000);
		Mockito.when(requestJSON.getInt("eventDuration")).thenReturn(3600000);
		Mockito.when(requestJSON.getString("eventType")).thenReturn("OTHER");
		Mockito.when(requestJSON.getString("eventPosition")).thenReturn("testPosition");
		PowerMockito.when(ScheduleManager.addEvent(Matchers.any(User.class), Matchers.anyString(),
				Matchers.any(Event.class), Matchers.anyString())).thenReturn(true);
		new AddEvent().doPost(request, response);
		Mockito.verify(request, Mockito.atLeast(1)).getMethod();
	}

}
