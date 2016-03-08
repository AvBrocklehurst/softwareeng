package simori.Exceptions;
/**
 * 
 * @author Josh
 * @version 1.0.0
 */
public class InvalidMIDIInstrumentException extends Exception {
    
	public InvalidMIDIInstrumentException() {}

    //Constructor that accepts a message
    public InvalidMIDIInstrumentException(String message)
    {
       super(message);
    }
}
