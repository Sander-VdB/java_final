package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MySQLDAOFactory extends DAOFactory {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/";
	private static final String DATABASE = "finale_opdracht";
	private static final String USER = "educationprogram";
	private static final String PASSWORD = "MilkM4n";

	public static Connection createConnection() {
		return createConnection(USER, PASSWORD);
	}

	public static Connection createConnection(String _user, String _password) {
		try {
			Class.forName(DRIVER).newInstance();
			return DriverManager.getConnection(URL + DATABASE, _user, _password);
		} catch (Exception ex) {
			ex.getMessage();
		}
		return null;
	}

	public static boolean createDatabase(String _user, String _password) {
		final String SQL_CREATEDATABASE = "CREATE DATABASE IF NOT EXISTS ?";
		try {
			Class.forName(DRIVER).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (Connection connection = DriverManager.getConnection(URL, _user, _password);
				PreparedStatement statement = connection.prepareStatement(SQL_CREATEDATABASE);) {
			statement.setString(1, DATABASE);
			statement.execute();
			return true;
		} catch (Exception ex) {
			ex.getMessage();
		}
		return false;
	}

	@Override
	public UserDAO getUserDAO() {
		return new MySQLUserDAO();
	}
}
