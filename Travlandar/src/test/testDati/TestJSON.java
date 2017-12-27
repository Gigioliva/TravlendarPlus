package test.testDati;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestJSON {

	public static boolean JSONSyntaxTest(String json) {
		try {
			new JSONObject(json);
		} catch (JSONException e1) {
			try {
				new JSONArray(json);
			} catch (JSONException e2) {
				return false;
			}
		}
		return true;
	}

}
