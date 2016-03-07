package simori.Tests;

import static org.junit.Assert.assertEquals;
import static simori.FunctionButton.OK;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.SimoriGui;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.KeyboardException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

/**
 * Unit tests for the Event types and listener
 * interfaces defined in SimoriGui.java
 * @author Matt
 * @version 2.0.0
 */
public class TestSimoriGui {
	
	SimoriGui mockGui;

	private static final boolean O = false;
	private static final boolean[][] prettyGrid = {
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O}, 
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
			{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O}};

	/** Instantiates mock GUI */
	@Before
	public void setUp() throws KeyboardException {
		mockGui = new SimoriJFrame(new QwertyKeyboard((byte) 16, (byte) 16));
	}
	
	/** Tests the getX of GridButtonEvent */
	@Test
	public void testGetX() {
		assertEquals(1, new GridButtonEvent(mockGui, 1, 0).getX());
	}
	
	/** Tests the getY of GridButtonEvent */
	@Test
	public void testGetY() {
		assertEquals(1, new GridButtonEvent(mockGui, 0, 1).getY());
	}
	
	/** Tests the getSource of GridButtonEvent */
	@Test
	public void testGetGridSource() {
		assertEquals(mockGui, new GridButtonEvent(mockGui, 0, 0).getSource());
	}
	
	/** Tests the getFunctionButton of FunctionButtonEvent */
	@Test
	public void testGetFunctionButton() {
		FunctionButtonEvent e = new FunctionButtonEvent(mockGui, OK);
		assertEquals(OK, e.getFunctionButton());
	}
	
	/** Tests the getSource of FunctionButtonEvent */
	@Test
	public void testGetFunctionSource() {
		assertEquals(mockGui, new FunctionButtonEvent(mockGui, OK).getSource());
	}
	
	/** Resets the mock GUI */
	@After
	public void tearDown() {
		mockGui = null;
	}
}