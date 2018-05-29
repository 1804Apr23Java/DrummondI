import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MainClassTest {

	@Test
	public void isPanagramTest() {
		assertTrue(MainClass.isPanagram("abcdefghijklmnopqrstuvwxyz"));
	}
	
	@Test
	public void isNotPanagramTest() {
		assertFalse(MainClass.isPanagram("NotAPanagram"));
	}
	
	@Test
	public void isAPanagram() {
		assertTrue(MainClass.isPanagram("THEquickFOXjumpsOVERtheLAZYbrownDOG"));
	}
	
	@Test
	public void nullInputTest() {
		assertFalse(MainClass.isPanagram(null));
	}
	
	@Test
	public void badCharacterTest() {
		assertFalse(MainClass.isPanagram("THEquickFOXjumpedOVERtheLAZYbrownDOG!"));
	}
}
