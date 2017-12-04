package dati;

import java.sql.Time;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;

public class User {
	private String username;
	private String name;
	private String surname;
	private String mail;
	private String phone;
	private String drivingLicense;
	private String creditCard;
	private int maxWalk;
	private Time maxHourMeans;
	private ArrayList<Break> breakPref;
	private ArrayList<TypeMeans> meansPref;

	public User(String username, String name, String surname, String mail, String phone, String drivingLicense,
			String creditCard, int maxWalk, Time maxHourMeans) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.mail = mail;
		this.phone = phone;
		this.drivingLicense = drivingLicense;
		this.creditCard = creditCard;
		this.breakPref = new ArrayList<Break>();
		this.meansPref = new ArrayList<TypeMeans>();
		this.maxHourMeans = maxHourMeans;
		this.maxWalk = maxWalk;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getMail() {
		return mail;
	}

	public String getPhone() {
		return phone;
	}

	public String getDrivingLicense() {
		return drivingLicense;
	}

	public String getCreditCard() {
		return creditCard;
	}
	
	public int getMaxWalk() {
		return maxWalk;
	}
	
	public Time getMaxHoursMeans() {
		return maxHourMeans;
	}

	public ArrayList<Break> getBreakPref() {
		return breakPref;
	}

	public ArrayList<TypeMeans> getMeansPref() {
		return meansPref;
	}

	public void addBreakPref(Break evt) {
		breakPref.add(evt);
	}

	public void addMeansPref(TypeMeans means) {
		meansPref.add(means);
	}

	private String getJsonJourneyBreak() {
		JsonArrayBuilder array = Json.createArrayBuilder();
		for (Break el : breakPref) {
			array.add(el.getJson());
		}
		return array.build().toString();
	}

	private String getJsonJourneyMeans() {
		JsonArrayBuilder array = Json.createArrayBuilder();
		for (TypeMeans el : meansPref) {
			array.add(el.toString());
		}
		return array.build().toString();
	}

	public String getJson() {
		return Json.createObjectBuilder().add("username", username).add("name", name).add("surname", surname)
				.add("mail", mail).add("phone", phone).add("drivingLicense", drivingLicense)
				.add("creditCard", creditCard).add("maxWalk", maxWalk).add("maxHourMeans", maxHourMeans.toString())
				.add("breakPref", getJsonJourneyBreak()).add("meansPref", getJsonJourneyMeans()).build().toString();
	}
}
