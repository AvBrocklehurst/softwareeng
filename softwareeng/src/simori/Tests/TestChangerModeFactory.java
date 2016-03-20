package simori.Tests;

import java.io.IOException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.FunctionButton;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.SimoriGui.FunctionButtonEvent;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.ChangerMode;
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
	
	
}
