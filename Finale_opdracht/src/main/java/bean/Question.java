package bean;

import util.ParseAnswerException;

public abstract class Question {
	/*
	 * public static final int YESNO = 1; public static final int MULTIPLECHOICE = 2; public static final int CHECKBOX =
	 * 3; public static final int SLIDER = 4; public static final int NUMERIC = 5; public static final int DRAGANDDROP =
	 * 6;
	 */
	public static enum Types {
		YESNO, MULTIPLECHOICE, CHECKBOX, SLIDER, NUMERIC, DRAGANDDROP
	}

	private int id;
	private String question;
	private String series;
	private String theme;

	public static Question getQuestion(Types choice) {
		switch (choice) {
		case YESNO:
			return new Yesno();
		case MULTIPLECHOICE:
			return new Multiplechoice();
		case CHECKBOX:
			return new Checkbox();
		case SLIDER:
			return new Slider();
		case NUMERIC:
			return new Numeric();
		case DRAGANDDROP:
			return new DragAndDrop();
		default:
			return null;
		}
	}

	public abstract Types getType();

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

	public abstract String answerToString();

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
