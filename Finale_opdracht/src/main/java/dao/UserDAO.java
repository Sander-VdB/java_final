package dao;

import java.util.List;

import bean.User;

public interface UserDAO {
	List<User> findAll();

	List<User> findById(int id);

	List<User> findByName(String username);

	User find(String username, String password);

	// List<User> findByName(String username, boolean ignoreCase);

	/**
	 * @param user
	 * @return id of inserted user
	 */
	int insertUser(User user);

	boolean updateUser(User user);

	boolean deleteUser(User user);

	boolean createTable(String user, String password);
}
