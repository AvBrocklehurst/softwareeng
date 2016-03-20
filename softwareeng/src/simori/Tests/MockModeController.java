package simori.Tests;

import simori.AudioFeedbackSystem;
import simori.MatrixModel;
import simori.SimoriGui;
import simori.Modes.Mode;

import simori.ModeController;

/**
 * A mock object for ModeController providing
 * getters and setters required in testing.
 *
 * @author James
 * @author Jurek
 * @version 1.0.1
 * @see Modecontroller.java, TestModeController 
 */
public class MockModeController extends ModeController{
	
	public MockModeController(SimoriGui gui, MatrixModel model, AudioFeedbackSystem audio, int port) {
		super(gui, model, audio, port);
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
