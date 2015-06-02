package bean;

import util.ParseAnswerException;

public class Numeric extends Question {

	private int answer;

	protected int getAnswer() {
		return answer;
	}

	protected void setAnswer(int answer) {
		this.answer = answer;
	}

	@Override
	public boolean checkAnswer(Object answer) {
		if (answer instanceof Integer) {
			return (Integer) answer == this.answer;
		} else {
			return false;
		}
	}

	@Override
	public void setAnswer(String answer) throws ParseAnswerException {
		try {
			this.setAnswer(Integer.parseInt(answer));
		} catch (Exception ex) {
			throw new ParseAnswerException("Parsing numeric answer failed with exception: " + ex.getMessage());
		}
	}

	@Override
	public String toString() {
		return String.valueOf(this.getAnswer());
	}

}
