package simori.Tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.ModeController;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.Mode;
import simori.Modes.NetworkMaster;
import simori.Modes.NetworkSlave;
import simori.Modes.PerformanceMode;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

/**
 * A class to test ModeController.
 * 
 * @author James
 * @author Jurek
 * @version 1.0.2
 * @see ModeController.java
 *
 */
public class TestModeController{

	private SimoriJFrame testgui;
	private MatrixModel testmodel;
	private MockModeController mockcontroller;
	private QwertyKeyboard keyboard;
	private NetworkSlave testslave;
	private NetworkMaster testmaster;
	private AudioFeedbackSystem testaudio;
	private MIDISoundSystem testmidi;
	
	@Before
	public void setUp() throws SimoriNonFatalException, IOException{
		keyboard = new QwertyKeyboard((byte)16, (byte)16);
		testgui = new SimoriJFrame(keyboard);
		testmodel = new MatrixModel(16, 16);
		testslave = new NetworkSlave(0, mockcontroller);
		testmaster = new NetworkMaster(0, mockcontroller, testslave);
		testmidi = new MIDISoundSystem();
		testaudio = new AudioFeedbackSystem(testmidi, testmodel);
		mockcontroller = new MockModeController(testgui, testmodel, testaudio, 0);
	}
	
	@After
	public void tearDown(){
		testmaster.stopRunning();
		testslave.switchOff();
		testslave = null;
		keyboard = null;
		testgui = null;
		testmodel = null;
		mockcontroller = null;
		testaudio = null;
		testmidi = null;
	}
	
	@Test 
	public void test_getModel(){
		assertThat("The returned object is not a model", mockcontroller.getModel(), instanceOf(MatrixModel.class));
	}
	
	@Test
	public void test_getGui(){
		assertThat("The returned object is not a valid gui", mockcontroller.getGui(), instanceOf(SimoriJFrame.class));
	}
	
	@Test
	public void test_isOn(){
		assertEquals("The simori-on should be on!", true, mockcontroller.isOn());
	}
	
	@Test
	public void test_getPort(){
		assertEquals("The port was not returned correctly!", 0, mockcontroller.getPort());
	}
	
	@Test
	public void test_get_set_DisplayLayer(){
		mockcontroller.setDisplayLayer((byte)5);
		assertEquals("Not the correct display layer number!", 5, mockcontroller.getDisplayLayer());
	}
	
	
	@Test
	public void test_setMode(){
		try {
			mockcontroller.setMode(new PerformanceMode(mockcontroller));
			assertThat("The mode was not retrieved correctly", mockcontroller.getMode(), instanceOf(Mode.class));
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	@Test
	public void test_setOnFalse(){
		try {
			mockcontroller.setComponentsToPowerToggle(testmodel); //need a component to toggle
			mockcontroller.setOn(false, false);
			assertEquals("The simori was not turned off", false, mockcontroller.getOn());
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	@Test
	public void test_setOnFalse_noToggles(){
		try {
			mockcontroller.setOn(false, false);
			assertEquals("The simori was not turned off", false, mockcontroller.getOn());
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	@Test
	public void test_setOn(){
		try {
			mockcontroller.setComponentsToPowerToggle(testmodel);   
			mockcontroller.setFalseOn();
			mockcontroller.setOn(true, false);
			assertEquals("The simori was not turned on", true, mockcontroller.getOn());
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}

}
