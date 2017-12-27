package test.testDati;

import java.sql.Time;

import org.junit.Before;
import org.junit.Test;

import dati.Event;
import dati.EventType;
import dati.Journey;
import dati.Schedule;
import dati.TypeMeans;

public class TestSchedule {

	private int eventId1, eventId2;
	private String username, eventName, day;
	private Time startJ, startE, durationJ, durationE;
	private String positionJ, positionE;
	private Event eventMeeting, eventBreak;
	private Journey journeyMeeting, journeyBreak;
	private Schedule schedule;

	@Before
	public void init() {
		eventId1 = 1;
		eventId2 = 2;
		username = new String("sched1");
		day = new String("test");
		eventName = new String("event1");
		startJ = new Time(36000000);
		//10AM GMT
		startE = new Time(36003600);
		//11AM GMT
		durationJ = new Time(3600);
		//1 hour
		durationE = new Time(7200);
		//2 hour
		positionJ = new String("posJ");
		positionE = new String("posE");
		schedule = new Schedule(username, day);
	}

	@Test
	public void testCreateSchedule() {
		assert (schedule.getDay().equals(day));
		assert (schedule.getUsername().equals(username));
		assert (schedule.getSchedule().isEmpty());
	}

	@Test
	public void testAddJourney() {
		eventMeeting = new Event(eventId1, eventName, startE, durationE, EventType.MEETING, positionE);
		journeyMeeting = new Journey(startJ, durationJ, TypeMeans.bicycling, eventMeeting, positionJ);
		schedule.addJourney(journeyMeeting);
		assert(schedule.getSchedule().contains(journeyMeeting));
		assert(schedule.getSchedule().size()==1);
	}

	@Test
	public void testGetAndRemoveBreak() {
		eventBreak = new Event(eventId2, eventName, startE, durationE, EventType.BREAK, positionE);
		journeyBreak = new Journey(startJ, durationJ, TypeMeans.bicycling, eventBreak, positionJ);
		schedule.addJourney(journeyBreak);
		assert(schedule.getSchedule().contains(journeyBreak));
		assert(schedule.getAndRemoveBreak().contains(journeyBreak));
		assert(!schedule.getSchedule().contains(journeyBreak));
	}
	
	@Test
	public void testGetJSON() {
		assert(TestJSON.JSONSyntaxTest(schedule.getJson()));
	}
	
}
