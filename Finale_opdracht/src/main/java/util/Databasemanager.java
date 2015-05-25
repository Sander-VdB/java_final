package util;

public abstract class Databasemanager {

	public abstract boolean databaseExists();

	public abstract boolean createDatabase(String url, String username, String password);
}
