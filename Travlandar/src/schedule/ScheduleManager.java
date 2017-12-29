package schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import database.DataHandlerDBMS;
import dati.Break;
import dati.Event;
import dati.EventType;
import dati.Journey;
import dati.Schedule;
import dati.TypeMeans;
import dati.User;

public class ScheduleManager {

	public static boolean createSchedule(User user, String day) {
		String username = user.getUsername();
		DataHandlerDBMS.executeDML("insert into schedule (username, day) values ('" + username + "','" + day + "')");
		ArrayList<Break> br = user.getBreakPref();
		for (Break el : br) {
			addBreak(el, user, day);
		}
		return true;
	}

	public static boolean hasSchedule(String username, String day) {
		try {
			return DataHandlerDBMS
					.sendQuery("select * from schedule where username='" + username + "' AND day='" + day + "'").next();
		} catch (SQLException e) {
			System.out.println("Error in hasSchedule");
			return false;
		}
	}

	public static ArrayList<Schedule> getSchedules(String username) {
		try {
			ArrayList<String> schedules = new ArrayList<String>();
			ResultSet query = DataHandlerDBMS.sendQuery("select * from schedule where username='" + username + "'");
			while (query.next()) {
				schedules.add(query.getString("day"));
			}
			ArrayList<Schedule> schedule = new ArrayList<Schedule>();
			for (String el : schedules) {
				schedule.add(getSchedule(username, el));
			}
			return schedule;
		} catch (SQLException e) {
			System.out.println("Error in getSchedules");
			return null;
		}
	}

