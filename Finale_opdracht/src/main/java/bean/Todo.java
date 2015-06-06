package bean;

import java.util.HashMap;
import java.util.Map;

public class Todo {

	private User user;
	private Map<Question, String> givenanswers;

	public Todo() {
		givenanswers = new HashMap<Question, String>();
	}

	public Todo(User user) {
		this.setUser(user);
	}

	public void addQuestion(Question question) {
		this.givenanswers.putIfAbsent(question, null);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<Question, String> getGivenanswers() {
		return givenanswers;
	}

	public String getAnswer(Question question) {
		return givenanswers.get(question);
	}

	public String setAnswer(Question question, String answer) {
		return this.givenanswers.replace(question, answer);
	}

}
