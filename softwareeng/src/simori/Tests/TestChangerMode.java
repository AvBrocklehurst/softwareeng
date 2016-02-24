package simori.Tests;

import static org.junit.Assert.*;
import static simori.FunctionButton.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import simori.ChangerMode;
import simori.ChangerMode.Changer;
import simori.ChangerMode.Setting;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;
import simori.SwingGui.SimoriJFrame;


/**
 * 
 * @author Adam
 * 
 * @author James
 * @version 1.0.0
 * 
 * The class that tests ChangerMode, the class
 * which facilitates the changing of modes.
 * 
 * @TODO Unfinished.
 *
 */
public class TestChangerMode {
	
	private ChangerMode testcmode;
	private ModeController testcontroller;
	private SimoriJFrame gui;
	private MatrixModel model;
	
	private GridButtonEvent testgb;
	private FunctionButtonEvent testfb;
	private GridButtonEvent failgb;
	
	private Setting testSetting;
	
	
	private Changer makeTestChanger() {
		return new Changer() {
		
			@Override
			public String getText(Setting s) {
				return null;
			}
			
			@Override
			public boolean doThingTo(ModeController controller) {
				return true; 
			}

			@Override
			public Setting getCurrentSetting() {
				return null;  
			}
		};
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	
	@Before 
	public void setUp(){
		gui = new SimoriJFrame(16,16);
		model = new MatrixModel(16,16);
		testcontroller = new ModeController(gui, model);
		testcmode = new ChangerMode(testcontroller, null, false, false);
		testgb = new GridButtonEvent(gui, 5, 5);
		failgb = new GridButtonEvent(gui,-3,-4);
		testfb = new FunctionButtonEvent(gui, OK);
		//testSetting = new Setting(3,4);
	}
	
	@After
	public void tearDown(){
		gui = null;
		model = null;
		testcontroller = null;
		testcmode = null;
		testgb = null;
		failgb = null;
		testfb = null;
	}
	
	/*@Test
	public void test_onGridButtonPress_Coordinates() throws InvalidCoordinatesException{
		testcmode.onGridButtonPress(testgb);
		assertEquals("Coordinates were not set correctly on press!", 5, testgb.getX());
		
	}
	
	@Test
	public void test_onGridButtonPress_InvalidCoordinatesException() throws InvalidCoordinatesException{
		thrown.expect(InvalidCoordinatesException.class);
		testcmode.onGridButtonPress(failgb);
	}
	
	@Test
	public void test_onFunctionButtonPress_ON(){
		testcmode.onFunctionButtonPress(testfb);
		boolean on = testcontroller.isOn();
		assertEquals("The controller was not turned on!", true, on);
	}
	
	@Test
	public void test_onFunctionButtonPress_OK(){
		
	}
	
	@Test
	public void test_setInitialGrid(){
		
		
	}
	
	@Test
	public void test_GetX(){
		
	}
	
	*/
	
}

