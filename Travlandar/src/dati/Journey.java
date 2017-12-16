package dati;

import java.sql.Time;

import javax.json.Json;

public class Journey {

	private Time start;
	private Time duration;
	private TypeMeans path;
	private Event event;
	private String position;

	public Journey(Time start, Time duration, TypeMeans path, Event event, String position) {
		this.start = start;
		this.duration = duration;
		this.path = path;
		this.event = event;
		this.position = position;
	}

	public Time getStart() {
		return start;
	}

	public Time getDuration() {
		return duration;
	}

	public TypeMeans getPath() {
		return path;
	}

	public Event getEvent() {
		return event;
	}

	public String getLatitude() {
		return position;
	}

	public String stringValuesQuery() {
		return "'" + start + "','" + duration + "','" + path + "','" + event.getID() + "','" + position + "'";
	}

	public String getJson() {
		return Json.createObjectBuilder().add("start", start.toString()).add("duration", duration.toString())
				.add("means", path.toString()).add("event", event.getJson()).add("position", position).build()
				.toString();
	}

}
