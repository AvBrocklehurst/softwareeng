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
import simori.FunctionButton;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.Mode;
import simori.Modes.NetworkMaster;
import simori.Modes.NetworkSlave;
import simori.Modes.PerformanceMode;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

/**
 * A class to test an implementation of the abstract class
 * Mode for Mode's general functionality.
 * 
 * @author James
 * @author Jurek
 * @version 1.0.2
 * @see Mode.java
 *
 */
public class TestMode{
	
	private SimoriJFrame testgui;
	private MatrixModel testmodel;
	private TesterMode testermode;
	private NetworkMaster testmaster;
	private NetworkSlave testslave;
	private QwertyKeyboard keyboard;
	private MockModeController mockcontroller;
	private FunctionButtonEvent testfbevent;
	private FunctionButton testfb;
	private FunctionButton testfb2;
	private FunctionButtonEvent testfbevent2;
	private FunctionButton testfb3;
	private FunctionButtonEvent testfbevent3;
	private AudioFeedbackSystem testaudio;
	private MIDISoundSystem testmidi;
	
	/**
	 * An implementation of Mode with method overrides to 
	 * allow thorough testing of Mode.
	 * 
	 * @author James
	 * @author Jurek
	 * @version 1.0.1
	 * @see Mode
	 *
	 */
	private class TesterMode extends Mode{

		public TesterMode(ModeController controller) {
			super(controller);
		}

		@Override
		public void onGridButtonPress(GridButtonEvent e) {}
		
		@Override 
		public ModeController getController(){
			return super.getController();
		}
		
		@Override
		public SimoriGui getGui(){
			return super.getGui();
		}
		
		@Override
		public MatrixModel getModel(){
			return super.getModel();
		}
		
		@Override 
		public byte getDisplayLayer(){
			return super.getDisplayLayer();
		}

		
	}
	
	@Before
	public void setUp() throws SimoriNonFatalException, IOException{
		keyboard = new QwertyKeyboard((byte)16,(byte)16);
		testgui = new SimoriJFrame(keyboard);
		testfb = FunctionButton.OK;
		testfb2 = FunctionButton.L1;
		testfb3 = FunctionButton.ON;
		testfbevent = new FunctionButtonEvent(testgui, testfb);
		testfbevent2 = new FunctionButtonEvent(testgui, testfb2);
		testfbevent3 = new FunctionButtonEvent(testgui, testfb3);
		testmodel = new MatrixModel(16, 16);
		testmidi = new MIDISoundSystem(false);
		testaudio = new AudioFeedbackSystem(testmidi, testmodel);
		mockcontroller = new MockModeController(testgui, testmodel, testaudio, 20160);
		testslave = new NetworkSlave(0, mockcontroller);
		testmaster = new NetworkMaster(0, mockcontroller, testslave);
		testermode = new TesterMode(mockcontroller);
	}
	
	@After
	public void tearDown(){
		keyboard = null;
		testgui = null;
		testfbevent = null;
		testfbevent2 = null;
		testfbevent3 = null;
		testfb = null;
		testfb2 = null;
		testfb3 = null;
		testmodel = null;
		mockcontroller = null;
		testslave.switchOff();
		testslave = null;
		testmaster.stopRunning();
		testmaster = null;
		testermode = null;
		testaudio = null;
		testmidi = null;
	}
	
	@Test
	public void test_getModeController(){
		assertThat("The retrieved object is not a mode controller!", testermode.getController(), instanceOf(ModeController.class));
	}
	
	@Test
	public void test_getGui(){
		assertThat("The retrieved object is not a SimoriGui!", testermode.getGui(), instanceOf(SimoriGui.class));
	}
	
	@Test
	public void test_getModel(){
		assertThat("The retrieved object is not a MatrixModel", testermode.getModel(), instanceOf(MatrixModel.class));
	}
	
	@Test
	public void test_getDisplayLayer(){
		testermode.getController().setDisplayLayer((byte)0);
		assertEquals("That is not the correct layer", (byte)0, testermode.getDisplayLayer());
	}
	
	@Test
	public void test_onFunctionButtonPress_Ok(){
		try {
			testermode.onFunctionButtonPress(testfbevent);
			assertThat("The mode was not set to performance mode", mockcontroller.getMode(), instanceOf(PerformanceMode.class));
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	@Test
	public void test_onFunctionButtonPress_On(){
		try {
			testermode.onFunctionButtonPress(testfbevent3);
			assertEquals("Simori was not turned off as expected", false, testermode.getController().isOn());
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	@Test
	public void test_onFunctionButtonPress_Changer(){
		try {
			testermode.onFunctionButtonPress(testfbevent2);
			assertThat("Mode was not changed to Change Voice as expected!", mockcontroller.getMode(), instanceOf(Mode.class));
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}

}
