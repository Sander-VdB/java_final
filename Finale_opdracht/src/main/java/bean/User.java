package bean;

import util.Encryptor;

public class User {

	public static enum Roles {
		SUPERADMIN, ADMIN, PARTICIPANT
	}

	private int id;
	private String username;
	private String encryptedPassword;
	private Roles role;

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEncryptedPassword() {
		return this.encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedpassword) {
		this.encryptedPassword = encryptedpassword;
	}

	/*
	 * protected String getPassword() throws Exception { return Encryptor.decrypt(this.encryptedPassword); }
	 */

	public void setPassword(String password) throws Exception {
		this.encryptedPassword = Encryptor.encrypt(password);
	}

	public boolean comparepassword(String toCheck) throws Exception {
		return Encryptor.encrypt(toCheck).compareTo(encryptedPassword) == 0;
	}

	public User() {
		this.setId(-1);
		this.setRole(Roles.PARTICIPANT);
	}

	public User(String username) {
		this.setUsername(username);
		this.setId(-1);
	}

	@Override
	public boolean equals(Object object) {
		// TODO
		if (object instanceof User) {
			User userobject = (User) object;
			if (userobject.getUsername() == this.getUsername()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
