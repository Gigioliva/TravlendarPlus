package dati;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

public class Schedule {

	private String username;
	private String day;
	private ArrayList<Journey> schedule;

	public Schedule(String username, String day) {
		this.username = username;
		this.day = day;
		schedule = new ArrayList<Journey>();
	}

	public String getUsername() {
		return username;
	}

	public String getDay() {
		return day;
	}

	public ArrayList<Journey> getSchedule() {
		return schedule;
	}
	
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

	public String getJson() {
		return Json.createObjectBuilder().add("username", username).add("day", day).add("singleSchedule", getJsonJourney())
				.build().toString();
	}
}
