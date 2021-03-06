package dao;

public abstract class DAOFactory {
	public static final int MYSQL = 1;

	public abstract UserDAO getUserDAO();

	public abstract QuestionDAO getQuestionDAO();

	public abstract TodoDAO getTodoDAO();

	public static DAOFactory getDAOFactory(int choice) {
		switch (choice) {
		case MYSQL:
			return new MySQLDAOFactory();
		default:
			return null;
		}
	}

	public abstract boolean createDatabase(String user, String password);
}
