package dati;

import java.sql.Time;

import javax.json.Json;

/**
 * Class that contains all journey's data
 */
public class Journey {

	
	private Time start;
	private Time duration;
	private TypeMeans path;
	private Event event;
	private String position;

	/**
	 * It creates an instance of {@link Journey}
	 * 
	 * @param start
	 *            The beginning of the journey
	 * @param duration
	 *            The duration of the journey
	 * @param path
	 *            The means used for the journey
	 * @param event
	 *            The destination event of the journey
	 * @param position
	 *            The initial position of the journey
	 */
	public Journey(Time start, Time duration, TypeMeans path, Event event, String position) {
		this.start = start;
		this.duration = duration;
		this.path = path;
		this.event = event;
		this.position = position;
	}

	/**
	 * @return the beginning of the journey
	 */
	public Time getStart() {
		return start;
	}

	/**
	 * @return the duration of the journey
	 */
	public Time getDuration() {
		return duration;
	}

	/**
	 * @return the means used for the journey
	 */
	public TypeMeans getPath() {
		return path;
	}

	/**
	 * @return the destination event of the journey
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @return the initial position of the journey
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @return the {@link String} for the database query
	 */
	public String stringValuesQuery() {
		return "'" + start + "','" + duration + "','" + path + "','" + event.getID() + "','" + position + "'";
	}

	/**
	 * 
	 * @return the {@link String} in JSON format that contains all the journey's data
	 */
	public String getJson() {
		return Json.createObjectBuilder().add("start", start.toString()).add("duration", duration.toString())
				.add("means", path.toString()).add("event", event.getJson()).add("position", position).build()
				.toString();
	}

}
