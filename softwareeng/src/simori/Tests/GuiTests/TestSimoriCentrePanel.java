package simori.Tests.GuiTests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.LedPanel;
import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.SimoriCentrePanel;
import simori.SwingGui.SimoriJFrame;
import simori.Tests.GuiTests.MockSimoriJFrame.MockCentrePanel;

/**
 * A class to test SimoriCentrePanel.
 * 
 * @author James
 * @version 1.0.0
 * @see SimoriCentrePanel.java
 *
 */
public class TestSimoriCentrePanel {
	
	private MockCentrePanel mockpanel;
	private OnPressListenerMaker testmaker;
	private QwertyKeyboard keyboard;
	private SimoriJFrame testgui;
	
	@Before 
	public void setUp() throws SimoriNonFatalException{
		keyboard = new QwertyKeyboard((byte)16,(byte)16);
		testgui = new SimoriJFrame(keyboard);
		testmaker = new OnPressListenerMaker(testgui);
		mockpanel = new MockCentrePanel(keyboard, testmaker);
	}
	
	@After
	public void tearDown(){
		testgui = null;
		testmaker = null;
		keyboard = null;
		mockpanel = null;
	}
	
	@Test
	public void test_constructor(){
		assertThat("A SimoriCentrePanel was not instantiated properly!", mockpanel, instanceOf(SimoriCentrePanel.class));
	}
	
	@Test
	public void test_makeLedPanel(){
		LedPanel lp = mockpanel.makeLedPanel(keyboard, testmaker);
		assertThat("An LedPanel was not created as expected!", lp, instanceOf(LedPanel.class));
	}
	
	/*
	 * setGrid()'s functionality is already fully tested in TestLedPanel.java
	 */
	@Test
	public void call_setGrid(){
		boolean[][] grid = new boolean[16][16];
		mockpanel.setGrid(grid);
	}
	
	
	/*
	 * A coverage caller as the methods: setKeyBoardShown, switchOn and switchOff simply call 
	 * a single method from java.awt.CardLayout therefore making them untestable in this package
	 * unless perhaps a gui window was generated. However in the scope of junit this is a wasteful
	 * and error prone venture.
	 */
	@Test
	public void coverage(){
		mockpanel.setKeyboardShown(true);
		mockpanel.switchOff();
		mockpanel.switchOn();
	}

}
