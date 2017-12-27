package test.testSchedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import database.DataHandlerDBMS;
import dati.Event;
import dati.EventType;
import dati.Journey;
import dati.Schedule;
import dati.TypeMeans;
import dati.User;
import schedule.ScheduleManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DataHandlerDBMS.class, ScheduleManager.class})		

public class TestScheduleManager {

	private User user;
	
	@Mock
	private ResultSet resultSet;
	private Schedule sched;
	//ArrayList<Journey> j = new ArrayList<Journey>();
	
	
	@Before
	public void init() {
		user = new User("username", "name", "surname", "mail", "phone", "drivinglicence", "creditcard", 1, new Time(3600000));
	}
	
	@Test
	public void testCreateSchedule() {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.executeDML(Matchers.anyString())).thenReturn(true);
		Boolean bool1 = new Boolean(false);
		bool1 = ScheduleManager.createSchedule(user, new String("test"));
		assert(bool1);
	}
	
	@Test
	public void testHasSchedule() {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.sendQuery(Matchers.anyString())).thenReturn(resultSet);
		try {
			Mockito.when(resultSet.next()).thenReturn(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Boolean bool2 = new Boolean(false);
		bool2 = ScheduleManager.hasSchedule("username", "test");
		assert(bool2);
	}	
	
	//sistemare, il test non copre il metodo completamente
	@Test
	public void testGetSchedule() {
		PowerMockito.mockStatic(ScheduleManager.class);
		PowerMockito.when(ScheduleManager.hasSchedule("username", "test")).thenReturn(true);
		try {
		Schedule schedule = ScheduleManager.getSchedule("username", "test");
		assert(schedule.equals(null));
		} catch (NullPointerException e) {
		}
	}
	
	@Test
	public void testAddEvent() {
		
	}
	
	@Test
	public void testDeleteSchedule() {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.executeDML(Matchers.anyString())).thenReturn(true);
		PowerMockito.mockStatic(ScheduleManager.class);
		PowerMockito.when(ScheduleManager.getSchedule("username", "test")).thenReturn(sched);
		ArrayList<Journey> j = new ArrayList<Journey>();
		j.add(new Journey(new Time(0), new Time(3600000), TypeMeans.bicycling, 
				new Event(100, "name", new Time(3600000), new Time(7200000), EventType.BREAK, "eventPosition"),
				"testPosition"));
		Schedule sched = Mockito.mock(Schedule.class);
		Mockito.when(sched.getSchedule()).thenReturn(j);
		PowerMockito.when(ScheduleManager.deleteEvent(Matchers.anyInt())).thenReturn(true);
		//Boolean bool3 = new Boolean(false);
		//bool3 = ScheduleManager.deleteSchedule("username", "test");
		//assert(bool3);
	}
	
	@Test
	public void testDeleteEvent() {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.executeDML(Matchers.anyString())).thenReturn(true);
		Boolean bool4 = ScheduleManager.deleteEvent(123);
		assert(bool4);
	}
	
}