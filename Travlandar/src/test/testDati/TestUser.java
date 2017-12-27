package test.testDati;

import java.sql.Time;
import org.junit.*;
import dati.Break;
import dati.TypeMeans;
import dati.User;

public class TestUser {

	private String username, name, surname, mail, phone, drivingLicense, creditCard;
	private int maxWalk;
	private Time maxHourMeans;
	private User user;
	private String breakName1, breakName2;
	private Time start, end, duration;
	private Break br1, br2;

	@Before
	public void init() {
		username = new String("testUsername");
		name = new String("testName");
		surname = new String("testSurname");
		mail = new String("mail@test.com");
		phone = new String("3331234567");
		drivingLicense = new String("AB123456");
		creditCard = new String("1111222233334444");
		maxWalk = 1000;
		maxHourMeans = new Time(32400000);
		//max 9 hours
		user = new User(username, name, surname, mail, phone, drivingLicense, creditCard, maxWalk, maxHourMeans);
		breakName1 = new String("break1");
		breakName2 = new String("break2");
		start = new Time(32400000);
		//9AM GMT
		end = new Time(39600000);
		//11AM GMT
		duration = new Time(3600000);
		//1 hour
		br1 = new Break(breakName1, start, end, duration);
		br2 = new Break(breakName2, start, end, duration);
	}

	@Test
	public void testCreateUser() {
		assert (user.getUsername().equals(username));
		assert (user.getName().equals(name));
		assert (user.getSurname().equals(surname));
		assert (user.getMail().equals(mail));
		assert (user.getPhone().equals(phone));
		assert (user.getDrivingLicense().equals(drivingLicense));
		assert (user.getCreditCard().equals(creditCard));
		assert (user.getMaxWalk() == maxWalk);
		assert (user.getMaxHoursMeans().equals(maxHourMeans));
		assert (user.getBreakPref().isEmpty());
		assert (user.getMeansPref().isEmpty());
	}

	@Test
	public void testAddBreakPref() {
		user.addBreakPref(br1);
		assert (user.getBreakPref().contains(br1));
	}

	@Test
	public void testGetBreakFromName() {
		user.addBreakPref(br2);
		assert (user.getBreakFromName(breakName2).equals(br2));
	}

	@Test
	public void testGetMeansPref() {
		user.addMeansPref(TypeMeans.bicycling);
		assert (user.getMeansPref().contains(TypeMeans.bicycling));
	}

	@Test
	public void testGetJsonJourneyBreak() {
		assert (TestJSON.JSONSyntaxTest(user.getJson()));
	}

}