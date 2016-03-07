package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.awt.event.MouseEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.FunctionButton;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.FunctionButtonListener;
import simori.SimoriGui.GridButtonEvent;
import simori.SimoriGui.GridButtonListener;
import simori.Exceptions.InvalidCoordinatesException;
import simori.Exceptions.KeyboardException;
import simori.SwingGui.Button;
import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.OnPressListenerMaker.OnPressListener;
import simori.SwingGui.PressableCircle;

/**
 * Tests {@link OnPressListenerMaker} to 100% coverage.
 * @author Matt
 * @version 1.0.1
 */
public class TestOnPressListenerMaker {
	
	private MockSimoriJFrame gui;
	private OnPressListenerMaker testSubject;
	private OnPressListener listener;
	private PressableCircle circle;
	private MouseEvent event;
	private FunctionButton pressed;
	private Integer pressX, pressY;
	
	/** Instantiates mock objects and test subjects */
	@Before
	public void setUp() throws KeyboardException {
		gui = new MockSimoriJFrame();
		testSubject = new OnPressListenerMaker(gui);
		circle = new Button();
		event = new MouseEvent(gui, 0, 0, 0, 0, 0, 0, false);
		pressed = null;
		pressX = pressY = null;
	}
	
	/** 
	 * Returns a listener to register as the {@link MockSimoriJFrame}'s
	 * {@link FunctionButtonListener}. This must be done before calling
	 * {@link #trigger} so that the {@link OnPressListener} has a further
	 * listener to call. If used correctly, the {@link FunctionButton}
	 * pressed is recorded in {@link #pressed}.
	 */
	private FunctionButtonListener getRecordFbListener() {
		return new FunctionButtonListener() {
			@Override
			public void onFunctionButtonPress(FunctionButtonEvent e) {
				pressed = e.getFunctionButton();
			}
		};
	}
	
	/** 
	 * Returns a listener to register as the {@link MockSimoriJFrame}'s
	 * {@link GridButtonListener}. This must be done before calling
	 * {@link #trigger} so that the {@link OnPressListener} has a further
	 * listener to call. If used correctly, the coordinates of the button
	 * pressed are recorded in {@link #pressX} and {@link #pressY}.
	 * If the x coordinate is less than zero, an exception is thrown,
	 * simulating the effect of trying to update a non-existent LED.
	 */
	private GridButtonListener getRecordGbListener() {
		return new GridButtonListener() {
			@Override
			public void onGridButtonPress(GridButtonEvent e)
					throws InvalidCoordinatesException {
				if (e.getX() < 0) throw new InvalidCoordinatesException();
				pressX = e.getX();
				pressY = e.getY();
			}
		};
	}
	
	/** Resets mock objects and test subjects */
	@After
	public void tearDown() {
		gui = null;
		testSubject = null;
		circle = null;
		event = null;
		pressed = null;
		pressX = pressY = null;
	}
	
	/**
	 * Causes the {@link OnPressListener#onPress} method of the given
	 * {@link OnPressListener} to be called, by setting it as the
	 * listener of {@link #circle} and simulating a press.
	 */
	private void trigger(OnPressListener listener) {
		circle.addOnPressListener(listener);
		circle.mouseEntered(event);
		circle.mousePressed(event);
	}
	
	/**
	 * Makes an {@link OnPressListener} to trigger a {@link GridButtonEvent}
	 * and checks that the {@link GridButtonListener}
	 * reports the same x coordinate as was passed to
	 * {@link OnPressListenerMaker#getListener(int, int)}.
	 */
	@Test
	public void testCoordsX() {
		gui.setGridButtonListener(getRecordGbListener());
		Integer x = 7;
		Integer y = 9;
		listener = testSubject.getListener(x, y);
		trigger(listener);
		assertEquals(x, pressX);
	}
	
	/**
	 * Makes an {@link OnPressListener} to trigger a {@link GridButtonEvent}
	 * and checks that the {@link GridButtonListener}
	 * reports the same y coordinate as was passed to
	 * {@link OnPressListenerMaker#getListener(int, int)}.
	 */
	@Test
	public void testCoordsY() {
		gui.setGridButtonListener(getRecordGbListener());
		Integer x = 7;
		Integer y = 9;
		listener = testSubject.getListener(x, y);
		trigger(listener);
		assertEquals(y, pressY);
	}
	
	/**
	 * Makes an {@link OnPressListener} to trigger a
	 * {@link FunctionButtonEvent} and checks that the
	 * {@link FunctionButtonListener} reports the same
	 * {@link FunctionButton} as was passed to
	 * {@link OnPressListenerMaker#getListener(int, int)}.
	 */
	@Test
	public void testFunctionButton() {
		gui.setFunctionButtonListener(getRecordFbListener());
		FunctionButton fb = FunctionButton.OK;
		listener = testSubject.getListener(fb);
		trigger(listener);
		assertEquals(fb, pressed);
	}

	/**
	 * Makes an {@link OnPressListener} to trigger a
	 * {@link FunctionButtonEvent} for a null {@link FunctionButton}
	 * and checks that the {@link FunctionButtonListener} reports it null.
	 */
	@Test
	public void testNullFunctionButton() {
		gui.setFunctionButtonListener(getRecordFbListener());
		FunctionButton fb = null;
		listener = testSubject.getListener(fb);
		trigger(listener);
		assertNull(pressed);
	}
	
	/**
	 * Makes an {@link OnPressListener} to trigger a {@link GridButtonEvent}
	 * with a invalid x coordinate so that an
	 * {@link InvalidCoordinatesException} and checks that when the
	 * {@link GridButtonListener} throws, this is caught.
	 */
	@Test
	public void testInvalidCoordsCatch() {
		gui.setGridButtonListener(getRecordGbListener());
		Integer x = -1;
		Integer y = 10;
		listener = testSubject.getListener(x, y);
		trigger(listener);
		assertNull(pressX);
		assertNull(pressY);
	}
}