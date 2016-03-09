package simori.Tests.GuiTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.FunctionButton;
import simori.Exceptions.KeyboardException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.Button;
import simori.SwingGui.Lcd;
import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.SimoriEdgeBar;
import simori.SwingGui.SimoriJFrame;
import simori.SwingGui.SimoriPanel;
import simori.Tests.GuiTests.MockSimoriJFrame.MockSimoriEdgeBar;

/**
 * 
 * @author Josh
 * @version 1.0.3
 * 
 * Class that tests the SimoriEdgeBar
 */
public class TestSimoriEdgeBar {
	MockSimoriJFrame gui;
	MockSimoriEdgeBar edgeBarBottom;
	MockSimoriEdgeBar edgeBarLeft;

	@Before
	public void setUp() throws Exception {
		gui = new MockSimoriJFrame();
		edgeBarBottom = gui.getBottomBar();
		edgeBarLeft = gui.getLeftBar();
	}

	@After
	public void tearDown() throws Exception {
		gui = null;
		edgeBarBottom = null;
		edgeBarLeft = null;
	}

	@Test
	public void testConstructor() throws KeyboardException, InterruptedException {
		OnPressListenerMaker maker = new OnPressListenerMaker(gui);
		SimoriEdgeBar bar = new SimoriEdgeBar(true, true, maker, FunctionButton.L1, FunctionButton.L2, FunctionButton.L3, FunctionButton.L4); // since constructor hides most things away it means there is very little to test!
		assertNotNull(bar);
	}
	
	@Test
	public void testWithLcd() throws KeyboardException, InterruptedException {
		Lcd lcd = edgeBarBottom.getLcd();
		assertNotNull(lcd);
	}
	
	@Test
	public void testWithoutLcd() throws KeyboardException, InterruptedException {
		Lcd lcd = edgeBarLeft.getLcd();
		assertNull(lcd);
	}
	
	@Test
	public void testCorrectNumberOfComponentsLeft(){
		assertEquals(11, edgeBarLeft.getComponentCount()); // how many components (buttons, glue, etc) should be in a left bar
	}
	
	@Test
	public void testCorrectNumberOfComponentsBottom(){
		assertEquals(7, edgeBarBottom.getComponentCount()); // how many components (buttons, glue, etc) should be in a bottom bar
	}
	
	@Test
	public void testButtonOnByDefault(){
		Button[] buttons = edgeBarLeft.getButtons();
		for(Button button: buttons){
			assertTrue(button.isEnabled());
		}
	}
	/*
	@Test
	public void testOff(){
		edgeBarLeft.switchOff();
		Button[] buttons = edgeBarLeft.getButtons();
		for(Button button: buttons){
			assertFalse(button.isEnabled());
		}
	}

	@Test
	public void testOn(){
		edgeBarLeft.switchOff();
		Button[] buttons = edgeBarLeft.getButtons();
		for(Button button: buttons){
			assertFalse(button.isEnabled());
		}
		edgeBarLeft.switchOn();
		for(Button button: buttons){
			assertTrue(button.isEnabled());
		}
	} */
}
