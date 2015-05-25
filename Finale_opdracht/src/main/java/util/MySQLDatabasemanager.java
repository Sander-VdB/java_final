package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLDatabasemanager extends Databasemanager {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL;
	private static String USER;
	private static String PASSWORD;

	protected static Connection createConnection() {
		try {
			Class.forName(DRIVER).newInstance();
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception ex) {
			ex.getMessage();
		}
		return null;
	}

	@Override
	public boolean databaseExists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createDatabase(String url, String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
