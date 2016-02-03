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
