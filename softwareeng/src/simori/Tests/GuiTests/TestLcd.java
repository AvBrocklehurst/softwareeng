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
		lcd.setShorterSize(10);
		gui.setVisible(true);
		lcd.setVisible(true);
		assertEquals(10, lcd.getHeight());
		gui.setVisible(false);
		lcd.setVisible(false);
	}
	
	@Test
	public void testSetText() throws InterruptedException, KeyboardException{
		lcd.setText("hello world!");
		String text = lcd.getText();
		gui.setVisible(true);
		lcd.setVisible(true);
		assertEquals("hello world!", text);
		gui.setVisible(false);
		lcd.setVisible(false);
	}
	
	@Test
	public void testSetTextLong() throws InterruptedException, KeyboardException{
		lcd.setText("hello world my goodness this is a long sentance!");
		String text = lcd.getText();
		gui.setVisible(true);
		lcd.setVisible(true);
		assertEquals("hello world my goodness this is a long sentance!", text);
		gui.setVisible(false);
		lcd.setVisible(false);
	}
	
	@Test
	public void testSetTextNone() throws InterruptedException, KeyboardException{
		lcd.setText("");
		String text = lcd.getText();
		gui.setVisible(true);
		lcd.setVisible(true);
		assertEquals("", text);
		gui.setVisible(false);
		lcd.setVisible(false);
	}

}
