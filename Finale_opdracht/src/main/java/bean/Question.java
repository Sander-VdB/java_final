package bean;

import util.ParseAnswerException;

enum Types {
	YESNO, MULTIPLECHOICE, CHECKBOXES, SLIDER, NUMERIC, DRAGANDDROP
}

public abstract class Question {

	private int id;
	private String question;
	private String series;
	private String theme;

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public abstract void setAnswer(String answer) throws ParseAnswerException;

	public abstract boolean checkAnswer(Object answer);

	@Override
	public abstract String toString();

	@Override
	public boolean equals(Object object) {
		// TODO
		if (object instanceof Question) {
			Question userobject = (Question) object;
			if (userobject.getQuestion() == this.getQuestion()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
