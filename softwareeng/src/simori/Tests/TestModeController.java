package simori.Tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.ModeController;
import simori.Exceptions.KeyboardException;
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
 * @version 1.0.0
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
	
	@Before
	public void setUp() throws KeyboardException, IOException{
		keyboard = new QwertyKeyboard((byte)16, (byte)16);
		testgui = new SimoriJFrame(keyboard);
		testmodel = new MatrixModel(16, 16);
		testslave = new NetworkSlave(0, mockcontroller);
		testmaster = new NetworkMaster(0, mockcontroller, testslave);
		mockcontroller = new MockModeController(testgui, testmodel, 0, testmaster);
	}
	
	@After
	public void tearDown(){
		keyboard = null;
		testgui = null;
		testmodel = null;
		mockcontroller = null;
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
		mockcontroller.setMode(new PerformanceMode(mockcontroller));
		assertThat("The mode was not retrieved correctly", mockcontroller.getMode(), instanceOf(Mode.class));
	}
	
	@Test
	public void test_setOnFalse(){
		mockcontroller.setComponentsToPowerToggle(testmodel); //need a component to toggle
		mockcontroller.setOn(false);
		assertEquals("The simori was not turned off", false, mockcontroller.getOn());
	}
	
	@Test
	public void test_setOnFalse_noToggles(){
		mockcontroller.setOn(false);
		assertEquals("The simori was not turned off", false, mockcontroller.getOn());
	}
	
	@Test
	public void test_setOn(){
		mockcontroller.setComponentsToPowerToggle(testmodel);   
		mockcontroller.setFalseOn();
		mockcontroller.setOn(true);
		assertEquals("The simori was not turned on", true, mockcontroller.getOn());
	}

}
