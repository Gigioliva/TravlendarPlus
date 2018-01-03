package dati;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

/**
 * Class that contains all schedule's data
 */
public class Schedule {

	private String username;
	private String day;
	private ArrayList<Journey> schedule;

	/**
	 * It creates an instance of {@link Schedule}
	 * 
	 * @param username
	 *            the username of the user to whom the schedule is associated
	 * @param day
	 *            the day of the schedule
	 */
	public Schedule(String username, String day) {
		this.username = username;
		this.day = day;
		schedule = new ArrayList<Journey>();
	}

	/**
	 * @return the username of the user to whom the schedule is associated
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the day of the schedule
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @return the {@link ArrayList} of {@link Journey} in the schedule
	 */
	public ArrayList<Journey> getSchedule() {
		return schedule;
	}
	
	/**
	 * @return the {@link ArrayList} of {@link Journey} associated with a break event 
	 */
	public ArrayList<Journey> getAndRemoveBreak(){
		ArrayList<Journey> ris = new ArrayList<Journey>();
		for(Journey el: schedule) {
			if(el.getEvent().getType() == EventType.BREAK) {
				ris.add(el);
			}
		}
		for(Journey el : ris) {
			schedule.remove(el);
		}
		return ris;
	}

	/**
	 * 
	 * @param journey
	 *            the {@link Journey} to add to the schedule
	 */
	public void addJourney(Journey journey) {
		schedule.add(journey);
	}

	private String getJsonJourney() {
		JsonArrayBuilder array = Json.createArrayBuilder();
		for (Journey el : schedule) {
			array.add(el.getJson());
		}
		return array.build().toString();
	}

	/**
	 * 
	 * @return the {@link String} in JSON format that contains all the schedule's data
	 */
	public String getJson() {
		return Json.createObjectBuilder().add("username", username).add("day", day).add("singleSchedule", getJsonJourney())
				.build().toString();
	}
}
