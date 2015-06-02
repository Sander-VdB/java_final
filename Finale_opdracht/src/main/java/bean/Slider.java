package bean;

import util.ParseAnswerException;

public class Slider extends Numeric {

	private int start;
	private int end;
	private int increment;

	public int getStart() {
		return start;
	}

	protected void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	protected void setEnd(int end) {
		this.end = end;
	}

	public int getIncrement() {
		return increment;
	}

	protected void setIncrement(int increment) {
		this.increment = increment;
	}

	@Override
	public void setAnswer(String answer) throws ParseAnswerException {
		try {
			String[] entries = answer.split(";");
			super.setAnswer(Integer.parseInt(entries[0]));
			this.setStart(Integer.parseInt(entries[1]));
			this.setEnd(Integer.parseInt(entries[2]));
			this.setIncrement(Integer.parseInt(entries[3]));
		} catch (Exception ex) {
			throw new ParseAnswerException("Parsing slider answer failed with exception: " + ex.getMessage());
		}
	}

	@Override
	public String toString() {
		return String.valueOf(super.getAnswer()) + ";" + String.valueOf(start) + ";" + String.valueOf(end) + ";"
				+ String.valueOf(increment);
	}
}
