package dati;

import java.sql.Time;

import javax.json.Json;

/**
 * Class that contains all event's data
 */
public class Event {

	private int ID;
	private String name;
	private Time start;
	private Time duration;
	private EventType type;
	private String position;

	/**
	 * It creates an instance of {@link Event}
	 * 
	 * @param ID
	 *            the unique ID of the event
	 * @param name
	 *            the name of the event
	 * @param start
	 *            The beginning of the event
	 * @param duration
	 *            The duration of the event
	 * @param type
	 *            The {@link EventType} of the event
	 * @param position
	 *            The position of the event
	 */
	public Event(int ID, String name, Time start, Time duration, EventType type, String position) {
		this.ID = ID;
		this.name = name;
		this.start = start;
		this.duration = duration;
		this.type = type;
		this.position = position;
	}

	/**
	 * @return the ID of the event
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return the name of the event
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the beginning of the event
	 */
	public Time getStart() {
		return start;
	}

	/**
	 * @return the duration of the event
	 */
	public Time getDuration() {
		return duration;
	}

	/**
	 * @return the {@link EventType} of the event
	 */
	public EventType getType() {
		return type;
	}

	/**
	 * @return the position of the event
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @return the String for the query
	 */
	public String stringValuesQuery() {
		return "'" + ID + "','" + name + "','" + start + "','" + duration + "','" + type + "','" + position + "'";
	}

	/**
	 * 
	 * @return the {@link String} in JSON format that contains all the event's data
	 */
	public String getJson() {
		return Json.createObjectBuilder().add("ID", ID).add("name", name).add("start", start.toString())
				.add("duration", duration.toString()).add("eventType", type.toString()).add("position", position)
				.build().toString();
	}
}
