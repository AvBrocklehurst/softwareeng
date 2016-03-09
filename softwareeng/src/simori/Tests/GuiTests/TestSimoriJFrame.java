package simori.Tests.GuiTests;

import static org.junit.Assert.*;
import static simori.SwingGui.GuiProperties.SCREEN_PROPORTION;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.SimoriGui.KeyboardMapping;
import simori.Modes.QwertyKeyboard;
import simori.Tests.GuiTests.MockSimoriJFrame.MockLed;

/**
 * Class that tests SimoriJFrame
 * It uses the MockSimoriJFrame to access the normally private/protected fields
 * @author Jurek
 */
public class TestSimoriJFrame {
	MockSimoriJFrame jframe;
	KeyboardMapping mapping;
	
	@Before
	public void setUp() throws Exception {
		mapping = new QwertyKeyboard((byte)16, (byte)16);
		jframe = new MockSimoriJFrame(mapping);
		
	}

	@After
	public void tearDown() throws Exception {
		jframe = null;
		mapping = null;
	}
	
	@Test
	public void testSetGrid() {
		boolean[][] grid = new boolean[16][16];
		grid[5][5] = true;
		jframe.setGrid(grid);
		
		//check that only 5,5 LED got changed
		assertTrue(jframe.getLedPanel().getLed((byte)5, (byte)5).getIlluminated());
		assertFalse(jframe.getLedPanel().getLed((byte)0, (byte)0).getIlluminated());
	}
	
	@Test
	public void testClearGrid() {
		boolean[][] grid = new boolean[16][16];
		grid[5][5] = true;
		jframe.setGrid(grid);
		jframe.clearGrid();
		assertFalse(jframe.getLedPanel().getLed((byte)5, (byte)5).getIlluminated());
	}
	
	/**
	 * Since both tests would be the same (as there is no other way
	 * of getting or settings the text field) this unit test tests
	 * both setText and getText at the same time.
	 * @author Jurek
	 */
	@Test
	public void testSetAndGetText() {
		//jframe.switchOn();
		jframe.setText("test");
		assertEquals(jframe.getText(), "test");
	}
	
	@Test
	public void switchOn() {

	}
	
	@Test
	public void testSwitchOff() {

	}
	
	@Test
	public void testSetGridButtonListener() {

	}
	
	@Test
	public void testSetFunctionButtonListener() {

	}
	
	@Test
	public void testGetGridButtonListener() {

	}
	
	@Test
	public void testGetFunctionButtonListener() {

	}
	
	@Test
	public void testSetKeyboardShown() {

	}
	
	@Test
	public void testGetKeyboardMapping() {

	}

	@Test
	public void testMouseDragged() {

	}

	@Test
	public void testMouseMoved() {
		
	}	
}
