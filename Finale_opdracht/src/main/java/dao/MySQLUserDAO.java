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
		final String SQL_SELECT = "SELECT Id,Username,Password,Role FROM Users";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			ResultSet set = statementSelect.executeQuery();

			List<User> userList = new ArrayList<User>();
			while (set.next()) {
				User user = new User();
				user.setId(set.getInt("Id"));
				user.setUsername(set.getString("Username"));
				user.setEncryptedPassword(set.getString("Password"));
				user.setRole(User.Roles.valueOf(set.getString("Role")));

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
		final String SQL_SELECT = "SELECT Username,Password,Role FROM Users WHERE Id=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			statementSelect.setInt(1, id);
			ResultSet set = statementSelect.executeQuery();

			List<User> userList = new ArrayList<User>();
			while (set.next()) {
				User user = new User();
				user.setId(id);
				user.setUsername(set.getString("Username"));
				user.setEncryptedPassword(set.getString("Password"));
				user.setRole(User.Roles.valueOf(set.getString("Role")));

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
		final String SQL_SELECT = "SELECT Id,Password,Role FROM Users WHERE Username=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			statementSelect.setString(1, username);
			ResultSet set = statementSelect.executeQuery();

			List<User> userList = new ArrayList<User>();
			while (set.next()) {
				User user = new User();
				user.setId(set.getInt("Id"));
				user.setUsername(username);
				user.setEncryptedPassword(set.getString("Password"));
				user.setRole(User.Roles.valueOf(set.getString("Role")));

				userList.add(user);
			}
			return userList;
		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return null;
	}

	@Override
	public User find(String username, String password) {
		final String SQL_SELECT = "SELECT Id,Role FROM Users WHERE Username=?,Password=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			statementSelect.setString(1, username);
			statementSelect.setString(2, password);
			ResultSet set = statementSelect.executeQuery();

			User user = new User();
			if (set.first()) {
				user.setId(set.getInt("Id"));
				user.setUsername(username);
				user.setEncryptedPassword(password);
				user.setRole(User.Roles.valueOf(set.getString("Role")));
			}
			return user;
		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return null;
	}

	@Override
	public int insertUser(User user) {
		final String SQL_INSERT = "INSERT INTO Users (Username,Password,Role) VALUES (?,?,?)";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementInsert = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);
			statementInsert.setString(1, user.getUsername());
			statementInsert.setString(2, user.getEncryptedPassword());
			statementInsert.setString(3, user.getRole().name());

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
		final String SQL_UPDATE = "UPDATE Users SET Username=?,Password=?,Role=? WHERE Id=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementUpdate = connection.prepareStatement(SQL_UPDATE)) {
			connection.setAutoCommit(false);
			statementUpdate.setString(1, user.getUsername());
			statementUpdate.setString(2, user.getEncryptedPassword());
			statementUpdate.setString(3, user.getRole().name());
			statementUpdate.setInt(4, user.getId());

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
		final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS Users (Id INT NOT NULL AUTO_INCREMENT,"
				+ "Username VARCHAR(30) NOT NULL," + "Password VARCHAR(30) NOT NULL," + "Role VARCHAR(30) NOT NULL,"
				+ "PRIMARY KEY (Id))";
		try (Connection connection = MySQLDAOFactory.createConnection(user, password);
				PreparedStatement statementCreate = connection.prepareStatement(SQL_CREATE)) {
			connection.setAutoCommit(false);

			if (statementCreate.executeUpdate() == 0) {
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
