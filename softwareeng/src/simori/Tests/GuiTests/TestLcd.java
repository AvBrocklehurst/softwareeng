package simori.Tests.GuiTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Exceptions.KeyboardException;
import simori.SwingGui.Lcd;
/**
 * 
 * @author Josh
 * @version 1.0.7
 * 
 * class that tests Lcd class.
 */
public class TestLcd {
	MockSimoriJFrame gui;
	Lcd lcd;

	@Before
	public void setUp() throws Exception {
		gui = new MockSimoriJFrame();
		lcd = gui.getBottomBar().getLcd();
	}

	@After
	public void tearDown() throws Exception {
		gui = null;
		lcd = null;
	}

	@Test
	public void testLcd() {
		Lcd lcd2 = new Lcd(false);
		assertNotNull(lcd2);
		lcd2 = null;
	}
	
	@Test
	public void testSetShorterSide() throws InterruptedException, KeyboardException{
		lcd.setShorterSize(2);
		assertEquals(2, lcd.getHeight());
	}
	
	@Test
	public void testSetText() throws InterruptedException, KeyboardException{
		lcd.setText("hello world!");
		String text = lcd.getText();
		
		assertEquals("hello world!", text);
		
	}
	
	@Test
	public void testSetTextLong() throws InterruptedException, KeyboardException{
		lcd.setText("hello world my goodness this is a long sentance!");
		String text = lcd.getText();
	
		assertEquals("hello world my goodness this is a long sentance!", text);
		
	}
	@Test
	public void testSetTextNone() throws InterruptedException, KeyboardException{
		lcd.setText("");
		String text = lcd.getText();
		assertEquals("", text);
	}
	
}
