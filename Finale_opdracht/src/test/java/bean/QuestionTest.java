package bean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class QuestionTest {

	@Test
	public void sliderTest() {
		assertNotNull(new Slider());
	}

	@Test
	public void checkboxTest() {
		assertNotNull(new Checkbox());
	}

	@Test
	public void draganddropTest() {
		assertNotNull(new DragAndDrop());
	}

	@Test
	public void multiplechoiceTest() {
		assertNotNull(new Multiplechoice());
	}

	@Test
	public void numericTest() {
		assertNotNull(new Numeric());
	}

	@Test
	public void yesnoTest() {
		assertNotNull(new Yesno());
	}

	@Test
	public void answerToString() {
		Question question = new Multiplechoice();
		assertNotNull(question.answerToString());
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

}
