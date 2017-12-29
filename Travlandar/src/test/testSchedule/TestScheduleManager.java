package test.testSchedule;

import static org.junit.Assert.assertEquals;

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
import dati.Break;
import dati.Event;
import dati.EventType;
import dati.Journey;
import dati.Schedule;
import dati.TypeMeans;
import dati.User;
import junit.framework.Assert;
import schedule.ScheduleManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DataHandlerDBMS.class, ScheduleManager.class })

public class TestScheduleManager {

	@Mock
	private ResultSet resultSet;

	@Before
	public void init() {

	}

	@Test
	public void testCreateSchedule() {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.executeDML(Matchers.anyString())).thenReturn(true);
		User user = Mockito.mock(User.class);
		ArrayList<Break> br = new ArrayList<Break>();
		Mockito.when(user.getBreakPref()).thenReturn(br);
		Boolean bool1 = new Boolean(false);
		bool1 = ScheduleManager.createSchedule(user, new String("test"));
		assert (bool1);
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
		assert (bool2);
	}

	@Test
	public void testDeleteEvent() {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.executeDML(Matchers.anyString())).thenReturn(true);
		Boolean bool3 = new Boolean(false);
		bool3 = ScheduleManager.deleteEvent(123);
		assert (bool3);
	}

	@Test
	public void testDeleteSchedule() {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.executeDML(Matchers.anyString())).thenReturn(true);
		PowerMockito.mockStatic(ScheduleManager.class);
		Schedule sched = new Schedule("username", "01-01-2000");
		sched.addJourney(new Journey(new Time(0), new Time(3600000), TypeMeans.bicycling,
				new Event(100, "name", new Time(3600000), new Time(7200000), EventType.BREAK, "eventPosition"),
				"testPosition"));
		PowerMockito.when(ScheduleManager.getSchedule("username", "test")).thenReturn(sched);
		PowerMockito.when(ScheduleManager.deleteEvent(Matchers.anyInt())).thenReturn(true);
		Boolean bool4 = new Boolean(false);
		bool4 = ScheduleManager.deleteEvent(123);
		assert (bool4);
	}

	@Test
	public void testGetIntMax() {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.sendQuery(Matchers.anyString())).thenReturn(resultSet);
		try {
			Mockito.when(resultSet.next()).thenReturn(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Mockito.when(resultSet.getInt("Max")).thenReturn(12345);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(ScheduleManager.getIntMax(), 12346);
	}

	// da completare

	@Test
	public void testGetSchedules() {
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.sendQuery(Matchers.anyString())).thenReturn(resultSet);
		try {
			Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Mockito.when(resultSet.getString(Matchers.anyString())).thenReturn("01-01-2000");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PowerMockito.mockStatic(ScheduleManager.class);
		PowerMockito.when(ScheduleManager.getSchedule(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(new Schedule("testUsername", "01-01-2000"));
		Assert.assertNotNull(ScheduleManager.getSchedules("testUsername"));
	}

	@Test
	public void testGetSchedule() {
		PowerMockito.mockStatic(ScheduleManager.class);
		PowerMockito.when(ScheduleManager.hasSchedule(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(true);
		PowerMockito.mockStatic(DataHandlerDBMS.class);
		PowerMockito.when(DataHandlerDBMS.sendQuery(Matchers.anyString())).thenReturn(resultSet);
		try {
			Mockito.when(resultSet.next()).thenReturn(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Assert.assertNotNull(ScheduleManager.getSchedule("testUsername","01-01-2000"));
	}

	@Test
	public void testAddEvent() {
		// Richiede ExternalRequestManager ch da un problema di compatibilità
	}

	@Test
	public void testAddBreak() {

	}

	@Test
	public void testcanAddBreak() {

	}

}