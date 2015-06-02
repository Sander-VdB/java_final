package bean;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ParseAnswerException;

public class DragAndDrop extends Question {

	private Map<String, String> answer;

	public DragAndDrop() {
		this.answer = new HashMap<String, String>();
	}

	private String getAnswer(String key) {
		return answer.get(key);
	}

	private void addAnswer(String key, String value) {
		this.answer.put(key, value);
	}

	public List<String> getKeys() {
		// TODO
		// set does not retain order-> still need shuffle?
		List<String> collection = (List<String>) this.answer.keySet();
		Collections.shuffle(collection);
		return collection;
	}

	public List<String> getValues() {
		// TODO
		// set does not retain order-> still need shuffle?
		List<String> collection = (List<String>) this.answer.values();
		Collections.shuffle(collection);
		return collection;
	}

	@Override
	public boolean checkAnswer(Object answer) {
		if (answer instanceof Map) {
			Map<String, String> answers = (Map<String, String>) answer;
			for (Map.Entry<String, String> entry : answers.entrySet()) {
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
				this.addAnswer(keyvalue[0], keyvalue[1]);
			}
		} catch (Exception ex) {
			throw new ParseAnswerException("Parsing checkbox answer failed with exception: " + ex.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (Map.Entry<String, String> entry : this.answer.entrySet()) {
			builder.append(entry.getKey() + ":" + entry.getValue() + ";");
		}
		// remove last ;
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

}
