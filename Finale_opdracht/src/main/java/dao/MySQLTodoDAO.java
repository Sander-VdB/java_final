package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.Question;
import bean.Todo;

public class MySQLTodoDAO implements TodoDAO {

	private UserDAO userdao = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getUserDAO();
	private QuestionDAO questiondao = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getQuestionDAO();

	public MySQLTodoDAO() {

	}

	@Override
	public List<Todo> findAll() {
		final String SQL_SELECT = "SELECT Userid,Questionid,Answer FROM Todo ORDER BY Userid";

		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			ResultSet set = statementSelect.executeQuery();

			List<Todo> todoList = new ArrayList<Todo>();
			int userid;
			int previoususerid = -1;
			Todo todo = new Todo();
			while (set.next()) {
				userid = set.getInt("Userid");
				if (userid != previoususerid) {
					todo.setUser(userdao.findById(userid).get(0));
					todoList.add(todo);
					todo = new Todo();
				}
				previoususerid = userid;

				Question question = questiondao.findById(set.getInt("Questionid")).get(0);
				todo.addQuestion(question);
				todo.setAnswer(question, set.getString("Answer"));
			}
			todoList.add(todo);
			return todoList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Todo findByUserid(int userid) {
		final String SQL_SELECT = "SELECT Questionid,Answer FROM Todo WHERE Userid=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			statementSelect.setInt(1, userid);
			ResultSet set = statementSelect.executeQuery();

			Todo todo = new Todo();
			todo.setUser(userdao.findById(userid).get(0));
			while (set.next()) {
				Question question = questiondao.findById(set.getInt("Questionid")).get(0);
				todo.addQuestion(question);
				todo.setAnswer(question, set.getString("Answer"));
			}
			return todo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Integer> insertTodo(Todo todo) {
		final String SQL_INSERT = "INSERT INTO Todo (Userid,Questionid,Answer) VALUES (?,?,?)";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementInsert = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);
			for (Map.Entry<Question, String> entry : todo.getGivenanswers().entrySet()) {
				statementInsert.setInt(1, todo.getUser().getId());
				statementInsert.setInt(2, entry.getKey().getId());
				statementInsert.setString(3, entry.getValue());
				statementInsert.addBatch();
			}
			int[] results = statementInsert.executeBatch();
			boolean rollback = false;
			for (int result : results) {
				if (result != 1) {
					connection.rollback();
					rollback = true;
				}
			}
			if (!rollback) {
				connection.commit();
				ResultSet keyset = statementInsert.getGeneratedKeys();
				List<Integer> keys = null;
				if (keyset.next()) {
					keys.add(keyset.getInt(1));
				}
				return keys;
			}

		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return null;
	}

	@Override
	public boolean updateTodo(Todo todo) {
		// TODO optimize
		final String SQL_UPDATE = "UPDATE Todo SET Answer=? WHERE Userid=? AND Questionid=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementUpdate = connection.prepareStatement(SQL_UPDATE)) {
			connection.setAutoCommit(false);
			for (Map.Entry<Question, String> entry : todo.getGivenanswers().entrySet()) {
				statementUpdate.setString(1, entry.getValue());
				statementUpdate.setInt(2, todo.getUser().getId());
				statementUpdate.setInt(3, entry.getKey().getId());
				statementUpdate.addBatch();
			}
			int[] results = statementUpdate.executeBatch();
			boolean rollback = false;
			for (int result : results) {
				if (result != 1) {
					connection.rollback();
					rollback = true;
				}
			}
			if (!rollback) {
				connection.commit();
				return true;
			}

		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean deleteTodo(Todo todo) {
		final String SQL_DELETE = "DELETE FROM Todo WHERE Userid=? AND Questionid=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementDelete = connection.prepareStatement(SQL_DELETE)) {
			connection.setAutoCommit(false);
			for (Map.Entry<Question, String> entry : todo.getGivenanswers().entrySet()) {
				statementDelete.setInt(1, todo.getUser().getId());
				statementDelete.setInt(2, entry.getKey().getId());
				statementDelete.addBatch();
			}
			int[] results = statementDelete.executeBatch();
			boolean rollback = false;
			for (int result : results) {
				if (result != 1) {
					connection.rollback();
					rollback = true;
				}
			}
			if (!rollback) {
				connection.commit();
				return true;
			}
		} catch (SQLException ex) {
			System.console().printf(ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean createTable(String user, String password) {
		final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS Todo (Userid INT NOT NULL," + "Questionid INT NOT NULL,"
				+ "Answer VARCHAR(60) NOT NULL," + "PRIMARY KEY (Userid,Questionid),"
				+ "FOREIGN KEY (Questionid) REFERENCES Questions(Id)," + "FOREIGN KEY (Userid) REFERENCES Users(Id))";
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
