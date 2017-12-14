package userManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import database.DataHandlerDBMS;
import dati.Break;
import dati.TypeMeans;
import dati.User;

public class UserManager {

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

	public static boolean signUp(HashMap<String, String> param) {
		try {
			ResultSet rs = DataHandlerDBMS
					.sendQuery("select count(*) as Num from user where username='" + param.get("username") + "'");
			if (rs.next() && Integer.parseInt(rs.getString("Num")) == 0) {

				String insert = "insert into user (username, password, name, surname, email, phone, drivingLicense, creditCard, maxWalk, maxHourMeans) values "
						+ "('" + param.get("username") + "','" + param.get("password") + "','" + param.get("name") + "','"
						+ param.get("surname") + "','" + param.get("email") + "','" + param.get("phone") + "','"
						+ param.get("drivingLicense") + "','" + param.get("creditCard") + "','" + param.get("maxWalk") + "','"
						+ param.get("maxHourMeans") + "')";
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

	private static void getUserMeansPref(User user) {
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

	private static void getUserBreakPref(User user) {
		ResultSet rs = DataHandlerDBMS
				.sendQuery("select * from breakPref where username='" + user.getUsername() + "'");
		try {
			while (rs.next()) {
				user.addBreakPref(new Break(rs.getString("name"), rs.getTime("start"), rs.getTime("end"),
						rs.getTime("duration")));
			}
		} catch (SQLException e) {
			System.out.println("Error in getUserBreakPref");
		}
	}

	public static boolean setFieldUser(String username, String field, String newValue) {
		return DataHandlerDBMS
				.executeDML("update user set " + field + "='" + newValue + "' where username='" + username + "'");
	}

	public static boolean setFieldMeansPref(String username, boolean flag, TypeMeans means) {
		if (flag) {
			return DataHandlerDBMS.executeDML(
					"insert into meansPref (username, typeMeans) values ('" + username + "','" + means + "')");
		} else {
			return DataHandlerDBMS.executeDML(
					"delete from meansPref where username='" + username + "' AND typeMeans='" + means + "'");
		}
	}

	public static boolean setBreakPref(String username, boolean flag, Break breakPref) {
		if (flag) {
			return DataHandlerDBMS.executeDML("insert into breakPref (username, name, start, end, duration) values ('"
					+ username + "','" + breakPref.getName() + "','" + breakPref.getStart() + "','" + breakPref.getEnd()
					+ "','" + breakPref.getDuration() + "')");
		} else {
			return DataHandlerDBMS.executeDML("delete from breakPref where username='" + username + "' AND name='"
					+ breakPref.getName() + "'");
		}
	}
}
