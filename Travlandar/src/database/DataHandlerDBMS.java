package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class that contains the logic of interaction with the database
 */
public class DataHandlerDBMS {

	private static Connection DBMS;
	private String url = "jdbc:mysql://localhost:3306/Travlandar?user=root&password=prova&useSSL=false";

	/**
	 * It initializes the class by connecting to the database
	 */
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

	/**
	 * 
	 * @param query
	 *            the query to send to the database
	 * @return the {@link ResultSet} that contains the results of the query
	 */
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

	/**
	 * 
	 * @param query
	 *            the query to send to the database
	 * @return the {@link Boolean} indicating the result of the action
	 */
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
}
