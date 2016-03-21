package simori.Tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.FunctionButton;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.ChangerMode;
import simori.Modes.ChangerMode.Setting;
import simori.Modes.ChangerModeFactory;
import simori.Modes.NetworkMaster;
import simori.Modes.NetworkSlave;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

/**
 * A class to test ChangerModeFactory.
 * 
 * @author James
 * @author Jurek
 * @version 1.0.1
 * @see ChangerModeFactory.java
 *
 */
public class TestChangerModeFactory {
	
	/**
	 * Mock ChangerMode so that the Changer can be extracted
	 * @author Jurek
	 */
	private class MockChangerMode extends ChangerMode {
		public MockChangerMode(ModeController controller, Changer changer, boolean verticalLine,
				boolean horizontalLine) {
			super(controller, changer, verticalLine, horizontalLine);
		}

		public Changer getChanger() {
			return changer;
		}
	}
	
	private MockModeController mockcontroller;
	private FunctionButton fb;
	private FunctionButtonEvent fbevent;
	private MatrixModel testmodel;
	private SimoriJFrame testgui;
	private NetworkMaster testmaster;
	private NetworkSlave testslave;
	private QwertyKeyboard keyboard;
	private AudioFeedbackSystem testaudio;
	private MIDISoundSystem testmidi;
	
	@Before
	public void setUp() throws SimoriNonFatalException, IOException{
		keyboard = new QwertyKeyboard((byte)16,(byte)16);
		testgui = new SimoriJFrame(keyboard);
		testmodel = new MatrixModel(16, 16);
		testslave = new NetworkSlave(0, mockcontroller);
		testmaster = new NetworkMaster(0, mockcontroller, testslave);
		testmidi = new MIDISoundSystem();
		testaudio = new AudioFeedbackSystem(testmidi, testmodel);
		mockcontroller = new MockModeController(testgui, testmodel, testaudio, 20160);
		fb = FunctionButton.L1;
		fbevent = new FunctionButtonEvent(testgui, fb);
	}
	
	@After 
	public void tearDown(){
		testslave.switchOff();
		testmaster.stopRunning();
		keyboard = null;
		testgui = null;
		testmodel = null;
		testslave = null;
		testmaster = null;
		mockcontroller = null;
		fb = null;
		fbevent = null;
		testaudio = null;
		testmidi = null;
	}

	@Test
	public void test_getChanger(){
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertThat("A ChangerMode was not returned as expected", cmode, instanceOf(ChangerMode.class));
	}
	
	@Test 
	public void test_getChanger_null(){
		fb = FunctionButton.OK;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertNull("The default case of the switch statement was not evaluated!", cmode);
	}

	@Test 
	public void testGetChangerL1() throws SimoriNonFatalException{
		fb = FunctionButton.L1;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertNull("The default case of the switch statement was not evaluated!", cmode);
	}
	

	@Test 
	public void testGetChangerL2(){
		fb = FunctionButton.L2;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertNull("The default case of the switch statement was not evaluated!", cmode);
	}

	@Test 
	public void testGetChangerL3(){
		fb = FunctionButton.L3;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertNull("The default case of the switch statement was not evaluated!", cmode);
	}

	@Test 
	public void testGetChangerL4(){
		fb = FunctionButton.L4;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertNull("The default case of the switch statement was not evaluated!", cmode);
	}

	@Test 
	public void testGetChangerR1(){
		fb = FunctionButton.R1;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertNull("The default case of the switch statement was not evaluated!", cmode);
	}

	@Test 
	public void testGetChangerR2(){
		fb = FunctionButton.R2;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertNull("The default case of the switch statement was not evaluated!", cmode);
	}

	@Test 
	public void testGetChangerR3(){
		fb = FunctionButton.R3;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertNull("The default case of the switch statement was not evaluated!", cmode);
	}

	@Test 
	public void testGetChangerR4(){
		fb = FunctionButton.R4;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertNull("The default case of the switch statement was not evaluated!", cmode);
	}
	
	@Test
	public void testMakeLayerChanger_getText() throws SimoriNonFatalException{
		fb = FunctionButton.L1;

	}
	
}
