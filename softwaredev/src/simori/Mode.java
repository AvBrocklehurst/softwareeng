package simori;

/**
 * An abstract class defining methods for general
 * use in the Mode subclasses.
 * 
 * @author James
 * @version 1.0.0
 */
public abstract class Mode {
	
	
	/**
	 * Changes the current mode to a specified one.
	 * 
	 * @author James
	 * @version 1.0.0
	 */
	public void setMode(String button){
		
		switch(button){
		
		case "L1" : mode to change voice
					break;
		
		case "L2" : mode to change velocity
					break;
		
		case "L3" : mode to loop speed
					break;
		
		case "L4" : mode to loop point
					break;
		
		case "R1" : mode to change layer mode
					break;
		
		case "R2" : mode to save configuration mode
					break;
		
		case "R3" : mode to load configuration mode
					break;
		
		case "R4" : mode to Master/Slave mode
					break;
		
		case "OK" : mode to performance mode
					break;
		}
	}
	
	public void tick(){
		
	}
	

}
