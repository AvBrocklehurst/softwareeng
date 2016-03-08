package simori.Exceptions;
/**
 * 
 * @author Josh
 * @version 1.0.0
 */
public class InvalidMIDIVelocityException extends Exception {
    
	public InvalidMIDIVelocityException() {}

    //Constructor that accepts a message
    public InvalidMIDIVelocityException(String message)
    {
       super(message);
    }
}
