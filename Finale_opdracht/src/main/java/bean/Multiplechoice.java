package bean;

import java.util.Set;

import util.ParseAnswerException;

public class Multiplechoice extends Question {

	private String answer;
	private Set<String> entries;

	private String getAnswer() {
		return answer;
	}

	public Set<String> getEntries() {
		return entries;
	}

	@Override
	/**
	 *   @param answer the chosen answer string
	 *   @return 	true/false
	 */
	public boolean checkAnswer(Object answer) {
		if (answer instanceof String) {
			return ((String) answer).compareTo(this.getAnswer()) == 0;
		} else {
			return false;
		}
	}

	@Override
	/**
	 * converts a string to a multiplechoice answer
	 * the correct answer is found in the front followed by the remaining entries delimited by ';'
	 */
	public void setAnswer(String answer) throws ParseAnswerException {
		try {
			String[] answerkeys = answer.split(":");
			this.answer = answerkeys[0];
			String[] entries = answerkeys[1].split(";");
			for (int i = 0; i < entries.length; i++) {
				this.entries.add(entries[i]);
			}
		} catch (Exception ex) {
			throw new ParseAnswerException("Parsing checkbox answer failed with exception: " + ex.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(this.answer + ":");
		for (String entry : this.entries) {
			builder.append(entry + ";");
		}
		// remove last ;
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}
}