	public static Schedule getSchedule(String username, String day) {
		try {
			if (hasSchedule(username, day)) {
				Schedule schedule = new Schedule(username, day);
				ResultSet journeys = DataHandlerDBMS.sendQuery(
						"select * from journey where username='" + username + "' AND day='" + day + "' order by start");
				while (journeys.next()) {
					ResultSet event = DataHandlerDBMS
							.sendQuery("select * from event where ID='" + journeys.getInt("EventID") + "'");
					if (event.next()) {
						Event e = new Event(event.getInt("ID"), event.getString("name"), event.getTime("start"),
								event.getTime("duration"), Enum.valueOf(EventType.class, event.getString("type")),
								event.getString("position"));
						Journey j = new Journey(journeys.getTime("start"), journeys.getTime("duration"),
								Enum.valueOf(TypeMeans.class, journeys.getString("path")), e,
								journeys.getString("position"));
						schedule.addJourney(j);
					}
				}
				return schedule;
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println("Error in getSchedule");
			return null;
		}
	}

	public static boolean addEvent(User user, String day, Event event, String origin) {
		String username = user.getUsername();
		ArrayList<TypeMeans> means = user.getMeansPref();
		Schedule schedule = getSchedule(username, day);
		Time t = null;
		TypeMeans meansUsed = null;
		int wheater = ExternalRequestManager.getWeatherForecast(origin, day);
		for (TypeMeans el : means) {
			HashMap<String, Integer> ris = ExternalRequestManager.getDistanceMatrixAPI(origin, event.getPosition(),
					el.getTypeAPI());
			if (ris != null) {
				Time temp = new Time((ris.get("duration") - 3600) * 1000);
				if (el == TypeMeans.bicycling && wheater == 1000) {
					if (t == null || temp.compareTo(t) < 0) {
						t = temp;
						meansUsed = el;
					}
				}
				if (el == TypeMeans.walking && ris.get("distance") <= user.getMaxWalk()) {
					if (t == null || temp.compareTo(t) < 0) {
						t = temp;
						meansUsed = el;
					}
				}
				if (el == TypeMeans.driving) {
					if (t == null || temp.compareTo(t) < 0) {
						t = temp;
						meansUsed = el;
					}
				}
				if (el.isTransit() && user.getMaxHoursMeans().compareTo(event.getStart()) >= 0) {
					if (t == null || temp.compareTo(t) < 0) {
						t = temp;
						meansUsed = el;
					}
				}
			}
		}
		if (t != null && meansUsed != null) {
			int startJourney = (int) (event.getStart().getTime() - t.getTime() - 3600000);
			if (startJourney < -3600000) {
				return false;
			}
			Journey j = new Journey(new Time(event.getStart().getTime() - t.getTime() - 3600000), t, meansUsed, event,
					origin);
			boolean notOverlaps = true;
			ArrayList<Journey> breakEx = schedule.getAndRemoveBreak();
			Time startj = j.getStart();
			Time endj = new Time(event.getStart().getTime() + event.getDuration().getTime() + 3600000);
			for (Journey el : schedule.getSchedule()) {
				Time startEl = el.getStart();
				Time endEl = new Time(
						el.getEvent().getStart().getTime() + el.getEvent().getStart().getTime() + 3600000);
				if ((startj.compareTo(startEl) > 0 && startj.compareTo(endEl) < 0)
						|| (endj.compareTo(startEl) > 0 && endj.compareTo(endEl) < 0)) {
					notOverlaps = false;
					break;
				}
			}
			if (notOverlaps && canAddBreak(user, schedule)) {
				for (Journey el : breakEx) {
					deleteEvent(el.getEvent().getID());
				}
				DataHandlerDBMS.executeDML("insert into event (ID, name, start, duration, type, position) values ("
						+ event.stringValuesQuery() + ")");
				DataHandlerDBMS.executeDML(
						"insert into journey (username, day, start, duration, path, EventID, position) values ('"
								+ username + "','" + day + "'," + j.stringValuesQuery() + ")");
				for (Journey el : breakEx) {
					Break br = user.getBreakFromName(el.getEvent().getName());
					if (br != null) {
						addBreak(br, user, day);
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean deleteSchedule(String username, String day) {
		Schedule schedule = getSchedule(username, day);
		for (Journey el : schedule.getSchedule()) {
			deleteEvent(el.getEvent().getID());
		}
		return DataHandlerDBMS
				.executeDML("delete from schedule where username='" + username + "' AND day='" + day + "'");
	}

	public static boolean deleteEvent(int ID) {
		return DataHandlerDBMS.executeDML("delete from event where ID='" + ID + "'");
	}

	public static int getIntMax() {
		ResultSet r = DataHandlerDBMS.sendQuery("select max(ID) as Max from event");
		try {
			if (r.next()) {
				return r.getInt("Max") + 1;
			} else {
				return 0;
			}
		} catch (SQLException e) {
			return 0;
		}
	}

	private static void addBreak(Break br, User user, String day) {
		Schedule schedule = getSchedule(user.getUsername(), day);
		Time startBreak = br.getStart();
		Time endBreak = br.getEnd();
		ArrayList<Journey> jouney = new ArrayList<Journey>();
		for (Journey el : schedule.getSchedule()) {
			Time startEl = el.getStart();
			Time endEl = new Time(el.getEvent().getStart().getTime() + el.getEvent().getDuration().getTime() + 3600000);
			if ((startBreak.compareTo(endEl) < 0 && startBreak.compareTo(startEl) >= 0)
					|| (startEl.compareTo(endBreak) < 0 && startEl.compareTo(startBreak) >= 0)) {
				jouney.add(el);
			}
		}
		Time temp = null;
		for (int i = 0; i < jouney.size() - 1; i++) {
			Time startEl = jouney.get(i + 1).getStart();
			Time endEl = new Time(jouney.get(i).getEvent().getStart().getTime()
					+ jouney.get(i).getEvent().getDuration().getTime() + 3600000);
			if ((br.getDuration().compareTo(new Time(startEl.getTime() - endEl.getTime() - 3600000)) < 0)
					&& endBreak.compareTo(new Time(endEl.getTime() + br.getDuration().getTime() + 3600000)) >= 0) {
				temp = endEl;
				break;
			}
		}
		if (jouney.size() == 0) {
			temp = startBreak;
		}
		if (jouney.size() == 1) {
			Journey event = jouney.get(0);
			Time endEl = new Time(
					event.getEvent().getStart().getTime() + event.getEvent().getDuration().getTime() + 3600000);
			if (endBreak.compareTo(new Time(endEl.getTime() + br.getDuration().getTime() + 3600000)) >= 0) {
				temp = endEl;
			}
		}
		if (temp != null) {
			ResultSet r = DataHandlerDBMS.sendQuery("select max(ID) as Max from event");
			try {
				if (r.next()) {
					Event event = new Event(r.getInt("Max") + 1, br.getName(), temp, br.getDuration(), EventType.BREAK,
							"Milan");
					Journey j = new Journey(event.getStart(), new Time(-3600000), TypeMeans.walking, event, "Milan");
					DataHandlerDBMS.executeDML("insert into event (ID, name, start, duration, type, position) values ("
							+ event.stringValuesQuery() + ")");
					DataHandlerDBMS.executeDML(
							"insert into journey (username, day, start, duration, path, EventID, position) values ('"
									+ user.getUsername() + "','" + day + "'," + j.stringValuesQuery() + ")");
				}
			} catch (SQLException e) {
				System.out.println("Error addBreak");
			}
		}

	}

	public static boolean canAddBreak(User user, Schedule schedule) {
		boolean flag = true;
		for (Break br : user.getBreakPref()) {
			Time startBreak = br.getStart();
			Time endBreak = br.getEnd();
			ArrayList<Journey> jouney = new ArrayList<Journey>();
			for (Journey el : schedule.getSchedule()) {
				Time startEl = el.getStart();
				Time endEl = new Time(
						el.getEvent().getStart().getTime() + el.getEvent().getDuration().getTime() + 3600000);
				if ((startBreak.compareTo(endEl) < 0 && startBreak.compareTo(startEl) >= 0)
						|| (startEl.compareTo(endBreak) < 0 && startEl.compareTo(startBreak) >= 0)) {
					jouney.add(el);
				}
			}
			Time temp = null;
			for (int i = 0; i < jouney.size() - 1; i++) {
				Time startEl = jouney.get(i + 1).getStart();
				Time endEl = new Time(jouney.get(i).getEvent().getStart().getTime()
						+ jouney.get(i).getEvent().getDuration().getTime() + 3600000);
				if ((br.getDuration().compareTo(new Time(startEl.getTime() - endEl.getTime() - 3600000)) < 0)
						&& endBreak.compareTo(new Time(endEl.getTime() + br.getDuration().getTime() + 3600000)) >= 0) {
					temp = endEl;
					break;
				}
			}
			if (jouney.size() == 0) {
				temp = startBreak;
			}
			if (jouney.size() == 1) {
				Journey event = jouney.get(0);
				Time endEl = new Time(
						event.getEvent().getStart().getTime() + event.getEvent().getDuration().getTime() + 3600000);
				if (endBreak.compareTo(new Time(endEl.getTime() + br.getDuration().getTime() + 3600000)) >= 0) {
					temp = endEl;
				}
			}
			if (temp == null) {
				flag = false;
			}
		}
		return flag;
	}
}
