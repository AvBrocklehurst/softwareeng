package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.FunctionButton;
import simori.SwingGui.Button;
import simori.SwingGui.GuiProperties;
import simori.SwingGui.Lcd;
import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.SimoriEdgeBar;
import simori.Tests.GuiTests.MockSimoriJFrame.MockButton;
import simori.Tests.GuiTests.MockSimoriJFrame.MockSimoriEdgeBar;

/**
 * 
 * @author Josh
 * @author Jurek
 * @version 1.0.4
 * 
 * Class that tests the SimoriEdgeBar
 */
public class TestSimoriEdgeBar {
	MockSimoriJFrame gui;
	MockSimoriEdgeBar edgeBarBottom;
	MockSimoriEdgeBar edgeBarLeft;

	@Before
	public void setUp() {
		gui = new MockSimoriJFrame();
		edgeBarBottom = gui.getBottomBar();
		edgeBarLeft = gui.getLeftBar();
	}

	@After
	public void tearDown() {
		gui = null;
		edgeBarBottom = null;
		edgeBarLeft = null;
	}

	@Test
	public void testConstructor() {
		OnPressListenerMaker maker = new OnPressListenerMaker(gui);
		SimoriEdgeBar bar = new SimoriEdgeBar(true, true, maker, FunctionButton.L1, FunctionButton.L2, FunctionButton.L3, FunctionButton.L4); // since constructor hides most things away it means there is very little to test!
		assertNotNull(bar);
	}
	
	@Test
	public void testWithLcd() {
		Lcd lcd = edgeBarBottom.getLcd();
		assertNotNull(lcd);
	}
	
	@Test
	public void testWithoutLcd() {
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
	
	@Test
	public void testOff(){
		edgeBarLeft.switchOff();
		Button[] buttons = edgeBarLeft.getButtons();
		for(Button button: buttons){
			assertEquals(GuiProperties.CIRCLE_GREYED,((MockButton)button).getFillColour());
		}
	}

	@Test
	public void testOn(){
		edgeBarLeft.switchOff();
		Button[] buttons = edgeBarLeft.getButtons();
		for(Button button: buttons){
			assertEquals(GuiProperties.CIRCLE_GREYED,((MockButton)button).getFillColour());
		}
		edgeBarLeft.switchOn();
		for(Button button: buttons){
			assertTrue(button.isEnabled());
		}
	} 
	
	@Test
	public void testSetGreyedOutValid() {
		assertTrue(edgeBarLeft.setGreyedOut(FunctionButton.L1, true));
		assertEquals(edgeBarLeft.getButtons()[0].getCursor(), GuiProperties.NORMAL_CURSOR);
	} 
	
	@Test
	public void testSetGreyedOutInvalid() {
		assertFalse(edgeBarLeft.setGreyedOut(FunctionButton.ON, true));
	}
}
