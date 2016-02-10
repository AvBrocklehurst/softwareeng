package simori.Tests;

import static org.junit.Assert.assertEquals;
import static simori.SimoriGuiEvents.FunctionButton.OK;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.SimoriGui;
import simori.SimoriGuiEvents;
import simori.SimoriGuiEvents.FunctionButtonEvent;
import simori.SimoriGuiEvents.GridButtonEvent;

/**
 * Unit tests for the Event types, listener interfaces
 * and enumerated type defined in TestSimoriGuiEvents.
 * @author Matt
 * @version 1.0.0
 */
public class TestSimoriGuiEvents {
	
	SimoriGui mockGui;
	
	/** Instantiates mock GUI */
	@Before
	public void setUp() {
		mockGui = new SimoriGui(16, 16);
	}
	
	/**
	 * The class never need be instantiated, but
	 * this test checks it is possible to do so.
	 */
	@Test
	public void testInstantiation() {
		new SimoriGuiEvents();
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
