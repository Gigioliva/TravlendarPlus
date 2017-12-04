package dati;

import java.sql.Time;

import javax.json.Json;

public class Event {

	private int ID;
	private String name;
	private Time start;
	private Time duration;
	private EventType type;
	private String position;

	public Event(int ID, String name, Time start, Time duration, EventType type, String position) {
		this.ID = ID;
		this.name = name;
		this.start = start;
		this.duration = duration;
		this.type = type;
		this.position = position;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public Time getStart() {
		return start;
	}

	public Time getDuration() {
		return duration;
	}

	public EventType getType() {
		return type;
	}

	public String getPosition() {
		return position;
	}

	public String stringValuesQuery() {
		return "'" + ID + "','" + name + "','" + start + "','" + duration + "','" + type + "','" + position + "'";
	}

	public String getJson() {
		return Json.createObjectBuilder().add("ID", ID).add("name", name).add("start", start.toString())
				.add("duration", duration.toString()).add("eventType", type.toString()).add("position", position)
				.build().toString();
	}
}
