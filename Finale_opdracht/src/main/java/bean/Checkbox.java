package bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import util.ParseAnswerException;

public class Checkbox extends Question {

	private Map<String, Boolean> answers;

	public Checkbox() {
		this.answers = new HashMap<String, Boolean>();
	}

	@Override
	public Types getType() {
		return Types.CHECKBOX;
	}

	private Boolean getAnswer(String value) {
		return answers.get(value);
	}

	private void addAnswer(String key, boolean value) {
		this.answers.put(key, value);
	}

	public Set<String> getEntries() {
		return answers.keySet();
	}

	@Override
	/**
	 *   @param answer the answers given in a map
	 *   @return 	true/false
	 */
	public boolean checkAnswer(Object answer) {
		if (answer instanceof String[]) {
			String[] answers = (String[]) answer;
			for (String entry : answers) {
				if (!this.getAnswer(entry)) {
					return false;
				}
			}
			// if all checks ok then all answers are correct
			return true;
		} else if (answer instanceof Map) {
			Map<String, Boolean> answers = (Map<String, Boolean>) answer;
			for (Map.Entry<String, Boolean> entry : answers.entrySet()) {
				if (this.getAnswer(entry.getKey()) != entry.getValue()) {
					return false;
				}
			}
			// if all checks ok then all answers are correct
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setAnswer(String answer) throws ParseAnswerException {
		String[] entries = answer.split(";");
		try {
			for (String entry : entries) {
				String[] keyvalue = entry.split(":");
				// check if true to cast to boolean
				this.addAnswer(keyvalue[0], keyvalue[1] == "true");
			}
		} catch (Exception ex) {
			throw new ParseAnswerException("Parsing checkbox answer failed with exception: " + ex.getMessage());
		}
	}

	@Override
	public String answerToString() {
		StringBuilder builder = new StringBuilder();

		for (Map.Entry<String, Boolean> entry : this.answers.entrySet()) {
			builder.append(entry.getKey() + ":" + entry.getValue() + ";");
		}
		// remove last ;
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}
}
