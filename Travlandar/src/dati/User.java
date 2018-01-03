package dati;

import java.sql.Time;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;

/**
 * Class that contains all user's data
 */
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

	/**
	 * It creates an instance of User
	 * 
	 * @param username
	 *            the username of the user
	 * @param name
	 *            the name of the user
	 * @param surname
	 *            the surname of the user
	 * @param mail
	 *            the mail of the user
	 * @param phone
	 *            the phone of the user
	 * @param drivingLicense
	 *            the driving license of the user
	 * @param creditCard
	 *            the credit card of the user
	 * @param maxWalk
	 *            the maximum walking distance
	 * @param maxHourMeans
	 *            the maximum hour in which the user can take the means
	 */
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

	/**
	 * @return the username of the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the surname of the user
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return the mail of the user
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @return the phone of the user
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the driving license of the user
	 */
	public String getDrivingLicense() {
		return drivingLicense;
	}

	/**
	 * @return the credit card of the user
	 */
	public String getCreditCard() {
		return creditCard;
	}

	/**
	 * @return the maximum walking distance of the user
	 */
	public int getMaxWalk() {
		return maxWalk;
	}

	/**
	 * @return the maximum hour in which the user can take the means
	 */
	public Time getMaxHoursMeans() {
		return maxHourMeans;
	}

	/**
	 * @return the preferences of the breaks
	 */
	public ArrayList<Break> getBreakPref() {
		return breakPref;
	}

	/**
	 * @return the preferences of means of transport
	 */
	public ArrayList<TypeMeans> getMeansPref() {
		return meansPref;
	}

	/**
	 * 
	 * @param evt
	 *            the {@link Break} preference to add to the user
	 */
	public void addBreakPref(Break evt) {
		breakPref.add(evt);
	}

	/**
	 * 
	 * @param means
	 *            the {@link TypeMeans} preference to add to the user
	 */
	public void addMeansPref(TypeMeans means) {
		meansPref.add(means);
	}

	/**
	 * 
	 * @param name
	 *            the name of the {@link Break} preference
	 * @return the {@link Break} preference with the given name
	 */
	public Break getBreakFromName(String name) {
		for (Break el : breakPref) {
			if (el.getName().equalsIgnoreCase(name)) {
				return el;
			}
		}
		return null;
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
			array.add(Json.createObjectBuilder().add("type", el.toString()).build().toString());
		}
		return array.build().toString();
	}

	/**
	 * 
	 * @return the {@link String} in JSON format that contains all the user's data
	 */
	public String getJson() {
		return Json.createObjectBuilder().add("username", username).add("name", name).add("surname", surname)
				.add("mail", mail).add("phone", phone).add("drivingLicense", drivingLicense)
				.add("creditCard", creditCard).add("maxWalk", maxWalk).add("maxHourMeans", maxHourMeans.toString())
				.add("breakPref", getJsonJourneyBreak()).add("meansPref", getJsonJourneyMeans()).build().toString();
	}
}
