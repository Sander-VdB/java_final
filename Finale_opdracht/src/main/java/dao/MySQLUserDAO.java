package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.User;

public class MySQLUserDAO implements UserDAO {

	public MySQLUserDAO() {

	}

	@Override
	public List<User> findAll() {
		final String SQL_SELECT = "SELECT Id,Username FROM Users";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			ResultSet set = statementSelect.executeQuery();

			List<User> userList = new ArrayList<User>();
			while (set.next()) {
				User user = new User();
				user.setId(set.getInt("Id"));
				user.setUsername(set.getString("Username"));

				userList.add(user);
			}
			return userList;
		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return null;
	}

	@Override
	public List<User> findById(int id) {
		final String SQL_SELECT = "SELECT Username FROM Users WHERE Id=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			statementSelect.setInt(1, id);
			ResultSet set = statementSelect.executeQuery();

			List<User> userList = new ArrayList<User>();
			while (set.next()) {
				User user = new User();
				user.setId(id);
				user.setUsername(set.getString("Username"));

				userList.add(user);
			}
			return userList;
		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return null;
	}

	@Override
	public List<User> findByName(String username) {
		final String SQL_SELECT = "SELECT Id FROM Users WHERE Username=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			statementSelect.setString(1, username);
			ResultSet set = statementSelect.executeQuery();

			List<User> userList = new ArrayList<User>();
			while (set.next()) {
				User user = new User();
				user.setId(set.getInt("Id"));
				user.setUsername(username);

				userList.add(user);
			}
			return userList;
		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return null;
	}

	@Override
	public int insertUser(User user) {
		final String SQL_INSERT = "INSERT INTO Users (Username) VALUES (?)";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementInsert = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);
			statementInsert.setString(1, user.getUsername());

			if (statementInsert.executeUpdate() == 1) {
				connection.commit();
				ResultSet keys = statementInsert.getGeneratedKeys();
				if (keys.next()) {
					return keys.getInt(1);
				}
			} else {
				connection.rollback();
			}

		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return -1;
	}

	@Override
	public boolean updateUser(User user) {
		final String SQL_UPDATE = "UPDATE Users SET Username=? WHERE Id=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementUpdate = connection.prepareStatement(SQL_UPDATE)) {
			connection.setAutoCommit(false);
			statementUpdate.setString(1, user.getUsername());
			statementUpdate.setInt(2, user.getId());

			if (statementUpdate.executeUpdate() == 1) {
				connection.commit();
				return true;
			} else {
				connection.rollback();
			}

		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean deleteUser(User user) {
		final String SQL_INSERT = "DELETE FROM Users WHERE Id=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementInsert = connection.prepareStatement(SQL_INSERT)) {
			connection.setAutoCommit(false);
			statementInsert.setInt(1, user.getId());

			if (statementInsert.executeUpdate() == 1) {
				connection.commit();
				return true;
			} else {
				connection.rollback();
			}
		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean createTable(String user, String password) {
		final String SQL_INSERT = "CREATE TABLE IF NOT EXISTS Users (Id INT NOT NULL AUTO_INCREMENT,"
				+ "Username VARCHAR(30) NOT NULL," + "Password VARCHAR(30) NOT NULL," + "PRIMARY KEY (Id))";
		try (Connection connection = MySQLDAOFactory.createConnection(user, password);
				PreparedStatement statementInsert = connection.prepareStatement(SQL_INSERT)) {
			connection.setAutoCommit(false);

			if (statementInsert.executeUpdate() == 1) {
				connection.commit();
				return true;
			} else {
				connection.rollback();
			}
		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return false;
	}

}
