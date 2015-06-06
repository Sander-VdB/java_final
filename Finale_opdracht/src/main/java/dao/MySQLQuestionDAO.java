package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.ParseAnswerException;
import bean.Question;

public class MySQLQuestionDAO implements QuestionDAO {

	public MySQLQuestionDAO() {

	}

	@Override
	public List<Question> findAll() {
		final String SQL_SELECT = "SELECT Id,Question,Series,Theme,Type,Answer FROM Questions";

		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			ResultSet set = statementSelect.executeQuery();

			List<Question> questionList = new ArrayList<Question>();
			while (set.next()) {
				Question question = Question.getQuestion(Question.Types.valueOf(set.getString("Type")));
				question.setId(set.getInt("Id"));
				question.setQuestion(set.getString("Question"));
				question.setSeries(set.getString("Series"));
				question.setTheme(set.getString("Theme"));
				question.setAnswer(set.getString("Answer"));

				questionList.add(question);
			}
			return questionList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseAnswerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Question> findById(int id) {
		final String SQL_SELECT = "SELECT Question,Series,Theme,Type,Answer FROM Questions WHERE Id=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			statementSelect.setInt(1, id);
			ResultSet set = statementSelect.executeQuery();

			List<Question> questionList = new ArrayList<Question>();
			while (set.next()) {
				Question question = Question.getQuestion(Question.Types.valueOf(set.getString("Type")));
				question.setId(id);
				question.setQuestion(set.getString("Question"));
				question.setSeries(set.getString("Series"));
				question.setTheme(set.getString("Theme"));
				question.setAnswer(set.getString("Answer"));

				questionList.add(question);
			}
			return questionList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseAnswerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Question> findByName(String questionname) {
		final String SQL_SELECT = "SELECT Id,Series,Theme,Type,Answer FROM Questions WHERE Question=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			statementSelect.setString(1, questionname);
			ResultSet set = statementSelect.executeQuery();

			List<Question> questionList = new ArrayList<Question>();
			while (set.next()) {
				Question question = Question.getQuestion(Question.Types.valueOf(set.getString("Type")));
				question.setId(set.getInt("Id"));
				question.setQuestion(questionname);
				question.setSeries(set.getString("Series"));
				question.setTheme(set.getString("Theme"));
				question.setAnswer(set.getString("Answer"));

				questionList.add(question);
			}
			return questionList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseAnswerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Question> findBySeries(String series) {
		final String SQL_SELECT = "SELECT Id,Question,Theme,Type,Answer FROM Questions WHERE Series=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			statementSelect.setString(1, series);
			ResultSet set = statementSelect.executeQuery();

			List<Question> questionList = new ArrayList<Question>();
			while (set.next()) {
				Question question = Question.getQuestion(Question.Types.valueOf(set.getString("Type")));
				question.setId(set.getInt("Id"));
				question.setQuestion(set.getString("Question"));
				question.setSeries(series);
				question.setTheme(set.getString("Theme"));
				question.setAnswer(set.getString("Answer"));

				questionList.add(question);
			}
			return questionList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseAnswerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Question> findByTheme(String theme) {
		final String SQL_SELECT = "SELECT Id,Question,Series,Type,Answer FROM Questions WHERE Theme=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT)) {
			statementSelect.setString(1, theme);
			ResultSet set = statementSelect.executeQuery();

			List<Question> questionList = new ArrayList<Question>();
			while (set.next()) {
				Question question = Question.getQuestion(Question.Types.valueOf(set.getString("Type")));
				question.setId(set.getInt("Id"));
				question.setQuestion(set.getString("Question"));
				question.setSeries(set.getString("Series"));
				question.setTheme(theme);
				question.setAnswer(set.getString("Answer"));

				questionList.add(question);
			}
			return questionList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseAnswerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int insertQuestion(Question question) {
		final String SQL_INSERT = "INSERT INTO Questions (Question,Series,Theme,Type,Answer) VALUES (?,?,?,?,?)";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementInsert = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);
			statementInsert.setString(1, question.getQuestion());
			statementInsert.setString(2, question.getSeries());
			statementInsert.setString(3, question.getTheme());
			statementInsert.setString(4, question.getType().name());
			statementInsert.setString(5, question.answerToString());

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
	public boolean updateQuestion(Question question) {
		final String SQL_UPDATE = "UPDATE Questions SET Question=?,Series=?,Theme=?,Type=?,Answer=? WHERE Id=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementUpdate = connection.prepareStatement(SQL_UPDATE)) {
			connection.setAutoCommit(false);
			statementUpdate.setString(1, question.getQuestion());
			statementUpdate.setString(2, question.getSeries());
			statementUpdate.setString(3, question.getTheme());
			statementUpdate.setString(4, question.getType().name());
			statementUpdate.setString(5, question.answerToString());
			statementUpdate.setInt(6, question.getId());

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
	public boolean deleteQuestion(Question question) {
		final String SQL_INSERT = "DELETE FROM Questions WHERE Id=?";
		try (Connection connection = MySQLDAOFactory.createConnection();
				PreparedStatement statementInsert = connection.prepareStatement(SQL_INSERT)) {
			connection.setAutoCommit(false);
			statementInsert.setInt(1, question.getId());

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
	public boolean createTable(String question, String password) {
		final String SQL_INSERT = "CREATE TABLE IF NOT EXISTS Questions (Id INT NOT NULL AUTO_INCREMENT,"
				+ "Question VARCHAR(60) NOT NULL," + "Answer VARCHAR(60) NOT NULL," + "Series VARCHAR(30) NOT NULL,"
				+ "Theme VARCHAR(30) NOT NULL," + "Type VARCHAR(20) NOT NULL," + "PRIMARY KEY (Id))";
		try (Connection connection = MySQLDAOFactory.createConnection(question, password);
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
