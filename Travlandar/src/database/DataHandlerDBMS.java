package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataHandlerDBMS {

	private static Connection DBMS;
	private String url = "jdbc:mysql://localhost:3306/Travlandar?user=root&password=prova&useSSL=false";

	public DataHandlerDBMS() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			DBMS = DriverManager.getConnection(url);
			System.out.println("DBSM inizializzato");
		} catch (SQLException e) {
			System.out.println("Errore apertura DBMS");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Assenza driver mySQL");
		}
	}

	public static ResultSet sendQuery(String query) {
		try {
			Statement stm = DBMS.createStatement();
			ResultSet res = stm.executeQuery(query);
			return res;
		} catch (SQLException e) {
			System.out.println("Error in sendQuery");
			e.printStackTrace();
		}
		return null;
	}

	public static boolean executeDML(String query) {
		try {
			PreparedStatement stm = DBMS.prepareStatement(query);
			int ris = stm.executeUpdate();
			return ris > 0;
		} catch (SQLException e) {
			System.out.println("Error in executeDML");
			e.printStackTrace();
			return false;
		}
	}

	public static String test() {
		return "ciao";
	}

}
