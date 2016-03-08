package simori.Exceptions;
/**
 * 
 * @author Josh
 * @version 1.0.0
 */
public class InvalidMIDIChannelException extends Exception {
    
	public InvalidMIDIChannelException() {}

    //Constructor that accepts a message
    public InvalidMIDIChannelException(String message)
    {
       super(message);
    }
}
