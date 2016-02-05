package simori;

import simori.SimoriGuiEvents.FunctionButtonEvent;
import simori.SimoriGuiEvents.FunctionButtonListener;
import simori.SimoriGuiEvents.GridButtonListener;

import static simori.SimoriGuiEvents.FunctionButton;

/**
 * An abstract class defining methods for general
 * use in the Mode subclasses. Mode handles the logic
 * of a given Function Button press.
 * 
 * @author James
 * @version 1.0.0
 */
public abstract class Mode implements FunctionButtonListener, GridButtonListener {
	
	private Layer tempLayer = new Layer();     //default layer setting between modes
	public String currentModeName;         //keep track of current mode name
	private MatrixModel model;
	
	/**
	 * Gets the function button pressed and the source Gui and then
	 * changes the current mode based on a specified FunctionButton.
	 * 
	 * @author James
	 * @version 1.0.1
	 */
	public void onFunctionButtonPress(FunctionButtonEvent e){
		
		FunctionButton fb = e.getFunctionButton();
		SimoriGui sg = e.getSource();       //get the source object and the button pressed (from enum Function Button)
		
		
		switch(fb){
		
		case L1 : //TODO(next sprint) mode to change voice
					break;
		
		case L2 : //TODO(next sprint) mode to change velocity
					break;
		
		case L3 : //TODO(next sprint) mode to loop speed
					break;
		
		case L4 : //TODO(next sprint) mode to loop point
					break;
		
		case R1 : //TODO(next sprint) mode to change layer mode
					break;
		
		case R2 : //TODO(next sprint) mode to save configuration mode
					break;
		
		case R3 : //TODO(next sprint) mode to load configuration mode
					break;
		
		case R4 : //TODO(next sprint) mode to Master/Slave mode
					break;
		
		case OK :	//sg.setMode(new PerformanceMode(model, 1, 1));      //change source, the Gui to performance mode
					currentModeName = "Performance Mode";                //update tracker
					break;
		
		case POWER: //sg.setMode(new PerformanceMode(model, 1,1));
					currentModeName = "Performance Mode";
					break;
		}
	}
	
	/**
	 * Gets the temporary empty layer which is a default setting
	 * between modes. On invoking certain modes given lights are 
	 * lit.
	 * 
	 * @author James
	 * @return Layer
	 * @see tempLayer
	 */
	public Layer getTempLayer(){
		return tempLayer;
	}
	

}
