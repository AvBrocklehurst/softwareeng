package simori.Tests;

import simori.MatrixModel;
import simori.SimoriGui;
import simori.Modes.Mode;

import simori.ModeController;

/**
 * A mock object for ModeController providing
 * getters and setters required in testing.
 *
 * @author James
 * @version 1.0.0
 * @see Modecontroller.java, TestModeController 
 */
public class MockModeController extends ModeController{
	
	public MockModeController(SimoriGui gui, MatrixModel model, int port) {
		super(gui, model, port);
	}
	
	public Mode getMode(){
		return mode;
	}
	
	public boolean getOn(){
		return on;
	}
	
	public void setFalseOn(){
		on = false;
	}
}
