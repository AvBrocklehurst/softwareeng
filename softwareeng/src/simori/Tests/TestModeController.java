package simori.Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui;


public class TestModeController {
	
	private SimoriGui testgui;
	private MatrixModel testmodel;
	private ModeController testcontroller;
	private byte currentColumn;
	
	
	@Before
	public void setUp(){
		testmodel = new MatrixModel(16, 16);
		testcontroller = new ModeController(testgui, testmodel);
	}
	
	@After
	public void tearDown(){
		testmodel = null;
		testcontroller = null;
	}
	
	@Test
	public void testTickThrough(){
		testmodel.incrementColumn();
		System.out.println(testmodel.getCurrentColumn());
		//testcontroller.tickThrough((byte)2);
	}

}
