package simori.Tests.GuiTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.FunctionButton;
import simori.Exceptions.KeyboardException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.Lcd;
import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.SimoriEdgeBar;
import simori.SwingGui.SimoriJFrame;

/**
 * 
 * @author Josh
 * @version 1.0.3
 * 
 * Class that tests the SimoriEdgeBar
 */
public class TestSimoriEdgeBar {
	QwertyKeyboard keyBoard;
	SimoriJFrame gui;
	OnPressListenerMaker maker;

	@Before
	public void setUp() throws Exception {
		keyBoard = new QwertyKeyboard((byte)16, (byte)16);
		gui = new SimoriJFrame(keyBoard);
		maker = new OnPressListenerMaker(gui);
	}

	@After
	public void tearDown() throws Exception {
		keyBoard = null;
		gui = null;
		maker = null;
	}

	@Test
	public void testWithLcd() throws KeyboardException, InterruptedException {
		SimoriEdgeBar test = new SimoriEdgeBar(true, true, maker, FunctionButton.L1, FunctionButton.L2, FunctionButton.L3, FunctionButton.L4);
		assertNotNull(test.getLcd());
	}
	
	@Test
	public void testWithoutLcd() throws KeyboardException, InterruptedException {
		SimoriEdgeBar test = new SimoriEdgeBar(true, false, maker, FunctionButton.L1, FunctionButton.L2, FunctionButton.L3, FunctionButton.L4);
		assertNull(test.getLcd());
	}

}
