package test.testDati;

import java.sql.Time;

import org.junit.Before;
import org.junit.Test;

import dati.Event;
import dati.EventType;

public class TestEvent {

	private int ID;
	private String name, position;
	private Time start, duration;
	private Event event;

	@Before
	public void init() {
		ID = 5;
		name = new String("eventName");
		start = new Time(32400000);
		//9AM GMT
		duration = new Time(7200000);
		//2 hours
		position = new String("pos");
		event = new Event(ID, name, start, duration, EventType.OTHER, position);
	}

	@Test
	public void testCreateEvent() {
		assert(event.getID()==ID);
		assert(event.getName().equals(name));
		assert(event.getStart().equals(start));
		assert(event.getDuration().equals(duration));
		assert(event.getPosition().equals(position));
		assert(event.getType().equals(EventType.OTHER));
	}
	
	@Test
	public void testGetJSON() {
		assert(TestJSON.JSONSyntaxTest(event.getJson()));
	}
}
