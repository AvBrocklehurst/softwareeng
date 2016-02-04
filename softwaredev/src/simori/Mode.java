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
	
	private Layer tempLayer = new Layer();
	public String currentMode;
	
	
	/**
	 * Changes the current mode based on a specified 
	 * FunctionButton.
	 * 
	 * @author James
	 * @version 1.0.0
	 */
	public void onFunctionButtonPress(FunctionButtonEvent e){
		
		FunctionButton fb = e.getFunctionButton();
		SimoriGui sg = e.getSource();
		
		
		switch(fb){
		
		case L1 : //mode to change voice
					break;
		
		case L2 : //mode to change velocity
					break;
		
		case L3 : //mode to loop speed
					break;
		
		case L4 : //mode to loop point
					break;
		
		case R1 : //mode to change layer mode
					break;
		
		case R2 :// mode to save configuration mode
					break;
		
		case R3 :// mode to load configuration mode
					break;
		
		case R4 :// mode to Master/Slave mode
					break;
		
		case OK :	sg.setMode(new PerformanceMode(1, 1));
					currentMode = "Performance Mode";
					break;
		
		case POWER : //ON/OFF 
					break;
		}
	}
	
	public void tick(int xcord){
		
	}
	
	public Layer getTempLayer(){
		return tempLayer;
	}
	

}
