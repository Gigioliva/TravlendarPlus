package userManager;

import java.util.HashMap;
import java.util.Random;

public class SecurityAuthenticator {

	private static HashMap<String, String> token = new HashMap<String, String>();
	public static final String special = "!%&/=#@";
	public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String lower = upper.toLowerCase();
	public static final String digits = "0123456789";
	public static final String alphanum = upper + lower + digits + special;

	public static String addLogin(String username) {
		if (token.containsValue(username)) {
			for (String el : token.keySet()) {
				if (token.get(el).equals(username)) {
					Logout(el);
				}
			}
		}
		String tok;
		do {
			tok = nextString();
		} while (token.containsKey(tok));
		token.put(tok, username);
		return tok;
	}

	private static String nextString() {
		StringBuilder str = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			str.append(alphanum.charAt(random.nextInt(alphanum.length())));
		}
		return str.toString();
	}

	public static String getUsername(String tok) {
		if(token.containsKey(tok)) {
			return token.get(tok);
		}else {
			return "";
		}
	}

	public static boolean Logout(String tok) {
		if(token.containsKey(tok)) {
			token.remove(tok);
			return true;
		}else {
			return false;
		}
	}

}
