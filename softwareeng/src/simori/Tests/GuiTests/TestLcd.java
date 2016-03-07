package simori.Tests.GuiTests;

import static org.junit.Assert.*;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.SwingGui.Lcd;
/**
 * 
 * @author Josh
 * version 1.0.1
 * 
 * class that tests Lcd class.
 */
public class TestLcd {
	Lcd lcd;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLcd() {
		Lcd lcd = new Lcd(false);
		assertNotNull(lcd);
	}
	
	@Test
	public void testSetShorterSide() throws InterruptedException{
		JFrame gui = new JFrame();
		gui.setTitle("It has a title");
		JPanel panel = new JPanel();
		gui.add(panel);
		panel.setMinimumSize(new Dimension(300, 300));
		gui.setVisible(true);

		lcd = new Lcd(false);
		lcd.setMinimumSize(new Dimension(100, 100));
		gui.add(lcd);

		Thread.sleep(5000);
		lcd.setShorterSize(100f);
		//System.out.println(lcd.getHeight());
	}
	
	

}
