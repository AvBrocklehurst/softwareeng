package simori.Tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;
import simori.Exceptions.KeyboardException;
import simori.Modes.NetworkMaster;
import simori.Modes.NetworkSlave;
import simori.Modes.PerformanceMode;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;



/**
 * The class for testing Performance Mode - unfinished.
 * @author James
 * @version 1.1.0
 * @see simori.Modes.PerformanceMode
 *
 */

public class TestPerformanceMode {
	
	private MatrixModel testmodel;
	private SimoriJFrame testgui;
	private ModeController testcontroller;
	private PerformanceMode testpm;
	private GridButtonEvent testgb;
	private QwertyKeyboard keyboard;
	
	
	@Before
	public void setUp() throws KeyboardException, IOException{
		keyboard = new QwertyKeyboard((byte)16,(byte)16);
		testmodel = new MatrixModel(16, 16);
		testgui = new SimoriJFrame(keyboard);
		testcontroller = new ModeController(testgui, testmodel, 0);
		testcontroller.setMode(new PerformanceMode(testcontroller));
		testpm = new PerformanceMode(testcontroller);
		testgb = new GridButtonEvent(testgui, 5, 5);
	}
	
	@After
	public void tearDown(){
		keyboard = null;
		testmodel = null;
		testgui = null;
		testcontroller = null;
		testpm = null;
	}
	
	@Test
	public void testOnGridButtonPress() throws InvalidCoordinatesException{
		testpm.onGridButtonPress(testgb);
		boolean changedgridcoords = testpm.getModifiedGrid()[5][5];
		assertEquals("The grid button was not inverted!", true, changedgridcoords);
		
		
	}
	
	@Test
	public void test_onGridButtonPress_false() throws InvalidCoordinatesException{
		testpm.onGridButtonPress(testgb); //invert to true
		testpm.onGridButtonPress(testgb); //invert to false
		boolean changedgridcoords = testpm.getModifiedGrid()[5][5];
		assertEquals("The grid button was not inverted back to false!", false, changedgridcoords);
	}
	
	
	@Test
	public void test_tickerLight() throws InvalidCoordinatesException{
		
		testpm.tickerLight((byte)0); 
		boolean tickeredgridcoords = testpm.getModifiedGrid()[5][0];
		assertEquals("The values are grid index grid[5][0] were not set to true by the ticker", true, tickeredgridcoords);
	}
	
	@Test
	public void test_makeGridCopy(){
		boolean[][] initialgrid = testpm.getModifiedGrid();
		testpm.makeGridCopy((byte)0);
		boolean[][] finalgrid = testpm.getModifiedGrid();
		assertEquals("The grid was not copied correctly!", false, finalgrid[5][5]);
		
	} 
	
	
}
