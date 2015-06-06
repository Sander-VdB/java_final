package dao;

import java.util.List;

import bean.Todo;

public interface TodoDAO {
	List<Todo> findAll();

	Todo findByUserid(int userid);

	/**
	 * @param todo
	 * @return ids of inserted entries
	 */
	List<Integer> insertTodo(Todo todo);

	boolean updateTodo(Todo todo);

	boolean deleteTodo(Todo todo);

	boolean createTable(String user, String password);
}
