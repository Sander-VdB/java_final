package dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DAOFactoryTest {

	private static final String DATABASESUPERUSER = "root";
	private static final String DATABASEPASSWORD = "java";

	@Test
	public void testDAOFactoryMySQL() {
		assertNotNull(DAOFactory.getDAOFactory(DAOFactory.MYSQL));
	}

	@Test
	public void testDAOFactorygetUserDAOMySQL() {
		assertNotNull(DAOFactory.getDAOFactory(DAOFactory.MYSQL).getUserDAO());
	}

	@Test
	public void testDAOFactoryCreateConnection() {
		assertNotNull(MySQLDAOFactory.createConnection());
	}

	@Test
	public void testDAOFactorycreateDatabase() {
		assertTrue(DAOFactory.getDAOFactory(DAOFactory.MYSQL).createDatabase(DATABASESUPERUSER, DATABASEPASSWORD));
	}
}
