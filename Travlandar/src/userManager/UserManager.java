package userManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;
import database.DataHandlerDBMS;
import dati.Break;
import dati.TypeMeans;
import dati.User;

/**
 * Class that contains the user management logic
 */
public class UserManager {

	/**
	 * @param username
	 *            the user's username
	 * @param password
	 *            the user's password
	 * @return the {@link String} that represents a unique token associated with the
	 *         user
	 */
	public static String login(String username, String password) {
		try {
			ResultSet rs = DataHandlerDBMS.sendQuery("select password from user where username='" + username + "'");
			if (rs.next() && rs.getString("password").equals(password)) {
				return SecurityAuthenticator.addLogin(username);
			}
		} catch (SQLException e) {
			System.out.println("Error in login");
		}
		return null;
	}

	/**
	 * @param username
	 *            the user's username
	 * @return the {@link String} that represents a url containing the image of the
	 *         user's profile
	 */
	public static String getImage(String username) {
		try {
			ResultSet rs = DataHandlerDBMS.sendQuery("select img from user where username='" + username + "'");
			if (rs.next() && !rs.getString("img").equals("noSet")) {
				return rs.getString("img");
			}
		} catch (SQLException e) {
			System.out.println("Error in getImage");
		}
		return "";
	}

	/**
	 * @param param
	 *            the {@link HashMap} containing all user's data
	 * @return the {@link Boolean} indicating the result of the sign up
	 */
	public static boolean signUp(HashMap<String, String> param) {
		try {
			ResultSet rs = DataHandlerDBMS
					.sendQuery("select count(*) as Num from user where username='" + param.get("username") + "'");
			if (rs.next() && Integer.parseInt(rs.getString("Num")) == 0) {

				String insert = "insert into user (username, password, name, surname, email, phone, drivingLicense, creditCard, maxWalk, maxHourMeans, img) values "
						+ "('" + param.get("username") + "','" + param.get("password") + "','" + param.get("name")
						+ "','" + param.get("surname") + "','" + param.get("email") + "','" + param.get("phone") + "','"
						+ param.get("drivingLicense") + "','" + param.get("creditCard") + "','" + param.get("maxWalk")
						+ "','" + param.get("maxHourMeans") + "','" + param.get("img") + "')";
				if (DataHandlerDBMS.executeDML(insert)) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Errore singUp");
			return false;
		}
		return false;
	}

	/**
	 * @param username
	 *            the username of the user
	 * @return the {@link User} with the given username
	 */
	public static User getUserInformation(String username) {
		User user = null;
		ResultSet rs = DataHandlerDBMS.sendQuery("select * from user where username='" + username + "'");
		try {
			if (rs.next()) {
				user = new User(rs.getString("username"), rs.getString("name"), rs.getString("surname"),
						rs.getString("email"), rs.getString("phone"), rs.getString("drivingLicense"),
						rs.getString("creditCard"), rs.getInt("maxWalk"), rs.getTime("maxHourMeans"));
				getUserBreakPref(user);
				getUserMeansPref(user);
			}
		} catch (SQLException e) {
			System.out.println("Error in getUserInformation");
			return user;
		}
		return user;
	}

	/**
	 * @param user
	 *            the {@link User} whose information is to be obtained
	 */
	public static void getUserMeansPref(User user) {
		ResultSet rs = DataHandlerDBMS
				.sendQuery("select typeMeans from meansPref where username='" + user.getUsername() + "'");
		try {
			while (rs.next()) {
				user.addMeansPref(Enum.valueOf(TypeMeans.class, rs.getString("typeMeans")));
			}
		} catch (SQLException e) {
			System.out.println("Error in getUserMeansPref");
		}
	}

	/**
	 * @param user
	 *            the {@link User} whose information is to be obtained
	 */
	public static void getUserBreakPref(User user) {
		ResultSet rs = DataHandlerDBMS.sendQuery("select * from breakPref where username='" + user.getUsername() + "'");
		try {
			while (rs.next()) {
				user.addBreakPref(new Break(rs.getString("name"), rs.getTime("start"), rs.getTime("end"),
						rs.getTime("duration")));
			}
		} catch (SQLException e) {
			System.out.println("Error in getUserBreakPref");
		}
	}

	/**
	 * @param username
	 *            the username of the user
	 * @param field
	 *            the field that you want to change
	 * @param newValue
	 *            the new field value
	 * @return the {@link Boolean} indicating the result of the modification
	 */
	public static boolean setFieldUser(String username, String field, String newValue) {
		return DataHandlerDBMS
				.executeDML("update user set " + field + "='" + newValue + "' where username='" + username + "'");
	}

	/**
	 * @param username
	 *            the username of the user
	 * @param flag
	 *            {@link Boolean} true if you want to add, false otherwise
	 * @param means
	 *            {@link TypeMeans} that you want to modify
	 * @return the {@link Boolean} indicating the result of the modification
	 */
	public static boolean setFieldMeansPref(String username, boolean flag, TypeMeans means) {
		if (flag) {
			return DataHandlerDBMS.executeDML(
					"insert into meansPref (username, typeMeans) values ('" + username + "','" + means + "')");
		} else {
			return DataHandlerDBMS.executeDML(
					"delete from meansPref where username='" + username + "' AND typeMeans='" + means + "'");
		}
	}

	/**
	 * @param username
	 *            the username of the user
	 * @param flag
	 *            {@link Boolean} true if you want to add, false otherwise
	 * @param breakPref
	 *            {@link Break} that you want to modify
	 * @return the {@link Boolean} indicating the result of the modification
	 */
	public static boolean setBreakPref(String username, boolean flag, Break breakPref) {
		if (flag) {
			User user = getUserInformation(username);
			Time startBreak = breakPref.getStart();
			Time endBreak = breakPref.getEnd();
			if (startBreak.compareTo(endBreak) > 0
					|| breakPref.getDuration().compareTo(new Time(endBreak.getTime() - startBreak.getTime())) > 0) {
				return false;
			}
			boolean overlaps = false;
			for (Break el : user.getBreakPref()) {
				Time start = el.getStart();
				Time end = el.getEnd();
				if ((startBreak.compareTo(end) < 0 && startBreak.compareTo(start) >= 0)
						|| (start.compareTo(endBreak) < 0 && start.compareTo(startBreak) >= 0)) {
					overlaps = true;
				}
			}
			if (!overlaps) {
				return DataHandlerDBMS
						.executeDML("insert into breakPref (username, name, start, end, duration) values ('" + username
								+ "','" + breakPref.getName() + "','" + breakPref.getStart() + "','"
								+ breakPref.getEnd() + "','" + breakPref.getDuration() + "')");
			} else {
				return false;
			}
		} else {
			return DataHandlerDBMS.executeDML(
					"delete from breakPref where username='" + username + "' AND name='" + breakPref.getName() + "'");
		}
	}
}
