package dati;

import java.sql.Time;
import javax.json.Json;

public class Break {

	private String name;
	private Time start;
	private Time end;
	private Time duration;

	public Break(String name, Time start, Time end, Time duration) {
		super();
		this.name = name;
		this.start = start;
		this.end = end;
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public Time getStart() {
		return start;
	}

	public Time getEnd() {
		return end;
	}

	public Time getDuration() {
		return duration;
	}

	public String getJson() {
		return Json.createObjectBuilder().add("name", name).add("start", start.toString()).add("end", end.toString())
				.add("duration", duration.toString()).build().toString();
	}
	
	public String toString() {
		return name;
	}
}
