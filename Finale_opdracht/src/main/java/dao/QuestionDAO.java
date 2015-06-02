package dao;

import java.util.List;

import bean.Question;

public interface QuestionDAO {
	List<Question> findAll();

	List<Question> findById(int id);

	List<Question> findByName(String question);

	/**
	 * @param question
	 * @return id of inserted question
	 */
	int insertQuestion(Question question);

	boolean updateQuestion(Question question);

	boolean deleteQuestion(Question question);

	boolean createTable(String user, String password);
}
