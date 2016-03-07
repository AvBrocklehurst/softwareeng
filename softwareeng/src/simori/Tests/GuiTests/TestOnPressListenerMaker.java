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
import simori.Exceptions.KeyboardException;
import simori.SwingGui.Button;
import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.OnPressListenerMaker.OnPressListener;
import simori.SwingGui.PressableCircle;

public class TestOnPressListenerMaker {
	
	private MockSimoriJFrame gui;
	private OnPressListenerMaker testSubject;
	private OnPressListener listener;
	private PressableCircle circle;
	private MouseEvent event;
	private FunctionButton pressed;
	private Integer pressX, pressY;
	
	@Before
	public void setUp() throws KeyboardException {
		gui = new MockSimoriJFrame();
		testSubject = new OnPressListenerMaker(gui);
		circle = new Button();
		event = new MouseEvent(gui, 0, 0, 0, 0, 0, 0, false);
		pressed = null;
		pressX = pressY = null;
	}
	
	private FunctionButtonListener getRecordFbListener() {
		return new FunctionButtonListener() {
			@Override
			public void onFunctionButtonPress(FunctionButtonEvent e) {
				pressed = e.getFunctionButton();
			}
		};
	}
	
	private GridButtonListener getRecordGbListener() {
		return new GridButtonListener() {
			@Override
			public void onGridButtonPress(GridButtonEvent e) {
				pressX = e.getX();
				pressY = e.getY();
			}
		};
	}
	
	@After
	public void tearDown() {
		gui = null;
		testSubject = null;
		circle = null;
		event = null;
		pressed = null;
		pressX = pressY = null;
	}
	
	private void trigger(OnPressListener listener) {
		circle.addOnPressListener(listener);
		circle.mouseEntered(event);
		circle.mousePressed(event);
	}
	
	@Test
	public void testCoordsX() {
		gui.setGridButtonListener(getRecordGbListener());
		Integer x = 7;
		Integer y = 9;
		listener = testSubject.getListener(x, y);
		trigger(listener);
		assertEquals(x, pressX);
	}
	
	@Test
	public void testCoordsY() {
		gui.setGridButtonListener(getRecordGbListener());
		Integer x = 7;
		Integer y = 9;
		listener = testSubject.getListener(x, y);
		trigger(listener);
		assertEquals(y, pressY);
	}
	
	@Test
	public void testFunctionButton() {
		gui.setFunctionButtonListener(getRecordFbListener());
		FunctionButton fb = FunctionButton.OK;
		listener = testSubject.getListener(fb);
		trigger(listener);
		assertEquals(fb, pressed);
	}

	@Test
	public void testNullFunctionButton() {
		gui.setFunctionButtonListener(getRecordFbListener());
		FunctionButton fb = null;
		listener = testSubject.getListener(fb);
		trigger(listener);
		assertNull(pressed);
	}
	
	@Test
	public void testInvalidCoordsCatch() {
		gui.setGridButtonListener(getRecordGbListener());
		Integer x = -1;
		Integer y = -1;
		listener = testSubject.getListener(x, y);
		trigger(listener);
		assertNull(pressX);
		assertNull(pressY);
	}
}