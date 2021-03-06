package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.FunctionButton;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.Button;
import simori.SwingGui.GuiProperties;
import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.SimoriPanel;
import simori.Tests.GuiTests.MockSimoriJFrame.MockButton;
import simori.Tests.GuiTests.MockSimoriJFrame.MockSimoriPanel;


/**
 * 
 * @author Josh
 * @author Jurek
 * @version 1.0.2
 * 
 * Class that tests SimoriPanel.
 */
public class TestSimoriPanel {
	MockSimoriJFrame gui;
	MockSimoriPanel panel;

	@Before
	public void setUp() {
		gui = new MockSimoriJFrame();
		panel = gui.getSimoriPanel();
	}

	@After
	public void tearDown() {
		gui = null;
		panel = null;
	}

	
	@Test
	public void testConstructor() {
		QwertyKeyboard map = new QwertyKeyboard((byte)16, (byte)16);
		OnPressListenerMaker maker = new OnPressListenerMaker(gui);
		SimoriPanel panel = new SimoriPanel(map, maker);// since constructor hides most things away it means there is very little to test!
		assertNotNull(panel);
	}
	
	
	@Test
	public void testLcd() {
		assertNotNull(panel.getLcd());
	}
	
	@Test
	public void testOff(){
		panel.switchOff();
		Button[] buttons = panel.getLeftBar().getButtons();
		for(Button button: buttons){
			assertEquals(GuiProperties.CIRCLE_GREYED,((MockButton)button).getFillColour());
		}
	}
	
	@Test
	public void testOn(){
		panel.switchOff();
		Button[] buttons = panel.getRightBar().getButtons();
		for(Button button: buttons){
			assertEquals(GuiProperties.CIRCLE_GREYED,((MockButton)button).getFillColour());
		}
		panel.switchOn();
		for(Button button: buttons){
			assertTrue(button.isEnabled());
		}
	}
	
	@Test
	public void testCanDragFrom() {
		Point point = new Point(0, 0);
		assertTrue(panel.canDragFrom(point));
	}
	
	@Test
	public void testCanDragFromLeft() {
		Rectangle rec = panel.getLeftBar().getBounds();
		double insideX = rec.getCenterX();
		double insideY = rec.getCenterY();
		Point point = new Point((int)insideX, (int)insideY);
		assertTrue(panel.canDragFrom(point));
	}
	
	@Test
	public void testCanDragFromNo() {
		Point point = new Point(99999999, 99999999);
		assertFalse(panel.canDragFrom(point));
	}
	
	@Test
	public void testSetKeyboardShown() {
		assertTrue(panel.getGridPanel().getLedPanel().isVisible());
		panel.setKeyboardShown(true);
		assertFalse(panel.getGridPanel().getLedPanel().isVisible()); // something is showing that isnt the default led!
		// very hard to work out if keyboard is showing, instead work out if led's are NOT showing
	}
	
	@Test
	public void testSetGrid() {
		boolean[][] grid = new boolean[16][16];
		for(int i = 0; i<15; i++){
			grid[i][i] = true;
		}
		
		assert(grid[5][5]);
		assert(!grid[5][2]);
		panel.setGrid(grid);
		for(int i = 0; i<15; i++){
		assertTrue(panel.getGridPanel().getLedPanel().getLed((byte)i,(byte) i).getIlluminated());
		}
	}
	
	@Test
	public void testPainting() {
		// very hard to test if something is painted!
		// instead test that paint methods were called when panel is created
		// this is done through checking code coverage using emma,
		// unfortunately Junit doesn't provide a way of testing whether a method ran or not
		
		// of course the easy way to test this is just to look with your eyes and ask the question: "Has this been painted?"
		Graphics g = panel.getGraphics();
		panel.paintComponent(g);
		panel.paintBorder(g);
	}
	
	@Test
	public void testSetGreyedOutL1() {
		panel.setGreyedOut(FunctionButton.L1, true);
		assertEquals(panel.getLeftBar().getButtons()[0].getCursor(), GuiProperties.NORMAL_CURSOR);
		assertEquals(panel.getLeftBar().getButtons()[1].getCursor(), GuiProperties.HAND_CURSOR);
	} 

}
