package simori;

import simori.SimoriGuiEvents.GridButtonEvent;
import simori.SimoriGuiEvents.GridButtonListener;

/**
 * The class for Performance Mode, extending
 * the abstract class Mode.
 * 
 * @author James
 * @version 1.0.0
 */

public class PerformanceMode extends Mode implements GridButtonListener {
	
	
	private int loopspeed;
	private int looppoint;

	public PerformanceMode(int loopspeed, int looppoint /*voice, velocity*/){
		this.loopspeed = loopspeed;
		this.looppoint = looppoint;
		
	}
	
	/**
	 * Gets the current mode name.
	 * 
	 * @author James
	 * @version 1.0.0
	 */
	public String getMode(){
		return currentMode;
	}
	
	public void onGridButtonPress(GridButtonEvent e){
		
	}

}
