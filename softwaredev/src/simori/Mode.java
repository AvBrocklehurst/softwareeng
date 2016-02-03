package simori;

import simori.SimoriGuiEvents.FunctionButtonEvent;
import simori.SimoriGuiEvents.FunctionButtonListener;
import static simori.SimoriGuiEvents.FunctionButton;

/**
 * An abstract class defining methods for general
 * use in the Mode subclasses. Mode handles the logic
 * of a given Function Button press.
 * 
 * @author James
 * @version 1.0.0
 */
public abstract class Mode implements FunctionButtonListener {
	
	Layer tempLayer = new Layer();
	String currentMode;
	
	
	/**
	 * Changes the current mode based on a specified 
	 * FunctionButton.
	 * 
	 * @author James
	 * @version 1.0.0
	 */
	public void onFunctionButtonPress(FunctionButtonEvent e){
		
		FunctionButton fb = e.getFunctionButton();
		
		
		switch(fb){
		
		case L1 : mode to change voice
					break;
		
		case L2 : mode to change velocity
					break;
		
		case L3 : mode to loop speed
					break;
		
		case L4 : mode to loop point
					break;
		
		case R1 : mode to change layer mode
					break;
		
		case R2 : mode to save configuration mode
					break;
		
		case R3 : mode to load configuration mode
					break;
		
		case R4 : mode to Master/Slave mode
					break;
		
		case OK : mode to performance mode
					break;
		
		case POWER : ON/OFF 
					break;
		}
	}
	
	public void tick(int xcord){
		
	}
	

}
