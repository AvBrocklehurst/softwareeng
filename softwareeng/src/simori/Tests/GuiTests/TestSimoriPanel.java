package simori.Tests.GuiTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.SwingGui.Button;
import simori.Tests.GuiTests.MockSimoriJFrame.MockSimoriPanel;

/**
 * 
 * @author Josh
 * @version 1.0.1
 * 
 * Class that tests SimoriPanel.
 */
public class TestSimoriPanel {
	MockSimoriJFrame gui;
	MockSimoriPanel panel;

	@Before
	public void setUp() throws Exception {
		gui = new MockSimoriJFrame();
		panel = gui.getSimoriPanel();
	}

	@After
	public void tearDown() throws Exception {
		gui = null;
		panel = null;
	}

	@Test
	public void test() {
	}
	
	@Test
	public void testLcd() {
		assertNotNull(panel.getLcd());
	}
	
	@Test
	public void testA() {
		panel.getGridPanel();
	}
	
	@Test
	public void testOff(){
		panel.switchOff();
		Button[] buttons = panel.getLeftBar().getButtons();
		for(Button button: buttons){
			assertFalse(button.isEnabled());
		}
	}

	@Test
	public void testOn(){
		panel.switchOff();
		Button[] buttons = panel.getRightBar().getButtons();
		for(Button button: buttons){
			assertFalse(button.isEnabled());
		}
		panel.switchOn();
		for(Button button: buttons){
			assertTrue(button.isEnabled());
		}
	}

}
