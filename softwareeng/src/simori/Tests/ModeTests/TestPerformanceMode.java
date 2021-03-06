package simori.Tests.ModeTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui.GridButtonEvent;
import simori.Modes.PerformanceMode;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

/**
 * The class for testing Performance Mode - unfinished.
 * @author James
 * @author Jurek
 * @version 1.1.2
 * @see simori.Modes.PerformanceMode
 *
 */

public class TestPerformanceMode {
	
	private MatrixModel testmodel;
	private SimoriJFrame testgui;
	private ModeController mockcontroller;
	private PerformanceMode testpm;
	private GridButtonEvent testgb;
	private QwertyKeyboard keyboard;
	private AudioFeedbackSystem testaudio;
	private MIDISoundSystem testmidi;
	
	
	@Before
	public void setUp() {
		keyboard = new QwertyKeyboard((byte)16,(byte)16);
		testmodel = new MatrixModel(16, 16);
		testgui = new SimoriJFrame(keyboard);
		testmidi = new MIDISoundSystem(false);
		testaudio = new AudioFeedbackSystem(testmidi, testmodel);
		mockcontroller = new ModeController(testgui, testmodel, testaudio, 20160);
		mockcontroller.setMode(new PerformanceMode(mockcontroller));
		testpm = new PerformanceMode(mockcontroller);
		testgb = new GridButtonEvent(testgui, 5, 5);
	}
	
	@After
	public void tearDown(){
		testmidi.switchOff();
		keyboard = null;
		testmodel = null;
		testgui = null;
		mockcontroller = null;
		testpm = null;
		testaudio = null;
		testmidi = null;
	}
	
	@Test
	public void testOnGridButtonPress() {
		testpm.onGridButtonPress(testgb);
		boolean changedgridcoords = testpm.getModifiedGrid()[5][5];
		assertEquals("The grid button was not inverted!", true, changedgridcoords);	
	}
	
	@Test
	public void test_onGridButtonPress_false() {
		testpm.onGridButtonPress(testgb);
		testpm.onGridButtonPress(testgb); //invert to false
		boolean changedgridcoords = testpm.getModifiedGrid()[5][5];
		assertEquals("The grid button was not inverted back to false!", false, changedgridcoords); //invert to true
	}
	
	
	
	@Test
	public void test_tickerLight() {
		testpm.tickerLight((byte)0); 
		boolean tickeredgridcoords = testpm.getModifiedGrid()[5][0];
		assertEquals("The values are grid index grid[5][0] were not set to true by the ticker", true, tickeredgridcoords);
	}
	
	@Test
	public void test_makeGridCopy(){
		testpm.makeGridCopy((byte)0);
		boolean[][] finalgrid = testpm.getModifiedGrid();
		assertEquals("The grid was not copied correctly!", false, finalgrid[5][5]);
		
	} 
	
	
}
