package test.testDati;

import java.sql.Time;

import org.junit.Before;
import org.junit.Test;

import dati.Event;
import dati.EventType;
import dati.Journey;
import dati.TypeMeans;

public class TestJourney {

	private Time startJ, durationJ, startE, durationE;
	private TypeMeans path;
	private Event event;
	private String positionJ, positionE, eventName;
	private int eventID;
	private EventType typeE;
	private Journey journey;

	@Before
	public void init() {
		startE = new Time(32400000);
		//9AM GMT
		durationE = new Time(3600);
		//1 hour
		path = TypeMeans.walking;
		eventID = 1;
		eventName = new String("event");
		startJ = new Time(36000000);
		//10AM GMT
		durationJ = new Time(3600);
		//1 hour
		typeE = EventType.OTHER;
		positionE = new String("pos");
		positionJ = new String("pos");
		event = new Event(eventID, eventName, startE, durationE, typeE, positionE);
		journey = new Journey(startJ, durationJ, path, event, positionJ);
	}

	@Test
	public void testCreateJourney() {
		assert (journey.getStart().equals(startJ));
		assert (journey.getDuration().equals(durationJ));
		assert (journey.getEvent().equals(event));
		assert (journey.getPath().equals(path));
		assert (journey.getPosition().equals(positionJ));
	}

	@Test
	public void testGetJSON() {
		assert(TestJSON.JSONSyntaxTest(journey.getJson()));
	}
	
}