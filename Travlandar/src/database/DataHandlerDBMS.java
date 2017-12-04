package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataHandlerDBMS {

	private static Connection DBMS;
	private String url = "jdbc:mysql://localhost:3306/AUI?user=root&password=prova&useSSL=false";

	public DataHandlerDBMS() {
		try {
			DBMS = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println("Errore apertura DBMS");
		}
	}

	public static ResultSet sendQuery(String query) {
		try {
			Statement stm = DBMS.createStatement();
			ResultSet res = stm.executeQuery(query);
			return res;
		} catch (SQLException e) {
			System.out.println("Error in sendQuery");
		}
		return null;
	}

	public static boolean executeDML(String query) {
		try {
			PreparedStatement stm = DBMS.prepareStatement(query);
			int ris=stm.executeUpdate();
			return ris>0;
		} catch (SQLException e) {
			System.out.println("Error in executeDML");
			return false;
		}
	}

}
