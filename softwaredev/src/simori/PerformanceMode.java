package simori;

import simori.SimoriGuiEvents.GridButtonEvent;
import simori.SimoriGuiEvents.GridButtonListener;
import simori.Exceptions.InvalidCoordinatesException;

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
	private Layer currentLayer;

	public PerformanceMode(int loopspeed, int looppoint /*voice, velocity*/){
		this.loopspeed = loopspeed;
		this.looppoint = looppoint;
		
	}
	
	
	public void onGridButtonPress(GridButtonEvent e) throws InvalidCoordinatesException{
		
		int x = e.getX();
		int y = e.getY();
		currentLayer = getTempLayer();
		currentLayer.updateButton(x, y);
		
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
	
	public Layer getCurrentLayer(){
		return currentLayer;
	}

}
