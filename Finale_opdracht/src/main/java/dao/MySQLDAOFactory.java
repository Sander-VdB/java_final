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

	@Override
	public boolean createDatabase(String _user, String _password) {
		final String SQL_CREATEDATABASE = "CREATE DATABASE IF NOT EXISTS " + DATABASE;
		final String SQL_CREATEUSER = "GRANT SELECT,INSERT,DELETE ON " + DATABASE + ".* TO " + USER + " IDENTIFIED BY '"
				+ PASSWORD + "'";
		try {
			Class.forName(DRIVER).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// create database
		try (Connection connection = DriverManager.getConnection(URL, _user, _password);
				PreparedStatement databasequery = connection.prepareStatement(SQL_CREATEDATABASE);
				PreparedStatement userquery = connection.prepareStatement(SQL_CREATEUSER);) {
			databasequery.execute();
			userquery.execute();
		} catch (Exception ex) {
			ex.getMessage();
			return false;
		}
		// create all tables
		try {
			new MySQLUserDAO().createTable(_user, _password);
		} catch (Exception ex) {
			ex.getMessage();
			return false;
		}
		return true;
	}

	@Override
	public UserDAO getUserDAO() {
		return new MySQLUserDAO();
	}
}
