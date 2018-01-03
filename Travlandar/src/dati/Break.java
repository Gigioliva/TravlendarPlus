package dati;

import java.sql.Time;
import javax.json.Json;

/**
 * Class that contains all break's data
 */
public class Break {

	private String name;
	private Time start;
	private Time end;
	private Time duration;

	/**
	 * It creates an instance of {@link Break}
	 * 
	 * @param name
	 *            the name of the break
	 * @param start
	 *            The beginning of the break
	 * @param duration
	 *            The duration of the break
	 * @param end
	 *            The end of the break
	 */
	public Break(String name, Time start, Time end, Time duration) {
		super();
		this.name = name;
		this.start = start;
		this.end = end;
		this.duration = duration;
	}

	/**
	 * @return the name of the break
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the start of the break
	 */
	public Time getStart() {
		return start;
	}

	/**
	 * @return the end of the break
	 */
	public Time getEnd() {
		return end;
	}

	/**
	 * @return the duration of the break
	 */
	public Time getDuration() {
		return duration;
	}

	/**
	 * 
	 * @return the {@link String} in JSON format that contains all the break's data
	 */
	public String getJson() {
		return Json.createObjectBuilder().add("name", name).add("start", start.toString()).add("end", end.toString())
				.add("duration", duration.toString()).build().toString();
	}

	public String toString() {
		return name;
	}
}
