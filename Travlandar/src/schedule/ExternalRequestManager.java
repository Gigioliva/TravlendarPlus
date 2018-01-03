package schedule;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class that contains the logic of interaction with the external API
 */
public class ExternalRequestManager {

	private static final String keyGoogle = "AIzaSyACuHim7KBzg4kgeoLIQMTKSoh34eLXN2c";
	private static final String keyWeather = "788e2813e755493ab07120326172511";

	/**
	 * returns information based on the recommended route between start and end
	 * points, as calculated by the Google Maps API
	 * 
	 * @param origin
	 *            the origin of the journey
	 * @param destination
	 *            the origin of the journey
	 * @param mode
	 *            the means to be used for the journey
	 * @return the {@link HashMap} contains the duration and distance of the journey
	 */
	public static HashMap<String, Integer> getDistanceMatrixAPI(String origin, String destination, String mode) {
		String url = "";
		try {
			url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin + "&destinations="
					+ destination + "&" + mode + "&key=" + keyGoogle;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "text/plain");
			Scanner in = new Scanner(con.getInputStream());
			StringBuilder str = new StringBuilder();
			while (in.hasNext()) {
				str.append(in.nextLine() + "\n");
			}
			in.close();
			JSONObject risp = new JSONObject(str.toString());
			int temp = risp.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0)
					.getJSONObject("duration").getInt("value");
			int distance = risp.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0)
					.getJSONObject("distance").getInt("value");
			HashMap<String, Integer> ris = new HashMap<String, Integer>();
			ris.put("duration", temp);
			ris.put("distance", distance);
			return ris;
		} catch (JSONException e) {
			System.out.println("JSONException Error in getDistanceMatrixAPI");
			return null;
		} catch (IOException e) {
			System.out.println("IOException Error in getDistanceMatrixAPI");
			return null;
		}
	}

	/**
	 * returns weather forecast for a specific day and city
	 * 
	 * @param city
	 *            the city of the request
	 * @param day
	 *            the day of the request
	 * @return the {@link Integer} representative of the weather
	 */
	public static int getWeatherForecast(String city, String day) {
		try {
			String url = "http://api.apixu.com/v1/forecast.json?q=" + city + "&dt=" + day + "&lang=it&key="
					+ keyWeather;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "text/plain");
			Scanner in = new Scanner(con.getInputStream());
			StringBuilder str = new StringBuilder();
			while (in.hasNext()) {
				str.append(in.nextLine() + "\n");
			}
			JSONObject risp = new JSONObject(str.toString());
			int code = risp.getJSONObject("current").getJSONObject("condition").getInt("code");
			in.close();
			return code;
		} catch (IOException e) {
			System.out.println("IOException Error in getWeatherForecast");
			return 0;
		}

		catch (JSONException e) {
			System.out.println("JSONException Error in getWeatherForecast");
			return 0;
		}

	}
	// 1000 Soleggiato
	// 1003 Parzialmente nuvoloso
	// 1030 Foschia
	// 1183 pioggia debole

	/**
	 * returns weather forecast for a specific day and city
	 * 
	 * @param city
	 *            the city of the request
	 * @param day
	 *            the day of the request
	 * @return the {@link String} in JSON format that contains the weather forecast
	 */
	public static String getWeatherTot(String city, String day) {
		try {
			String url = "http://api.apixu.com/v1/forecast.json?q=" + city + "&dt=" + day + "&lang=it&key="
					+ keyWeather;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "text/plain");
			Scanner in = new Scanner(con.getInputStream());
			StringBuilder str = new StringBuilder();
			while (in.hasNext()) {
				str.append(in.nextLine() + "\n");
			}
			in.close();
			return str.toString();
		} catch (IOException e) {
			System.out.println("Error in getDistanceMatrixAPI");
			return "";
		}
	}

	/**
	 * It calculates directions between locations using an HTTP request.
	 * 
	 * @param origin
	 *            the origin of the journey
	 * @param destination
	 *            the origin of the journey
	 * @param mode
	 *            the means to be used for the journey
	 * @return the {@link String} in JSON format that contains the path
	 */
	public static String getPath(String origin, String destination, String mode) {
		try {
			String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination="
					+ destination + "&" + mode + "&key=" + keyGoogle;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "text/plain");
			Scanner in = new Scanner(con.getInputStream());
			StringBuilder str = new StringBuilder();
			while (in.hasNext()) {
				str.append(in.nextLine() + "\n");
			}
			in.close();
			return str.toString();
		} catch (IOException e) {
			System.out.println("Error in getDistanceMatrixAPI");
			return "";
		}
	}
}
