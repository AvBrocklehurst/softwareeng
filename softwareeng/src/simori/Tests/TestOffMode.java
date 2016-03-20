package simori.Tests;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import simori.FunctionButton;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
//import simori.Exceptions.InvalidCoordinatesException;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.NetworkMaster;
import simori.Modes.NetworkSlave;
//import simori.Modes.OffMode;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

/**
 * A class to test OffMode.
 * 
 * @author James
 * @author Jurek
 * @version 1.0.1B
 * @see OffMode.java
 * @deprecated since OffMode no longer exists
 * 			   as such all errors are commented out
 * 			   instead of being fixed
 *
 */
public class TestOffMode {
	
	private GridButtonEvent gb;
	private FunctionButton fb;
	private FunctionButtonEvent fbevent;
	private SimoriJFrame testgui;
	private QwertyKeyboard keyboard;
//	private OffMode testoffmode;
	private ModeController mockcontroller;
	private MatrixModel testmodel;
	private NetworkMaster testmaster;
	private NetworkSlave testslave;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setUp() throws SimoriNonFatalException, IOException{
		keyboard = new QwertyKeyboard((byte)16,(byte)16);
		testgui = new SimoriJFrame(keyboard); 
		gb = new GridButtonEvent(testgui, 0, 0);
		fb = FunctionButton.ON;
		fbevent = new FunctionButtonEvent(testgui, fb);
		testmodel = new MatrixModel(16,16);
		testslave = new NetworkSlave(0, mockcontroller);
		testmaster = new NetworkMaster(0, mockcontroller, testslave);
//		mockcontroller = new MockModeController(testgui, testmodel, 0, testmaster);
//		testoffmode = new OffMode(mockcontroller);
	}
	
	@After
	public void tearDown(){
		testslave.switchOff();
		testmaster.stopRunning();
		keyboard = null;
		testgui = null;
		gb = null;
		fb = null;
		fbevent = null;
		testmodel = null;
		testslave = null;
		testmaster = null;
		mockcontroller = null;
//		testoffmode = null;
	}
	
	
	
//	@Test
//	public void call_onGridButtonPress() throws InvalidCoordinatesException{
//		testoffmode.onGridButtonPress(gb);   //method does nothing, coverage call
//	}
	
	@Test
	public void test_onFunctionButtonPress(){
//		testoffmode.onFunctionButtonPress(fbevent);
		assertEquals("The simori was not changed!", true, mockcontroller.isOn());
	}
}
