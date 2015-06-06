package bean;

import util.ParseAnswerException;

public class Yesno extends Question {
	private boolean answer;

	@Override
	public Types getType() {
		return Types.YESNO;
	}

	private boolean getAnswer() {
		return answer;
	}

	protected void setAnswer(boolean answer) {
		this.answer = answer;
	}

	@Override
	public boolean checkAnswer(Object answer) {
		if (answer instanceof Boolean) {
			return ((Boolean) answer) == this.getAnswer();
		} else {
			return false;
		}
	}

	@Override
	public void setAnswer(String answer) throws ParseAnswerException {
		this.setAnswer(answer == "true");
	}

	@Override
	public String answerToString() {
		return String.valueOf(this.getAnswer());
	}

}
