package simori.Tests;

import static org.junit.Assert.assertEquals;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MIDISoundPlayer;
import simori.MatrixModel;
import simori.ModeController;
import simori.PerformanceMode;
import simori.Simori;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;

import simori.SwingGui.SimoriJFrame;



/**
 * The class for testing Performance Mode - unfinished.
 * @author Adam
 * @author James
 * @version 1.1.0
 * @see simori.PerformanceMode
 *
 */

public class TestPerformanceMode {
	
	MatrixModel model;
	SimoriJFrame gui;
	ModeController modes;
	PerformanceMode pm;
	
	
	
	@Before
	public void setUp(){
		
		MatrixModel model = new MatrixModel(16, 16);
		SimoriJFrame gui = new SimoriJFrame(16, 16);
		ModeController modes = new ModeController(gui, model);
		modes.setMode(new PerformanceMode(modes));
		pm = new PerformanceMode(modes);
	}
	
	@After
	public void tearDown(){
		model = null;
		gui = null;
		modes = null;
		
	}
	/*
	@Test
	public void testOnGridButtonPress() throws InvalidCoordinatesException{
		pm.onGridButtonPres);
		gui.butt
		boolean changedgridcoords = pm.getModifiedGrid()[2][3];
		assertEquals("The grid button was not inverted!", true, changedgridcoords);
		
		
	}
	
	@Test
	public void test_onGridButtonPress_false() throws InvalidCoordinatesException{
		
		testpm.onGridButtonPress(mockgb); //invert to true
		testpm.onGridButtonPress(mockgb); //invert to false
		boolean changedgridcoords = testpm.getModifiedGrid()[2][3];
		assertEquals("The grid button was not inverted back to false!", false, changedgridcoords);
	}
	
	@Test
	public void test_onGridButtonPress_notInverted() throws InvalidCoordinatesException{
		
		testpm.onGridButtonPress(mockgb);
		boolean changedgridcoords = testpm.getModifiedGrid()[5][6];
		assertEquals("The grid button should not be inverted!", false, changedgridcoords);
		
	}
	*/
	@Test
	public void test_tickerLight() throws InvalidCoordinatesException{
		
		pm.tickerLight((byte)0); 
		boolean tickeredgridcoords = pm.getModifiedGrid()[5][0];
		assertEquals("The values are grid index grid[5][0] were not set to true by the ticker", true, tickeredgridcoords);
	}
	
	@Test
	public void test_makeGridCopy(){
		boolean[][] initialgrid = pm.getModifiedGrid();
		assertEquals("The grid was not copied correctly!", false, initialgrid[2][3]);
		pm.makeGridCopy((byte)0);
		boolean[][] finalgrid = pm.getModifiedGrid();
		assertEquals("The grid was not copied correctly!", false, finalgrid[2][3]);
		
	} 
}
