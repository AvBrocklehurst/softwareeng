package simori.Tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Led.OnPressListener;
import simori.Mode;
import simori.SimoriGui;
import simori.SimoriGuiEvents.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;

/**
 * Unit tests for SimoriGui class.
 * Since its function is to display a user interface,
 * many of its methods are better suited to white
 * box testing than batch unit testing.
 * @author Matt
 * @version 1.0.0
 */
public class TestSimoriGui {
	
	SimoriGui gui;
	MockSimoriGui mockGui;
	GridButtonEvent receivedEvent;
	
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
	
	/**
	 * Concrete implementation of Mode for mock objects.
	 * Records the last {@link GridButtonEvent} it received.
	 */
	private class MockMode extends Mode {
		@Override
		public void onGridButtonPress(GridButtonEvent e)
				throws InvalidCoordinatesException {
			receivedEvent = e;
		}
	}
	
	/** Instantiates mock objects and test subjects */
	@Before
	public void setUp() {
		gui = new SimoriGui(16, 16);
		mockGui = new MockSimoriGui(16, 16);
		receivedEvent = null;
	}
	
	/** Tests that the Simori's mode can be changed */
	@Test
	public void testSetMode() {
		gui.setMode(new MockMode());
		//TODO Implement this test once we have more modes
	}
	
	/**
	 * Tests the listeners made by the makeListenerWith
	 * helper method to ensure that when triggered they
	 * report the correct GridButtonEvent to the Mode.
	 */
	@Test
	public void testMakeListenerWith() {
		mockGui.setMode(new MockMode());
		GridButtonEvent e = new GridButtonEvent(mockGui, 0, 0);
		OnPressListener l = mockGui.makeListenerWith(e);
		l.onPress();
		assertEquals(e, receivedEvent);
	}

	/**
	 * This covers a few of methods relating to visuals.
	 * I can't verify their results with assertions,
	 * but I can make sure they don't produce uncaught exceptions.
	 */
	@Test
	public void cannotTestProperly() {
		gui.setGrid(prettyGrid);
		mockGui.makeEdgeButtons();
	}
	
	/** Resets mock objects and test subjects */
	@After
	public void tearDown() {
		gui = mockGui = null;
		receivedEvent = null;
	}
}
