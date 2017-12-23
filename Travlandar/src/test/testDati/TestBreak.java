package test.testDati;

import java.sql.Time;
import org.junit.Before;
import org.junit.Test;

import dati.Break;

public class TestBreak {

	private String name;
	private Time start, end, duration;
	private Break br;
	
	@Before
	public void init() {
		name = new String("break1");
		start = new Time(32400000); 
		//9AM GMT
		end = new Time(39600000);
		//11AM GMT
		duration = new Time(3600000);
		//1 hour
		br = new Break(name, start, end, duration);
	}
	
	@Test
	public void testCreateBreak() {
		assert(br.getName().equals(name));
		assert(br.toString().equals(name));
		assert(br.getStart().equals(start));
		assert(br.getEnd().equals(end));
		assert(br.getDuration().equals(duration));
	}
	
	@Test
	public void testGetJSON() {
		assert (TestJSON.JSONSyntaxTest(br.getJson()));
	}

}
