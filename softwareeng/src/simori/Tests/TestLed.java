package simori.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.event.MouseEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Mode;
import simori.SimoriGuiEvents.GridButtonEvent;
import simori.SwingGui.Led;
import simori.SwingGui.Led.OnPressListener;
import simori.Exceptions.InvalidCoordinatesException;

/**
 * Unit tests for the Led class.
 * Since it's part of the UI system, some aspects are not
 * suited to unit testing and will have to be white box tested.
 * @author Matt
 * @version 1.0.2
 */
public class TestLed {
	
	//Mock objects and test subjects
	private MockSimoriGui mockGui;
	private Led led, otherLed;
	private MouseEvent mockEvent;
	private OnPressListener testListener;
	
	//Keeps track of whether testListener received a callback
	private boolean triggered;
	
	/** An arbitrary concrete Mode for the mock GUI to use */
	private class MockMode extends Mode {
		@Override
		public void onGridButtonPress(GridButtonEvent e)
				throws InvalidCoordinatesException {}
	};
	
	/** Instantiates mock objects and test subjects */
	@Before
	public void setUp() {
		mockGui = new MockSimoriGui(16, 16);
		mockGui.setVisible(true);
		mockGui.setMode(new MockMode());
		led = mockGui.getLed(2, 3);
		otherLed = mockGui.getLed(0, 0);
		mockEvent = new MouseEvent(led,
				0, 0, 0, 0, 0, 0, 0, 0, false, 0);
		testListener = new OnPressListener() {
			@Override
			public void onPress() {
				triggered = true;
			}
		};
		led.addOnPressListener(testListener);
		triggered = false;
	}
	
	/**
	 * Tests that moving the mouse over the Led whilst
	 * it is not pressed is not registered as a press.
	 */
	@Test
	public void testMouseOverNotPressed() {
		otherLed.mouseReleased(mockEvent);
		led.mouseEntered(mockEvent);
		assertFalse(triggered);
	}
	
	/**
	 * Tests that moving the mouse over the Led after pressing
	 * it in a different Led is registered as a press.
	 */
	@Test
	public void testMouseOverPressedElsewhere() {
		otherLed.mouseEntered(mockEvent);
		otherLed.mousePressed(mockEvent);
		led.mouseEntered(mockEvent);
		assertTrue(triggered);
	}
	
	/**
	 * Tests that pressing the mouse button outside
	 * the Led is not registered as a press
	 */
	@Test
	public void testMousePressedNotOver() {
		led.mousePressed(mockEvent);
		assertFalse(triggered);
	}
	
	/**
	 * Tests that pressing the mouse button
	 * inside the Led is registered as a press
	 */
	@Test
	public void testMousePressedOver() {
		led.mouseEntered(mockEvent);
		led.mousePressed(mockEvent);
		assertTrue(triggered);
	}
	
	/**
	 * Tests that clicking the mouse does not register
	 * a press after it has left the Led.
	 */
	@Test
	public void testMouseExitedNotPressed() {
		led.mouseReleased(mockEvent);
		led.mouseEntered(mockEvent);
		led.mouseExited(mockEvent);
		led.mousePressed(mockEvent);
		assertFalse(triggered);
	}
	
	/**
	 * Tests the other branch of mouseExited.
	 */
	@Test
	public void testMouseExitedPressed() {
		led.mouseEntered(mockEvent);
		led.mousePressed(mockEvent);
		led.mouseExited(mockEvent);
		assertTrue(triggered);
	}
	
	/**
	 * Tests that releasing the mouse and returning
	 * it to the Led does not register a press.
	 */
	@Test
	public void testMouseReleasedOutside() {
		led.mouseExited(mockEvent);
		led.mousePressed(mockEvent);
		led.mouseReleased(mockEvent);
		led.mouseEntered(mockEvent);
		assertFalse(triggered);
	}
	
	/**
	 * Tests the other branch of mouseReleased.
	 */
	@Test
	public void testMouseReleasedInside() {
		led.mouseEntered(mockEvent);
		led.mousePressed(mockEvent);
		led.mouseReleased(mockEvent);
		assertTrue(triggered);
	}
	
	/**
	 * Tests that mouseClicked does not register a press.
	 */
	@Test
	public void testMouseClicked() {
		led.mouseClicked(mockEvent);
		assertFalse(triggered);
	}
	
	/**
	 * Tests that a callback is not attempted
	 * to a null OnPressListener.
	 */
	@Test
	public void testNullListener() {
		led.addOnPressListener(null);
		led.mouseEntered(mockEvent);
		led.mousePressed(mockEvent);
		assertFalse(triggered);
	}
	
	/**
	 * This covers a few of Led's methods relating to visuals.
	 * I can't verify their results with assertions,
	 * but I can explore all their branches for exceptions.
	 */
	@Test
	public void cannotTestProperly() {
		led.paintAll(led.getGraphics());
		led.setIlluminated(true);
		led.paintAll(led.getGraphics());
		led.setIlluminated(true);
		led.setIlluminated(false);
	}
	
	/** Resets mock objects and test subjects */
	@After
	public void tearDown() {
		mockGui.setVisible(false);
		mockGui = null;
		led = otherLed = null;
		mockEvent = null;
		testListener = null;
		triggered = false;
	}
}
