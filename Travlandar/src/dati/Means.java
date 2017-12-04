package dati;

import javax.json.Json;

public class Means {

	private String name;
	private boolean status;
	private TypeMeans type;

	public Means(String name, boolean status, TypeMeans type) {
		this.name = name;
		this.status = status;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public boolean isStatus() {
		return status;
	}

	public TypeMeans getType() {
		return type;
	}

	public String getJson() {
		return Json.createObjectBuilder().add("String", name).add("status", status).add("type", type.toString()).build()
				.toString();
	}
}
