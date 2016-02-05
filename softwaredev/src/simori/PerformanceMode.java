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
 * @see Mode
 */

public class PerformanceMode extends Mode implements GridButtonListener {
	
	private int loopspeed;
	private int looppoint;
	private Layer currentLayer;    //current layer to be modified
	private Simori simori;

	/**
	 * Constructor for Performance Mode. In performance mode the ticker loops
	 * at a loopspeed to a certain looppoint. Notes are played depending on the
	 * layer. They are played with a certain voice and at a certain velocity.
	 * 
	 * @param loopspeed
	 * @param looppoint
	 * @author James
	 * @version 1.0.0
	 */
	public PerformanceMode(Simori simori, int loopspeed, int looppoint /*voice, velocity*/){
		this.loopspeed = loopspeed;
		this.looppoint = looppoint;
		this.simori = simori;
		
	}
	
	/**
	 * When a grid button is pressed in Performance mode, this method updates the current 
	 * layer by inverting the true/false value at the coordinates of press on the GUI.
	 * 
	 * @param e (A GridButtonEvent)
	 * @author James
	 * @see SimoriGuiEvents$GridButtonEvent, SimoriGuiEvents.GridButtonListener, Layer.updateButton
	 * @version 1.1.0
	 */
	public void onGridButtonPress(GridButtonEvent e) throws InvalidCoordinatesException{
		
		int x = e.getX();            //grid position of button press
		int y = e.getY();  
		SimoriGui sc = e.getSource();
		currentLayer = getTempLayer();     //get the temp layer to set as currently working layer
		
		currentLayer.updateButton((byte) x, (byte) y);
		simori.getModel().updateButton((byte) 0, (byte) x, (byte) y);   //update the data structure by inverting button at Gui position x,y
		sc.setPattern(currentLayer);
	}
	
	/**
	 * Instructs the GUI what buttons to naturally light on passing of the
	 * clock. Modifies the layer with the buttons needed. Whole layer can be
	 * obtained from getCurrentLayer().
	 * 
	 * @param col
	 * @author James
	 * @throws InvalidCoordinatesException
	 * @see Layer.updateButton, simori.Exceptions.InvalidCoordinatesException
	 * @version 1.0.0
	 */
	public void tickerLight(byte col) throws InvalidCoordinatesException{
		
		currentLayer = getTempLayer();
		currentLayer.updateButton(col, (byte) 0);
		currentLayer.updateButton(col, (byte) 5);
		currentLayer.updateButton(col, (byte) 10);   //data passed to GUI and structure through MatrixModel.updateButton()
		currentLayer.updateButton(col, (byte) 15);	//positions of lit buttons due to the clock	
		simori.getGui().setPattern(currentLayer);
	}
	
	
	/**
	 * Gets the current mode name.
	 * 
	 * @author James
	 * @see Mode.currentMode
	 * @version 1.0.0
	 */
	public String getModeName(){
		return currentModeName;
	}
	
	
	/**
	 * Gets the current layer.
	 * 
	 * @author James
	 * @return Layer
	 * @see currentLayer
	 * @version 1.0.0
	 */
	public Layer getCurrentLayer(){
		return currentLayer;
	}

}
